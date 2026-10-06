package com.example.provani1;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SegundaTela extends AppCompatActivity {

    RadioGroup group1, group2, group3;

    TextView text3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.segunda);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.segunda), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

       group1 = findViewById(R.id.group1);
       group2 = findViewById(R.id.group2);
       group3 = findViewById(R.id.group3);

       text3 = findViewById(R.id.text3);

       int resultado = group1.getCheckedRadioButtonId() + group2.getCheckedRadioButtonId() + group3.getCheckedRadioButtonId();

       text3.setText(resultado);

    }
}