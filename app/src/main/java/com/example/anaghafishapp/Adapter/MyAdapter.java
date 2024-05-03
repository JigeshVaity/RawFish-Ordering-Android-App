package com.example.anaghafishapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.anaghafishapp.Domain.FoodDomain;
import com.example.anaghafishapp.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<FoodDomain> mData;

    // Constructor to initialize the data
    public MyAdapter(List<FoodDomain> data) {
        this.mData = data;
    }



    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.displayproduct, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodDomain item = mData.get(position);
        holder.titleTxt.setText(item.getTitle());
        holder.categoryTxt.setText(item.getDescription());
        // Load image into ImageView using Glide or any other image loading library
        int drawableResourceId = holder.itemView.getContext().getResources()
                .getIdentifier(item.getPic(), "drawable",
                        holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.pic);
        holder.feeEachItem.setText("₹" + String.valueOf(item.getFee())); // Convert Double to String
       // holder.totalEachItem.setText("₹" + Math.round((mData.get(position).getNumberInCart() * mData.get(position).getFee())));
        holder.num.setText(String.valueOf(mData.get(position).getNumberInCart()));
        // Set other views accordingly
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTxt,categoryTxt, feeEachItem, totalEachItem,num;
        public ImageView pic;
        public ViewHolder(View view) {
            super(view);
            titleTxt = view.findViewById(R.id.titleTxt);
            categoryTxt = view.findViewById(R.id.categoryTxt);
            pic = view.findViewById(R.id.pic);
            feeEachItem = view.findViewById(R.id.feeEachItem);
            //totalEachItem = view.findViewById(R.id.totalEachItem);
            num = itemView.findViewById(R.id.numberItemTxt);
            // Initialize other views if needed
        }
    }
}
