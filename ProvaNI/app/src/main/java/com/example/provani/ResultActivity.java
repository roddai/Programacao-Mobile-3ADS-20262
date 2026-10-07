package com.example.provani;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {

    TextView Msg;
    Button Voltar;
    Integer soma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.result), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Msg = findViewById(R.id.tvMensagem);
        Voltar = findViewById(R.id.btnVoltar);

        soma = getIntent().getIntExtra("resultado",0);

        if ((soma >= 1) && (soma <=3))
        {Msg.setText("Você deve assistir uma comédia leve e divertida!");}
        else if ((soma > 3) && (soma <=6))
        {Msg.setText("Você deve assistir um filme de ação emocionante!");}
        else if ((soma > 6) && (soma <=9))
        {Msg.setText("Você deve assistir um suspense cheio de mistérios!");}
        else if ((soma < 1) || (soma > 9))
        {Msg.setText("Ocorreu um erro na avaliação.\nResultado do quiz: "+soma);}

        Voltar.setOnClickListener(v -> finish());

    }
}