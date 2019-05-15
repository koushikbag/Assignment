package com.example.assignment.presenter;

import com.example.assignment.model.ProductModel;
import com.example.assignment.network.RetrofitService;
import com.example.assignment.view.HomeFragment;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements HomeUsecase.HomePresenter {
    private HomeUsecase.HomeView mHomeView;

    public HomePresenter(HomeFragment mHomeView) {
        this.mHomeView = mHomeView;

    }

    @Override
    public void getProduct() {
        mHomeView.showProgress();
        Call<ProductModel> call = RetrofitService.getService().getProduct();
        call.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                mHomeView.hideProgress();
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    mHomeView.updateProduct(response.body());
                } else {
                    mHomeView.showError();
                }
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                mHomeView.hideProgress();
                mHomeView.showError();
            }
        });
    }
}
