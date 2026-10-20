package com.example.aula10_api;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etCep;
    private MaterialButton btnBuscar;
    private TextView tvLogradouro, tvBairro, tvCidade, tvEstado;
    private ViaCepService viaCepService;

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

        // Inicializando componentes
        etCep = findViewById(R.id.etCep);
        btnBuscar = findViewById(R.id.btnBuscar);
        tvLogradouro = findViewById(R.id.tvLogradouro);
        tvBairro = findViewById(R.id.tvBairro);
        tvCidade = findViewById(R.id.tvCidade);
        tvEstado = findViewById(R.id.tvEstado);

        // Configuração do Retrofit (HTTP + Unsafe Client para rede da faculdade)
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://viacep.com.br/") 
                .client(getUnsafeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        viaCepService = retrofit.create(ViaCepService.class);

        btnBuscar.setOnClickListener(v -> {
            String cep = etCep.getText().toString().trim();
            if (validarCep(cep)) {
                buscarCep(cep);
            } else {
                etCep.setError("Digite um CEP válido");
            }
        });
    }

    private boolean validarCep(String cep) {
        return cep != null && cep.length() == 8 && cep.matches("\\d+");
    }

    private void buscarCep(String cep) {
        limparCampos();
        tvLogradouro.setText("Buscando...");
        btnBuscar.setEnabled(false);

        viaCepService.buscarCep(cep).enqueue(new Callback<CepResponse>() {
            @Override
            public void onResponse(@NonNull Call<CepResponse> call, @NonNull Response<CepResponse> response) {
                btnBuscar.setEnabled(true);
                if (response.isSuccessful() && response.body() != null) {
                    CepResponse res = response.body();
                    if (res.isErro()) {
                        tvLogradouro.setText("CEP não encontrado.");
                    } else {
                        tvLogradouro.setText(res.getLogradouro());
                        tvBairro.setText(res.getBairro());
                        tvCidade.setText(res.getLocalidade());
                        tvEstado.setText(res.getUf());
                    }
                } else {
                    tvLogradouro.setText("Erro na resposta.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<CepResponse> call, @NonNull Throwable t) {
                btnBuscar.setEnabled(true);
                tvLogradouro.setText("Erro de conexão.");
                Toast.makeText(MainActivity.this, "Falha na rede", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void limparCampos() {
        tvLogradouro.setText("-");
        tvBairro.setText("-");
        tvCidade.setText("-");
        tvEstado.setText("-");
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override public void checkClientTrusted(X509Certificate[] chain, String authType) {}
                    @Override public void checkServerTrusted(X509Certificate[] chain, String authType) {}
                    @Override public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                }
            };
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            return new OkHttpClient.Builder()
                .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0])
                .hostnameVerifier((hostname, session) -> true)
                .build();
        } catch (Exception e) { throw new RuntimeException(e); }
    }
}