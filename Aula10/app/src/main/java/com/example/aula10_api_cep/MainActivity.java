package com.example.aula10_api_cep;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etCep, etLogradouro, etNumero, etComplemento, etBairro, etLocalidade, etUf, etEstado, etRegiao, etDdd;
    private MaterialButton btnBuscar;
    private TextView tvStatus;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private static final String TAG = "CEP_LOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicialização de todos os campos
        etCep = findViewById(R.id.etCep);
        etLogradouro = findViewById(R.id.etLogradouro);
        etNumero = findViewById(R.id.etNumero);
        etComplemento = findViewById(R.id.etComplemento);
        etBairro = findViewById(R.id.etBairro);
        etLocalidade = findViewById(R.id.etLocalidade);
        etUf = findViewById(R.id.etUf);
        etEstado = findViewById(R.id.etEstado);
        etRegiao = findViewById(R.id.etRegiao);
        etDdd = findViewById(R.id.etDdd);
        
        btnBuscar = findViewById(R.id.btnBuscar);
        tvStatus = findViewById(R.id.tvStatus);

        btnBuscar.setOnClickListener(v -> {
            String cep = etCep.getText().toString().trim();
            if (cep.length() == 8) {
                buscarCep(cep);
            } else {
                etCep.setError("Digite um CEP com 8 dígitos");
            }
        });

        // Mantendo o bypass de SSL para ambientes com Firewall restritivo
        configureSSLBypass();
    }

    private void configureSSLBypass() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
            };
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            Log.e(TAG, "Erro SSL Bypass", e);
        }
    }

    private void buscarCep(String cep) {
        tvStatus.setText("Buscando informações...");
        limparCampos();

        executorService.execute(() -> {
            try {
                // ViaCEP suporta HTTPS, mas em redes com firewall restritivo, o bypass configurado ajuda.
                URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);

                if (connection.getResponseCode() == 200) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) response.append(line);
                    in.close();

                    JSONObject json = new JSONObject(response.toString());
                    
                    if (json.has("erro")) {
                        mainHandler.post(() -> tvStatus.setText("CEP não encontrado."));
                    } else {
                        mainHandler.post(() -> {
                            try {
                                etLogradouro.setText(json.optString("logradouro", ""));
                                etBairro.setText(json.optString("bairro", ""));
                                etLocalidade.setText(json.optString("localidade", ""));
                                etUf.setText(json.optString("uf", ""));
                                etEstado.setText(json.optString("estado", ""));
                                etRegiao.setText(json.optString("regiao", ""));
                                etDdd.setText(json.optString("ddd", ""));
                                
                                tvStatus.setText("CEP localizado com sucesso!");
                                etNumero.requestFocus(); // Foca no número para o usuário digitar
                            } catch (Exception e) {
                                tvStatus.setText("Erro ao processar dados.");
                            }
                        });
                    }
                } else {
                    mainHandler.post(() -> tvStatus.setText("Erro na rede: " + TAG));
                }
            } catch (Exception e) {
                Log.e(TAG, "Erro na busca", e);
                mainHandler.post(() -> tvStatus.setText("Falha de conexão."));
            }
        });
    }

    private void limparCampos() {
        etLogradouro.setText("");
        etNumero.setText("");
        etComplemento.setText("");
        etBairro.setText("");
        etLocalidade.setText("");
        etUf.setText("");
        etEstado.setText("");
        etRegiao.setText("");
        etDdd.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}