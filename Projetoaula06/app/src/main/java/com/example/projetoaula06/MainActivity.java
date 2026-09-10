package com.example.projetoaula06;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Pet Rex, Thor, Luna, gatob, gatom, gatop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Rex = new Pet("Gabigol", "Branco", 11, "Bulldog", "8/09/2015");
        Thor = new Pet("Neymar", "Dourado", 13, "Golden", "5/08/2013");
        Luna = new Pet("Memphis", "Branco", 10, "Caramelo", "8/07/2016");



        Button bntRex =findViewById(R.id.button);
        Button bntThor =findViewById(R.id.button2);
        Button bntLuna =findViewById(R.id.button3);
        Button bntgatob =findViewById(R.id.button4);
        Button bntgatom =findViewById(R.id.button5);
        Button bntgatop =findViewById(R.id.button6);

        bntRex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent irParaSegundaTela = new Intent(MainActivity.this, Activity_detalhes.class);
                irParaSegundaTela.putExtra("infoPet", Rex.getInfo());
                startActivity(irParaSegundaTela);
            };






        });
    }
}