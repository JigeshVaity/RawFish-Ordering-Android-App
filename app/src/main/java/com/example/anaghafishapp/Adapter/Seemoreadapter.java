package com.example.anaghafishapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anaghafishapp.R;
import com.example.anaghafishapp.Useractivity.SeeMVendorFragment;
import com.example.anaghafishapp.retrieve.Product;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;


public class Seemoreadapter extends FirebaseRecyclerAdapter<Product, Seemoreadapter.myviewholder>
{

    private final SeeMVendorFragment fragment;

    public Seemoreadapter(@NonNull FirebaseRecyclerOptions<Product> options, SeeMVendorFragment fragment) {
        super(options);
        this.fragment = fragment;
    }
    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Product model)
    {

        // Bind the data to the ViewHolder views
        holder.productNameTextView.setText(model.getProduct_name());
        holder.vendorNameTextView.setText(model.getVendor_name());
        // Load image using a library like Picasso or Glide
        Picasso.get().load(model.getImage_url()).into(holder.imageView);
//        holder.phoneNumberTextView.setText(product.getPhoneNumber());
        holder.priceTextView.setText(model.getPrice());


        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Product selectedProduct = getItem(position);
                    if (selectedProduct != null) {
                        // Pass the selected product to the SeeMVendorFragment
                        fragment.openVendorShowDetail(selectedProduct);
                    }
                }
            }
        });


    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
       return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        public TextView productNameTextView;
        public TextView vendorNameTextView;
        public ImageView imageView;
        public TextView phoneNumberTextView;
        public TextView priceTextView;
        public Button addBtn;
        public myviewholder(@NonNull View itemView)
        {
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
