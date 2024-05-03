package com.example.anaghafishapp.search;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anaghafishapp.Domain.FoodDomain;
import com.example.anaghafishapp.R;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    SearchView s1;
    RecyclerView r1;
    private SearchView searchView;
    private List<FoodDomain> itemList;


    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);


        r1 = view.findViewById(R.id.searchrecylerview);
        s1 = view.findViewById(R.id.searchView);
        s1.setIconified(false);
        s1.clearFocus();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        r1.setLayoutManager(layoutManager);
        SearchAdapter searchAdapter = new SearchAdapter(SearchFragment.this, getRecommendedList());
        //adapter2 = new RecommendedAdapter(getRecommendedList());
        r1.setAdapter(searchAdapter);

        s1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle query submission if needed
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchAdapter.filter(newText);
                return true;
            }
        });

        // Rest of your code...

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



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
    private void showKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }



    private ArrayList<FoodDomain> getRecommendedList() {
        ArrayList<FoodDomain> recommendedList = new ArrayList<>();

        recommendedList.add(new FoodDomain("Pomfret(Paplet)", "raw_paplet_big", 1500.0, "Raw fish",4.6,"1kg" ,"Large","700",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Pomfret(Paplet)", "raw", 1100.0, "Raw fish",4.1,"1kg" ,"Medium","500",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Pomfret(Paplet)", "raw_paplet_small", 800.0,"Raw fish",4.8,"1kg" ,"Small","550",10,"Raw Fish"));

        recommendedList.add(new FoodDomain("Salmon(Ravas)", "raw_ravas_small", 1600.0, "Raw fish",4.6,"2kg" ,"Large","700",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Salmon(Ravas)", "raw_ravas_medium", 800.0, "Raw fish",4.1,"1kg" ,"Medium","500",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Salmon(Ravas)", "raw_ravas_small", 400.0,"Raw fish",4.8,"500g" ,"Small","550",10,"Raw Fish"));

        recommendedList.add(new FoodDomain("Black Pomfret(Halva)", "raw_blackpomfret", 700.0, "Raw fish",4.6,"1kg" ,"Large","700",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Black Pomfret(Halva)", "raw_blackpomfret", 350.0, "Raw fish",4.1,"500kg" ,"Medium","500",10,"Raw Fish"));


        recommendedList.add(new FoodDomain("Surmai(Seer)", "raw_surmai_large", 1500.0, "Raw fish",4.6,"2kg" ,"Large","700",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Surmai(Seer)", "raw_surmai_small", 800.0, "Raw fish",4.1,"1kg" ,"Medium","500",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Surmai(Seer)", "raw_surmai_small", 400.0,"Raw fish",4.8,"500g" ,"Small","550",10,"Raw Fish"));

        recommendedList.add(new FoodDomain("Prawns(kolambi)", "raw_prawn_white", 500.0, "Raw fish",4.6,"1kg" ,"Large","700",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Prawns(kolambi)", "raw_prawn_white", 250.0, "Raw fish",4.1,"500g" ,"Medium","500",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Prawns(kolambi)", "raw_prawn_grey", 400.0,"Raw fish",4.8,"1kg" ,"Large","550",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Prawns(kolambi)", "raw_prawn_grey", 250.0,"Raw fish",4.8,"1kg" ,"Medium","550",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Prawns(kolambi)", "raw_prawn_red", 450.0,"Raw fish",4.8,"1kg" ,"Medium","550",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Prawns(kolambi)", "raw_prawn_red", 250.0,"Raw fish",4.8,"1kg" ,"Small","550",10,"Raw Fish"));

        recommendedList.add(new FoodDomain("Bombayduck(Bombil)", "raw_bombil", 250.0, "Raw fish",4.6,"1kg" ,"Large","700",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Bombayduck(Bombil)", "raw_bombil_small", 200.0, "Raw fish",4.1,"1kg" ,"Medium","500",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Bombayduck(Bombil)", "raw_bombil_small", 100.0,"Raw fish",4.8,"500g" ,"Medium","550",10,"Raw Fish"));

        recommendedList.add(new FoodDomain("Ghol fish", "raw_ghol", 800.0, "Only steak fish pieces",4.6,"1kg" ,"Large","700",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Ghol fish", "raw_ghol", 400.0, "Only steak fish pieces",4.1,"500g" ,"Medium","500",10,"Raw Fish"));


        recommendedList.add(new FoodDomain("Crab(khekade)", "raw_crab", 300.0, "Red crab",4.1,"1kg" ,"Large","500",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Crabkhekade", "raw_crab_black", 600.0,"Black crab",4.8,"1kg" ,"Small","550",10,"Raw Fish"));


        recommendedList.add(new FoodDomain("Squid", "raw_squid", 400.0, "Raw fish",4.1,"1kg" ,"Large","500",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Squid", "raw_squid_small", 300.0,"Raw fish",4.8,"500g" ,"Small","550",10,"Raw Fish"));


        recommendedList.add(new FoodDomain("Lady fish(muddoshi)", "raw_ladyfish", 500.0, "Raw fish",4.1,"500g" ,"Large","500",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Lady fish(muddoshi)", "raw_ladyfish_small", 300.0,"Raw fish",4.8,"1kg" ,"Medium","550",10,"Raw Fish"));



        recommendedList.add(new FoodDomain("Bangda", "raw_bangda", 250.0, "Raw fish",4.1,"1kg" ,"Medium","500",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Bangda", "raw_bangda", 300.0,"Raw fish",4.8,"1kg" ,"Large","550",10,"Raw Fish"));


        recommendedList.add(new FoodDomain("Mandeli", "raw_mandeli", 100.0, "Raw fish",4.1,"1kg" ,"Medium","500",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Mandeli", "raw_mandeli", 150.0,"Raw fish",4.8,"1kg" ,"Large","550",10,"Raw Fish"));

        recommendedList.add(new FoodDomain("Clam fish", "raw_clam", 200.0, "Raw fish",4.1,"1kg" ,"Small","500",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Clam fish", "raw_clam", 250.0,"Raw fish",4.8,"1kg" ,"Medium","550",10,"Raw Fish"));

        recommendedList.add(new FoodDomain("Baby sharks", "rawbabysharks", 300.0, "Raw fish",4.6,"1kg" ,"Large","700",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Baby sharks", "rawbabysharks", 250.0, "Raw fish",4.1,"1kg" ,"Medium","500",10,"Raw Fish"));
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
        recommendedList.add(new FoodDomain("Prawn Biryani", "brfishbiryani",900.0, "Raw fish",4.4,"1kg","Medium","500",10,"Fish Biryani"));
        recommendedList.add(new FoodDomain("Pickle", "brfishpickle", 600.0, "Raw fish", 4.2, "500g", "","500",10,"Fish Pickle"));
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