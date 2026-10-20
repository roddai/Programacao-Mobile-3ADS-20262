package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etCep;
    private Button btnBuscar;
    private TextView tvLogradouro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Ajuste para telas modernas (notch/barras de sistema)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicialização dos componentes
        etCep = findViewById(R.id.etCep);
        btnBuscar = findViewById(R.id.btnBuscar);
        tvLogradouro = findViewById(R.id.tvLogradouro);

        btnBuscar.setOnClickListener(v -> {
            // Limpa o CEP: Remove qualquer coisa que não seja número (ex: 08450-350 vira 08450350)
            String cepLimpo = etCep.getText().toString().replaceAll("[^0-9]", "");

            if (cepLimpo.length() == 8) {
                consultarCepReal(cepLimpo);
            } else {
                etCep.setError("Digite os 8 números do CEP");
                Toast.makeText(this, "CEP inválido!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void consultarCepReal(String cep) {
        // Feedback visual imediato
        tvLogradouro.setText("Buscando dados na nuvem...");
        btnBuscar.setEnabled(false); // Desabilita o botão para evitar múltiplos cliques

        new Thread(() -> {
            try {
                // ViaCEP aceita HTTPS, que é mais estável
                URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setRequestMethod("GET");
                conexao.setConnectTimeout(8000); // 8 segundos de limite

                if (conexao.getResponseCode() == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                    StringBuilder resposta = new StringBuilder();
                    String linha;
                    while ((linha = reader.readLine()) != null) {
                        resposta.append(linha);
                    }
                    reader.close();

                    JSONObject json = new JSONObject(resposta.toString());

                    if (json.has("erro")) {
                        atualizarInterface("⚠️ CEP não encontrado!", true);
                    } else {
                        // Extração de todos os dados reais solicitados
                        String rua = json.optString("logradouro", "Não informado");
                        String bairro = json.optString("bairro", "Não informado");
                        String cidade = json.optString("localidade", "Não informado");
                        String uf = json.optString("uf", "N/A");

                        // Formatação final elegante
                        String resultadoFormatado = String.format(
                                "📍 Logradouro: %s\n🏠 Bairro: %s\n🏙️ Cidade: %s\n🏳️ Estado: %s",
                                rua, bairro, cidade, uf
                        );

                        atualizarInterface(resultadoFormatado, true);
                    }
                } else {
                    atualizarInterface("Erro de conexão com o servidor.", true);
                }

            } catch (Exception e) {
                Log.e("ERRO_CEP", "Falha ao buscar", e);
                atualizarInterface("Falha na rede. Verifique sua internet.", true);
            }
        }).start();
    }

    private void atualizarInterface(String mensagem, boolean reativarBotao) {
        runOnUiThread(() -> {
            tvLogradouro.setText(mensagem);
            if (reativarBotao) {
                btnBuscar.setEnabled(true);
            }
        });
    }
}