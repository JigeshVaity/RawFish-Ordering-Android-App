package com.example.anaghafishapp.use;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.anaghafishapp.R;
import com.example.anaghafishapp.retrieve.Product;
import com.squareup.picasso.Picasso;

public class ShowDetailFragmenttwo extends Fragment {

    private static final String ARG_SELECTED_PRODUCT = "selected_product";

    private Product selectedProduct;

    public ShowDetailFragmenttwo() {
        // Required empty public constructor
    }

    public static ShowDetailFragmenttwo newInstance(Product selectedProduct) {
        ShowDetailFragmenttwo fragment = new ShowDetailFragmenttwo();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SELECTED_PRODUCT, selectedProduct);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedProduct = getArguments().getParcelable(ARG_SELECTED_PRODUCT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vendorshowdetail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView foodPic = view.findViewById(R.id.foodPic);
        TextView titleTxt = view.findViewById(R.id.titleTxt);
        TextView nameTxt = view.findViewById(R.id.nameTxt);
        TextView priceTxt = view.findViewById(R.id.priceTxt);
        TextView starTxt = view.findViewById(R.id.starTxt);
        TextView qunatityTxt = view.findViewById(R.id.quantityTxt);
        TextView sizeTxt = view.findViewById(R.id.sizeTxt);
        TextView totalPriceTxt = view.findViewById(R.id.totalPriceTxt);
        ImageView minusBtn = view.findViewById(R.id.minusCartBtn);
        ImageView plusBtn = view.findViewById(R.id.plusCartBtn);


        if (selectedProduct != null) {
            // Load image using a library like Picasso or Glide
            Picasso.get().load(selectedProduct.getImage_url()).into(foodPic);
            titleTxt.setText(selectedProduct.getProduct_name());
            nameTxt.setText(selectedProduct.getVendor_name());
            //

            qunatityTxt.setText(selectedProduct.getQuantity());
            sizeTxt.setText(selectedProduct.getSize());
            starTxt.setText("0.0");


            int initialTotalPrice = Integer.parseInt(selectedProduct.getPrice()); // Replace with the actual price attribute
            totalPriceTxt.setText(String.valueOf(initialTotalPrice));

            // Plus button click listener
            plusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentTotalPrice = Integer.parseInt(totalPriceTxt.getText().toString());
                    int updatedTotalPrice = currentTotalPrice + Integer.parseInt(selectedProduct.getPrice());
                    totalPriceTxt.setText(String.valueOf(updatedTotalPrice));
                }
            });
            if (selectedProduct.getPrice() != null) {
                priceTxt.setText("₹ " + selectedProduct.getPrice());
            } else {
                priceTxt.setText("Price not available");
            }
            // Minus button click listener
            minusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentTotalPrice = Integer.parseInt(totalPriceTxt.getText().toString());
                    if (currentTotalPrice > Integer.parseInt(selectedProduct.getPrice())) {
                        int updatedTotalPrice = currentTotalPrice - Integer.parseInt(selectedProduct.getPrice());
                        totalPriceTxt.setText(String.valueOf(updatedTotalPrice));
                    }
                }
            });


        }
    }
}