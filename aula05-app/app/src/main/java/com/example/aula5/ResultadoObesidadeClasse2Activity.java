package com.example.aula5;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class ResultadoObesidadeClasse2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_obesidade_2);
    }

    public void voltar(View view) {
        finish();
    }
}
