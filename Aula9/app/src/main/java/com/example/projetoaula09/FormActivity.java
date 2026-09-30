package com.example.projetoaula09;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        EditText etNome = findViewById(R.id.etNome);
        EditText etIdade = findViewById(R.id.etIdade);
        RadioButton rbP = findViewById(R.id.rbP);
        RadioButton rbM = findViewById(R.id.rbM);
        RadioButton rbG = findViewById(R.id.rbG);
        Spinner spCor = findViewById(R.id.spCor);
        CheckBox cbCamiseta = findViewById(R.id.cbCamiseta);
        CheckBox cbCalca = findViewById(R.id.cbCalca);
        CheckBox cbJaqueta = findViewById(R.id.cbJaqueta);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        Button btnEnviar = findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(v -> {

            String nome = etNome.getText().toString();
            String idade = etIdade.getText().toString();

            String tamanho = "Nenhum";
            if (rbP.isChecked()) tamanho = "P";
            if (rbM.isChecked()) tamanho = "M";
            if (rbG.isChecked()) tamanho = "G";

            String cor = spCor.getSelectedItem().toString();

            String roupas = "";
            if (cbCamiseta.isChecked()) roupas += "Camiseta ";
            if (cbCalca.isChecked()) roupas += "Calça ";
            if (cbJaqueta.isChecked()) roupas += "Jaqueta ";

            float avaliacao = ratingBar.getRating();

            String resultado = "Nome: " + nome +
                    "\nIdade: " + idade +
                    "\nTamanho: " + tamanho +
                    "\nCor: " + cor +
                    "\nRoupas: " + roupas +
                    "\nAvaliação: " + avaliacao;

            Intent intent = new Intent(FormActivity.this, ResultActivity.class);
            intent.putExtra("resultado", resultado);
            startActivity(intent);
        });
    }
}