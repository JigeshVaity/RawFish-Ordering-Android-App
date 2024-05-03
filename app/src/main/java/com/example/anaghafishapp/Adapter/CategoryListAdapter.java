package com.example.anaghafishapp.Adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.anaghafishapp.Domain.FoodDomain;
import com.example.anaghafishapp.R;
import com.example.anaghafishapp.helper.ShowDetailFragment;

import java.util.ArrayList;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {
    private ArrayList<FoodDomain> cartlistDomains;
    private Fragment fragment;

    public CategoryListAdapter(ArrayList<FoodDomain> recommendedDomains, Fragment fragment) {
        this.cartlistDomains = recommendedDomains;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_categorylist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodDomain food = cartlistDomains.get(position);

        holder.title.setText(food.getTitle());
        holder.size.setText(food.getSize());
        holder.quantitytext.setText(food.getQuantity());

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(food.getPic(), "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.pic);

        if (food.getFee() != null) {
            double fee = food.getFee();

            // Calculate the original fee and set it to originaltxt
            double originalFee = fee * 100 / (100 - food.getPercentage());
            holder.originaltxt.setText(String.format("%.1f", originalFee));
            holder.originaltxt.setPaintFlags(holder.originaltxt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            // Set the feeTxt to the fee with only one decimal value
            holder.fee.setText(String.format("₹%.1f", fee));
        } else {
            holder.fee.setText("Price not available");
        }

        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && position < cartlistDomains.size()) {
                    FoodDomain selectedFood = cartlistDomains.get(position);
                    if (selectedFood != null) {
                        // Create a new instance of ShowDetailFragment with the selected FoodDomain
                        ShowDetailFragment showDetailFragment = ShowDetailFragment.newInstance(selectedFood);

                        // Use FragmentTransaction to display the fragment
                        FragmentTransaction fragmentTransaction = fragment.requireActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, showDetailFragment); // Use your container view id
                        fragmentTransaction.addToBackStack(null); // Add to the back stack if needed
                        fragmentTransaction.commit();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartlistDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView pic;
        LinearLayout addBtn;
        TextView fee,per,originaltxt,size,quantitytext;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            pic = itemView.findViewById(R.id.pic);
            addBtn = itemView.findViewById(R.id.addBtn);
            fee = itemView.findViewById(R.id.priceTxt);
            per = itemView.findViewById(R.id.percentagetxt);
            originaltxt = itemView.findViewById(R.id.originaltxt);
            size = itemView.findViewById(R.id.sizett);
            quantitytext = itemView.findViewById(R.id.quantitytext);
        }
    }
}
