package com.example.aula_05;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ResultadoObesidadeClase2Activity extends AppCompatActivity {

    Button btnVolta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obesidade_classe_2);

        btnVolta = findViewById(R.id.btnVolta);

        btnVolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }

        });
    }

}
