package com.example.aula_06;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btn1, btn2, btn3, btn4, btn5, btn6;
    Pet pet1, pet2, pet3, pet4, pet5, pet6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);

        pet1 = new Pet("Carlos", "Dourado", "Golden Retriever", 3, "02/02");
        pet2 = new Pet("Ronaldo", "Malhado Marrom", "Border Collie", 2, "03/03");
        pet3 = new Pet("Alvaro", "Preto", "Salsicha", 1, "04/04");
        pet4 = new Pet("Lucas", "Rajado Cinza", "Rajado", 3, "02/02");
        pet5 = new Pet("Bob the wizard", "Tuxedo", "Frajola", 1, "03/03");
        pet6 = new Pet("Carlos2", "Rajado Beje", "Rajado", 5, "05/05");

        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent;

                Intent infoPet1= new Intent(MainActivity.this, MainDetalhes.class);
                infoPet1.putExtra("pet", pet1.getInfo());

                startActivity(infoPet1);
            }

        });

        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent;

                Intent infoPet2= new Intent(MainActivity.this, MainDetalhes.class);
                infoPet2.putExtra("pet", pet2.getInfo());

                startActivity(infoPet2);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent;

                Intent infoPet3= new Intent(MainActivity.this, MainDetalhes.class);
                infoPet3.putExtra("pet", pet3.getInfo());

                startActivity(infoPet3);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent;

                Intent infoPet4= new Intent(MainActivity.this, MainDetalhes.class);
                infoPet4.putExtra("pet", pet4.getInfo());

                startActivity(infoPet4);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent;

                Intent infoPet5= new Intent(MainActivity.this, MainDetalhes.class);
                infoPet5.putExtra("pet", pet5.getInfo());

                startActivity(infoPet5);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent;

                Intent infoPet6= new Intent(MainActivity.this, MainDetalhes.class);
                infoPet6.putExtra("pet", pet6.getInfo());

                startActivity(infoPet6);
            }
        });
    }
}