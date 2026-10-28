package com.example.aula11paises;

import android.content.Context;
import android.os.Bundle;
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

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText etCountryName;
    private Button btnSearch;
    private ImageView ivFlag;
    private TextView tvCountryName, tvCapital, tvRegion, tvPopulation;
    private MaterialCardView cvResult;
    private ProgressBar progressBar;

    private CountryService countryService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar componentes da UI
        etCountryName = findViewById(R.id.etCountryName);
        btnSearch = findViewById(R.id.btnSearch);
        ivFlag = findViewById(R.id.ivFlag);
        tvCountryName = findViewById(R.id.tvCountryName);
        tvCapital = findViewById(R.id.tvCapital);
        tvRegion = findViewById(R.id.tvRegion);
        tvPopulation = findViewById(R.id.tvPopulation);
        cvResult = findViewById(R.id.cvResult);
        progressBar = findViewById(R.id.progressBar);

        // Configurar Retrofit com OkHttpClient "inseguro" para ignorar erros de certificado SSL
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://restcountries.com/v3.1/")
                .client(getUnsafeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        countryService = retrofit.create(CountryService.class);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchCountry();
            }
        });
    }

    private void searchCountry() {
        String countryName = etCountryName.getText().toString().trim();

        if (countryName.isEmpty()) {
            Toast.makeText(this, "Por favor, informe o nome de um país.", Toast.LENGTH_SHORT).show();
            return;
        }

        esconderTeclado();
        progressBar.setVisibility(View.VISIBLE);
        cvResult.setVisibility(View.GONE);

        countryService.getCountryByName(countryName).enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    Country country = response.body().get(0);
                    updateUI(country);
                } else {
                    Toast.makeText(MainActivity.this, "País não encontrado.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateUI(Country country) {
        cvResult.setVisibility(View.VISIBLE);
        tvCountryName.setText(country.name.common);
        tvCapital.setText("🏛 Capital: " + (country.capital != null && !country.capital.isEmpty() ? country.capital.get(0) : "N/A"));
        tvRegion.setText("🌍 Região: " + country.region);
        
        String formattedPopulation = NumberFormat.getNumberInstance(Locale.GERMAN).format(country.population);
        tvPopulation.setText("👥 População: " + formattedPopulation);

        if (country.flags != null && country.flags.png != null) {
            Glide.with(this)
                    .load(country.flags.png)
                    .placeholder(android.R.drawable.progress_horizontal)
                    .error(android.R.drawable.stat_notify_error)
                    .into(ivFlag);
        }
    }

    private void esconderTeclado() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            };

            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}