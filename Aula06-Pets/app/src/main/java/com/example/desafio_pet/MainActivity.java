package com.example.desafio_pet;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnc_bruno;
    private Button btnc_luna;
    private Button btnc_madonna;
    private Button btng_mimi;
    private Button btng_garfield;
    private Button btng_marcia;
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
        btnc_bruno = findViewById(R.id.btnc_bruno);
        btnc_luna = findViewById(R.id.btnc_luna);
        btnc_madonna = findViewById(R.id.btnc_madonna);
        btng_mimi = findViewById(R.id.btng_mimi);
        btng_garfield = findViewById(R.id.btng_garfield);
        btng_marcia = findViewById(R.id.btng_marcia);
        btnc_bruno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trocaDeTela = new Intent(MainActivity.this, DetalhesActivity.class);
                trocaDeTela.putExtra("pet",
                        "Nome: Bruno\n" + "Cor: Marrom\n" + "Raça: Pequinês\n" + "Idade: 4 anos\n" + "Aniversário: 10/05");
                startActivity(trocaDeTela);
            }
        });
        btnc_luna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trocaDeTela = new Intent(MainActivity.this, DetalhesActivity.class);
                trocaDeTela.putExtra("pet",
                        "Nome: Luna\n" + "Cor: Branca\n" + "Raça: Lulu da Pomerânia\n" + "Idade: 2 anos\n" + "Aniversário: 15/08");
                startActivity(trocaDeTela);
            }
        });
        btnc_madonna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trocaDeTela = new Intent(MainActivity.this, DetalhesActivity.class);
                trocaDeTela.putExtra("pet",
                        "Nome: Madonna\n" + "Cor: Caramelo\n" + "Raça: Beagle\n" + "Idade: 3 anos\n" + "Aniversário: 20/03");
                startActivity(trocaDeTela);
            }
        });
        btng_mimi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trocaDeTela = new Intent(MainActivity.this, DetalhesActivity.class);
                trocaDeTela.putExtra("pet",
                        "Nome: Mimi\n" + "Cor: Branca\n" + "Raça: Siamês\n" + "Idade: 1 ano\n" + "Aniversário: 12/11");
                startActivity(trocaDeTela);
            }
        });
        btng_garfield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent trocaDeTela = new Intent(MainActivity.this, DetalhesActivity.class);

                trocaDeTela.putExtra("pet",
                        "Nome: Garfield\n" + "Cor: Laranja\n" + "Raça: Persa\n" + "Idade: 5 anos\n" + "Aniversário: 01/06");
                startActivity(trocaDeTela);
            }
        });
        btng_marcia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent trocaDeTela = new Intent(MainActivity.this, DetalhesActivity.class);

                trocaDeTela.putExtra("pet",
                        "Nome: Marcia\n" + "Cor: Cinza\n" + "Raça: Siamês\n" + "Idade: 3 anos\n" + "Aniversário: 25/09");
                startActivity(trocaDeTela);
            }
        });
    }
}