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

    Button btnRex;
    Button btnLuna;
    Button btnThor;

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

        Pet Rex = new Pet("Rex","Bege","Pomerania","1000","29 de Fevereiro");
        Pet Luna = new Pet("Luna","Preta","Dobberman","1","12 de Dezembro");
        Pet Thor = new Pet("Thor","Caramelo","Poodle","2","21 de Setembro");

        btnRex = findViewById(R.id.Rex);
        btnLuna = findViewById(R.id.Luna);
        btnThor = findViewById(R.id.Thor);

        btnRex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irParaTelaDetalhe = new Intent(MainActivity.this, DetalhesActivity.class);
                irParaTelaDetalhe.putExtra("Detalhes",Rex.getInfo());
                startActivity(irParaTelaDetalhe);
            }
        });


        btnLuna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irParaTelaDetalhe = new Intent(MainActivity.this, DetalhesActivity.class);
                irParaTelaDetalhe.putExtra("Detalhes",Luna.getInfo());
                startActivity(irParaTelaDetalhe);
            }
        });


        btnThor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irParaTelaDetalhe = new Intent(MainActivity.this, DetalhesActivity.class);
                irParaTelaDetalhe.putExtra("Detalhes",Thor.getInfo());
                startActivity(irParaTelaDetalhe);
            }
        });
    }
}