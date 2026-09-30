package com.example.myapplication;

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
    private ActivityResultLauncher<String> pickImageLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        EditText etNome = findViewById(R.id.edNome);
        EditText etIdade = findViewById(R.id.edIdade);
        CheckBox cbOpcao1 = findViewById(R.id.cbopcao1);
        CheckBox cbOpcao2 = findViewById(R.id.cbopcao2);
        RadioButton rbp = findViewById(R.id.rbp);
        RadioButton rbm = findViewById(R.id.rbm);
        RadioButton rbg = findViewById(R.id.rbg);
        RadioGroup rgSimNao = findViewById(R.id.rgsn);
        Spinner spinnerCores   = findViewById(R.id.spinnerCores);
        RatingBar ratingBar = findViewById(R.id.ratingstars);
        Button btnEnviar = findViewById(R.id.btcadastrar);

        String[] cores = {"Vermelho", "Azul", "Verde"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCores.setAdapter(adapter);

        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        imageView.setImageURI(uri);
                    }
                }
        );
        btnEnviar.setOnClickListener(v -> {

            String nome = etNome.getText().toString();
            String idade = etIdade.getText().toString();

            String opcoes = "";
            if (cbOpcao1.isChecked()) opcoes += "Opção 1 ";
            if (cbOpcao2.isChecked()) opcoes += "Opção 2";

            String simNao = rbp.isChecked() ? "Tamanho: P" : rbm.isChecked() ? "Tamanho: M" : "Tamanho: G";

            String corSelecionada = spinnerCores.getSelectedItem().toString();
            float avaliacao = ratingBar.getRating();

            String resultado = "Nome: " + nome +
                    "\nIdade: " + idade +
                    "\nOpções marcadas: " + opcoes +
                    "\nCor escolhida: " + corSelecionada +
                    "\nAvaliação: " + avaliacao;

            Intent intent = new Intent(FormActivity.this, ResultadoActivity.class);
            intent.putExtra("resultado", resultado);
            startActivity(intent);
        });
    }
}
