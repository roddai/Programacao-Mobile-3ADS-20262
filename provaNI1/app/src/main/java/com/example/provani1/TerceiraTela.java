package com.example.provani1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TerceiraTela extends AppCompatActivity {
    TextView text7;

    Button btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.terceira);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.terceira), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        text7 = findViewById(R.id.text7);
        btn3 = findViewById(R.id.btn3);

        int res = getIntent().getIntExtra("resultado", 0);

        if(res>0 && res<4){
            text7.setText("Você deve assistir uma comédia leve e divertida!");
        }else if(res>=4 && res<7){
            text7.setText("Você deve assistir um filme de ação emocionante!");
        }else if(res>=7 && res<10){
            text7.setText("Você deve assistir um suspense cheio de mistérios!");
        }else{
            text7.setText("Marque uma opção no formulário anterior!");
        }


        btn3.setOnClickListener(v -> {
            finish();
        });
    }
}
