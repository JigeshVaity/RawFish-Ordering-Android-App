package com.example.anaghafishapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.anaghafishapp.Domain.FoodDomain;
import com.example.anaghafishapp.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ProductViewHolder> {
    private final List<FoodDomain> productList;

    public OrderAdapter(List<FoodDomain> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        FoodDomain product = productList.get(position);
        holder.titleTxt.setText(product.getTitle());
        holder.categoryTxt.setText(product.getCategory());
        holder.feeTxt.setText(String.valueOf(product.getFee()));

        // Load image using Picasso
        Picasso.get().load(product.getPic()).into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTxt;
        public TextView categoryTxt;
        public TextView feeTxt;
        public ImageView pic;

        public ProductViewHolder(View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            categoryTxt = itemView.findViewById(R.id.categorytxt);
            feeTxt = itemView.findViewById(R.id.totalEachItem);
            pic = itemView.findViewById(R.id.pic); // Assuming you have an ImageView in your item layout
        }
    }
}
