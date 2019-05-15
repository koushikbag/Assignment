package com.example.assignment.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.assignment.R;
import com.example.assignment.model.Conversion;
import com.example.assignment.model.Products;
import com.example.assignment.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemAdapter.ViewHolder> {
    private String mCurrencySymbol;
    private List<Products> mItemList;
    private Context mContext;
    private ArrayList<Conversion> mConversions;

    ProductItemAdapter(ArrayList<Products> mItemList, Context context) {
        this.mItemList = mItemList;
        mContext = context;
        mCurrencySymbol = "";
        mConversions = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(mItemList.get(position).getUrl())
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        Log.d("SUCCESS", "<---------->");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        Log.d("FAILURE", "<---------->");
                        return false;
                    }
                })
                .into(holder.vIVProductImage);

        holder.vTVProductName.setText(mItemList.get(position).getName());
        String formattedValue = "";
        if (mCurrencySymbol.isEmpty()) {
            formattedValue = Utils.getCurrencySymbol(mItemList.get(position).getCurrency()) + " "
                    + mItemList.get(position).getPrice();
        } else {
            Double doubles = Utils.getMappedPrice(mConversions,
                    mItemList.get(position).getCurrency(), mCurrencySymbol);
            Log.d("values: ", " : " + doubles);
            if (!doubles.equals(0.0)) {
                formattedValue = Utils.getCurrencySymbol(mCurrencySymbol) + " "
                        + String.format("%.2f", Double.parseDouble(mItemList.get(position).getPrice())*doubles);            } else
                formattedValue = Utils.getCurrencySymbol(mCurrencySymbol) + " "
                        + mItemList.get(position).getPrice();
        }
        holder.vTVProductPrice.setText(formattedValue);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    void updateList(ArrayList<Products> items,
                    String currencySymbol, ArrayList<Conversion> conversion) {
        mItemList = items;
        mCurrencySymbol = currencySymbol;
        mConversions = conversion;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView vIVProductImage;
        private TextView vTVProductName;
        private TextView vTVProductPrice;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            vIVProductImage = itemView.findViewById(R.id.iv_grid_image);
            vTVProductName = itemView.findViewById(R.id.tv_product_name);
            vTVProductPrice = itemView.findViewById(R.id.tv_product_price);

        }
    }
}
