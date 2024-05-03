package com.example.anaghafishapp.vendor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.anaghafishapp.ManagementCartone;
import com.example.anaghafishapp.R;
import com.example.anaghafishapp.helper.ManagementCart;
import com.example.anaghafishapp.retrieve.Product;

import java.util.ArrayList;
import java.util.List;

public class vendorshowdetail extends Fragment {
    private Button addToCartBtn, addToWishlistBtn;
    private TextView titleTxt, nametxt, feeTxt, qautxt, numberOrderTxt, totalPriceTxt,originaltxt,productsize;
    private ImageView plusBtn, minusBtn, picFood;
    private Product object;
    private int numberOrder = 1;
    private ManagementCartone managementCart;



    private static final String ARG_PRODUCT = "product";

    private List<Product> wishlist = new ArrayList<>();

    public vendorshowdetail() {
        // Required empty public constructor
    }

    public static vendorshowdetail newInstance(Product product) {
        vendorshowdetail fragment = new vendorshowdetail();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PRODUCT, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        managementCart = new ManagementCartone(requireContext()); // Initialize the managementCart
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vendorshowdetail, container, false);
        initView(view);
        getBundle();
        // Initialize your views
        ImageView shareButton = view.findViewById(R.id.sharebutton);

        // Set click listener for the share button
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareProduct();
            }
        });
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find the backbutton ImageView
        ImageView backButton = view.findViewById(R.id.backbutton);

        // Set OnClickListener for the backButton
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous fragment
                requireActivity().onBackPressed();
            }
        });

    }
    private void getBundle() {
        Bundle args = getArguments();
        if (args != null) {
            object = args.getParcelable(ARG_PRODUCT);
            if (object != null) {
                Glide.with(this)
                        .load(object.getImage_url())
                        .into(picFood);

                titleTxt.setText(object.getProduct_name());
                nametxt.setText(object.getVendor_name());
                if (object.getPrice() != null) {
                    double fee = Double.parseDouble(object.getPrice());

                    // Calculate the original fee and set it to originaltxt
                    double originalFee = fee * 100 / (100 - 10);
                    originaltxt.setText(String.format("%.1f", originalFee));
                    originaltxt.setPaintFlags(originaltxt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    // Set the feeTxt to the fee with only one decimal value
                    feeTxt.setText(String.format("₹%.1f", fee));
                } else {
                    feeTxt.setText("Price not available");
                }

                /*if (object.getPrice() != null) {
                    double fee = Double.parseDouble(object.getPrice());
                    feeTxt.setText(String.format("₹%.1f", fee));
                } else {
                    feeTxt.setText("Price not available");
                }*/
                qautxt.setText(object.getQuantity());
                productsize.setText(object.getQuantity());
                numberOrderTxt.setText(String.valueOf(numberOrder));
                // Calculate and set the initial total price
                double initialTotalPrice = numberOrder * (Double.parseDouble(object.getPrice() != null ? object.getPrice() : "0"));
                totalPriceTxt.setText(String.valueOf(initialTotalPrice));
            }
        }

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOrder > 1) {
                    numberOrder--;
                    numberOrderTxt.setText(String.valueOf(numberOrder));
                    double totalPrice = numberOrder * (Double.parseDouble(object.getPrice() != null ? object.getPrice() : "0"));
                    totalPriceTxt.setText(String.valueOf(totalPrice));
                }
            }
        });

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOrder++;
                numberOrderTxt.setText(String.valueOf(numberOrder));
                double totalPrice = numberOrder * (Double.parseDouble(object.getPrice() != null ? object.getPrice() : "0"));
                totalPriceTxt.setText(String.valueOf(totalPrice));
            }
        });

      /*  addToWishlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.setNumberInCart(numberOrder);
                managementCart.insertToWishlist(object);
                showToast("Added to your Wishlist");
            }
        });*/




    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void initView(View view) {
        addToCartBtn = view.findViewById(R.id.addToCartBtn);
        titleTxt = view.findViewById(R.id.titleTxt);
        nametxt = view.findViewById(R.id.nameTxt);
        feeTxt = view.findViewById(R.id.priceTxt);
        numberOrderTxt = view.findViewById(R.id.numberItemTxt);
        plusBtn = view.findViewById(R.id.plusCartBtn);
        minusBtn = view.findViewById(R.id.minusCartBtn);
        picFood = view.findViewById(R.id.foodPic);
        qautxt = view.findViewById(R.id.quantityTxt);
        originaltxt=view.findViewById(R.id.originaltxt);
        totalPriceTxt = view.findViewById(R.id.totalPriceTxt);
        addToWishlistBtn = view.findViewById(R.id.wishlistbutton);
        productsize = view.findViewById(R.id.productsize);
    }
    private void shareProduct() {
        // Create an intent with ACTION_SEND
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        // Set the MIME type to image/*
        shareIntent.setType("image/*");

        // Get the product image from ImageView and convert it to Bitmap
        BitmapDrawable bitmapDrawable = (BitmapDrawable) picFood.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        // Get the Uri of the Bitmap
        String bitmapPath = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, "Product Image", null);
        Uri bitmapUri = Uri.parse(bitmapPath);

        // Set the image Uri as the content of the shareIntent
        shareIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri);

        // Get the product name and price
        String productName = titleTxt.getText().toString();
        String productPrice =feeTxt.getText().toString();

        // Combine the product name and price into a share message
        String shareMessage = "Check out this product: " + productName + " - Price: " + productPrice;

        // Add the share message to the intent
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);

        // Start the activity for sharing
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }
}
