package com.example.aula11_moeda;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CurrencyApiService {
    private static final String BASE_URL = "https://api.exchangerate-api.com/v4/latest/";
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    public interface ApiCallback {
        void onSuccess(JSONObject response);
        void onError(String message);
    }

    public void getLatestRates(String baseCurrency, ApiCallback callback) {
        executor.execute(() -> {
            try {
                URL url = new URL(BASE_URL + baseCurrency.toLowerCase());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    JSONObject jsonResponse = new JSONObject(response.toString());
                    handler.post(() -> callback.onSuccess(jsonResponse));
                } else {
                    handler.post(() -> callback.onError("Erro na requisição: " + responseCode));
                }
            } catch (Exception e) {
                handler.post(() -> callback.onError("Falha na conexão: " + e.getMessage()));
            }
        });
    }
}