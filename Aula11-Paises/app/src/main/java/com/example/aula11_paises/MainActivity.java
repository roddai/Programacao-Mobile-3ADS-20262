package com.example.aula11_paises;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String API_BASE_URL = "https://restcountries.com/v3.1/name/";

    private EditText etCountryName;
    private Button btnSearch;
    private TextView tvStatus;
    private ProgressBar progressBar;
    private MaterialCardView cardResult;
    private ImageView ivFlag;
    private TextView tvCountryNameResult;
    private TextView tvCapital;
    private TextView tvRegion;
    private TextView tvPopulation;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

        btnSearch.setOnClickListener(v -> {
            String countryName = etCountryName.getText().toString().trim();
            if (countryName.isEmpty()) {
                showError(getString(R.string.error_empty));
                return;
            }
            hideKeyboard();
            searchCountry(countryName);
        });
    }

    private void initializeViews() {
        etCountryName = findViewById(R.id.etCountryName);
        btnSearch = findViewById(R.id.btnSearch);
        tvStatus = findViewById(R.id.tvStatus);
        progressBar = findViewById(R.id.progressBar);
        cardResult = findViewById(R.id.cardResult);
        ivFlag = findViewById(R.id.ivFlag);
        tvCountryNameResult = findViewById(R.id.tvCountryNameResult);
        tvCapital = findViewById(R.id.tvCapital);
        tvRegion = findViewById(R.id.tvRegion);
        tvPopulation = findViewById(R.id.tvPopulation);
    }

    private void searchCountry(String countryName) {
        showLoading();

        executorService.execute(() -> {
            HttpURLConnection connection = null;
            try {
                String encodedName = URLEncoder.encode(countryName, "UTF-8");
                URL url = new URL(API_BASE_URL + encodedName);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                    in.close();

                    JSONArray jsonArray = new JSONArray(response.toString());
                    if (jsonArray.length() > 0) {
                        parseAndDisplayCountry(jsonArray.getJSONObject(0));
                    } else {
                        showErrorOnMain(getString(R.string.error_not_found));
                    }
                } else if (responseCode == 404) {
                    showErrorOnMain(getString(R.string.error_not_found));
                } else {
                    showErrorOnMain(getString(R.string.error_generic));
                }

            } catch (Exception e) {
                Log.e(TAG, "Error fetching country", e);
                showErrorOnMain(getString(R.string.error_connection));
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        });
    }

    private void parseAndDisplayCountry(JSONObject countryJson) {
        try {
            String commonName = countryJson.getJSONObject("name").getString("common");
            
            String capital = "Capital não informada";
            if (countryJson.has("capital")) {
                JSONArray capitals = countryJson.getJSONArray("capital");
                if (capitals.length() > 0) {
                    capital = capitals.getString(0);
                }
            }

            String region = countryJson.optString("region", "Região não informada");
            long population = countryJson.optLong("population", 0);
            
            String flagUrl = "";
            if (countryJson.has("flags")) {
                flagUrl = countryJson.getJSONObject("flags").optString("png", "");
            }

            final String finalCapital = capital;
            final String finalFlagUrl = flagUrl;
            
            NumberFormat numberFormat = NumberFormat.getInstance(new Locale("pt", "BR"));
            String formattedPopulation = numberFormat.format(population) + " " + getString(R.string.population_suffix);

            mainHandler.post(() -> {
                tvCountryNameResult.setText(commonName);
                tvCapital.setText(finalCapital);
                tvRegion.setText(region);
                tvPopulation.setText(formattedPopulation);

                Glide.with(this)
                        .load(finalFlagUrl)
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .error(android.R.drawable.ic_menu_report_image)
                        .into(ivFlag);

                ivFlag.setContentDescription("Bandeira do " + commonName);
                
                showResult();
            });

        } catch (Exception e) {
            Log.e(TAG, "Error parsing JSON", e);
            showErrorOnMain(getString(R.string.error_generic));
        }
    }

    private void showLoading() {
        tvStatus.setVisibility(View.VISIBLE);
        tvStatus.setText(R.string.loading_message);
        tvStatus.setTextColor(getColor(R.color.text_gray));
        progressBar.setVisibility(View.VISIBLE);
        cardResult.setVisibility(View.GONE);
        btnSearch.setEnabled(false);
    }

    private void showResult() {
        tvStatus.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        cardResult.setVisibility(View.VISIBLE);
        btnSearch.setEnabled(true);
    }

    private void showError(String message) {
        tvStatus.setVisibility(View.VISIBLE);
        tvStatus.setText(message);
        tvStatus.setTextColor(getColor(R.color.error_red));
        progressBar.setVisibility(View.GONE);
        cardResult.setVisibility(View.GONE);
        btnSearch.setEnabled(true);
    }

    private void showErrorOnMain(String message) {
        mainHandler.post(() -> showError(message));
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}