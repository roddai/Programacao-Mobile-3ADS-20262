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

        Pet bruno = new Pet(
                "Bruno",
                "Marrom",
                "Pequinês",
                "4 anos",
                "10/05",
                R.drawable.bruno
        );

        Pet luna = new Pet(
                "Luna",
                "Branca",
                "Lulu da Pomerânia",
                "2 anos",
                "15/08",
                R.drawable.cao1
        );

        Pet madonna = new Pet(
                "Madonna",
                "Caramelo",
                "Beagle",
                "3 anos",
                "20/03",
                R.drawable.cao2
        );

        Pet mimi = new Pet(
                "Mimi",
                "Branca",
                "Siamês",
                "1 ano",
                "12/11",
                R.drawable.gato1
        );

        Pet garfield = new Pet(
                "Garfield",
                "Laranja",
                "Persa",
                "5 anos",
                "01/06",
                R.drawable.gato2
        );

        Pet marcia = new Pet(
                "Marcia",
                "Cinza",
                "Siamês",
                "3 anos",
                "25/09",
                R.drawable.gato3
        );

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

                trocaDeTela.putExtra("nome", bruno.getNome());
                trocaDeTela.putExtra("cor", bruno.getCor());
                trocaDeTela.putExtra("raca", bruno.getRaca());
                trocaDeTela.putExtra("idade", bruno.getIdade());
                trocaDeTela.putExtra("aniversario", bruno.getAniversario());
                trocaDeTela.putExtra("imagem", bruno.getImagem());

                startActivity(trocaDeTela);
            }
        });


        btnc_luna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent trocaDeTela = new Intent(MainActivity.this, DetalhesActivity.class);

                trocaDeTela.putExtra("nome", luna.getNome());
                trocaDeTela.putExtra("cor", luna.getCor());
                trocaDeTela.putExtra("raca", luna.getRaca());
                trocaDeTela.putExtra("idade", luna.getIdade());
                trocaDeTela.putExtra("aniversario", luna.getAniversario());
                trocaDeTela.putExtra("imagem", luna.getImagem());

                startActivity(trocaDeTela);
            }
        });


        btnc_madonna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent trocaDeTela = new Intent(MainActivity.this, DetalhesActivity.class);

                trocaDeTela.putExtra("nome", madonna.getNome());
                trocaDeTela.putExtra("cor", madonna.getCor());
                trocaDeTela.putExtra("raca", madonna.getRaca());
                trocaDeTela.putExtra("idade", madonna.getIdade());
                trocaDeTela.putExtra("aniversario", madonna.getAniversario());
                trocaDeTela.putExtra("imagem", madonna.getImagem());

                startActivity(trocaDeTela);
            }
        });


        btng_mimi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent trocaDeTela = new Intent(MainActivity.this, DetalhesActivity.class);

                trocaDeTela.putExtra("nome", mimi.getNome());
                trocaDeTela.putExtra("cor", mimi.getCor());
                trocaDeTela.putExtra("raca", mimi.getRaca());
                trocaDeTela.putExtra("idade", mimi.getIdade());
                trocaDeTela.putExtra("aniversario", mimi.getAniversario());
                trocaDeTela.putExtra("imagem", mimi.getImagem());

                startActivity(trocaDeTela);
            }
        });


        btng_garfield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent trocaDeTela = new Intent(MainActivity.this, DetalhesActivity.class);

                trocaDeTela.putExtra("nome", garfield.getNome());
                trocaDeTela.putExtra("cor", garfield.getCor());
                trocaDeTela.putExtra("raca", garfield.getRaca());
                trocaDeTela.putExtra("idade", garfield.getIdade());
                trocaDeTela.putExtra("aniversario", garfield.getAniversario());
                trocaDeTela.putExtra("imagem", garfield.getImagem());

                startActivity(trocaDeTela);
            }
        });


        btng_marcia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent trocaDeTela = new Intent(MainActivity.this, DetalhesActivity.class);

                trocaDeTela.putExtra("nome", marcia.getNome());
                trocaDeTela.putExtra("cor", marcia.getCor());
                trocaDeTela.putExtra("raca", marcia.getRaca());
                trocaDeTela.putExtra("idade", marcia.getIdade());
                trocaDeTela.putExtra("aniversario", marcia.getAniversario());
                trocaDeTela.putExtra("imagem", marcia.getImagem());

                startActivity(trocaDeTela);
            }
        });
    }
}