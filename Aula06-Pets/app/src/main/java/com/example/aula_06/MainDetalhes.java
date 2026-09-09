package com.example.aula_06;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.BreakIterator;

public class MainDetalhes extends AppCompatActivity {
    TextView textInfo;
    Button btnBack;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.detalhes);
            btnBack = findViewById(R.id.btnBack);

            textInfo = findViewById(R.id.textInfo);

            String informacao = getIntent().getStringExtra("pet");

            textInfo.setText( informacao );

            btnBack.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    finish();
                }
            });
        }

}
