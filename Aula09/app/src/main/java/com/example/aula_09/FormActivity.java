package com.example.aula_09;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class FormActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        EditText etNome = findViewById(R.id.etNome);
        EditText etIdade = findViewById(R.id.etIdade);
        RadioButton rbP = findViewById(R.id.rbP);
        RadioButton rbM = findViewById(R.id.rbM);
        RadioButton rbG = findViewById(R.id.rbG);
        CheckBox cbCamiseta = findViewById(R.id.cbCamiseta);
        CheckBox cbCalca = findViewById(R.id.cbCalca);
        CheckBox  cbJaqueta = findViewById(R.id.cbJaqueta);
        Spinner spinnerCores = findViewById(R.id.spinnerCores);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        Button btnEnviar = findViewById(R.id.btnProximo);

        String[] cores = {"Vermelho", "Azul", "Verde, Laranja, Ciano, Oliva"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCores.setAdapter(adapter);

        btnEnviar.setOnClickListener(v -> {

            String nome = etNome.getText().toString();
            String idade = etIdade.getText().toString();

            String opcoes = "";
            if (cbCamiseta.isChecked()) opcoes += "Camiseta ";
            if (cbCalca.isChecked()) opcoes += "Calça ";
            if (cbJaqueta.isChecked()) opcoes += "Jaqueta";

            String simNao = rbP.isChecked() ? "Pequeno" : rbM.isChecked() ? "Médio" : rbG.isChecked() ? "Grande" : "N/A";

            String corSelecionada = spinnerCores.getSelectedItem().toString();
            float avaliacao = ratingBar.getRating();

            String resultado = "Nome: " + nome +
                    "\nIdade: " + idade +
                    "\nOpções marcadas: " + opcoes +
                    "\nEscolha tamanho: " + simNao +
                    "\nCor escolhida: " + corSelecionada +
                    "\nAvaliação: " + avaliacao;

            Intent intent = new Intent(FormActivity.this, ResultActivity.class);
            intent.putExtra("resultado", resultado);
            startActivity(intent);
        });
    }
}
