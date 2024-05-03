package com.example.anaghafishapp.Useractivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anaghafishapp.R;
import com.example.anaghafishapp.Adapter.Seemoreadapter;
import com.example.anaghafishapp.retrieve.Product;
import com.example.anaghafishapp.vendor.vendorshowdetail;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class SeeMVendorFragment extends Fragment {
    private RecyclerView recview;
    private Seemoreadapter adapter;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seemvendor, container, false);

        recview = view.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("VendorProductlist").child("VendorName"), Product.class)
                        .build();

        adapter = new Seemoreadapter(options, this);

        recview.setAdapter(adapter);

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processSearch(s);
                return false;
            }
        });

        return view;
    }

    public void openVendorShowDetail(Product selectedProduct) {
        // Create a new instance of the vendorshowdetail fragment with the selected product
        recview.setVisibility(View.GONE);
        vendorshowdetail showDetailFragment = vendorshowdetail.newInstance(selectedProduct);

        // Use FragmentTransaction to display the fragment
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, showDetailFragment);
        fragmentTransaction.addToBackStack(null); // Add to the back stack if needed
        fragmentTransaction.commit();
    }

    private void processSearch(String s) {
        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("VendorProductlist").child("VendorName").orderByChild("vendor_name").startAt(s).endAt(s + "\uf8ff"), Product.class)
                        .build();

        adapter = new Seemoreadapter(options, this);

        adapter.startListening();
        recview.setAdapter(adapter);
    }



    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
