package com.example.provani1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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

    Button btn2;

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

       btn2 = findViewById(R.id.btn2);

       btn2.setOnClickListener(v -> {
           int val1 = group1.getCheckedRadioButtonId();
           int val2 = group2.getCheckedRadioButtonId();
           int val3 = group3.getCheckedRadioButtonId();

           if(val1>0){ val1=group1.getCheckedRadioButtonId(); }else{ val1=0; }
           if(val2>3){ val2=val2-3; }else{ val2=0; }
           if(val3>6){ val3=val3-6; }else{ val3=0; }

           int resultado = val1+val2+val3;

           Intent intent = new Intent(SegundaTela.this,TerceiraTela.class);
           intent.putExtra("resultado", resultado);
           startActivity(intent);
       });

    }
}