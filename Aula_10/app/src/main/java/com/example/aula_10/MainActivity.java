package com.example.aula_10;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etCep;
    private MaterialButton btnBuscarCep;
    private LinearProgressIndicator loadingIndicator;
    private MaterialCardView cvResultCard;

    // Novos campos do formulário (TextInputEditText)
    private TextInputEditText etLogradouro, etNumero, etComplemento, etBairro, etCidade, etUf, etEstado, etRegiao, etDdd;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configuração de Insets para telas modernas (Edge-to-Edge)
        View mainLayout = findViewById(R.id.mainLayout);
        if (mainLayout != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainLayout, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        // Inicialização dos componentes da interface
        etCep = findViewById(R.id.etCep);
        btnBuscarCep = findViewById(R.id.btnBuscarCep);
        loadingIndicator = findViewById(R.id.loadingIndicator);
        cvResultCard = findViewById(R.id.cvResultCard);

        // Inicialização dos campos de resultado
        etLogradouro = findViewById(R.id.etLogradouro);
        etNumero = findViewById(R.id.etNumero);
        etComplemento = findViewById(R.id.etComplemento);
        etBairro = findViewById(R.id.etBairro);
        etCidade = findViewById(R.id.etCidade);
        etUf = findViewById(R.id.etUf);
        etEstado = findViewById(R.id.etEstado);
        etRegiao = findViewById(R.id.etRegiao);
        etDdd = findViewById(R.id.etDdd);

        btnBuscarCep.setOnClickListener(v -> {
            String cepInput = etCep.getText().toString().trim();
            // Limpeza do input: remove qualquer caractere que não seja número
            String cepClean = cepInput.replaceAll("[^0-9]", "");

            if (cepClean.length() == 8) {
                fecharTeclado();
                buscarEndereco(cepClean);
            } else {
                etCep.setError("Informe exatamente 8 números");
                Toast.makeText(this, "CEP inválido", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buscarEndereco(String cep) {
        // Feedback visual de carregamento
        runOnUiThread(() -> {
            loadingIndicator.setVisibility(View.VISIBLE);
            cvResultCard.setVisibility(View.GONE);
            btnBuscarCep.setEnabled(false);
        });

        executorService.execute(() -> {
            HttpURLConnection connection = null;
            try {
                // Alterado para http para evitar erro de SSLHandshakeException em alguns ambientes
                URL url = new URL("http://viacep.com.br/ws/" + cep + "/json/");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                connection.setRequestProperty("User-Agent", "Mozilla/5.0");

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    JSONObject json = new JSONObject(response.toString());

                    // API retorna erro=true caso o CEP não exista
                    if (json.optBoolean("erro", false)) {
                        mostrarMensagemErro("CEP não localizado na base de dados.");
                    } else {
                        // Extração completa dos campos da API ViaCEP
                        String logradouro = json.optString("logradouro", "");
                        String bairro = json.optString("bairro", "");
                        String localidade = json.optString("localidade", "");
                        String uf = json.optString("uf", "");
                        String estado = json.optString("estado", "");
                        String regiao = json.optString("regiao", "");
                        String ddd = json.optString("ddd", "");
                        String complementoApi = json.optString("complemento", "");

                        atualizarUiSucesso(logradouro, bairro, localidade, uf, estado, regiao, ddd, complementoApi);
                    }
                } else {
                    mostrarMensagemErro("Erro do Servidor: Código " + responseCode);
                }

            } catch (Exception e) {
                Log.e("CONSULTA_CEP", "Erro técnico: " + e.getMessage(), e);
                final String technicalError = e.getClass().getSimpleName() + ": " + e.getMessage();
                mostrarMensagemErro("Falha na consulta!\n" + technicalError);
            } finally {
                if (connection != null) connection.disconnect();
            }
        });
    }

    private void atualizarUiSucesso(String logradouro, String bairro, String cidade, String uf, String estado, String regiao, String ddd, String complemento) {
        runOnUiThread(() -> {
            etLogradouro.setText(logradouro);
            etBairro.setText(bairro);
            etCidade.setText(cidade);
            etUf.setText(uf);
            etEstado.setText(estado);
            etRegiao.setText(regiao);
            etDdd.setText(ddd);
            etComplemento.setText(complemento);
            
            // Limpa o número para o usuário preencher
            etNumero.setText("");

            loadingIndicator.setVisibility(View.GONE);
            cvResultCard.setVisibility(View.VISIBLE);
            btnBuscarCep.setEnabled(true);
            
            // Foca no campo de número para o usuário completar o endereço
            etNumero.requestFocus();
        });
    }

    private void mostrarMensagemErro(String mensagem) {
        runOnUiThread(() -> {
            loadingIndicator.setVisibility(View.GONE);
            btnBuscarCep.setEnabled(true);
            Toast.makeText(MainActivity.this, mensagem, Toast.LENGTH_LONG).show();
        });
    }

    private void fecharTeclado() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}
