package com.example.anaghafishapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.anaghafishapp.Domain.FoodDomain;
import com.example.anaghafishapp.Interface.ChangeNumberItemsListener;
import com.example.anaghafishapp.R;
import com.example.anaghafishapp.helper.ManagementCart;

import java.util.ArrayList;


public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    public ArrayList<FoodDomain> listFoodSelected;

    private ManagementCart managementCart;
    ChangeNumberItemsListener changeNumberItemsListener;

    public CartListAdapter(ArrayList<FoodDomain> recommendedDomains, Context context,ChangeNumberItemsListener changeNumberItemsListener) {
        this.listFoodSelected= recommendedDomains;
        managementCart = new ManagementCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }
    public ArrayList<FoodDomain> getFoodDomainList() {
        return listFoodSelected;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       // FoodDomain food = listFoodSelected.get(position);

       // holder.title.setText(food.getTitle());
        //String feeWithSymbol = "₹ " + String.valueOf(food.getFee());
        //holder.feeEachItem.setText(feeWithSymbol);

        holder.title.setText(listFoodSelected.get(position).getTitle());
        holder.feeEachItem.setText("₹" + listFoodSelected.get(position).getFee());
        holder.totalEachItem.setText("₹" + Math.round((listFoodSelected.get(position).getNumberInCart() * listFoodSelected.get(position).getFee())));
        holder.num.setText(String.valueOf(listFoodSelected.get(position).getNumberInCart()));
        holder.categoryTxt.setText(listFoodSelected.get(position).getCategory());

        int drawableResourceId = holder.itemView.getContext().getResources()
                .getIdentifier(listFoodSelected.get(position).getPic(), "drawable",
                        holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.pic);

        holder.plusItem.setOnClickListener(v -> managementCart.plusNumberFood(listFoodSelected, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.changed();
        }));

        holder.minusItem.setOnClickListener(v -> managementCart.minusNumberFood(listFoodSelected, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.changed();
        }));

        holder.deletercartitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove the item from the cart
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    managementCart.removeFromCart(listFoodSelected, clickedPosition, changeNumberItemsListener);
                    // Notify the adapter that an item is removed
                    notifyItemRemoved(clickedPosition);
                }
            }
        });

        holder.itemView.setTag(position);

        // Set OnClickListener for saveforlater button
        holder.saveforlater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the stored position from the itemView's tag
                int clickedPosition = (int) holder.itemView.getTag();

                if (clickedPosition != RecyclerView.NO_POSITION) {
                    // Get the item at the clicked position
                    FoodDomain item = listFoodSelected.get(clickedPosition);

                    // Add the item to the wishlist
                    managementCart.insertToWishlist(item);

                    // Remove the item from the cart
                    managementCart.removeFromCart(listFoodSelected, clickedPosition, changeNumberItemsListener);

                    // Notify the adapter that an item is removed
                    notifyItemRemoved(clickedPosition);
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
        ImageView pic,plusItem,minusItem;

        TextView feeEachItem,categoryTxt;
        TextView totalEachItem,num;
        Button saveforlater,deletercartitem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            pic = itemView.findViewById(R.id.pic);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            plusItem = itemView.findViewById(R.id.plusCartBtn);
            minusItem = itemView.findViewById(R.id.minusCartBtn);
            num = itemView.findViewById(R.id.numberItemTxt);
            categoryTxt = itemView.findViewById(R.id.categoryTxt);
            saveforlater = itemView.findViewById(R.id.saveforlater);
            deletercartitem = itemView.findViewById(R.id.delectcartitem);
        }
    }

}
