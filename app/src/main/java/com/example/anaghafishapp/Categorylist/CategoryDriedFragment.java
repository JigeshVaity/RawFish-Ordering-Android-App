package com.example.anaghafishapp.Categorylist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
 * Use the {@link CategoryDriedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryDriedFragment extends Fragment {

    private RecyclerView recyclerViewCategoryList;
    private CategoryListAdapter adapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoryDriedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryPickleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryDriedFragment newInstance(String param1, String param2) {
        CategoryDriedFragment fragment = new CategoryDriedFragment();
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
        View view = inflater.inflate(R.layout.fragment_categorypickle, container, false);

        recyclerViewCategoryList = view.findViewById(R.id.view8); // Replace with your RecyclerView ID

        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(),2);
        recyclerViewCategoryList.setLayoutManager(layoutManager);

        // Create an instance of CategoryListAdapter and provide the data and fragment reference
        adapter = new CategoryListAdapter(getRecommendedList(), this);

        recyclerViewCategoryList.setAdapter(adapter);

        return view;
    }
    private ArrayList<FoodDomain> getRecommendedList() {
        ArrayList<FoodDomain> recommendedList = new ArrayList<>();

        recommendedList.add(new FoodDomain("Dried Bombayduck", "dried_bombil", 400.0, "Raw fish", 4.2, "500g", "","500",10,"Dried fish"));
        recommendedList.add(new FoodDomain("Dried small prawns", "dried_kardi", 300.0, "Raw fish", 4.2, "500g", "","500",10,"Dried fish"));

        recommendedList.add(new FoodDomain("Dried Prawns", "dried_prawn", 750.0, "Raw fish", 4.2, "500g", "","500",10,"Dried fish"));

        recommendedList.add(new FoodDomain("Dried Bangda", "dried_bangda", 300.0, "Raw fish", 4.2, "500g", "","500",10,"Dried fish"));

        // Add more recommended items here
        return recommendedList;
    }
}