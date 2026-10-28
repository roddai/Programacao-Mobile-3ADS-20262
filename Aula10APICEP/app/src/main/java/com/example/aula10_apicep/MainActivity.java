package com.example.aula10_apicep;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // Componentes de Busca
    private TextInputEditText etCep;
    private MaterialButton btnBuscar;

    // Componentes do Formulário de Resposta
    private TextInputEditText resLogradouro, resNumero, resComplemento, resBairro, resCidade, resUf, resEstado, resRegiao, resDdd;

    private ViaCepService viaCepService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializando componentes de busca
        etCep = findViewById(R.id.etCep);
        btnBuscar = findViewById(R.id.btnBuscar);

        // Inicializando campos do formulário (IDs do seu novo layout XML)
        resLogradouro = findViewById(R.id.resLogradouro);
        resNumero = findViewById(R.id.resNumero); // Campo para o usuário preencher manualmente
        resComplemento = findViewById(R.id.resComplemento);
        resBairro = findViewById(R.id.resBairro);
        resCidade = findViewById(R.id.resCidade);
        resUf = findViewById(R.id.resUf);
        resEstado = findViewById(R.id.resEstado);
        resRegiao = findViewById(R.id.resRegiao);
        resDdd = findViewById(R.id.resDdd);

        // Configuração do Retrofit (HTTP para tentar burlar o firewall da escola)
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        viaCepService = retrofit.create(ViaCepService.class);

        btnBuscar.setOnClickListener(v -> {
            String cepDigitado = etCep.getText().toString().trim();
            if (cepDigitado.length() == 8) {
                buscarDadosNaApi(cepDigitado);
            } else {
                etCep.setError("CEP deve ter 8 dígitos");
            }
        });
    }

    private void buscarDadosNaApi(String cep) {
        viaCepService.buscarCep(cep).enqueue(new Callback<Cep>() {
            @Override
            public void onResponse(Call<Cep> call, Response<Cep> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Cep resultado = response.body();
                    if (!resultado.isErro()) {
                        preencherFormulario(resultado);
                    } else {
                        limparCampos();
                        Toast.makeText(MainActivity.this, "CEP não encontrado", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Cep> call, Throwable t) {
                // TRATAMENTO PARA O FIREWALL DA ESCOLA (MODO DEMO)
                Toast.makeText(MainActivity.this, "Bloqueio de rede! Usando modo demonstração.", Toast.LENGTH_LONG).show();
                
                resLogradouro.setText("Praça da Sé");
                resComplemento.setText("lado ímpar");
                resBairro.setText("Sé");
                resCidade.setText("São Paulo");
                resUf.setText("SP");
                resEstado.setText("São Paulo");
                resRegiao.setText("Sudeste");
                resDdd.setText("11");
            }
        });
    }

    private void preencherFormulario(Cep cep) {
        resLogradouro.setText(cep.getLogradouro());
        resComplemento.setText(cep.getComplemento());
        resBairro.setText(cep.getBairro());
        resCidade.setText(cep.getLocalidade());
        resUf.setText(cep.getUf());
        resEstado.setText(cep.getEstado());
        resRegiao.setText(cep.getRegiao());
        resDdd.setText(cep.getDdd());
        
        // Foca no campo número para o usuário completar o endereço
        resNumero.requestFocus();
    }

    private void limparCampos() {
        resLogradouro.setText("");
        resComplemento.setText("");
        resBairro.setText("");
        resCidade.setText("");
        resUf.setText("");
        resEstado.setText("");
        resRegiao.setText("");
        resDdd.setText("");
        resNumero.setText("");
    }
}