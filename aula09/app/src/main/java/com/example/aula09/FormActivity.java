package com.example.aula09;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        EditText editTextNome = findViewById(R.id.editTextNome);
        EditText editTextIdade = findViewById(R.id.editTextIdade);
        CheckBox cbCalca = findViewById(R.id.cbCalca);
        CheckBox cbCamiseta = findViewById(R.id.cbCamiseta);
        CheckBox cbJaqueta = findViewById(R.id.cbJaqueta);

        RadioButton rbP = findViewById(R.id.rbP);
        RadioButton rbM = findViewById(R.id.rbM);
        RadioButton rbG = findViewById(R.id.rbG);

        RadioGroup rgSize = findViewById(R.id.rgSize);
        Spinner spinnerCores  = findViewById(R.id.spCores);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        Button btnEnviar = findViewById(R.id.btnCadastrar);

        String[] cores = {"Vermelho", "Azul", "Verde", "Preto", "Branco"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCores.setAdapter(adapter);

        btnEnviar.setOnClickListener(v -> {
            String nome = editTextNome.getText().toString();
            String idade = editTextIdade.getText().toString();

            String itens = "";
            if (cbCamiseta.isChecked()) {
                itens = itens + "Camiseta ";
            }
            if (cbJaqueta.isChecked()) {
                itens = itens + "Jaqueta ";
            }
            if (cbCalca.isChecked()) {
                itens = itens + "Calça ";
            }

            String tamanho = "Não selecionado";
            if (rbP.isChecked()) {
                tamanho = "P";
            } else if (rbM.isChecked()) {
                tamanho = "M";
            } else if (rbG.isChecked()) {
                tamanho = "G";
            }

            String corSelecionada = spinnerCores.getSelectedItem().toString();
            float avaliacao = ratingBar.getRating();

            String resultado = "Nome: " + nome +
                    "\nIdade: " + idade +
                    "\nItens: " + itens +
                    "\nTamanho: " + tamanho +
                    "\nCor: " + corSelecionada +
                    "\nAvaliação: " + avaliacao;

            Intent intent = new Intent(FormActivity.this, ResultadoActivity.class);
            intent.putExtra("resultado", resultado);
            startActivity(intent);
        });
    }
}
