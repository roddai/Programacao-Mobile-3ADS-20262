package com.example.aula11_desafio_apismoedaepases;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.Locale;

public class CountryActivity extends AppCompatActivity {

    private TextInputEditText etCountrySearch;
    private MaterialButton btnFetchCountry;
    private ProgressBar pbCountry;
    private MaterialCardView cardCountryResult;
    private ImageView ivFlag;
    private TextView tvCountryName, tvCapital, tvRegion, tvPopulation;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        etCountrySearch = findViewById(R.id.etCountrySearch);
        btnFetchCountry = findViewById(R.id.btnFetchCountry);
        pbCountry = findViewById(R.id.pbCountry);
        cardCountryResult = findViewById(R.id.cardCountryResult);
        ivFlag = findViewById(R.id.ivFlag);
        tvCountryName = findViewById(R.id.tvCountryName);
        tvCapital = findViewById(R.id.tvCapital);
        tvRegion = findViewById(R.id.tvRegion);
        tvPopulation = findViewById(R.id.tvPopulation);

        requestQueue = Volley.newRequestQueue(this);

        btnFetchCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchCountryData();
            }
        });
    }

    private void fetchCountryData() {
        String countryName = etCountrySearch.getText().toString().trim();

        if (countryName.isEmpty()) {
            Toast.makeText(this, "Por favor, digite o nome de um país", Toast.LENGTH_SHORT).show();
            return;
        }

        String encodedCountry = "";
        try {
            encodedCountry = URLEncoder.encode(countryName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = "https://restcountries.com/v3.1/name/" + encodedCountry;

        pbCountry.setVisibility(View.VISIBLE);
        cardCountryResult.setVisibility(View.GONE);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        pbCountry.setVisibility(View.GONE);
                        try {
                            if (response.length() > 0) {
                                JSONObject countryObj = response.getJSONObject(0);
                                displayCountryInfo(countryObj);
                            } else {
                                Toast.makeText(CountryActivity.this, "País não encontrado", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(CountryActivity.this, "Erro ao processar dados", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pbCountry.setVisibility(View.GONE);
                        if (error.networkResponse != null && error.networkResponse.statusCode == 404) {
                            Toast.makeText(CountryActivity.this, "País não encontrado", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CountryActivity.this, "Erro de conexão ou país inválido", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        requestQueue.add(request);
    }

    private void displayCountryInfo(JSONObject countryObj) throws JSONException {
        // Nome Oficial
        JSONObject nameObj = countryObj.getJSONObject("name");
        String officialName = nameObj.getString("official");
        
        // Capital (pode ser um array)
        String capital = "N/A";
        if (countryObj.has("capital")) {
            JSONArray capitals = countryObj.getJSONArray("capital");
            if (capitals.length() > 0) {
                capital = capitals.getString(0);
            }
        }

        // Região
        String region = countryObj.getString("region");

        // População
        long population = countryObj.getLong("population");
        String formattedPopulation = NumberFormat.getNumberInstance(Locale.getDefault()).format(population);

        // Bandeira
        JSONObject flagsObj = countryObj.getJSONObject("flags");
        String flagUrl = flagsObj.getString("png");

        // Atualizar UI
        tvCountryName.setText(officialName);
        tvCapital.setText("Capital: " + capital);
        tvRegion.setText("Região: " + region);
        tvPopulation.setText("População: " + formattedPopulation);

        Glide.with(this)
                .load(flagUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(ivFlag);

        cardCountryResult.setVisibility(View.VISIBLE);
    }
}
