package com.example.anaghafishapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.anaghafishapp.Domain.FoodDomain;
import com.example.anaghafishapp.helper.ShowDetailFragment;

import java.util.ArrayList;

public class YourAdapter extends RecyclerView.Adapter<YourAdapter.ViewHolder> {
    private ArrayList<FoodDomain> cartlistDomains;
    private Fragment fragment;

    public YourAdapter(ArrayList<FoodDomain> recommendedDomains, Fragment fragment) {
        this.cartlistDomains = recommendedDomains;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_vendorlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodDomain food = cartlistDomains.get(position);

        holder.title.setText(food.getTitle());
        String feeWithSymbol = "₹ " + String.valueOf(food.getFee());
        holder.pricetext.setText(feeWithSymbol);
        holder.nametext.setText(food.getName());

        Glide.with(holder.itemView.getContext()).load(food.getPic()).into(holder.pic);

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
        Button addBtn;
        TextView pricetext;
        TextView nametext;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.fishtext);
            pic = itemView.findViewById(R.id.img1);
            addBtn = itemView.findViewById(R.id.addBtn);
            pricetext = itemView.findViewById(R.id.pricetext);
            nametext = itemView.findViewById(R.id.nametext);
        }
    }
}
