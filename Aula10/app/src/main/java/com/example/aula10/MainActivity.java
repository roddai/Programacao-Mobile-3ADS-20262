package com.example.aula10;

import android.os.Bundle;
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

    private TextInputEditText etCep;
    private MaterialButton btnBuscar;
    private TextView tvResultado;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left + v.getPaddingLeft(), systemBars.top + v.getPaddingTop(), 
                         systemBars.right + v.getPaddingRight(), systemBars.bottom + v.getPaddingBottom());
            return insets;
        });

        // Inicialização dos componentes
        etCep = findViewById(R.id.etCep);
        btnBuscar = findViewById(R.id.btnBuscar);
        tvResultado = findViewById(R.id.tvResultado);

        // Configuração do Retrofit usando HTTP para evitar erros de certificado SSL
        retrofit = new Retrofit.Builder()
                .baseUrl("http://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btnBuscar.setOnClickListener(v -> {
            String cep = etCep.getText().toString().trim();
            if (cep.length() == 8) {
                consultarCEP(cep);
            } else {
                etCep.setError("O CEP deve conter 8 dígitos");
                Toast.makeText(this, "Por favor, insira um CEP válido.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void consultarCEP(String cep) {
        ViaCEPService service = retrofit.create(ViaCEPService.class);
        Call<CEP> call = service.buscarCEP(cep);

        tvResultado.setText("Buscando...");

        call.enqueue(new Callback<CEP>() {
            @Override
            public void onResponse(Call<CEP> call, Response<CEP> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CEP objetoCep = response.body();
                    if (objetoCep.isErro()) {
                        tvResultado.setText("CEP não encontrado.");
                    } else {
                        tvResultado.setText(objetoCep.toString());
                    }
                } else {
                    tvResultado.setText("Erro na consulta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<CEP> call, Throwable t) {
                tvResultado.setText("Erro de rede: " + t.getMessage());
            }
        });
    }
}