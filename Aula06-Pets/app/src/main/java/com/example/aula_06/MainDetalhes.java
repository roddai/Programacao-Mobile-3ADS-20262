package com.example.aula_06;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainDetalhes extends AppCompatActivity {

    Button btnBack;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.detalhes);
            btnBack = findViewById(R.id.btnBack);
            btnBack.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    finish();
                }
            });
        }

}
