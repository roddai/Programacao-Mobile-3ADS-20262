package com.example.aula10_api_cep;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ViaCepService {
    @GET("{cep}/json/")
    Call<CepResponse> buscarCep(@Path("cep") String cep);
}
