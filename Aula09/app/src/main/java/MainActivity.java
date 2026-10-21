package com.example.Aula09;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText edtNome = findViewById(R.id.edtNome);
        EditText edtIdade = findViewById(R.id.edtIdade);
        RadioGroup rgTamanho = findViewById(R.id.rgTamanho);
        Spinner spCor = findViewById(R.id.spCor);
        CheckBox cbCamiseta = findViewById(R.id.cbCamiseta);
        CheckBox cbCalca = findViewById(R.id.cbCalca);
        CheckBox cbJaqueta = findViewById(R.id.cbJaqueta);
        RatingBar rbAvaliacao = findViewById(R.id.rbAvaliacao);
        Button btnEnviar = findViewById(R.id.btnEnviar);

        // Opções do menu de cor
        String[] cores = {"Azul", "Vermelho", "Verde", "Amarelo", "Preto", "Branco"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, cores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCor.setAdapter(adapter);

        btnEnviar.setOnClickListener(v -> {
            String nome = edtNome.getText().toString().trim();
            String idade = edtIdade.getText().toString().trim();

            if (nome.isEmpty()) {
                edtNome.setError("Preencha seu nome");
                return;
            }
            if (idade.isEmpty()) {
                edtIdade.setError("Preencha sua idade");
                return;
            }

            RadioButton rbSelecionado = findViewById(rgTamanho.getCheckedRadioButtonId());
            String tamanho = rbSelecionado.getText().toString();

            String cor = spCor.getSelectedItem().toString();

            ArrayList<String> pecas = new ArrayList<>();
            if (cbCamiseta.isChecked()) pecas.add("Camiseta");
            if (cbCalca.isChecked()) pecas.add("Calça");
            if (cbJaqueta.isChecked()) pecas.add("Jaqueta");
            String textoPecas = pecas.isEmpty() ? "Nenhuma" : TextUtils.join(", ", pecas);

            int nota = (int) rbAvaliacao.getRating();

            // Manda os dados para a tela de resultado
            Intent intent = new Intent(FormActivity.this, ResultActivity.class);
            intent.putExtra("nome", nome);
            intent.putExtra("idade", idade);
            intent.putExtra("tamanho", tamanho);
            intent.putExtra("cor", cor);
            intent.putExtra("pecas", textoPecas);
            intent.putExtra("nota", nota);
            startActivity(intent);
        });
    }
}