package com.example.anaghafishapp.retrieve;

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

import com.example.anaghafishapp.R;
import com.example.anaghafishapp.use.ShowDetailFragmenttwo;
import com.example.anaghafishapp.vendor.vendorshowdetail;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private final List<Product> productList;

    private final SelectedItemsViewModel selectedItemsViewModel;

    public ProductAdapter(List<Product> productList, SelectedItemsViewModel selectedItemsViewModel) {
        this.productList = productList;
        this.selectedItemsViewModel = selectedItemsViewModel;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        // Bind the data to the ViewHolder views
        holder.productNameTextView.setText(product.getProduct_name());
        holder.vendorNameTextView.setText(product.getVendor_name());
        // Load image using a library like Picasso or Glide
        Picasso.get().load(product.getImage_url()).into(holder.imageView);
//        holder.phoneNumberTextView.setText(product.getPhoneNumber());
        holder.priceTextView.setText(product.getPrice());


        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && position <productList.size()) {
                    Product selectedFood = productList.get(position);
                    if (selectedFood != null) {
                        // Create a new instance of ShowDetailFragment with the selected FoodDomain
                        vendorshowdetail showDetailFragment = vendorshowdetail.newInstance(selectedFood);

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
        return productList.size();
    }
    // Method to update the list of products

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView productNameTextView;
        public TextView vendorNameTextView;
        public ImageView imageView;
        public TextView phoneNumberTextView;
        public TextView priceTextView;
        public Button addBtn;


        public ProductViewHolder(View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productName);
            vendorNameTextView = itemView.findViewById(R.id.vendorName);
            imageView = itemView.findViewById(R.id.productImage);
            //phoneNumberTextView = itemView.findViewById(R.id.phone_number);
            priceTextView = itemView.findViewById(R.id.productPrice);

            addBtn = itemView.findViewById(R.id.addBtn);
        }
    }


}