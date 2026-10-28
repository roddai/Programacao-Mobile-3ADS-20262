package com.example.aula11_paises_api;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText editTextCountry;
    private Button buttonSearch;
    private ImageView imageViewFlag;
    private TextView textViewName, textViewCapital, textViewRegion, textViewPopulation;
    private RequestQueue requestQueue;

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

        editTextCountry = findViewById(R.id.editTextCountry);
        buttonSearch = findViewById(R.id.buttonSearch);
        imageViewFlag = findViewById(R.id.imageViewFlag);
        textViewName = findViewById(R.id.textViewName);
        textViewCapital = findViewById(R.id.textViewCapital);
        textViewRegion = findViewById(R.id.textViewRegion);
        textViewPopulation = findViewById(R.id.textViewPopulation);

        requestQueue = Volley.newRequestQueue(this);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String countryName = editTextCountry.getText().toString().trim();
                if (!countryName.isEmpty()) {
                    clearFields();
                    searchCountry(countryName);
                } else {
                    Toast.makeText(MainActivity.this, "Por favor, digite o nome de um país", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void clearFields() {
        textViewName.setText("");
        textViewCapital.setText("");
        textViewRegion.setText("");
        textViewPopulation.setText("");
        imageViewFlag.setImageDrawable(null);
    }

    private void searchCountry(String countryName) {
        String encodedCountry = Uri.encode(countryName);
        String url = "https://restcountries.com/v3.1/name/" + encodedCountry;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if (response.length() > 0) {
                                JSONObject country = response.getJSONObject(0);

                                // Nome comum
                                String name = country.getJSONObject("name").optString("common", "N/A");
                                
                                // Capital (pode ser uma lista ou estar ausente)
                                String capital = "N/A";
                                if (country.has("capital")) {
                                    JSONArray capitals = country.getJSONArray("capital");
                                    if (capitals.length() > 0) {
                                        capital = capitals.getString(0);
                                    }
                                }

                                // Região
                                String region = country.optString("region", "N/A");

                                // População formatada
                                long populationValue = country.optLong("population", 0);
                                String population = NumberFormat.getNumberInstance(new Locale("pt", "BR")).format(populationValue);

                                // Bandeira
                                String flagUrl = "";
                                if (country.has("flags")) {
                                    flagUrl = country.getJSONObject("flags").optString("png", "");
                                }

                                textViewName.setText("País: " + name);
                                textViewCapital.setText("Capital: " + capital);
                                textViewRegion.setText("Região: " + region);
                                textViewPopulation.setText("População: " + population);

                                if (!flagUrl.isEmpty()) {
                                    Glide.with(MainActivity.this)
                                            .load(flagUrl)
                                            .placeholder(android.R.drawable.progress_horizontal)
                                            .error(android.R.drawable.stat_notify_error)
                                            .into(imageViewFlag);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Erro ao processar dados da API", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null && error.networkResponse.statusCode == 404) {
                    Toast.makeText(MainActivity.this, "País não encontrado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Falha na conexão ou erro na API", Toast.LENGTH_SHORT).show();
                }
            }
        });

        requestQueue.add(request);
    }
}