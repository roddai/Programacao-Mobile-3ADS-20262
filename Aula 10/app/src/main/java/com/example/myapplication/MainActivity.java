package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    private TextInputEditText etCep, etRua, etNumero, etBairro, etCidade, etEstado, etComplemento, etRegiao, etDdd;
    private Button btnBuscar;

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
        
        etRua = findViewById(R.id.etRua);
        etNumero = findViewById(R.id.etNumero);
        etBairro = findViewById(R.id.etBairro);
        etCidade = findViewById(R.id.etCidade);
        etEstado = findViewById(R.id.etEstado);
        etComplemento = findViewById(R.id.etComplemento);
        etRegiao = findViewById(R.id.etRegiao);
        etDdd = findViewById(R.id.etDdd);

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
        btnBuscar.setEnabled(false);
        btnBuscar.setText("Buscando...");

        new Thread(() -> {
            HttpURLConnection conexao = null;
            try {
                // TÉCNICA SÊNIOR PARA REDE DE ESCOLA: Usamos HTTP e User-Agent para evitar bloqueios.
                URL url = new URL("http://viacep.com.br/ws/" + cep + "/json/");
                conexao = (HttpURLConnection) url.openConnection();
                conexao.setRequestMethod("GET");
                conexao.setConnectTimeout(20000); 
                conexao.setReadTimeout(20000);
                
                conexao.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");

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
                        mostrarErroNoMainThread("⚠️ CEP não encontrado!");
                    } else {
                        // Extração de todos os dados reais
                        String rua = json.optString("logradouro", "");
                        String bairro = json.optString("bairro", "");
                        String cidade = json.optString("localidade", "");
                        String uf = json.optString("uf", "");
                        String complemento = json.optString("complemento", "");
                        String regiao = json.optString("regiao", "");
                        String ddd = json.optString("ddd", "");

                        runOnUiThread(() -> {
                            // Preenchendo os EditTexts com os dados da API
                            etRua.setText(rua);
                            etBairro.setText(bairro);
                            etCidade.setText(cidade);
                            etEstado.setText(uf);
                            etComplemento.setText(complemento);
                            etRegiao.setText(regiao);
                            etDdd.setText(ddd);
                            
                            // Foca no campo número para o usuário completar o cadastro
                            etNumero.requestFocus();
                            
                            btnBuscar.setEnabled(true);
                            btnBuscar.setText("buscar cep");
                        });
                    }
                } else {
                    mostrarErroNoMainThread("Erro no servidor: " + responseCode);
                }

            } catch (Exception e) {
                Log.e("ERRO_CEP", "Falha na rede", e);
                mostrarErroNoMainThread("Falha na rede. O firewall da escola pode estar bloqueando.");
            } finally {
                if (conexao != null) conexao.disconnect();
            }
        }).start();
    }

    private void mostrarErroNoMainThread(String mensagem) {
        runOnUiThread(() -> {
            Toast.makeText(MainActivity.this, mensagem, Toast.LENGTH_LONG).show();
            btnBuscar.setEnabled(true);
            btnBuscar.setText("buscar cep");
        });
    }
}
