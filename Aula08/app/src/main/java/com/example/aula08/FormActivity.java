package com.example.aula08;

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

        EditText etNome = findViewById(R.id.input_01);
        EditText etIdade = findViewById(R.id.input_02);
        CheckBox cbOpcao1 = findViewById(R.id.check_01);
        CheckBox cbOpcao2 = findViewById(R.id.check_02);
        RadioButton rbSim = findViewById(R.id.radioBtn_01);
        RadioButton rbNao = findViewById(R.id.radioBtn_02);
        RadioGroup rgSimNao = findViewById(R.id.group_01);
        Spinner spinner_01  = findViewById(R.id.spinner_01);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        Button btnEnviar = findViewById(R.id.btn_03);
        Button btnCarregarFoto = findViewById(R.id.btn_02);
        imageView = findViewById(R.id.image_01);

        String[] cores = {"Vermelho", "Azul", "Verde"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_01.setAdapter(adapter);

        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        imageView.setImageURI(uri);
                    }
                }
        );

        btnCarregarFoto.setOnClickListener(v -> pickImageLauncher.launch("image/*"));

        btnEnviar.setOnClickListener(v -> {

            String nome = etNome.getText().toString();
            String idade = etIdade.getText().toString();

            String opcoes = "";
            if (cbOpcao1.isChecked()) opcoes += "Opção 1 ";
            if (cbOpcao2.isChecked()) opcoes += "Opção 2";

            String simNao = rbSim.isChecked() ? "Sim" : rbNao.isChecked() ? "Não" : "N/A";

            String corSelecionada = spinner_01.getSelectedItem().toString();
            float avaliacao = ratingBar.getRating();

            String resultado = "Nome: " + nome +
                    "\nIdade: " + idade +
                    "\nOpções marcadas: " + opcoes +
                    "\nEscolha Sim/Não: " + simNao +
                    "\nCor escolhida: " + corSelecionada +
                    "\nAvaliação: " + avaliacao;

            Intent intent = new Intent(FormActivity.this, ResultadoActivity.class);
            intent.putExtra("resultado", resultado);
            startActivity(intent);
        });
    }
}
