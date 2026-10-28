package com.example.aula11_moedas_api

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.aula11_moedas_api.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBuscar.setOnClickListener { processarBusca() }

        binding.editCep.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                processarBusca()
                true
            } else false
        }
    }

    private fun processarBusca() {
        val cepInput = binding.editCep.text.toString()
        esconderTeclado()
        if (validarCep(cepInput)) {
            val cepLimpo = cepInput.replace("-", "").replace(" ", "")
            buscarCep(cepLimpo)
        }
    }

    private fun validarCep(cep: String): Boolean {
        if (cep.isBlank()) {
            mostrarErro(getString(R.string.error_empty_cep))
            return false
        }
        val cepLimpo = cep.replace("-", "").replace(" ", "")
        if (cepLimpo.length != 8 || !cepLimpo.all { it.isDigit() }) {
            mostrarErro(getString(R.string.error_invalid_cep))
            return false
        }
        return true
    }

    private fun buscarCep(cep: String) {
        // Trocando para HTTP para evitar o erro de Certificado SSL em ambiente de teste/emulador
        val urlString = "http://viacep.com.br/ws/$cep/json/"

        lifecycleScope.launch {
            setLoading(true)
            val resultado = withContext(Dispatchers.IO) {
                try {
                    val url = URL(urlString)
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "GET"
                    connection.connectTimeout = 10000
                    connection.readTimeout = 10000
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0")

                    val responseCode = connection.responseCode
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        val reader = BufferedReader(InputStreamReader(connection.inputStream))
                        val response = StringBuilder()
                        var line: String?
                        while (reader.readLine().also { line = it } != null) {
                            response.append(line)
                        }
                        reader.close()
                        val json = JSONObject(response.toString())
                        if (json.has("erro")) Result.failure(Exception("NOT_FOUND"))
                        else Result.success(json)
                    } else {
                        Result.failure(Exception("SERVER_ERROR|$responseCode"))
                    }
                } catch (e: Exception) {
                    Log.e("CEP_ERROR", "Erro na requisição", e)
                    Result.failure(e)
                }
            }

            setLoading(false)
            resultado.onSuccess { mostrarResultado(it) }
            resultado.onFailure { tratarErro(it) }
        }
    }

    private fun mostrarResultado(json: JSONObject) {
        binding.txtErro.visibility = View.GONE
        binding.txtCep.text = json.optString("cep", "-")
        binding.txtLogradouro.text = json.optString("logradouro", "-")
        binding.txtBairro.text = json.optString("bairro", "-")
        binding.txtCidade.text = json.optString("localidade", "-")
        binding.txtUf.text = json.optString("uf", "-")
        binding.txtDdd.text = json.optString("ddd", "-")

        binding.cardResultado.alpha = 0f
        binding.cardResultado.visibility = View.VISIBLE
        binding.cardResultado.animate().alpha(1f).setDuration(500).start()
    }

    private fun tratarErro(exception: Throwable) {
        val mensagem = when (exception) {
            is UnknownHostException -> "Sem conexão com a internet."
            is SocketTimeoutException -> "A conexão demorou muito."
            is SSLHandshakeException -> "Erro de segurança SSL. Verifique a data/hora do celular."
            else -> {
                if (exception.message == "NOT_FOUND") "CEP não encontrado."
                else "Erro: ${exception.localizedMessage}"
            }
        }
        mostrarErro(mensagem)
    }

    private fun mostrarErro(mensagem: String) {
        binding.cardResultado.visibility = View.GONE
        binding.txtErro.text = mensagem
        binding.txtErro.visibility = View.VISIBLE
    }

    private fun setLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnBuscar.isEnabled = !isLoading
    }

    private fun esconderTeclado() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}