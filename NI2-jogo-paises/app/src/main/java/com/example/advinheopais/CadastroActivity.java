
package com.example.advinheopais;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class CadastroActivity extends AppCompatActivity {

    private EditText editNome, editEmail, editSenha;
    private EditText editCep, editRua, editNumero;
    private EditText editComplemento, editBairro, editCidade, editEstado;

    private Button btnBuscarCep, btnCadastrar, btnVoltar;

    private RequestQueue fila;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editNome = findViewById(R.id.editNome);
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);

        editCep = findViewById(R.id.editCep);
        editRua = findViewById(R.id.editRua);
        editNumero = findViewById(R.id.editNumero);
        editComplemento = findViewById(R.id.editComplemento);
        editBairro = findViewById(R.id.editBairro);
        editCidade = findViewById(R.id.editCidade);
        editEstado = findViewById(R.id.editEstado);

        btnBuscarCep = findViewById(R.id.btnBuscarCep);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnVoltar = findViewById(R.id.btnVoltar);

        fila = Volley.newRequestQueue(this);

        btnBuscarCep.setOnClickListener(v -> consultarCep());
        btnCadastrar.setOnClickListener(v -> cadastrar());

        btnVoltar.setOnClickListener(v -> finish());
    }

    private void consultarCep() {

        String cep = editCep.getText().toString()
                .replace("-", "")
                .trim();

        if (!cep.matches("\\d{8}")) {
            Toast.makeText(this,
                    "Digite um CEP válido com 8 números.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        JsonObjectRequest requisicao = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                resposta -> {

                    if (resposta.optBoolean("erro", false)) {
                        Toast.makeText(this,
                                "CEP não encontrado.",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    editRua.setText(resposta.optString("logradouro"));
                    editBairro.setText(resposta.optString("bairro"));
                    editCidade.setText(resposta.optString("localidade"));
                    editEstado.setText(resposta.optString("uf"));

                    Toast.makeText(this,
                            "CEP consultado com sucesso!",
                            Toast.LENGTH_SHORT).show();
                },
                erro -> {
                    Log.e("VIACEP", "Falha na consulta", erro);

                    Toast.makeText(this,
                            "Erro ao consultar o CEP.",
                            Toast.LENGTH_LONG).show();
                }
        );

        fila.add(requisicao);
    }

    private void cadastrar() {

        String nome = editNome.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String senha = editSenha.getText().toString();

        String cep = editCep.getText().toString().trim();
        String rua = editRua.getText().toString().trim();
        String numero = editNumero.getText().toString().trim();
        String complemento = editComplemento.getText().toString().trim();
        String bairro = editBairro.getText().toString().trim();
        String cidade = editCidade.getText().toString().trim();
        String estado = editEstado.getText().toString().trim();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()
                || cep.isEmpty() || rua.isEmpty() || numero.isEmpty()
                || bairro.isEmpty() || cidade.isEmpty()
                || estado.isEmpty()) {

            Toast.makeText(this,
                    "Preencha todos os campos obrigatórios.",
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this,
                    "Digite um e-mail válido.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (!cep.replace("-", "").matches("\\d{8}")) {
            Toast.makeText(this,
                    "Digite um CEP válido.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences dados = getSharedPreferences(
                "DadosUsuario", MODE_PRIVATE);

        dados.edit()
                .putString("nome", nome)
                .putString("email", email)
                .putString("senha", senha)
                .putString("cep", cep)
                .putString("rua", rua)
                .putString("numero", numero)
                .putString("complemento", complemento)
                .putString("bairro", bairro)
                .putString("cidade", cidade)
                .putString("estado", estado)
                .apply();

        Toast.makeText(this,
                "Cadastro realizado com sucesso!",
                Toast.LENGTH_LONG).show();

        finish();
    }
}
