package com.example.anaghafishapp.Categorylist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.anaghafishapp.Adapter.CategoryListAdapter;
import com.example.anaghafishapp.Domain.FoodDomain;
import com.example.anaghafishapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFriedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFriedFragment extends Fragment {

    private RecyclerView recyclerViewCategoryList;
    private CategoryListAdapter adapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoryFriedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFriedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFriedFragment newInstance(String param1, String param2) {
        CategoryFriedFragment fragment = new CategoryFriedFragment();
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
        View view = inflater.inflate(R.layout.fragment_category_fried, container, false);

        recyclerViewCategoryList = view.findViewById(R.id.view5); // Replace with your RecyclerView ID

        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(),2);
        recyclerViewCategoryList.setLayoutManager(layoutManager);

        // Create an instance of CategoryListAdapter and provide the data and fragment reference
        adapter = new CategoryListAdapter(getRecommendedList(), this);

        recyclerViewCategoryList.setAdapter(adapter);

        return view;
    }


    private ArrayList<FoodDomain> getRecommendedList() {
        ArrayList<FoodDomain> recommendedList = new ArrayList<>();
        // Add more recommended items here
        recommendedList.add(new FoodDomain("Surmai fry", "fried_surmai", 300.0, "Seer fish fry arranged beautifully and garnished with onion lemon and tomato slices on white ceramic plate ",4.7,"per slice" ,"Medium","700",10,"Fried fish"));
        recommendedList.add(new FoodDomain("Paplet fry", "fried_paplet", 500.0,"tandoori pomfret fish fry with lemon and onion served in dish isolated on wooden table top view of indian spicy food",4.6,"1" ,"Medium","600",10,"Fried fish"));

        recommendedList.add(new FoodDomain("Salmon fry", "fried_ravas", 200.0, "Raw fish",4.1,"1kg" ,"Medium","500",10,"Fried fish"));
        recommendedList.add(new FoodDomain("Salmon fry", "fried_ravas2", 450.0,"Raw fish",4.8,"1kg" ,"Medium","550",10,"Fried fish"));
        recommendedList.add(new FoodDomain("Surmai fry", "surmai", 400.0,"Raw fish",4.9,"1kg" ,"Medium","500",10,"Fried fish"));
        //recommendedList.add(new FoodDomain("Crab", "fried_crab",400.0, "Boiled Crab",4.4,"1kg","Medium","500",10,"Fried fish"));
        recommendedList.add(new FoodDomain("Prawns fry", "fried_prawns", 600.0,"Raw fish",4.9,"500g" ,"Medium","500",10,"Fried fish"));

        recommendedList.add(new FoodDomain("Bombayduck fry", "fried_bombil", 300.0,"Raw fish",4.9,"6 piece" ,"Medium","500",10,"Fried fish"));

        recommendedList.add(new FoodDomain("Bangda fry", "fried_bangda", 100.0,"Raw fish",4.9,"1" ,"Medium","500",10,"Fried fish"));

        recommendedList.add(new FoodDomain("Paplet fry", "brfood", 500.0,"Raw fish",4.6,"per slice" ,"Medium","600",10,"Fried fish"));

        recommendedList.add(new FoodDomain("Rohu fry", "fried_rohu", 150.0,"Raw fish",4.6,"per slice" ,"Medium","600",10,"Fried fish"));

        recommendedList.add(new FoodDomain("Tuna fry", "fried_tuna", 100.0,"Raw fish",4.6,"per slice" ,"Medium","600",10,"Fried fish"));
        recommendedList.add(new FoodDomain("Mamdeli fry", "fried_mandeli", 150.0,"Raw fish",4.6,"250g" ,"Medium","600",10,"Fried fish"));

        // Add more recommended items here
        return recommendedList;
    }

}