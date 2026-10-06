package com.example.provani;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class QuizActivity extends AppCompatActivity {

    RadioGroup Perguntas1, Perguntas2, Perguntas3;
    RadioButton P1R1, P1R2, P1R3, P2R1, P2R2, P2R3, P3R1, P3R2, P3R3;
    Button Enviar;

    Integer soma = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.quiz), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Perguntas1 = findViewById(R.id.rgRespostas1);
        P1R1 = findViewById(R.id.rbR1resp1);
        P1R2 = findViewById(R.id.rbR1resp2);
        P1R3 = findViewById(R.id.rbR1resp3);

        Perguntas2 = findViewById(R.id.rgRespostas2);
        P2R1 = findViewById(R.id.rbR2resp1);
        P2R2 = findViewById(R.id.rbR2resp2);
        P2R3 = findViewById(R.id.rbR2resp3);

        Perguntas3 = findViewById(R.id.rgRespostas3);
        P3R1 = findViewById(R.id.rbR3resp1);
        P3R2 = findViewById(R.id.rbR3resp2);
        P3R3 = findViewById(R.id.rbR3resp3);

        Enviar = findViewById(R.id.btnEnviar);
        Enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irQuiz = new Intent(QuizActivity.this, ResultActivity.class);

                if(P1R1.isChecked())
                {soma+=1;}
                if(P1R2.isChecked())
                {soma+=2;}
                if(P1R2.isChecked())
                {soma+=3;}
                else

                if(P2R1.isChecked())
                {soma+=1;}
                if(P2R2.isChecked())
                {soma+=2;}
                if(P2R2.isChecked())
                {soma+=3;}
                else{soma+=0;}

                if(P3R1.isChecked())
                {soma+=1;}
                if(P3R2.isChecked())
                {soma+=2;}
                if(P3R2.isChecked())
                {soma+=3;}
                else{soma+=0;}

                startActivity(irQuiz);
            }
        });
    }
}