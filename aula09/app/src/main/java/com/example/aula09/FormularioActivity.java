package com.example.aula09;

import android.content.Intent;
import android.os.Bundle;
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

public class FormularioActivity extends AppCompatActivity {

    private EditText etNome, etIdade;
    private RadioGroup rgTamanho;
    private Spinner spCores;
    private CheckBox cbCamiseta, cbCalca, cbJaqueta;
    private RatingBar ratingBar;
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        etNome = findViewById(R.id.etNome);
        etIdade = findViewById(R.id.etIdade);
        rgTamanho = findViewById(R.id.rgTamanho);
        spCores = findViewById(R.id.spCores);
        cbCamiseta = findViewById(R.id.cbCamiseta);
        cbCalca = findViewById(R.id.cbCalca);
        cbJaqueta = findViewById(R.id.cbJaqueta);
        ratingBar = findViewById(R.id.ratingBar);
        btnEnviar = findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(v -> {
            String nome = etNome.getText().toString().trim();
            String idade = etIdade.getText().toString().trim();

            if (nome.isEmpty() || idade.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedRadioId = rgTamanho.getCheckedRadioButtonId();
            RadioButton rbSelecionado = findViewById(selectedRadioId);
            String tamanho = (rbSelecionado != null) ? rbSelecionado.getText().toString() : "";

            String cor = spCores.getSelectedItem().toString();

            ArrayList<String> itens = new ArrayList<>();
            if (cbCamiseta.isChecked()) itens.add("Camiseta");
            if (cbCalca.isChecked()) itens.add("Calça");
            if (cbJaqueta.isChecked()) itens.add("Jaqueta");
            String itensTexto = itens.isEmpty() ? "Nenhum" : String.join(", ", itens);

            float avaliacao = ratingBar.getRating();

            Intent intent = new Intent(FormularioActivity.this, ResultadoActivity.class);
            intent.putExtra("NOME", nome);
            intent.putExtra("IDADE", idade);
            intent.putExtra("TAMANHO", tamanho);
            intent.putExtra("COR", cor);
            intent.putExtra("ITENS", itensTexto);
            intent.putExtra("AVALIACAO", avaliacao);
            startActivity(intent);
        });
    }
}