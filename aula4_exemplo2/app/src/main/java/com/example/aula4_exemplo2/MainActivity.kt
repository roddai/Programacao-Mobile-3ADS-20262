package com.example.aula4_exemplo2

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var editNome: EditText? = null
    private var btnEnviar: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editNome = findViewById<EditText?>(R.id.editNome) // onde digitamos nosso nome
        btnEnviar = findViewById<Button?>(R.id.btnEnviar) // botão

        btnEnviar!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val nomeDigitado = editNome!!.getText().toString()

                val irParaSegundaTela = Intent(this@MainActivity, SegundaActivity::class.java)
                irParaSegundaTela.putExtra("nomeUsuario", nomeDigitado)
                startActivity(irParaSegundaTela)
            }
        })
    }
}