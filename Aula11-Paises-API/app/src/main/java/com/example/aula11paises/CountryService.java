package com.example.aula11paises;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CountryService {
    @GET("name/{name}")
    Call<List<Country>> getCountryByName(@Path("name") String name);
}