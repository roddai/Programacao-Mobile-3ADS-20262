package com.example.aula9;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_acitivity);

        EditText etNome = findViewById(R.id.etNome);
        EditText etIdade = findViewById(R.id.etIdade);
        CheckBox cbCamiseta = findViewById(R.id.cbCamiseta);
        CheckBox cbCalca = findViewById(R.id.cbCalça);
        CheckBox cbJaqueta = findViewById(R.id.cbJaqueta);
        RadioButton rbP = findViewById(R.id.rbP);
        RadioButton rbM = findViewById(R.id.rbM);
        RadioButton rbG = findViewById(R.id.rbG);
        RadioGroup rgSimNao = findViewById(R.id.rgSimNao);
        Spinner spinnerCores = findViewById(R.id.spinnerCores);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        Button btnEnviar = findViewById(R.id.btnEnviar);

        String[] cores = {"Azul", "Vermelho", "Verde"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCores.setAdapter(adapter);

        btnEnviar.setOnClickListener(v -> {

            String nome = etNome.getText().toString();
            String idade = etIdade.getText().toString();

            ArrayList<String> lista = new ArrayList<>();
            if (cbCamiseta.isChecked()) lista.add("Camiseta");
            if (cbCalca.isChecked()) lista.add("Calça");
            if (cbJaqueta.isChecked()) lista.add("Jaqueta");
            String opcoes = lista.isEmpty() ? "Nenhuma" : String.join(", ", lista);

            String tamanho = rbP.isChecked() ? "P" : rbM.isChecked() ? "M" : rbG.isChecked() ? "G" : "N/A";

            String corSelecionada = spinnerCores.getSelectedItem().toString();
            int avaliacao = (int) ratingBar.getRating();

            String resultado = "Nome: " + nome +
                    "\nIdade: " + idade +
                    "\nTamanho: " + tamanho +
                    "\nCor escolhida: " + corSelecionada +
                    "\nOpções marcadas: " + opcoes +
                    "\nAvaliação: " + avaliacao + " estrela(s)";

            Intent intent = new Intent(FormActivity.this, ResultadoActivity.class);
            intent.putExtra("resultado", resultado);
            startActivity(intent);
        });
    }
}