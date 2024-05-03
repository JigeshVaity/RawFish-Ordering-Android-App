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
 * Use the {@link CategoryCurryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryCurryFragment extends Fragment {

    private RecyclerView recyclerViewCategoryList;
    private CategoryListAdapter adapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoryCurryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryCurryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryCurryFragment newInstance(String param1, String param2) {
        CategoryCurryFragment fragment = new CategoryCurryFragment();
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
        View view = inflater.inflate(R.layout.fragment_categorycurry, container, false);

        recyclerViewCategoryList = view.findViewById(R.id.view6); // Replace with your RecyclerView ID

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
        recommendedList.add(new FoodDomain("Prawns Curry", "curry_prawns", 550.0, "Thai food; TOM YUM KUNG or river prawn spicy soup",4.5,"500g" ,"","700",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Pomfret Curry", "curry_pomfret2", 700.0,"Pomfret curry is a tasty dish made by frying Pomfret fish and then cooking it in a spicy tomato and onion sauce. It's great with rice and packs a flavorful punch!",4.8,"500g" ,"","550",10,"Fish Curry"));
        recommendedList.add(new FoodDomain("Prawns Curry", "curry_prawns2", 450.0,"Goan Prawns or Shrimp curry or zinga masala also known as Kolambi kalwan or Tikhle",4.6,"500g" ,"","600",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Bangada Curry", "curry_bangada",400.0, "That sounds absolutely delicious! Kerala fish curry is indeed renowned for its rich flavors, especially with the tangy kick from the tamarind. It's versatile too, perfect for a cozy winter dinner or a fulfilling lunch any day of the week. Pairing it with rice or roti sounds like a match made in culinary heaven. And packing it in an insulated lunch box is a great idea to savor its warmth and flavors later. Have you tried making it before, or are you looking forward to trying it out for the first time?",4.4,"500g","","500",10,"Fish Curry"));
        recommendedList.add(new FoodDomain("Rohu Curry", "curry_rohu", 400.0, "Rui macher rosha sounds like another delightful dish! The combination of the turmeric-marinated rohu fish fried to a golden perfection and then simmered in a spicy tomato-onion gravy must create a symphony of flavors. It's fascinating how each step adds its own depth to the dish, resulting in a tender fish infused with the rich essence of the gravy.",4.1,"500g" ,"","500",10,"Fish Curry"));
        recommendedList.add(new FoodDomain("Pomfret Curry", "curry_pomfret", 600.0,"Pomfret curry is a tasty dish made by frying Pomfret fish and then cooking it in a spicy tomato and onion sauce. It's great with rice and packs a flavorful punch!",4.8,"500g" ,"","550",10,"Fish Curry"));
        recommendedList.add(new FoodDomain("Surmai Curry", "curry_surmai", 600.0, "Surmai fish curry",4.5,"500g" ,"Medium","700",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Salmon Curry", "curry_ravas", 600.0, "Salmon curry offers a fusion of rich flavors, with succulent salmon pieces simmered in a fragrant blend of spices and creamy coconut milk. This indulgent dish pairs wonderfully with steamed rice or naan for a satisfying meal.",4.5,"1kg" ,"","700",10,"Fish Curry"));
        recommendedList.add(new FoodDomain("Bombay duck Curry", "curry_bombil", 400.0,"Bombay duck curry features tender pieces of Bombay duck fish simmered in a flavorful gravy made with spices, tomatoes, and onions. It's a delicious seafood dish that pairs perfectly with rice or bread.",4.9,"1kg" ,"","500",10,"Fish Curry"));
        recommendedList.add(new FoodDomain("Dry Bombayduck Curry", "curry_bombil2", 400.0,"Dry bombil fish curry/kalwan",4.9,"500g" ,"Medium","500",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Crab Curry", "curry_crab", 600.0, "Crab curry is a flavorful seafood delight, featuring succulent crab meat cooked in a spicy tomato and coconut milk-based gravy. This aromatic dish is best enjoyed with steamed rice or crusty bread for a truly satisfying meal.",4.5,"500g" ,"","700",10,"Fish Curry"));

        //
        recommendedList.add(new FoodDomain("Surmai Curry", "curry_surmai", 1100.0, "Surmai fish curry",4.5,"1kg" ,"","700",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Salmon Curry", "curry_ravas", 1100.0, "Salmon curry offers a fusion of rich flavors, with succulent salmon pieces simmered in a fragrant blend of spices and creamy coconut milk. This indulgent dish pairs wonderfully with steamed rice or naan for a satisfying meal.",4.5,"1kg" ,"","700",10,"Fish Curry"));

        recommendedList.add(new FoodDomain("Pomfret Curry", "curry_pomfret2", 1000.0,"Pomfret curry is a tasty dish made by frying Pomfret fish and then cooking it in a spicy tomato and onion sauce. It's great with rice and packs a flavorful punch!",4.8,"1kg" ,"","550",10,"Fish Curry"));
        recommendedList.add(new FoodDomain("Prawns Curry", "curry_prawns2", 300.0,"Goan Prawns or Shrimp curry or zinga masala also known as Kolambi kalwan or Tikhle",4.2,"1kg" ,"","600",10,"Raw Fish"));

        // Add more recommended items here
        return recommendedList;
    }
}