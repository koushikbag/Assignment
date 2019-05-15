package com.example.assignment.network;

import com.example.assignment.model.ProductModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IRetrofitClient {
    @GET("common/json/assignment.json")
    Call<ProductModel> getProduct();
}
