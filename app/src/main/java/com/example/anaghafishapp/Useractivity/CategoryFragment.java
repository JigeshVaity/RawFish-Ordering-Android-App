package com.example.anaghafishapp.Useractivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anaghafishapp.Categorylist.CategoryBiryaniFragment;
import com.example.anaghafishapp.Categorylist.CategoryCurryFragment;
import com.example.anaghafishapp.Categorylist.CategoryFoodFragment;
import com.example.anaghafishapp.Categorylist.CategoryFriedFragment;
import com.example.anaghafishapp.Categorylist.CategoryDriedFragment;
import com.example.anaghafishapp.Categorylist.CategoryRawFragment;
import com.example.anaghafishapp.Domain.FoodDomain;
import com.example.anaghafishapp.NotificationFragment;
import com.example.anaghafishapp.R;

import com.example.anaghafishapp.YourAdapter;
import com.example.anaghafishapp.helper.ManagementCart;
import com.example.anaghafishapp.retrieve.Product;
import com.example.anaghafishapp.retrieve.ProductAdapter;
import com.example.anaghafishapp.retrieve.SelectedItemsViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    LinearLayout linearLayout1, linearLayout2, linearLayout3, linearLayout4, linearLayout5, linearLayout6;

    private ProductAdapter productAdapter;
    private List<Product> productList;
    RecyclerView recview;

    TextView seemore;
    private YourAdapter yourAdapter;
    private ArrayList<FoodDomain> cartlistDomains;

    private TextView wishlistCountTextView;
    private ManagementCart managementCart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        linearLayout1 = view.findViewById(R.id.linearLayout1);
        linearLayout2 = view.findViewById(R.id.linearLayout2);
        linearLayout3 = view.findViewById(R.id.linearLayout3);
        linearLayout4 = view.findViewById(R.id.linearLayout4);
        linearLayout5 = view.findViewById(R.id.linearLayout5);
        linearLayout6 = view.findViewById(R.id.linearLayout6);

        recview = view.findViewById(R.id.recview);

        RelativeLayout rel1 = view.findViewById(R.id.rel1);

        // Set background color for rel1
        rel1.setBackgroundResource(R.color.skyblue);

        // Set status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.skyblue));
        }
        ImageView notification = view.findViewById(R.id.notification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace "YourFragment" with the fragment you want to switch to
                Fragment newFragment = new NotificationFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, newFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment secondFrag = new CategoryRawFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.fragment_container, secondFrag).commit();
            }
        });
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment secondFrag = new CategoryFoodFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.fragment_container, secondFrag).commit();
            }
        });

        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment secondFrag = new CategoryFriedFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.fragment_container, secondFrag).commit();
            }
        });

        linearLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment secondFrag = new CategoryCurryFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.fragment_container, secondFrag).commit();
            }
        });

        linearLayout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment secondFrag = new CategoryBiryaniFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.fragment_container, secondFrag).commit();
            }
        });

        linearLayout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment secondFrag = new CategoryDriedFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.fragment_container, secondFrag).commit();
            }
        });

        ImageView wishlistIcon = view.findViewById(R.id.wishlist);

        // Set a click listener to the wishlist icon ImageView
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

        seemore = view.findViewById(R.id.seemore);
        seemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment secondFrag = new SeeMVendorFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.fragment_container, secondFrag).commit();
            }
        });


        // ... (other linear layout click listeners)

        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference productsRef = database.getReference("VendorProductlist/VendorName");

        // Initialize the RecyclerView and its adapter for products
        RecyclerView recyclerView = view.findViewById(R.id.recview);
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(productList, getSelectedItemsViewModel());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(productAdapter);

        // Initialize the RecyclerView and its adapter for cart items
        cartlistDomains = new ArrayList<>();
        yourAdapter = new YourAdapter(cartlistDomains, this);
        // Assuming there's another RecyclerView in your layout for cart items
        // Example: RecyclerView cartRecyclerView = view.findViewById(R.id.cart_recycler_view);
        // cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // cartRecyclerView.setAdapter(yourAdapter);

        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear();
                for (DataSnapshot vendorSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot productSnapshot : vendorSnapshot.getChildren()) {
                        Product product = new Product();
                        product.setProduct_name(productSnapshot.child("product_name").getValue(String.class));
                        product.setVendor_name(productSnapshot.child("vendor_name").getValue(String.class));
                        product.setImage_url(productSnapshot.child("image_url").getValue(String.class));
                        product.setPrice(productSnapshot.child("price").getValue(String.class));
                        productList.add(product);
                    }
                }
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseError", "Firebase Data Error: " + databaseError.getMessage());
            }
        });

        // Load cart items or perform any other initialization for yourAdapter
        // ...
// Initialize ManagementCart instance
        managementCart = new ManagementCart(getContext());

        // Assuming you have a TextView named wishlistCountTextView in your fragment layout
        wishlistCountTextView = view.findViewById(R.id.wishlistBadge);

        // Retrieve the wishlist count
        int wishlistItemCount = managementCart.getWishlistItemCount();

        // Update the TextView to display the wishlist count
        wishlistCountTextView.setText(String.valueOf(wishlistItemCount));
        return view;
    }
    private SelectedItemsViewModel getSelectedItemsViewModel() {
        return new ViewModelProvider(this).get(SelectedItemsViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();

        Query query = FirebaseDatabase.getInstance().getReference("VendorProductlist/VendorName");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear();

                if (dataSnapshot.exists()) {
                    for (DataSnapshot studentsnapshot : dataSnapshot.getChildren()) {
                        Product studentModel = studentsnapshot.getValue(Product.class);
                        productList.add(studentModel);
                    }

                    if (productAdapter != null) {
                        productAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseError", "Firebase Data Error: " + databaseError.getMessage());
            }
        });
    }
}
