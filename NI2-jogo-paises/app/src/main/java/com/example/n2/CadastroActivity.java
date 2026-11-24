package com.example.n2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

public class CadastroActivity extends AppCompatActivity {

    private EditText etNome, etEmail, etSenha, etCep, etRua, etNumero,
            etComplemento, etBairro, etCidade, etEstado;
    private ProgressBar pbCep;
    private UsuarioRepositorio repositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets barras = insets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.ime());
            v.setPadding(barras.left, barras.top, barras.right, barras.bottom);
            return insets;
        });

        repositorio = new UsuarioRepositorio(this);

        etNome = findViewById(R.id.etNome);
        etEmail = findViewById(R.id.etEmail);
        etSenha = findViewById(R.id.etSenha);
        etCep = findViewById(R.id.etCep);
        etRua = findViewById(R.id.etRua);
        etNumero = findViewById(R.id.etNumero);
        etComplemento = findViewById(R.id.etComplemento);
        etBairro = findViewById(R.id.etBairro);
        etCidade = findViewById(R.id.etCidade);
        etEstado = findViewById(R.id.etEstado);
        pbCep = findViewById(R.id.pbCep);
        Button btnCadastrar = findViewById(R.id.btnCadastrar);

        // Quando o CEP tiver 8 números, consulta o ViaCEP automaticamente
        etCep.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String cep = s.toString().trim();
                if (cep.length() == 8) {
                    buscarCep(cep);
                }
            }
        });

        btnCadastrar.setOnClickListener(v -> cadastrarUsuario());
    }

    private void buscarCep(String cep) {
        pbCep.setVisibility(View.VISIBLE);

        // Requisição de rede não pode rodar na thread principal
        new Thread(() -> {
            try {
                String resposta = ConexaoHttp.buscarTexto("https://viacep.com.br/ws/" + cep + "/json/");
                JSONObject endereco = new JSONObject(resposta);
                runOnUiThread(() -> preencherEndereco(endereco));
            } catch (Exception e) {
                runOnUiThread(() -> {
                    pbCep.setVisibility(View.GONE);
                    Toast.makeText(this, R.string.erro_cep_conexao, Toast.LENGTH_LONG).show();
                });
            }
        }).start();
    }

    private void preencherEndereco(JSONObject endereco) {
        pbCep.setVisibility(View.GONE);

        // O ViaCEP responde {"erro": "true"} quando o CEP não existe
        if (endereco.optBoolean("erro", false)) {
            etCep.setError(getString(R.string.erro_cep_nao_encontrado));
            return;
        }

        etRua.setText(endereco.optString("logradouro"));
        etBairro.setText(endereco.optString("bairro"));
        etCidade.setText(endereco.optString("localidade"));
        etEstado.setText(endereco.optString("uf"));

        // Número e complemento o próprio usuário preenche
        etNumero.requestFocus();
    }

    private void cadastrarUsuario() {
        EditText[] camposObrigatorios = {etNome, etEmail, etSenha, etCep, etRua,
                etNumero, etComplemento, etBairro, etCidade, etEstado};

        boolean camposValidos = true;
        for (EditText campo : camposObrigatorios) {
            if (campo.getText().toString().trim().isEmpty()) {
                campo.setError(getString(R.string.erro_campo_obrigatorio));
                camposValidos = false;
            }
        }
        if (!camposValidos) {
            Toast.makeText(this, R.string.erro_preencha_campos, Toast.LENGTH_SHORT).show();
            return;
        }

        String email = etEmail.getText().toString().trim().toLowerCase();
        String cep = etCep.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(getString(R.string.erro_email_invalido));
            etEmail.requestFocus();
            return;
        }

        if (cep.length() != 8) {
            etCep.setError(getString(R.string.erro_cep_invalido));
            etCep.requestFocus();
            return;
        }

        if (repositorio.emailExiste(email)) {
            etEmail.setError(getString(R.string.erro_email_existente));
            etEmail.requestFocus();
            return;
        }

        try {
            JSONObject usuario = new JSONObject();
            usuario.put("nome", etNome.getText().toString().trim());
            usuario.put("email", email);
            usuario.put("senha", UsuarioRepositorio.gerarHash(etSenha.getText().toString()));
            usuario.put("cep", cep);
            usuario.put("rua", etRua.getText().toString().trim());
            usuario.put("numero", etNumero.getText().toString().trim());
            usuario.put("complemento", etComplemento.getText().toString().trim());
            usuario.put("bairro", etBairro.getText().toString().trim());
            usuario.put("cidade", etCidade.getText().toString().trim());
            usuario.put("estado", etEstado.getText().toString().trim().toUpperCase());

            repositorio.salvarUsuario(usuario);
        } catch (JSONException e) {
            Toast.makeText(this, R.string.erro_salvar_usuario, Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this, R.string.cadastro_sucesso, Toast.LENGTH_SHORT).show();
        finish(); // volta para a tela de login
    }
}
