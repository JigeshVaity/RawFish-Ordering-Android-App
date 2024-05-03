package com.example.anaghafishapp.Useractivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anaghafishapp.Adapter.WishlistAdapter;
import com.example.anaghafishapp.Interface.ChangeNumberItemsListener;
import com.example.anaghafishapp.R;
import com.example.anaghafishapp.helper.ManagementCart;

public class WishlistFragment extends Fragment {

    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;


    public WishlistFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        managementCart = new ManagementCart(requireContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        initList(view);
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
    private void initList(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewList = view.findViewById(R.id.view10);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        Log.d("WishlistFragment", "Number of items in wishlist: " + managementCart.getWishlist().size());
        WishlistAdapter adapter = new WishlistAdapter(managementCart.getWishlist(), requireContext(), new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                // Handle changes if needed
            }
        });

        recyclerViewList.setAdapter(adapter);

    }
}
