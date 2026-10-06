package com.example.aula5;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class ResultadoAbaixoDoPesoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_abaixo_peso);
    }

    public void voltar(View view) {
        finish();
    }
}