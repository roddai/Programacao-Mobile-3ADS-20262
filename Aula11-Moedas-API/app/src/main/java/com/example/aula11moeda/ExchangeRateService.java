package com.example.aula11moeda;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ExchangeRateService {
    @GET("v4/latest/{base}")
    Call<ExchangeRateResponse> getLatestRates(@Path("base") String base);
}
