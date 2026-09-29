package com.example.aula09_formulario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        EditText etNome = findViewById(R.id.etNome);
        EditText etIdade = findViewById(R.id.etIdade);
        RadioGroup rgTamanho = findViewById(R.id.rgTamanho);
        Spinner spinnerCores = findViewById(R.id.spinnerCores);
        CheckBox cbCamiseta = findViewById(R.id.cbCamiseta);
        CheckBox cbCalca = findViewById(R.id.cbCalca);
        CheckBox cbJaqueta = findViewById(R.id.cbJaqueta);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        Button btnEnviar = findViewById(R.id.btnEnviar);

        String[] cores = {"Azul", "Vermelho", "Verde"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, cores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCores.setAdapter(adapter);

        btnEnviar.setOnClickListener(v -> {

            String nome = etNome.getText().toString().trim();
            String idade = etIdade.getText().toString().trim();

            if (nome.isEmpty() || idade.isEmpty()) {
                Toast.makeText(this, "Preencha nome e idade", Toast.LENGTH_SHORT).show();
                return;
            }

            String tamanho;
            int checkedId = rgTamanho.getCheckedRadioButtonId();
            if (checkedId == R.id.rbP) {
                tamanho = "P";
            } else if (checkedId == R.id.rbM) {
                tamanho = "M";
            } else if (checkedId == R.id.rbG) {
                tamanho = "G";
            } else {
                tamanho = "N/A";
            }

            String cor = spinnerCores.getSelectedItem().toString();

            StringBuilder itens = new StringBuilder();
            if (cbCamiseta.isChecked()) itens.append("Camiseta, ");
            if (cbCalca.isChecked()) itens.append("Calça, ");
            if (cbJaqueta.isChecked()) itens.append("Jaqueta, ");

            String itensMarcados;
            if (itens.length() > 0) {
                itensMarcados = itens.substring(0, itens.length() - 2); // remove a última vírgula
            } else {
                itensMarcados = "Nenhum";
            }

            int avaliacao = (int) ratingBar.getRating();

            String resultado = "Nome: " + nome +
                    "\nIdade: " + idade +
                    "\nTamanho: " + tamanho +
                    "\nCor escolhida: " + cor +
                    "\nItens: " + itensMarcados +
                    "\nAvaliação do atendimento: " + avaliacao + "/5";

            Intent intent = new Intent(FormActivity.this, ResultadoActivity.class);
            intent.putExtra("resultado", resultado);
            startActivity(intent);
        });
    }
}
