package com.example.n1_davibigottoebrunofaccio;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;

import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {


    public int conta1 = 0;
    public int conta2 = 0;
    public int conta3 = 0;

    private int acumulador = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);



        RadioButton rb1_a = findViewById(R.id.rb1_a);
        RadioButton rb1_b = findViewById(R.id.rb1_b);
        RadioButton rb1_c = findViewById(R.id.rb1_c);
        RadioGroup rgPergunta1 = findViewById(R.id.rgPergunta1);
        RadioButton rb2_a = findViewById(R.id.rb2_a);
        RadioButton rb2_b = findViewById(R.id.rb2_b);
        RadioButton rb2_c = findViewById(R.id.rb2_c);
        RadioGroup rgPergunta2 = findViewById(R.id.rgPergunta2);
        RadioButton rb3_a = findViewById(R.id.rb3_a);
        RadioButton rb3_b = findViewById(R.id.rb3_b);
        RadioButton rb3_c = findViewById(R.id.rb3_c);
        RadioGroup rgPergunta3 = findViewById(R.id.rgPergunta3);
        Button btnProximo = findViewById(R.id.btnProximo);




        rb1_a.setOnClickListener(v -> {

            if (rb1_a.isChecked()) {
                rb1_b.setChecked(false);
                rb1_c.setChecked(false);
            }
            acumulador = 1;
        });

        rb1_b.setOnClickListener(v -> {

            if (rb1_a.setChecked(false)){
                rb1_b.isChecked();
                rb1_c.setChecked(false);
            }
            acumulador = 2;
        });

        rb1_c.setOnClickListener(v -> {

            if (rb1_a.setChecked(false)){
                rb1_b.setChecked(false);
                rb1_c.isChecked();
            }
            acumulador = 3;
        });


        btnProximo.setOnClickListener(v -> {


                    String Pergunta1 = rb1_a.isChecked() ? "A) Filmes engraçados e leves" : rb1_b.isChecked() ? "B) Filmes com muita ação" : rb1_c.isChecked() ? "C) Filmes com mistério e suspense";
                    String Pergunta2 = rb2_a.isChecked() ? "A) Relaxar e dar risada" : rb2_b.isChecked() ? "B) Ficar animado com cenas intensas" : rb2_c.isChecked() ? "C) Pensar e tentar descobrir o final";
                    String Pergunta3 = rb3_a.isChecked() ? "A) Para se divertir" : rb3_b.isChecked() ? "B) Para sentir adrenalina" : rb3_c.isChecked() ? "C) Para refletir e se supreender";


                    String resultado = "1 a 3: " + conta1 +
                            "\n3 a 6" + conta2 +
                            "\n6 a 9: " + conta3 +

                });
    }

            Intent intent = new Intent(QuizActivity.this, ResultadoActivity.class);
            intent.putExtra("resultado", resultado);
            startActivity(intent);
        }
