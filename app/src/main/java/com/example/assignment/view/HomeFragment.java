package com.example.assignment.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.assignment.R;
import com.example.assignment.model.ProductModel;
import com.example.assignment.model.Products;
import com.example.assignment.presenter.HomePresenter;
import com.example.assignment.presenter.HomeUsecase;
import com.example.assignment.utils.RecyclerViewItemDecoration;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment implements HomeUsecase.HomeView,
        View.OnClickListener {
    private Context mContext;
    private final String TAG = HomeFragment.class.getSimpleName();
    private Button vBtnINR;
    private Button vBtnAED;
    private Button vBtnSAR;
    private TextView vTVTitle;
    private ProductItemAdapter mProductItemAdapter;
    private ProductModel mProductModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        HomeUsecase.HomePresenter presenter = new HomePresenter(this);
        presenter.getProduct();
        return view;
    }

    /**
     * initializing all view here
     * passing view instance to initialize all the item in the view
     *
     * @param view
     */
    private void init(View view) {
        vBtnINR = view.findViewById(R.id.btn_inr);
        vBtnAED = view.findViewById(R.id.btn_aed);
        vBtnSAR = view.findViewById(R.id.btn_sar);
        vTVTitle = view.findViewById(R.id.tv_title);
        RecyclerView vRVProducts = view.findViewById(R.id.rv_products);
        vBtnAED.setOnClickListener(this);
        vBtnSAR.setOnClickListener(this);
        vBtnINR.setOnClickListener(this);

        // creating mProductItemAdapter class for recycler view
        mProductItemAdapter = new ProductItemAdapter(new ArrayList<Products>(), mContext);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.margin_10);
        vRVProducts.addItemDecoration(new RecyclerViewItemDecoration(spacingInPixels,
                getResources().getDimensionPixelSize(R.dimen.margin_5)
                , getResources().getDimensionPixelSize(R.dimen.margin_5)));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        vRVProducts.setLayoutManager(gridLayoutManager);
        vRVProducts.setNestedScrollingEnabled(false);
        vRVProducts.setAdapter(mProductItemAdapter);
    }

    @Override
    public void showProgress() {
        Log.d(TAG, "Show Progress");
    }

    @Override
    public void hideProgress() {
        Log.d(TAG, "Hide Progress");
    }

    @Override
    public void showError() {
        Log.d(TAG, "Error");
    }

    @Override
    public void updateProduct(ProductModel model) {
        Log.d(TAG, model.toString());
        mProductModel = model;
        vTVTitle.setText(model.getTitle());
        mProductItemAdapter.updateList(model.getProducts(), "", mProductModel.getConversion());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_inr:
                vBtnINR.setBackground(mContext.getDrawable(R.drawable.btn_bg_pressed));
                vBtnAED.setBackground(mContext.getDrawable(R.drawable.btn_background));
                vBtnSAR.setBackground(mContext.getDrawable(R.drawable.btn_background));
                mProductItemAdapter.updateList(mProductModel.getProducts(),
                        "INR", mProductModel.getConversion());
                break;
            case R.id.btn_aed:
                vBtnINR.setBackground(mContext.getDrawable(R.drawable.btn_background));
                vBtnAED.setBackground(mContext.getDrawable(R.drawable.btn_bg_pressed));
                vBtnSAR.setBackground(mContext.getDrawable(R.drawable.btn_background));
                mProductItemAdapter.updateList(mProductModel.getProducts(), "AED",
                        mProductModel.getConversion());
                break;
            case R.id.btn_sar:
                vBtnINR.setBackground(mContext.getDrawable(R.drawable.btn_background));
                vBtnAED.setBackground(mContext.getDrawable(R.drawable.btn_background));
                vBtnSAR.setBackground(mContext.getDrawable(R.drawable.btn_bg_pressed));
                mProductItemAdapter.updateList(mProductModel.getProducts(), "SAR",
                        mProductModel.getConversion());
                break;
        }
    }
}
