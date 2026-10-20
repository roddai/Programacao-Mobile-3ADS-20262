package com.example.aula10;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ViaCEPService {
    @GET("{cep}/json/")
    Call<CEP> buscarCEP(@Path("cep") String cep);
}