package com.example.assignment.presenter;

import com.example.assignment.model.ProductModel;

public interface HomeUsecase {
    interface HomeView {
        void showProgress();
        void hideProgress();
        void showError();
        void updateProduct(ProductModel model);
    }

    interface HomePresenter {
        void getProduct();
    }

}
