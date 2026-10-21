package com.example.aula_10;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etCep, etLogradouro, etNumero, etComplemento, etBairro, etCidade, etUf, etEstado, etRegiao, etDdd;
    private MaterialButton btnBuscar;
    private TextView tvStatus;
    private ViaCepService viaCepService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        View mainView = findViewById(R.id.main);
        ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Configurar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        viaCepService = retrofit.create(ViaCepService.class);

        // Inicialização dos componentes
        etCep = findViewById(R.id.etCep);
        etLogradouro = findViewById(R.id.etLogradouro);
        etNumero = findViewById(R.id.etNumero);
        etComplemento = findViewById(R.id.etComplemento);
        etBairro = findViewById(R.id.etBairro);
        etCidade = findViewById(R.id.etCidade);
        etUf = findViewById(R.id.etUf);
        etEstado = findViewById(R.id.etEstado);
        etRegiao = findViewById(R.id.etRegiao);
        etDdd = findViewById(R.id.etDdd);
        btnBuscar = findViewById(R.id.btnBuscar);
        tvStatus = findViewById(R.id.tvStatus);

        btnBuscar.setOnClickListener(v -> {
            String cep = etCep.getText().toString();
            if (cep.length() == 8) {
                buscarCep(cep);
            } else {
                etCep.setError("Digite 8 números");
            }
        });
    }

    private void buscarCep(String cep) {
        tvStatus.setText("Buscando...");
        limparCampos();
        
        viaCepService.buscarCep(cep).enqueue(new Callback<CepResponse>() {
            @Override
            public void onResponse(Call<CepResponse> call, Response<CepResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CepResponse cepData = response.body();
                    
                    if (cepData.isErro()) {
                        tvStatus.setText("CEP não encontrado.");
                    } else {
                        preencherCampos(cepData);
                        tvStatus.setText("CEP localizado com sucesso!");
                    }
                } else {
                    tvStatus.setText("Erro na resposta do servidor.");
                }
            }

            @Override
            public void onFailure(Call<CepResponse> call, Throwable t) {
                tvStatus.setText("Falha na rede: " + t.getMessage());
            }
        });
    }

    private void preencherCampos(CepResponse data) {
        etLogradouro.setText(data.getLogradouro());
        etComplemento.setText(data.getComplemento());
        etBairro.setText(data.getBairro());
        etCidade.setText(data.getLocalidade());
        etUf.setText(data.getUf());
        etEstado.setText(data.getEstado());
        etRegiao.setText(data.getRegiao());
        etDdd.setText(data.getDdd());
        etNumero.requestFocus();
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
}