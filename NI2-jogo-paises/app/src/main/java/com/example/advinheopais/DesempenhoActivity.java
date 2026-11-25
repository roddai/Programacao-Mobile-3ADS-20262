
package com.example.advinheopais;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DesempenhoActivity extends AppCompatActivity {

    private TextView txtRecorde;
    private TextView txtTotalPartidas;
    private TextView txtAproveitamento;
    private TextView txtHistorico;
    private Button btnVoltarMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desempenho);

        txtRecorde = findViewById(R.id.txtRecorde);
        txtTotalPartidas = findViewById(R.id.txtTotalPartidas);
        txtAproveitamento = findViewById(R.id.txtAproveitamento);
        txtHistorico = findViewById(R.id.txtHistorico);
        btnVoltarMenu = findViewById(R.id.btnVoltarMenu);

        SharedPreferences sessao = getSharedPreferences(
                "SessaoUsuario", MODE_PRIVATE);

        String email = sessao.getString("email", "");

        if (email.isEmpty() || !sessao.getBoolean("logado", false)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        SharedPreferences dados = getSharedPreferences(
                "DadosUsuario", MODE_PRIVATE);

        int recorde = dados.getInt(email + "_recorde", 0);
        int partidas = dados.getInt(email + "_partidas", 0);
        int totalAcertos = dados.getInt(email + "_totalAcertos", 0);
        String historico = dados.getString(email + "_historico", "");

        int aproveitamento = 0;

        if (partidas > 0) {
            aproveitamento = (totalAcertos * 100) / (partidas * 10);
        }

        txtRecorde.setText(recorde + " pontos");
        txtTotalPartidas.setText("Partidas jogadas: " + partidas);
        txtAproveitamento.setText(
                "Aproveitamento: " + aproveitamento + "%");

        if (historico.isEmpty()) {
            txtHistorico.setText("Nenhuma partida realizada ainda.");
        } else {
            txtHistorico.setText(historico);
        }

        btnVoltarMenu.setOnClickListener(v -> {
            finish();
        });
    }
}
