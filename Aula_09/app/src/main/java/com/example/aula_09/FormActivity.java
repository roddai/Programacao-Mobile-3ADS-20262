package com.example.aula_09;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View telaInicial = findViewById(R.id.telaInicial);
        View telaFormulario = findViewById(R.id.telaFormulario);
        Button btnCadastrar = findViewById(R.id.btnCadastrar);

        EditText etNome = findViewById(R.id.etNome);
        EditText etIdade = findViewById(R.id.etIdade);
        RadioGroup rgTamanho = findViewById(R.id.rgTamanho);
        Spinner spCor = findViewById(R.id.spCor);
        CheckBox cbCamiseta = findViewById(R.id.cbCamiseta);
        CheckBox cbCalca = findViewById(R.id.cbCalca);
        CheckBox cbJaqueta = findViewById(R.id.cbJaqueta);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        Button btnEnviar = findViewById(R.id.btnEnviar);

        String[] cores = {"Azul", "Vermelho", "Verde", "Amarelo"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, cores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCor.setAdapter(adapter);

        // Botão Cadastrar: troca da tela inicial para o formulário
        btnCadastrar.setOnClickListener(v -> {
            telaInicial.setVisibility(View.GONE);
            telaFormulario.setVisibility(View.VISIBLE);
        });

        // Botão voltar do celular: do formulário volta para a tela inicial
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (telaFormulario.getVisibility() == View.VISIBLE) {
                    telaFormulario.setVisibility(View.GONE);
                    telaInicial.setVisibility(View.VISIBLE);
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        });

        // Botão Enviar: manda os dados para a tela de resultado
        btnEnviar.setOnClickListener(v -> {
            String nome = etNome.getText().toString();
            String idade = etIdade.getText().toString();

            RadioButton rbSelecionado = findViewById(rgTamanho.getCheckedRadioButtonId());
            String tamanho = rbSelecionado.getText().toString();

            String cor = spCor.getSelectedItem().toString();

            String pecas = "";
            if (cbCamiseta.isChecked()) pecas += "Camiseta ";
            if (cbCalca.isChecked()) pecas += "Calça ";
            if (cbJaqueta.isChecked()) pecas += "Jaqueta ";
            if (pecas.isEmpty()) pecas = "Nenhuma";

            int avaliacao = (int) ratingBar.getRating();

            String resultado = "Nome: " + nome +
                    "\nIdade: " + idade +
                    "\nTamanho: " + tamanho +
                    "\nCor: " + cor +
                    "\nPeças: " + pecas.trim() +
                    "\nAvaliação: " + avaliacao + " estrela(s)";

            Intent intent = new Intent(FormActivity.this, ResultadoActivity.class);
            intent.putExtra("resultado", resultado);
            startActivity(intent);
        });
    }
}