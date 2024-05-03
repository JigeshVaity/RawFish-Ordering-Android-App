package com.example.anaghafishapp.Useractivity;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.anaghafishapp.AboutusFragment;
import com.example.anaghafishapp.NotificationFragment;
import com.example.anaghafishapp.R;
import com.example.anaghafishapp.first.registerpage;
import com.example.anaghafishapp.helper.ManagementCart;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM1 ="param1" ;
    private static final String ARG_PARAM2 ="param2" ;
    Button btn,orderbtn;
    private TextView wishlistCountTextView;
    private ManagementCart managementCart;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btn = view.findViewById(R.id.logout);
        orderbtn = view.findViewById(R.id.order);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                // Depending on your use case, you might want to navigate back or perform some other action
                // getActivity().onBackPressed(); // Navigates back


                //startActivity(new Intent(getContext(), Signupcustomer.class));
                Intent intent = new Intent(requireContext(), registerpage.class);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        LottieAnimationView animationView = view.findViewById(R.id.lottieAnimationView7);
        animationView.setAnimation("profile.json"); // Set the animation file
        animationView.loop(true); // Set the animation to loop
        // Start the animation
        animationView.playAnimation();


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
        TextView aboutus = view.findViewById(R.id.aboutus);

        // Set a click listener to the wishlist icon ImageView
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace the current fragment with the WishlistFragment
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new AboutusFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        orderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new OrderFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        ImageView notification = view.findViewById(R.id.notification);
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
}
