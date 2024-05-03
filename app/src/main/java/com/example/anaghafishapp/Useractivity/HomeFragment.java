package com.example.anaghafishapp.Useractivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.anaghafishapp.Adapter.AdvertisementAdapter;
import com.example.anaghafishapp.Adapter.CategoryAdapter;
import com.example.anaghafishapp.Adapter.RecommendedAdapter;
import com.example.anaghafishapp.Categorylist.CategoryDiscountFragment;
import com.example.anaghafishapp.Domain.CategoryDomain;
import com.example.anaghafishapp.Domain.FoodDomain;
import com.example.anaghafishapp.NotificationFragment;
import com.example.anaghafishapp.R;
import com.example.anaghafishapp.helper.ManagementCart;
import com.example.anaghafishapp.search.SearchFragment;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewCategoryList, recyclerViewRecommendedList;
    private RecyclerView.Adapter adapter1, adapter2;

    // Advertisement
    private ViewPager viewPager;
    private int currentPage = 0;
    private Timer timer;
    final long delay = 5000;
    ImageView searchImageView,notification;
    private TextView seemore;

    private TextView wishlistCountTextView;
    private ManagementCart managementCart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerViewCategoryList = view.findViewById(R.id.view1);
        recyclerViewRecommendedList = view.findViewById(R.id.view2);

        seemore = view.findViewById(R.id.seemore);
        notification = view.findViewById(R.id.notification);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList.setLayoutManager(layoutManager1);
        recyclerViewRecommendedList.setLayoutManager(layoutManager2);

        adapter1 = new CategoryAdapter(requireContext(), getCategoryList());
        adapter2 = new RecommendedAdapter(getRecommendedList());
        recyclerViewCategoryList.setAdapter(adapter1);
        recyclerViewRecommendedList.setAdapter(adapter2);

        RelativeLayout rel1 = view.findViewById(R.id.rel1);

        // Set background color for rel1
        rel1.setBackgroundResource(R.color.skyblue);

        // Set status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.skyblue));
        }


        viewPager = view.findViewById(R.id.adv);
        AdvertisementAdapter viewPagerAdapter = new AdvertisementAdapter(getActivity());
        viewPager.setAdapter(viewPagerAdapter);

        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage == viewPagerAdapter.getCount()) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, delay, delay);

        ImageView wishlistIcon = view.findViewById(R.id.wishlist);

        // Set a click listener to the wishlist icon ImageView
        wishlistIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace the current fragment with the WishlistFragment
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new WishlistFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        searchImageView = view.findViewById(R.id.search);

       searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace "YourFragment" with the fragment you want to switch to
                Fragment newFragment = new SearchFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, newFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace "YourFragment" with the fragment you want to switch to
                Fragment newFragment = new NotificationFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, newFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

       seemore.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Fragment newFragment = new CategoryDiscountFragment();
               getParentFragmentManager().beginTransaction()
                       .replace(R.id.fragment_container,newFragment)
                       .addToBackStack(null)
                       .commit();

           }
       });

// Initialize ManagementCart instance
        managementCart = new ManagementCart(getContext());

        // Assuming you have a TextView named wishlistCountTextView in your fragment layout
        wishlistCountTextView = view.findViewById(R.id.wishlistBadge);

        // Retrieve the wishlist count
        int wishlistItemCount = managementCart.getWishlistItemCount();

        // Update the TextView to display the wishlist count
        wishlistCountTextView.setText(String.valueOf(wishlistItemCount));

        return view;
    }

    private ArrayList<CategoryDomain> getCategoryList() {
        ArrayList<CategoryDomain> categoryList = new ArrayList<>();
        // Add more categories here
        categoryList.add(new CategoryDomain("  Raw  ", R.drawable.fbackground));
        categoryList.add(new CategoryDomain(" food  ", R.drawable.food1));
        categoryList.add(new CategoryDomain(" Fried ", R.drawable.brfood));
        categoryList.add(new CategoryDomain(" Curry ", R.drawable.brcurries));
        categoryList.add(new CategoryDomain("Biryani", R.drawable.brfishbiryani));
        categoryList.add(new CategoryDomain("Dried", R.drawable.driedfish));
        categoryList.add(new CategoryDomain("Offer", R.drawable.brdiscount));
        // Add more categories here
        return categoryList;
    }

    private ArrayList<FoodDomain> getRecommendedList() {
        ArrayList<FoodDomain> recommendedList = new ArrayList<>();
        // Add more recommended items here
        recommendedList.add(new FoodDomain("Pomfret", "raw_paplet_small", 800.0,"Raw fish",4.8,"1kg" ,"Small","550",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Crab", "fried_crab",400.0, "Boiled Crab",4.4,"1kg","Medium","500",10,"Raw Fish"));

        recommendedList.add(new FoodDomain("Prawn Biryani", "biryani_prawns", 500.0, "Food", 4.3, "500g", "","500",10,"Food"));
        recommendedList.add(new FoodDomain("Prawns Curry", "curry_prawns", 550.0, "Thai food; TOM YUM KUNG or river prawn spicy soup",4.5,"500g" ,"","700",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Surmai fry", "fried_surmai", 300.0, "Seer fish fry arranged beautifully and garnished with onion lemon and tomato slices on white ceramic plate ",4.7,"per slice" ,"Medium","700",10,"Raw Fish"));
        recommendedList.add(new FoodDomain("Paplet fry", "fried_paplet", 500.0,"tandoori pomfret fish fry with lemon and onion served in dish isolated on wooden table top view of indian spicy food",4.6,"1" ,"Medium","600",10,"Raw Fish"));

        recommendedList.add(new FoodDomain("Salmon fry", "fried_ravas", 200.0, "Raw fish",4.1,"1kg" ,"Medium","500",10,"Raw Fish"));

        recommendedList.add(new FoodDomain("Prawn Biryani", "biryani_prawns2", 750.0, "Food", 4.1, "500g", "","500",10,"Food"));

        return recommendedList;
    }

}
