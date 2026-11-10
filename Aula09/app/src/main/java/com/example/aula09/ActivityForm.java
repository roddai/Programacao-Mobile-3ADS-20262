package com.example.aula09;

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

public class ActivityForm extends AppCompatActivity {

    private ImageView imageView;
    private ActivityResultLauncher<String> pickImageLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        // Mapeando com os IDs exatos do seu XML (activity_form.xml)
        EditText edNome = findViewById(R.id.edNome);
        EditText edIdade = findViewById(R.id.edIdade);
        CheckBox cbOpcao1 = findViewById(R.id.cbOpcao1);
        CheckBox cbOpcao2 = findViewById(R.id.cbOpcao2);
        RadioButton rbSim = findViewById(R.id.rgSim);
        RadioButton rbNao = findViewById(R.id.rgNao);
        RadioGroup rgSimNao = findViewById(R.id.rgSimNao);
        Spinner spinnerCores = findViewById(R.id.spinnerCores);
        RatingBar ratingEstrelas = findViewById(R.id.ratingEstrelas);
        Button btCadastrar = findViewById(R.id.btCadastrar);
        Button btCarregarFoto = findViewById(R.id.btCarregarFoto);
        imageView = findViewById(R.id.imageView);

        // Configuração do Spinner de Cores
        String[] cores = {"Vermelho", "Azul", "Verde"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCores.setAdapter(adapter);

        // Configuração para carregar a foto da galeria
        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        imageView.setImageURI(uri);
                    }
                }
        );

        btCarregarFoto.setOnClickListener(v -> pickImageLauncher.launch("image/*"));

        // Ação do botão de cadastrar/enviar
        btCadastrar.setOnClickListener(v -> {
            String nome = edNome.getText().toString();
            String idade = edIdade.getText().toString();

            String opcoes = "";
            if (cbOpcao1.isChecked()) opcoes += "Opção 1 ";
            if (cbOpcao2.isChecked()) opcoes += "Opção 2";

            String simNao = rbSim.isChecked() ? "Sim" : rbNao.isChecked() ? "Não" : "N/A";

            String corSelecionada = spinnerCores.getSelectedItem().toString();
            float avaliacao = ratingEstrelas.getRating();

            String resultado = "Nome: " + nome +
                    "\nIdade: " + idade +
                    "\nOpções marcadas: " + opcoes +
                    "\nEscolha Sim/Não: " + simNao +
                    "\nCor escolhida: " + corSelecionada +
                    "\nAvaliação: " + avaliacao + " estrelas";

            // Indo para a tela de resultado correta (ActivityResultado)
            Intent intent = new Intent(ActivityForm.this, ActivityResultado.class);
            intent.putExtra("resultado", resultado);
            startActivity(intent);
        });
    }
}