package com.example.aula04exemplo2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    private EditText editNome;
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNome = findViewById(R.id.editNome); // onde digitamos nosso nome
        btnEnviar = findViewById(R.id.btnEnviar); // botão

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeDigitado = editNome.getText().toString();

                Intent irParaSegundaTela = new Intent(MainActivity.this, SegundaActivity.class);
                irParaSegundaTela.putExtra("nomeUsuario", nomeDigitado);
                startActivity(irParaSegundaTela);
            }
        });
    }
}