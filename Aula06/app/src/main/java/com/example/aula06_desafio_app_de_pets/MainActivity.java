package com.example.aula06_desafio_app_de_pets;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    Pet rex, thor, toy, luna, mimi, fred;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rex  = new Pet("Rex",  "Castanho", "Pastor", 3, "12/05/2022", R.drawable.rex);
        thor = new Pet("Thor", "Dourado",    "Golden",  2, "20/09/2023", R.drawable.thor);
        toy = new Pet("Toy", "Branco",   "Poodle",    4, "01/03/2021", R.drawable.toy);
        luna = new Pet("Luna", "Branca",   "Siamês",    1, "03/01/2024", R.drawable.luna);
        mimi = new Pet("Mimi", "Cinza",    "Persa",     2, "15/07/2023", R.drawable.mimi);
        fred = new Pet("Fred", "Laranja",  "Vira-lata", 5, "22/11/2020", R.drawable.fred);

        Button btnRex  = findViewById(R.id.btnRex);
        Button btnThor = findViewById(R.id.btnThor);
        Button btnToy = findViewById(R.id.btnToy);
        Button btnLuna = findViewById(R.id.btnLuna);
        Button btnMimi = findViewById(R.id.btnMimi);
        Button btnFred = findViewById(R.id.btnFred);

        btnRex.setOnClickListener(v -> abrirDetalhes(rex));
        btnThor.setOnClickListener(v -> abrirDetalhes(thor));
        btnToy.setOnClickListener(v -> abrirDetalhes(toy));
        btnLuna.setOnClickListener(v -> abrirDetalhes(luna));
        btnMimi.setOnClickListener(v -> abrirDetalhes(mimi));
        btnFred.setOnClickListener(v -> abrirDetalhes(fred));
    }

    private void abrirDetalhes(Pet pet) {
        Intent intent = new Intent(MainActivity.this, DetalhesPetActivity.class);
        intent.putExtra("nome", pet.getNome());
        intent.putExtra("cor", pet.getCor());
        intent.putExtra("raca", pet.getRaca());
        intent.putExtra("idade", pet.getIdade());
        intent.putExtra("aniversario", pet.getAniversario());
        intent.putExtra("imagemId", pet.getImagemId());
        startActivity(intent);
    }
}