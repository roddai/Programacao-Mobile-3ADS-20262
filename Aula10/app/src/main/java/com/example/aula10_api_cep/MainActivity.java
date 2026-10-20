package com.example.aula10_api_cep;

import android.os.Bundle;
import android.util.Log;
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

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etCep;
    private MaterialButton btnBuscar;
    private TextView tvLogradouro, tvComplemento, tvBairro, tvLocalidade, tvUf, tvEstado, tvRegiao, tvDdd;
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

        // Inicialização dos componentes
        etCep = findViewById(R.id.etCep);
        btnBuscar = findViewById(R.id.btnBuscar);
        
        tvLogradouro = findViewById(R.id.tvLogradouro);
        tvComplemento = findViewById(R.id.tvComplemento);
        tvBairro = findViewById(R.id.tvBairro);
        tvLocalidade = findViewById(R.id.tvLocalidade);
        tvUf = findViewById(R.id.tvUf);
        tvEstado = findViewById(R.id.tvEstado);
        tvRegiao = findViewById(R.id.tvRegiao);
        tvDdd = findViewById(R.id.tvDdd);

        // Configuração do Interceptor de Log
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Configuração do Cliente HTTP para contornar restrições de rede
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                            .build();
                    return chain.proceed(request);
                })
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        // Retrofit configurado para HTTP para evitar problemas com SSL Inspection em firewalls acadêmicos
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://viacep.com.br/ws/") 
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        viaCepService = retrofit.create(ViaCepService.class);

        btnBuscar.setOnClickListener(v -> {
            String cep = etCep.getText().toString();
            if (cep.length() == 8) {
                buscarCepNoWebService(cep);
            } else {
                etCep.setError("O CEP deve conter 8 dígitos");
                limparCampos();
            }
        });
    }

    private void buscarCepNoWebService(String cep) {
        limparCampos();
        tvLogradouro.setText("Buscando...");
        
        viaCepService.buscarCep(cep).enqueue(new Callback<CepResponse>() {
            @Override
            public void onResponse(@NonNull Call<CepResponse> call, @NonNull Response<CepResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CepResponse cepResult = response.body();
                    if (cepResult.isErro()) {
                        tvLogradouro.setText("CEP não encontrado.");
                    } else {
                        exibirResultado(cepResult);
                    }
                } else {
                    tvLogradouro.setText("Erro na API: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CepResponse> call, @NonNull Throwable t) {
                Log.e("API_ERROR", "Falha: " + t.getMessage());
                tvLogradouro.setText("Falha na conexão.");
                Toast.makeText(MainActivity.this, "Erro de rede persistente.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void exibirResultado(CepResponse res) {
        tvLogradouro.setText(res.getLogradouro() != null && !res.getLogradouro().isEmpty() ? res.getLogradouro() : "-");
        tvComplemento.setText(res.getComplemento() != null && !res.getComplemento().isEmpty() ? res.getComplemento() : "-");
        tvBairro.setText(res.getBairro() != null && !res.getBairro().isEmpty() ? res.getBairro() : "-");
        tvLocalidade.setText(res.getLocalidade() != null && !res.getLocalidade().isEmpty() ? res.getLocalidade() : "-");
        tvUf.setText(res.getUf() != null && !res.getUf().isEmpty() ? res.getUf() : "-");
        tvEstado.setText(res.getEstado() != null && !res.getEstado().isEmpty() ? res.getEstado() : "-");
        tvRegiao.setText(res.getRegiao() != null && !res.getRegiao().isEmpty() ? res.getRegiao() : "-");
        tvDdd.setText(res.getDdd() != null && !res.getDdd().isEmpty() ? res.getDdd() : "-");
    }

    private void limparCampos() {
        tvLogradouro.setText("-");
        tvComplemento.setText("-");
        tvBairro.setText("-");
        tvLocalidade.setText("-");
        tvUf.setText("-");
        tvEstado.setText("-");
        tvRegiao.setText("-");
        tvDdd.setText("-");
    }
}