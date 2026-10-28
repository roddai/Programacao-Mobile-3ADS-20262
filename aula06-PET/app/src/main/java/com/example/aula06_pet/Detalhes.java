package com.example.aula06_pet;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class Detalhes extends AppCompatActivity {
    private TextView txtDetalhes;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        txtDetalhes = findViewById(R.id.txtdetalhes);

        String nome = getIntent().getStringExtra("nome");
        String raca = getIntent().getStringExtra("raca");
        String cor = getIntent().getStringExtra("cor");
        String idade = getIntent().getStringExtra("idade");
        String niver = getIntent().getStringExtra("aniversario");

        String textoExibir = "Nome: " + nome + "\n" +
                "Raça: " + raca + "\n" +
                "Cor: " + cor + "\n" +
                "Idade: " + idade + "\n" +
                "Niver: " + niver;

        txtDetalhes.setText(textoExibir);
    }
}
