package com.example.aula10_api_cep;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ViaCEPService {
    @GET("{cep}/json/")
    Call<CEP> getCEP(@Path("cep") String cep);
}