package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        EditText etNome = findViewById(R.id.etNome);
        EditText etIdade = findViewById(R.id.etIdade);
        RadioGroup rgSexo = findViewById(R.id.rgSexo);
        RadioButton rbFeminino = findViewById(R.id.rbFeminino);
        RadioButton rbmasculino = findViewById(R.id.rbMasculino);
        RadioButton rbOutros = findViewById(R.id.rbOutros);
        Spinner spCor = findViewById(R.id.spCor);
        CheckBox cbRI = findViewById(R.id.cbRI);
        CheckBox cbHistoria  = findViewById(R.id.cbHistoria);
        CheckBox cbRP = findViewById(R.id.cbRP);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        Button bntEnviar = findViewById(R.id.bntEnviar);

        String [] cores = {"Azul", "Vermelho", "Rosa"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCor.setAdapter(adapter);

        bntEnviar.setOnClickListener(v-> {
            String nome = etNome.getText().toString();
            String idade = etIdade.getText().toString();


        });


    }
}