package com.example.anaghafishapp.vendor;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.anaghafishapp.Adapter.Seemoreadapter;
import com.example.anaghafishapp.R;
import com.example.anaghafishapp.retrieve.Product;
import com.example.anaghafishapp.retrieve.ProductAdapter;
import com.example.anaghafishapp.vendorhisadapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class vendorhistoryFragment extends Fragment {

    private RecyclerView recview;
    private vendorhisadapter adapter;
    private DatabaseReference databaseReference;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_vendorhistory, container, false);
/*
        recview = view.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("VendorProductlist").child("VendorName"), Product.class)
                        .build();

        adapter = new vendorhisadapter(options, this);

        recview.setAdapter(adapter);

*/

        // Initialize DatabaseReference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("VendorProductlist");

        EditText phoneNumberEditText = view.findViewById(R.id.phoneNumberEditText);
        Button searchButton = view.findViewById(R.id.searchButton);
        RecyclerView recyclerView = view.findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phoneNumberEditText.getText().toString().trim();

                // Query Firebase database for products with the entered phone number
                Query query = databaseReference.child("VendorName").orderByChild("vendor_name").equalTo(phoneNumber);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            recyclerView.setVisibility(View.VISIBLE);
                            // Use FirebaseRecyclerAdapter to populate the RecyclerView
                            FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder<Product>()
                                    .setQuery(query, Product.class)
                                    .build();
                            vendorhisadapter adapter = new vendorhisadapter(options, vendorhistoryFragment.this);
                            recyclerView.setAdapter(adapter);
                            adapter.startListening();
                        } else {
                            recyclerView.setVisibility(View.GONE);
                            // No products found with the entered phone number
                            // You can show a message or handle it as needed
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle errors
                    }
                });
            }
        });

        return view;
    }
/*
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }*/

    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }
/*
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
*/
}
