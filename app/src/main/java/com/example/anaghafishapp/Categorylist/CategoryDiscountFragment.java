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
 * Use the {@link CategoryDiscountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryDiscountFragment extends Fragment {

    private RecyclerView recyclerViewCategoryList;
    private CategoryListAdapter adapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoryDiscountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryDiscountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryDiscountFragment newInstance(String param1, String param2) {
        CategoryDiscountFragment fragment = new CategoryDiscountFragment();
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
        View view = inflater.inflate(R.layout.fragment_categorydiscount, container, false);

        recyclerViewCategoryList = view.findViewById(R.id.view9); // Replace with your RecyclerView ID

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
        recommendedList.add(new FoodDomain("Pomfret", "raw_paplet_small", 760.0,"Raw fish",4.8,"1kg" ,"Small","550",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Surmai", "raw_surmai_small", 750.0, "Raw fish",4.1,"1kg" ,"Medium","500",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Prawns", "raw_prawn_white", 450.0, "Raw fish",4.6,"1kg" ,"Large","700",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Bombayduck", "raw_bombil", 230.0, "Raw fish",4.6,"1kg" ,"Large","700",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Crab", "fried_crab",400.0, "Boiled Crab",4.4,"1kg","Medium","500",10,"Raw Fish"));

        recommendedList.add(new FoodDomain("Dried Bombayduck", "dried_bombil", 380.0, "Raw fish", 4.2, "500g", "","500",10,"Food"));
        recommendedList.add(new FoodDomain("Dried small prawns", "dried_kardi", 290.0, "Raw fish", 4.2, "500g", "","500",10,"Food"));

        recommendedList.add(new FoodDomain("Prawns Curry", "curry_prawns", 530.0, "Thai food; TOM YUM KUNG or river prawn spicy soup",4.5,"500g" ,"","700",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Pomfret Curry", "curry_pomfret2", 570.0,"Pomfret curry is a tasty dish made by frying Pomfret fish and then cooking it in a spicy tomato and onion sauce. It's great with rice and packs a flavorful punch!",4.8,"500g" ,"","550",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Bangada Curry", "curry_bangada",360.0, "That sounds absolutely delicious! Kerala fish curry is indeed renowned for its rich flavors, especially with the tangy kick from the tamarind. It's versatile too, perfect for a cozy winter dinner or a fulfilling lunch any day of the week. Pairing it with rice or roti sounds like a match made in culinary heaven. And packing it in an insulated lunch box is a great idea to savor its warmth and flavors later. Have you tried making it before, or are you looking forward to trying it out for the first time?",4.4,"500g","","500",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Rohu Curry", "curry_rohu", 370.0, "Rui macher rosha sounds like another delightful dish! The combination of the turmeric-marinated rohu fish fried to a golden perfection and then simmered in a spicy tomato-onion gravy must create a symphony of flavors. It's fascinating how each step adds its own depth to the dish, resulting in a tender fish infused with the rich essence of the gravy.",4.1,"500g" ,"","500",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Crab Curry", "curry_crab", 560.0, "Crab curry is a flavorful seafood delight, featuring succulent crab meat cooked in a spicy tomato and coconut milk-based gravy. This aromatic dish is best enjoyed with steamed rice or crusty bread for a truly satisfying meal.",4.5,"500g" ,"","700",10,"Raw Fish"));


        return recommendedList;
    }
}