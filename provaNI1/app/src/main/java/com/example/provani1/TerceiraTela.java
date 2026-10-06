package com.example.provani1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TerceiraTela extends AppCompatActivity {
    TextView text6;

    Button btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.terceira);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.terceira), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        text6 = findViewById(R.id.text6);

        btn3 = findViewById(R.id.btn3);

        text6.setText(String.valueOf(getIntent().getIntExtra("resultado", 0)));

        btn3.setOnClickListener(v -> {
            finish();
        });
    }
}
