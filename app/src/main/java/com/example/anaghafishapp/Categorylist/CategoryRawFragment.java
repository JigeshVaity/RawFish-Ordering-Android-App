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


public class CategoryRawFragment extends Fragment {

    private RecyclerView recyclerViewCategoryList;
    private CategoryListAdapter adapter;

    public CategoryRawFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categoryraw, container, false);

        recyclerViewCategoryList = view.findViewById(R.id.view3); // Replace with your RecyclerView ID

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(),2);
        recyclerViewCategoryList.setLayoutManager(gridLayoutManager);

        // Create an instance of CategoryListAdapter and provide the data and fragment reference
        adapter = new CategoryListAdapter(getRecommendedList(), this);

        recyclerViewCategoryList.setAdapter(adapter);

        return view;
    }
    private ArrayList<FoodDomain> getRecommendedList() {
        ArrayList<FoodDomain> recommendedList = new ArrayList<>();
        // Add more recommended items here
        recommendedList.add(new FoodDomain("Pomfret", "raw_paplet_big", 1500.0, "Raw fish",4.6,"1kg" ,"Large","700",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Pomfret", "raw", 1100.0, "Raw fish",4.1,"1kg" ,"Medium","500",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Pomfret", "raw_paplet_small", 800.0,"Raw fish",4.8,"1kg" ,"Small","550",10,"Raw Fish"));

        recommendedList.add(new FoodDomain("Salmon", "raw_ravas_small", 1600.0, "Raw fish",4.6,"2kg" ,"Large","700",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Salmon", "raw_ravas_medium", 800.0, "Raw fish",4.1,"1kg" ,"Medium","500",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Salmon", "raw_ravas_small", 400.0,"Raw fish",4.8,"500g" ,"Small","550",10,"Raw Fish"));

        recommendedList.add(new FoodDomain("Black Pomfret", "raw_blackpomfret", 700.0, "Raw fish",4.6,"1kg" ,"Large","700",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Black Pomfret", "raw_blackpomfret", 350.0, "Raw fish",4.1,"500kg" ,"Medium","500",10,"Raw Fish"));


        recommendedList.add(new FoodDomain("Surmai", "raw_surmai_large", 1500.0, "Raw fish",4.6,"2kg" ,"Large","700",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Surmai", "raw_surmai_small", 800.0, "Raw fish",4.1,"1kg" ,"Medium","500",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Surmai", "raw_surmai_small", 400.0,"Raw fish",4.8,"500g" ,"Small","550",10,"Raw Fish"));

        recommendedList.add(new FoodDomain("Prawns", "raw_prawn_white", 500.0, "Raw fish",4.6,"1kg" ,"Large","700",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Prawns", "raw_prawn_white", 250.0, "Raw fish",4.1,"500g" ,"Medium","500",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Prawns", "raw_prawn_grey", 400.0,"Raw fish",4.8,"1kg" ,"Large","550",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Prawns", "raw_prawn_grey", 250.0,"Raw fish",4.8,"1kg" ,"Medium","550",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Prawns", "raw_prawn_red", 450.0,"Raw fish",4.8,"1kg" ,"Medium","550",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Prawns", "raw_prawn_red", 250.0,"Raw fish",4.8,"1kg" ,"Small","550",10,"Raw Fish"));

        recommendedList.add(new FoodDomain("Bombayduck", "raw_bombil", 250.0, "Raw fish",4.6,"1kg" ,"Large","700",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Bombayduck", "raw_bombil_small", 200.0, "Raw fish",4.1,"1kg" ,"Medium","500",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Bombayduck", "raw_bombil_small", 100.0,"Raw fish",4.8,"500g" ,"Medium","550",10,"Raw Fish"));

        recommendedList.add(new FoodDomain("Ghol fish", "raw_ghol", 800.0, "Only steak fish pieces",4.6,"1kg" ,"Large","700",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Ghol fish", "raw_ghol", 400.0, "Only steak fish pieces",4.1,"500g" ,"Medium","500",10,"Raw Fish"));


        recommendedList.add(new FoodDomain("Crab", "raw_crab", 300.0, "Red crab",4.1,"1kg" ,"Large","500",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Crab", "raw_crab_black", 600.0,"Black crab",4.8,"1kg" ,"Small","550",10,"Raw Fish"));


        recommendedList.add(new FoodDomain("Squid", "raw_squid", 400.0, "Raw fish",4.1,"1kg" ,"Large","500",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Squid", "raw_squid_small", 300.0,"Raw fish",4.8,"500g" ,"Small","550",10,"Raw Fish"));


        recommendedList.add(new FoodDomain("Lady fish", "raw_ladyfish", 500.0, "Raw fish",4.1,"500g" ,"Large","500",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Lady fish", "raw_ladyfish_small", 300.0,"Raw fish",4.8,"1kg" ,"Medium","550",10,"Raw Fish"));



        recommendedList.add(new FoodDomain("Bangda", "raw_bangda", 250.0, "Raw fish",4.1,"1kg" ,"Medium","500",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Bangda", "raw_bangda", 300.0,"Raw fish",4.8,"1kg" ,"Large","550",10,"Raw Fish"));


        recommendedList.add(new FoodDomain("Mandeli", "raw_mandeli", 100.0, "Raw fish",4.1,"1kg" ,"Medium","500",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Mandeli", "raw_mandeli", 150.0,"Raw fish",4.8,"1kg" ,"Large","550",10,"Raw Fish"));

        recommendedList.add(new FoodDomain("Clam fish", "raw_clam", 200.0, "Raw fish",4.1,"1kg" ,"Small","500",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Clam fish", "raw_clam", 250.0,"Raw fish",4.8,"1kg" ,"Medium","550",10,"Raw Fish"));

        recommendedList.add(new FoodDomain("Baby sharks", "rawbabysharks", 300.0, "Raw fish",4.6,"1kg" ,"Large","700",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Baby sharks", "rawbabysharks", 250.0, "Raw fish",4.1,"1kg" ,"Medium","500",10,"Raw Fish"));


        // Add more recommended items here
        return recommendedList;
    }
}
