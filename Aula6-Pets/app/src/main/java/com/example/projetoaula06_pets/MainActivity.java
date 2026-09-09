package com.example.projetoaula06_pets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnCao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnCao = findViewById(R.id.Luna);

        btnCao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pet Luna = new Pet("Luna","Preta","Dobberman",1,"12 de dezembro");
                Intent irParaTelaDetalhe = new Intent(MainActivity.this, DetalhesActivity.class);
                startActivity(irParaTelaDetalhe);
            }
        });
    }
}