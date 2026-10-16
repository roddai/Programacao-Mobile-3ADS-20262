package com.example.Aula09;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FormActivity extends AppCompatActivity {

    private EditText edtNome, edtIdade;
    private RadioGroup rgTamanho;
    private Spinner spnCor;
    private CheckBox cbCamiseta, cbCalca, cbJaqueta;
    private RatingBar rbAvaliacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        // Liga as variáveis aos componentes do XML
        edtNome = findViewById(R.id.edtNome);
        edtIdade = findViewById(R.id.edtIdade);
        rgTamanho = findViewById(R.id.rgTamanho);
        spnCor = findViewById(R.id.spnCor);
        cbCamiseta = findViewById(R.id.cbCamiseta);
        cbCalca = findViewById(R.id.cbCalca);
        cbJaqueta = findViewById(R.id.cbJaqueta);
        rbAvaliacao = findViewById(R.id.rbAvaliacao);
        Button btnEnviar = findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(v -> enviar());
    }

    private void enviar() {
        String nome = edtNome.getText().toString().trim();
        String idade = edtIdade.getText().toString().trim();

        // Validações
        if (nome.isEmpty()) {
            edtNome.setError("Informe seu nome");
            edtNome.requestFocus();
            return;
        }
        if (idade.isEmpty()) {
            edtIdade.setError("Informe sua idade");
            edtIdade.requestFocus();
            return;
        }

        // Tamanho escolhido (P, M ou G)
        RadioButton rbSelecionado = findViewById(rgTamanho.getCheckedRadioButtonId());
        String tamanho = rbSelecionado.getText().toString();

        // Cor escolhida no Spinner
        String cor = spnCor.getSelectedItem().toString();

        // Peças marcadas
        ArrayList<String> pecas = new ArrayList<>();
        if (cbCamiseta.isChecked()) pecas.add("Camiseta");
        if (cbCalca.isChecked()) pecas.add("Calça");
        if (cbJaqueta.isChecked()) pecas.add("Jaqueta");

        if (pecas.isEmpty()) {
            Toast.makeText(this, "Escolha pelo menos uma peça", Toast.LENGTH_SHORT).show();
            return;
        }

        // Avaliação
        float nota = rbAvaliacao.getRating();
        if (nota == 0) {
            Toast.makeText(this, "Avalie o atendimento", Toast.LENGTH_SHORT).show();
            return;
        }

        // Envia tudo para a tela de resultado
        Intent intent = new Intent(this, ResultadoActivity.class);
        intent.putExtra("nome", nome);
        intent.putExtra("idade", idade);
        intent.putExtra("tamanho", tamanho);
        intent.putExtra("cor", cor);
        intent.putExtra("pecas", TextUtils.join(", ", pecas));
        intent.putExtra("nota", nota);
        startActivity(intent);
    }
}