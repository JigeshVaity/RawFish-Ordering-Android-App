package com.example.anaghafishapp.Categorylist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anaghafishapp.Adapter.CategoryListAdapter;
import com.example.anaghafishapp.Domain.FoodDomain;
import com.example.anaghafishapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryBiryaniFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryBiryaniFragment extends Fragment {
    private RecyclerView recyclerViewCategoryList;
    private CategoryListAdapter adapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoryBiryaniFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryBiryaniFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryBiryaniFragment newInstance(String param1, String param2) {
        CategoryBiryaniFragment fragment = new CategoryBiryaniFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorybiryani, container, false);

        recyclerViewCategoryList = view.findViewById(R.id.view7); // Replace with your RecyclerView ID

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(),2);
        recyclerViewCategoryList.setLayoutManager(gridLayoutManager);

        // Create an instance of CategoryListAdapter and provide the data and fragment reference
        adapter = new CategoryListAdapter(getRecommendedList(), this);

        recyclerViewCategoryList.setAdapter(adapter);

        return view;
    }
    private ArrayList<FoodDomain> getRecommendedList() {
        ArrayList<FoodDomain> recommendedList = new ArrayList<>();

        recommendedList.add(new FoodDomain("Prawn Biryani", "brfishbiryani", 1000.0, "Food", 4.4, "1kg", "","500",10,"Fish Biryani"));
        recommendedList.add(new FoodDomain("Surmai Biryani", "biryani_surmai", 1100.0, "Food", 4.6, "1kg", "","500",15,"Fish Biryani"));
        recommendedList.add(new FoodDomain("Prawn Biryani", "biryani_prawns", 500.0, "Food", 4.3, "500g", "","500",10,"Fish Biryani"));
        recommendedList.add(new FoodDomain("Pomfret Biryani", "biryani_pomfret", 1100.0, "Food", 4.3, "1kg", "","500",10,"Fish Biryani"));
        recommendedList.add(new FoodDomain("Prawn Biryani", "biryani_prawns2", 750.0, "Food", 4.1, "500g", "","500",10,"Fish Biryani"));
        recommendedList.add(new FoodDomain("Prawn Biryani", "brfishbiryani", 400.0, "Food", 4.4, "500g", "","500",10,"Fish Biryani"));
        recommendedList.add(new FoodDomain("Surmai Biryani", "biryani_surmai", 450.0, "Food", 4.6, "1kg", "","500",15,"Fish Biryani"));
        // Add more recommended items here
        return recommendedList;
    }
}