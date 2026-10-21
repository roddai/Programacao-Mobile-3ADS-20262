package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        // Inicialização dos componentes
        etCep = findViewById(R.id.etCep);
        btnBuscar = findViewById(R.id.btnBuscar);
        tvLogradouro = findViewById(R.id.tvLogradouro);

        btnBuscar.setOnClickListener(v -> {
            // Limpa o input: remove qualquer caractere que não seja número
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
        tvLogradouro.setText("Buscando dados (Rede Escola)...");
        btnBuscar.setEnabled(false);

        new Thread(() -> {
            HttpURLConnection conexao = null;
            try {
                // TÉCNICA SÊNIOR PARA FIREWALL DE ESCOLA:
                // 1. Usamos HTTP (porta 80) em vez de HTTPS para evitar bloqueios de certificados SSL ou inspeção profunda.
                // 2. Definimos User-Agent para simular um navegador comum (muitos firewalls barram scripts "puros").
                // 3. Aumentamos o Timeout para 20 segundos (redes escolares são instáveis).
                URL url = new URL("http://viacep.com.br/ws/" + cep + "/json/");
                conexao = (HttpURLConnection) url.openConnection();
                conexao.setRequestMethod("GET");
                conexao.setConnectTimeout(20000); 
                conexao.setReadTimeout(20000);
                
                // Simula um navegador Chrome no Windows
                conexao.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");

                int responseCode = conexao.getResponseCode();
                
                if (responseCode == HttpURLConnection.HTTP_OK) {
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
                        // Extração detalhada de todos os campos reais
                        String rua = json.optString("logradouro", "Não informado");
                        String bairro = json.optString("bairro", "Não informado");
                        String cidade = json.optString("localidade", "Não informado");
                        String uf = json.optString("uf", "N/A");

                        // Formatação final profissional
                        String resultadoFormatado = String.format(
                                "📍 Logradouro: %s\n🏠 Bairro: %s\n🏙️ Cidade: %s\n🏳️ Estado: %s",
                                rua, bairro, cidade, uf
                        );

                        atualizarInterface(resultadoFormatado, true);
                    }
                } else {
                    atualizarInterface("Erro no servidor da escola: " + responseCode, true);
                }

            } catch (java.net.UnknownHostException e) {
                Log.e("ERRO_CEP", "DNS fail", e);
                atualizarInterface("O firewall da escola bloqueou o acesso. Dica: Use o roteador do seu celular.", true);
            } catch (java.net.SocketTimeoutException e) {
                Log.e("ERRO_CEP", "Timeout", e);
                atualizarInterface("Tempo esgotado. A internet da escola está muito lenta.", true);
            } catch (Exception e) {
                Log.e("ERRO_CEP", "Falha geral", e);
                atualizarInterface("Falha na rede. Verifique seu Wi-Fi ou firewall.", true);
            } finally {
                if (conexao != null) conexao.disconnect();
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
