package com.example.api_cep;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
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

    private TextInputEditText etCep, etNumero, etComplemento;
    private Button btnBuscar, btnSalvar;
    
    // TextViews para os resultados vindos da API
    private TextView tvLogradouro, tvBairro, tvLocalidade, 
                     tvUf, tvEstado, tvRegiao, tvDdd;
    
    private Retrofit retrofit;

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

        // Inicialização dos componentes de busca
        etCep = findViewById(R.id.etCep);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnSalvar = findViewById(R.id.btnSalvar);

        // Inicialização dos campos de resultado (API)
        tvLogradouro = findViewById(R.id.tvLogradouro);
        tvBairro = findViewById(R.id.tvBairro);
        tvLocalidade = findViewById(R.id.tvLocalidade);
        tvUf = findViewById(R.id.tvUf);
        tvEstado = findViewById(R.id.tvEstado);
        tvRegiao = findViewById(R.id.tvRegiao);
        tvDdd = findViewById(R.id.tvDdd);

        // Inicialização dos campos editáveis pelo usuário
        etNumero = findViewById(R.id.etNumero);
        etComplemento = findViewById(R.id.etComplemento);

        // Configuração do Retrofit com bypass de SSL para ambientes restritos
        retrofit = new Retrofit.Builder()
                .baseUrl("http://viacep.com.br/ws/")
                .client(getUnsafeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btnBuscar.setOnClickListener(v -> {
            String cep = etCep.getText().toString();
            if (cep.length() == 8) {
                buscarDadosCEP(cep);
            } else {
                etCep.setError("O CEP deve ter 8 dígitos");
            }
        });

        btnSalvar.setOnClickListener(v -> {
            // Lógica para salvar as informações
            String resultado = "Dados de " + tvLogradouro.getText() + " salvos com sucesso!";
            Toast.makeText(this, resultado, Toast.LENGTH_LONG).show();
        });
    }

    private void buscarDadosCEP(String cep) {
        ViaCEPService service = retrofit.create(ViaCEPService.class);
        Call<CEP> call = service.buscarCEP(cep);

        limparCampos("Buscando...");

        call.enqueue(new Callback<CEP>() {
            @Override
            public void onResponse(@NonNull Call<CEP> call, @NonNull Response<CEP> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CEP dados = response.body();
                    
                    if (dados.getLogradouro() != null) {
                        atualizarInterface(dados);
                    } else {
                        limparCampos("CEP não encontrado.");
                    }
                } else {
                    limparCampos("Erro na busca.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<CEP> call, @NonNull Throwable t) {
                Log.e("API_ERROR", "Falha: " + t.getMessage());
                limparCampos("Falha na conexão.");
                Toast.makeText(MainActivity.this, "Erro de rede: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void atualizarInterface(CEP dados) {
        tvLogradouro.setText(dados.getLogradouro());
        tvBairro.setText(dados.getBairro());
        tvLocalidade.setText(dados.getLocalidade());
        tvUf.setText(dados.getUf());
        tvEstado.setText(dados.getEstado());
        tvRegiao.setText(dados.getRegiao());
        tvDdd.setText(dados.getDdd());
        
        if (dados.getComplemento() != null && !dados.getComplemento().isEmpty()) {
            etComplemento.setText(dados.getComplemento());
        }

        etNumero.requestFocus();
    }

    private void limparCampos(String mensagem) {
        tvLogradouro.setText(mensagem);
        tvBairro.setText("-");
        tvLocalidade.setText("-");
        tvUf.setText("-");
        tvEstado.setText("-");
        tvRegiao.setText("-");
        tvDdd.setText("-");
        etNumero.setText("");
        etComplemento.setText("");
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {}
                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {}
                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
            };
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            return new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0])
                    .hostnameVerifier((hostname, session) -> true)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}