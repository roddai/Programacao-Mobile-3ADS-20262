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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etCep, etLogradouro, etNumero, etComplemento, etBairro, etCidade, etUf, etEstado, etRegiao, etDdd;
    private MaterialButton btnBuscar;
    private TextView tvStatus;
    private FloatingActionButton fabMenu;
    private ViaCepService viaCepService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Ajuste de Padding para o Layout Edge-to-Edge - Corrigido para main_layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicialização dos Componentes
        inicializarComponentes();

        // Configuração do Retrofit (HTTP + Unsafe Client para ignorar proxy da faculdade)
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://viacep.com.br/") 
                .client(getUnsafeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        viaCepService = retrofit.create(ViaCepService.class);

        // Ação do Botão Buscar
        btnBuscar.setOnClickListener(v -> {
            String cep = etCep.getText().toString().trim();
            if (cep.length() == 8) {
                buscarCep(cep);
            } else {
                etCep.setError("CEP deve ter 8 dígitos");
            }
        });

        // Ação do Botão Flutuante (Limpar campos)
        fabMenu.setOnClickListener(v -> {
            limparCampos();
            etCep.setText("");
            tvStatus.setText("Campos limpos");
            Toast.makeText(this, "Formulário resetado", Toast.LENGTH_SHORT).show();
        });
    }

    private void inicializarComponentes() {
        etCep = findViewById(R.id.etCep);
        btnBuscar = findViewById(R.id.btnBuscar);
        etLogradouro = findViewById(R.id.etLogradouro);
        etNumero = findViewById(R.id.etNumero);
        etComplemento = findViewById(R.id.etComplemento);
        etBairro = findViewById(R.id.etBairro);
        etCidade = findViewById(R.id.etCidade);
        etUf = findViewById(R.id.etUf);
        etEstado = findViewById(R.id.etEstado);
        etRegiao = findViewById(R.id.etRegiao);
        etDdd = findViewById(R.id.etDdd);
        tvStatus = findViewById(R.id.tvStatus);
        fabMenu = findViewById(R.id.fabMenu);
    }

    private void buscarCep(String cep) {
        tvStatus.setText("Buscando...");
        btnBuscar.setEnabled(false);

        viaCepService.buscarCep(cep).enqueue(new Callback<CepResponse>() {
            @Override
            public void onResponse(@NonNull Call<CepResponse> call, @NonNull Response<CepResponse> response) {
                btnBuscar.setEnabled(true);
                if (response.isSuccessful() && response.body() != null) {
                    CepResponse res = response.body();
                    if (res.isErro()) {
                        tvStatus.setText("CEP não encontrado.");
                        limparCampos();
                    } else {
                        preencherCampos(res);
                        tvStatus.setText("CEP localizado com sucesso!");
                    }
                } else {
                    tvStatus.setText("Erro na resposta do servidor.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<CepResponse> call, @NonNull Throwable t) {
                btnBuscar.setEnabled(true);
                tvStatus.setText("Erro: Verifique a conexão da faculdade.");
            }
        });
    }

    private void preencherCampos(CepResponse res) {
        etLogradouro.setText(res.getLogradouro());
        etComplemento.setText(res.getComplemento());
        etBairro.setText(res.getBairro());
        etCidade.setText(res.getLocalidade());
        etUf.setText(res.getUf());
        etEstado.setText(res.getEstado());
        etRegiao.setText(res.getRegiao());
        etDdd.setText(res.getDdd());
        etNumero.requestFocus(); // Move o foco para o número após carregar
    }

    private void limparCampos() {
        etLogradouro.setText("");
        etComplemento.setText("");
        etBairro.setText("");
        etCidade.setText("");
        etUf.setText("");
        etEstado.setText("");
        etRegiao.setText("");
        etDdd.setText("");
        etNumero.setText("");
    }

    // Cliente HTTP para contornar bloqueios de certificado da rede da faculdade
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