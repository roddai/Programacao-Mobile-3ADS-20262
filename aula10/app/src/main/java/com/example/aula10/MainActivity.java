package com.example.aula10;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText editTextCep;
    private MaterialButton buttonBuscar;
    private ProgressBar progressBar;
    
    // Campos do formulário
    private TextInputEditText editLogradouro;
    private TextInputEditText editComplemento;
    private TextInputEditText editBairro;
    private TextInputEditText editLocalidade;
    private TextInputEditText editUf;
    private TextInputEditText editEstado;
    private TextInputEditText editRegiao;
    private TextInputEditText editDdd;
    private TextInputEditText editNumero;
    
    private TextView statusMessage;
    private FloatingActionButton fabMenu;

    private Retrofit retrofit;
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

        // Configuração Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("http://viacep.com.br/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        
        viaCepService = retrofit.create(ViaCepService.class);

        // Mapeamento dos componentes (IDs ajustados para o novo layout)
        editTextCep = findViewById(R.id.editTextCep);
        buttonBuscar = findViewById(R.id.buttonBuscar);
        progressBar = findViewById(R.id.progressBar);
        
        editLogradouro = findViewById(R.id.editLogradouro);
        editComplemento = findViewById(R.id.editComplemento);
        editBairro = findViewById(R.id.editBairro);
        editLocalidade = findViewById(R.id.editLocalidade);
        editUf = findViewById(R.id.editUf);
        editEstado = findViewById(R.id.editEstado);
        editRegiao = findViewById(R.id.editRegiao);
        editDdd = findViewById(R.id.editDdd);
        editNumero = findViewById(R.id.editNumero);
        
        statusMessage = findViewById(R.id.statusMessage);
        fabMenu = findViewById(R.id.fabMenu);

        buttonBuscar.setOnClickListener(v -> {
            String cep = editTextCep.getText().toString();
            if (cep.length() == 8) {
                buscarCep(cep);
            } else {
                editTextCep.setError("8 dígitos");
                Toast.makeText(this, "CEP inválido", Toast.LENGTH_SHORT).show();
            }
        });

        fabMenu.setOnClickListener(v -> {
            Toast.makeText(this, "Menu de opções", Toast.LENGTH_SHORT).show();
        });
    }

    private void buscarCep(String cep) {
        progressBar.setVisibility(View.VISIBLE);
        statusMessage.setVisibility(View.GONE);
        limparCampos();
        
        viaCepService.buscarCep(cep).enqueue(new Callback<CepResponse>() {
            @Override
            public void onResponse(Call<CepResponse> call, Response<CepResponse> response) {
                progressBar.setVisibility(View.GONE);
                
                if (response.isSuccessful() && response.body() != null) {
                    CepResponse data = response.body();
                    
                    if (data.isErro()) {
                        Toast.makeText(MainActivity.this, "CEP não encontrado", Toast.LENGTH_SHORT).show();
                    } else {
                        preencherCampos(data);
                        statusMessage.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<CepResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Falha na busca", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void preencherCampos(CepResponse data) {
        editLogradouro.setText(data.getLogradouro());
        editComplemento.setText(data.getComplemento());
        editBairro.setText(data.getBairro());
        editLocalidade.setText(data.getLocalidade());
        editUf.setText(data.getUf());
        editEstado.setText(data.getEstado());
        editRegiao.setText(data.getRegiao());
        editDdd.setText(data.getDdd());
        editNumero.requestFocus();
    }

    private void limparCampos() {
        editLogradouro.setText("");
        editComplemento.setText("");
        editBairro.setText("");
        editLocalidade.setText("");
        editUf.setText("");
        editEstado.setText("");
        editRegiao.setText("");
        editDdd.setText("");
        editNumero.setText("");
    }
}