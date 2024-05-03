package com.example.anaghafishapp.vendor;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.anaghafishapp.R;
import com.example.anaghafishapp.first.registerpage;
import com.example.anaghafishapp.loginActivity;
import com.example.anaghafishapp.otplogin.entermobilenumberone1;
import com.google.firebase.auth.FirebaseAuth;

public class vendorprofilefragment extends Fragment {
    private FirebaseAuth mAuth;

    // Declare the parameters for the fragment
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Parameters for the fragment
    private String mParam1;
    private String mParam2;

    public vendorprofilefragment() {
        // Required empty public constructor
    }

    public static vendorprofilefragment newInstance(String param1, String param2) {
        vendorprofilefragment fragment = new vendorprofilefragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        // Check for arguments if needed
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vendorprofilefragment, container, false);

        // Check if mAuth is not null before using it
        if (mAuth != null) {
            view.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAuth.signOut();
                    Intent intent = new Intent(getActivity(), registerpage.class);
                    startActivity(intent);

                    // Use getActivity() instead of requireActivity() for consistency
                    if (getActivity() != null) {
                        getActivity().finish();
                    }
                }
            });
        }
        LottieAnimationView animationView = view.findViewById(R.id.lottieAnimationView6);
        animationView.setAnimation("profile.json"); // Set the animation file
        animationView.loop(true); // Set the animation to loop
        // Start the animation
        animationView.playAnimation();

        RelativeLayout rel1 = view.findViewById(R.id.relativ1);

        // Set background color for rel1
        rel1.setBackgroundResource(R.color.skyblue);

        // Set status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.skyblue));
        }

        return view;
    }
}
