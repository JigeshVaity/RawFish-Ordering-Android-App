package com.example.anaghafishapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.anaghafishapp.Domain.FoodDomain;
import com.example.anaghafishapp.Interface.ChangeNumberItemsListener;
import com.example.anaghafishapp.R;
import com.example.anaghafishapp.helper.ManagementCart;
import com.example.anaghafishapp.helper.ShowDetailFragment;

import java.util.ArrayList;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {
    private ArrayList<FoodDomain> listFoodSelected;
    private ManagementCart managementCart;
    private ChangeNumberItemsListener changeNumberItemsListener;

    public WishlistAdapter(ArrayList<FoodDomain> recommendedDomains, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.listFoodSelected = recommendedDomains;
        this.managementCart = new ManagementCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_wishlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(listFoodSelected.get(position).getTitle());
        holder.feeEachItem.setText("₹" + listFoodSelected.get(position).getFee());

        int drawableResourceId = holder.itemView.getContext().getResources()
                .getIdentifier(listFoodSelected.get(position).getPic(), "drawable",
                        holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.pic);

        /*holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove the item from the list
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    listFoodSelected.remove(clickedPosition);
                    // Notify the adapter that an item is removed
                    notifyItemRemoved(clickedPosition);
                }
            }
        });*/
        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove the item from the wishlist
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    managementCart.removeFromWish(listFoodSelected, clickedPosition, changeNumberItemsListener);
                    // Notify the adapter that an item is removed
                    notifyItemRemoved(clickedPosition);
                }
            }
        });

        /*holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the clicked item position
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    // Remove the item from the wishlist
                    managementCart.removeFromWish(listFoodSelected, clickedPosition, changeNumberItemsListener);
                    // Notify the adapter that an item is removed
                    notifyItemRemoved(clickedPosition);
                }
            }
        });*/


        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && position < listFoodSelected.size()) {
                    FoodDomain selectedFood = listFoodSelected.get(position);
                    if (selectedFood != null) {
                        // Create a new instance of ShowDetailFragment with the selected FoodDomain
                        ShowDetailFragment showDetailFragment = ShowDetailFragment.newInstance(selectedFood);

                        // Use FragmentTransaction to display the fragment
                        FragmentTransaction fragmentTransaction = ((AppCompatActivity) holder.itemView.getContext()).getSupportFragmentManager().beginTransaction();
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
        return listFoodSelected.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView pic;
        TextView feeEachItem;
        Button removeBtn,addBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            pic = itemView.findViewById(R.id.pic);
            feeEachItem = itemView.findViewById(R.id.fee);
            removeBtn = itemView.findViewById(R.id.removeitem);
            addBtn = itemView.findViewById(R.id.addBtn);
        }
    }
}