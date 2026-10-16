package com.example.n1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);

        RadioGroup rgPergunta1 = findViewById(R.id.rgPergunta1);
        RadioGroup rgPergunta2 = findViewById(R.id.rgPergunta2);
        RadioGroup rgPergunta3 = findViewById(R.id.rgPergunta3);
        Button bntRecomendacao = findViewById(R.id.bntRecomendacao);

        bntRecomendacao.setOnClickListener(v -> {

            int resposta1 = rgPergunta1.getCheckedRadioButtonId();
            int resposta2 = rgPergunta2.getCheckedRadioButtonId();
            int resposta3 = rgPergunta3.getCheckedRadioButtonId();

            int pontuacao = 0;

            if (resposta1 == R.id.rb1A) {
                pontuacao = pontuacao + 1;
            } else if (resposta1 == R.id.rb1B) {
                pontuacao = pontuacao + 2;
            } else if (resposta1 == R.id.rb1C) {
                pontuacao = pontuacao + 3;
            }


            if (resposta2 == R.id.rb2A) {
                pontuacao = pontuacao + 1;
            } else if (resposta2 == R.id.rb2B) {
                pontuacao = pontuacao + 2;
            } else if (resposta2 == R.id.rb2C) {
                pontuacao = pontuacao + 3;
            }

            if (resposta3 == R.id.rb3A) {
                pontuacao = pontuacao + 1;
            } else if (resposta3 == R.id.rb3B) {
                pontuacao = pontuacao + 2;
            } else if (resposta3 == R.id.rb3C) {
                pontuacao = pontuacao + 3;
            }

            Intent intent = new Intent(QuizActivity.this, ResultadoActivity.class);
            intent.putExtra("pontuacao", pontuacao);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
