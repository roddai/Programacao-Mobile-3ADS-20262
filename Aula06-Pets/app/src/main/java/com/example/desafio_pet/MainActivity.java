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

                trocaDeTela.putExtra("nome", "Bruno");
                trocaDeTela.putExtra("cor", "Marrom");
                trocaDeTela.putExtra("raca", "Pequinês");
                trocaDeTela.putExtra("idade", "4 anos");
                trocaDeTela.putExtra("aniversario", "10/05");
                trocaDeTela.putExtra("imagem", R.drawable.bruno);

                startActivity(trocaDeTela);
            }
        });


        btnc_luna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent trocaDeTela = new Intent(MainActivity.this, DetalhesActivity.class);

                trocaDeTela.putExtra("nome", "Luna");
                trocaDeTela.putExtra("cor", "Branca");
                trocaDeTela.putExtra("raca", "Lulu da Pomerânia");
                trocaDeTela.putExtra("idade", "2 anos");
                trocaDeTela.putExtra("aniversario", "15/08");
                trocaDeTela.putExtra("imagem", R.drawable.cao1);

                startActivity(trocaDeTela);
            }
        });


        btnc_madonna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent trocaDeTela = new Intent(MainActivity.this, DetalhesActivity.class);

                trocaDeTela.putExtra("nome", "Madonna");
                trocaDeTela.putExtra("cor", "Caramelo");
                trocaDeTela.putExtra("raca", "Beagle");
                trocaDeTela.putExtra("idade", "3 anos");
                trocaDeTela.putExtra("aniversario", "20/03");
                trocaDeTela.putExtra("imagem", R.drawable.cao2);

                startActivity(trocaDeTela);
            }
        });


        btng_mimi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent trocaDeTela = new Intent(MainActivity.this, DetalhesActivity.class);

                trocaDeTela.putExtra("nome", "Mimi");
                trocaDeTela.putExtra("cor", "Branca");
                trocaDeTela.putExtra("raca", "Siamês");
                trocaDeTela.putExtra("idade", "1 ano");
                trocaDeTela.putExtra("aniversario", "12/11");
                trocaDeTela.putExtra("imagem", R.drawable.gato1);

                startActivity(trocaDeTela);
            }
        });


        btng_garfield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent trocaDeTela = new Intent(MainActivity.this, DetalhesActivity.class);

                trocaDeTela.putExtra("nome", "Garfield");
                trocaDeTela.putExtra("cor", "Laranja");
                trocaDeTela.putExtra("raca", "Persa");
                trocaDeTela.putExtra("idade", "5 anos");
                trocaDeTela.putExtra("aniversario", "01/06");
                trocaDeTela.putExtra("imagem", R.drawable.gato2);

                startActivity(trocaDeTela);
            }
        });


        btng_marcia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent trocaDeTela = new Intent(MainActivity.this, DetalhesActivity.class);

                trocaDeTela.putExtra("nome", "Marcia");
                trocaDeTela.putExtra("cor", "Cinza");
                trocaDeTela.putExtra("raca", "Siamês");
                trocaDeTela.putExtra("idade", "3 anos");
                trocaDeTela.putExtra("aniversario", "25/09");
                trocaDeTela.putExtra("imagem", R.drawable.gato3);

                startActivity(trocaDeTela);
            }
        });
    }
}