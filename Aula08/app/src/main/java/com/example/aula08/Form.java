package com.example.aula08;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class Form extends AppCompatActivity {

    private ImageView imageView;
    private ActivityResultLauncher<String> pickImageLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);

        EditText etNome = findViewById(R.id.etnome);
        EditText etIdade = findViewById(R.id.etidade);
        CheckBox cbOpcao1 = findViewById(R.id.check1);
        CheckBox cbOpcao2 = findViewById(R.id.check2);
        RadioButton rbSim = findViewById(R.id.rbsim);
        RadioButton rbNao = findViewById(R.id.rbnao);
        RadioGroup rgsimnao = findViewById(R.id.rgsimnao);
        Spinner spinnerCores = findViewById(R.id.spinnerCores);

        RatingBar ratingBar = findViewById(R.id.ratingapp);
        Button btnEnviar = findViewById(R.id.bntcadastrar);
        Button btnCarregarFoto = findViewById(R.id.bntfoto);
        imageView = findViewById(R.id.image2);

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

        btnCarregarFoto.setOnClickListener(v -> pickImageLauncher.launch("image/*"));

        btnEnviar.setOnClickListener(v -> {

            String nome = etNome.getText().toString();
            String idade = etIdade.getText().toString();

            String opcoes = "";
            if (cbOpcao1.isChecked()) opcoes += "Opção 1 ";
            if (cbOpcao2.isChecked()) opcoes += "Opção 2";

            String simNao = rbSim.isChecked() ? "Sim" : rbNao.isChecked() ? "Não" : "N/A";

            String corSelecionada = spinnerCores.getSelectedItem().toString();
            float avaliacao = ratingBar.getRating();

            String resultado = "Nome: " + nome +
                    "\nIdade: " + idade +
                    "\nOpções marcadas: " + opcoes +
                    "\nEscolha Sim/Não: " + simNao +
                    "\nCor escolhida: " + corSelecionada +
                    "\nAvaliação: " + avaliacao;

            Intent intent = new Intent(Form.this, Resultado.class);
            intent.putExtra("resultado", resultado);
            startActivity(intent);
        });
    }
}
