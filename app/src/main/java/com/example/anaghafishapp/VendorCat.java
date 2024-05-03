package com.example.anaghafishapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.SearchView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anaghafishapp.Adapter.Seemoreadapter;
import com.example.anaghafishapp.retrieve.Product;
import com.example.anaghafishapp.vendor.vendorshowdetail;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class VendorCat extends AppCompatActivity
{
   RecyclerView recview;
   Seemoreadapter adapter;

   SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vencat);

        recview=(RecyclerView)findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("VendorProductlist").child("VendorName"), Product.class)
                        .build();

      //  adapter=new myadapter(options);
        recview.setAdapter(adapter);
/*
        searchView=findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });
*/


    }
    public void openVendorShowDetail(Product selectedProduct) {
        // Create a new instance of the vendorshowdetail fragment with the selected product
        recview.setVisibility(View.GONE);
        vendorshowdetail showDetailFragment = vendorshowdetail.newInstance(selectedProduct);

        // Use FragmentTransaction to display the fragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, showDetailFragment);
        fragmentTransaction.addToBackStack(null); // Add to the back stack if needed
        fragmentTransaction.commit();
    }
    /*
    private void processsearch(String s)
    {
        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("VendorProductlist").child("VendorName").orderByChild("product_name").startAt(s).endAt(s+"\uf8ff"), Product.class)
                        .build();

        adapter = new myadapter(options);
        adapter.startListening();
        recview.setAdapter(adapter);
    }*/
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}