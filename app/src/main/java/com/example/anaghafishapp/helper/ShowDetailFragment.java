// ShowDetailFragment.java
package com.example.anaghafishapp.helper;

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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.anaghafishapp.Adapter.RecommendedAdapter;
import com.example.anaghafishapp.Domain.FoodDomain;
import com.example.anaghafishapp.R;
import com.example.anaghafishapp.Useractivity.WishlistFragment;

import java.util.ArrayList;
import java.util.List;

public class ShowDetailFragment extends Fragment {
    private RecyclerView  recyclerViewRecommendedList;
    private RecyclerView.Adapter adapter1;
    private RecyclerView.Adapter adapter;
    private Button addToCartBtn,addToWishlistBtn;
    private TextView titleTxt, feeTxt, qautxt, descriptionTxt, numberOrderTxt, totalPriceTxt, starTxt, caloryTxt, timeTxt,categorytxt,originaltxt,percentagetxt;
    private ImageView plusBtn, minusBtn, picFood;
    private FoodDomain object;
    private int numberOrder = 1;
    private ManagementCart managementCart;
    private ImageView wishlistIcon;
    private static final String ARG_FOOD_DOMAIN = "food_domain";

    private List<FoodDomain> wishlist = new ArrayList<>();
    private TextView wishlistCountTextView;

    public ShowDetailFragment() {
        // Required empty public constructor
    }

    public static ShowDetailFragment newInstance(FoodDomain foodDomain) {
        ShowDetailFragment fragment = new ShowDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_FOOD_DOMAIN, foodDomain);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        managementCart = new ManagementCart(requireContext()); // Initialize the managementCart
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_detail, container, false);

        recyclerViewRecommendedList = view.findViewById(R.id.recylerviewdetail);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewRecommendedList.setLayoutManager(layoutManager2);
        adapter1 = new RecommendedAdapter(getRecommendedList());
        recyclerViewRecommendedList.setAdapter(adapter1);
        wishlistIcon = view.findViewById(R.id.wishlist);
        initView(view);
        getBundle();
        // Assuming you have a TextView named wishlistCountTextView in your fragment layout
        wishlistCountTextView = view.findViewById(R.id.wishlistBadge);

        // Retrieve the wishlist count
        int wishlistItemCount = managementCart.getWishlistItemCount();

        // Update the TextView to display the wishlist count
        wishlistCountTextView.setText(String.valueOf(wishlistItemCount));

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
    // Method to handle sharing of the product
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
    private ArrayList<FoodDomain> getRecommendedList() {
        ArrayList<FoodDomain> recommendedList = new ArrayList<>();
        // Add more recommended items here
        recommendedList.add(new FoodDomain("Paplet", "raw", 600.0, "Raw fish",4.6,"1kg" ,"Medium","700",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Paplet fry", "brfood", 500.0,"Raw fish",4.6,"1kg" ,"Medium","600",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Prawn Biryani", "brfishbiryani",400.0, "Raw fish",4.4,"1kg","Medium","500",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Bombill", "bombill", 300.0, "Raw fish",4.1,"1kg" ,"Medium","500",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Prawn", "prawn", 450.0,"Raw fish",4.8,"1kg" ,"Medium","550",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Surmai", "surmai", 400.0,"Raw fish",4.9,"1kg" ,"Medium","500",10,"Raw Fish"));
        // Add more recommended items here
        return recommendedList;
    }
    private void getBundle() {
        Bundle args = getArguments();
        if (args != null) {
            object = (FoodDomain) args.getSerializable(ARG_FOOD_DOMAIN);
            if (object != null) {
                int drawableResourceId = getResources().getIdentifier(object.getPic(), "drawable", requireContext().getPackageName());

                Glide.with(this)
                        .load(drawableResourceId)
                        .into(picFood);

                titleTxt.setText(object.getTitle());

                if (object.getFee() != null) {
                    double fee = object.getFee();

                    // Calculate the original fee and set it to originaltxt
                    double originalFee = fee * 100 / (100 - object.getPercentage());
                    originaltxt.setText(String.format("%.1f", originalFee));
                    originaltxt.setPaintFlags(originaltxt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    // Set the feeTxt to the fee with only one decimal value
                    feeTxt.setText(String.format("₹%.1f", fee));
                } else {
                    feeTxt.setText("Price not available");
                }

                descriptionTxt.setText(object.getDescription());
                numberOrderTxt.setText(String.valueOf(numberOrder));
                caloryTxt.setText(object.getSize());
                starTxt.setText(String.valueOf(object.getStar()));
                timeTxt.setText(object.getQuantity());
                qautxt.setText(object.getQuantity());
                // Calculate and set the initial total price
                double initialTotalPrice = numberOrder * (object.getFee() != null ? object.getFee() : 0);
                totalPriceTxt.setText(String.valueOf(initialTotalPrice));

                categorytxt.setText(object.getCategory());
                percentagetxt.setText(String.valueOf(object.getPercentage()) + "%");
            }
        }

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOrder > 1) {
                    numberOrder = numberOrder - 1;
                }
                numberOrderTxt.setText(String.valueOf(numberOrder));
                totalPriceTxt.setText(String.valueOf(numberOrder * object.getFee()));
            }
        });
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOrder = numberOrder + 1;
                numberOrderTxt.setText(String.valueOf(numberOrder));
                totalPriceTxt.setText(String.valueOf(numberOrder * (object.getFee() != null ? object.getFee() : 0)));
            }
        });

        // Similar logic for the minus button...

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Your logic to add the item to the cart...
                object.setNumberInCart(numberOrder);
                managementCart.insertFood(object);
            }
        });

        // for add item in wishlist
        addToWishlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.setNumberInCart(numberOrder);
                managementCart.insertToWishlist(object);
            }
        });

        wishlistIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace the current fragment with the WishlistFragment
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new WishlistFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }
    public void setData(List<FoodDomain> dataList) {
        this.wishlist = dataList;
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
    private void initView(View view) {
        // Initialize your views here...
        addToCartBtn = view.findViewById(R.id.addToCartBtn);
        titleTxt = view.findViewById(R.id.titleTxt);
        feeTxt = view.findViewById(R.id.priceTxt);
        descriptionTxt = view.findViewById(R.id.descriptionTxt);
        numberOrderTxt = view.findViewById(R.id.numberItemTxt);
        plusBtn = view.findViewById(R.id.plusCartBtn);
        minusBtn = view.findViewById(R.id.minusCartBtn);
        picFood = view.findViewById(R.id.foodPic);
        totalPriceTxt = view.findViewById(R.id.totalPriceTxt);
        starTxt = view.findViewById(R.id.starTxt);
        caloryTxt = view.findViewById(R.id.caloriesTxt);
        timeTxt = view.findViewById(R.id.timeTxt);
        percentagetxt = view.findViewById(R.id.percentagetxt);
        originaltxt = view.findViewById(R.id.originaltxt);
        categorytxt = view.findViewById(R.id.categorytxt);
        qautxt = view.findViewById(R.id.quatxt);
        addToWishlistBtn=view.findViewById(R.id.wishlistbutton);
    }
}
