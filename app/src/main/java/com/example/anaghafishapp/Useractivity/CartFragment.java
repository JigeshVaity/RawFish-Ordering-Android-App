package com.example.anaghafishapp.Useractivity;

import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;

import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.anaghafishapp.Adapter.CartListAdapter;
import com.example.anaghafishapp.NotificationFragment;
import com.example.anaghafishapp.order.AddressFragment;
import com.example.anaghafishapp.Domain.FoodDomain;
import com.example.anaghafishapp.Interface.ChangeNumberItemsListener;

import com.example.anaghafishapp.order.PaymentSuccess;
import com.example.anaghafishapp.R;
import com.example.anaghafishapp.helper.ManagementCart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    private TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emptyTxt, pstatus;
    private double tax;
    private ScrollView scrollView;
    //payment
    Button paybtn;
    private TextView cartBadge;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private FirebaseAuth mAuth;
    private String a;
    private TextView wishlistCountTextView;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
        // Now, initialize your ManagementCart instance here
        managementCart = new ManagementCart(requireContext());
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);



        // Call initView to initialize your views
        initView(view);
        initList(view);



        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        //String phoneNumber = user.getPhoneNumber();
        // Set the OnClickListener after paybtn is initialized
        /////////////////////////////////////////////////



        ///////////////////////////////////////////////////////////
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

        a = totalTxt.getText().toString();

        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new AddressFragment());
                transaction.addToBackStack(null);
                transaction.commit();

                // this code for passing the value to anther fragment
                Bundle bundle = new Bundle();
                bundle.putString("totalpayment",totalTxt.getText().toString());
                getParentFragmentManager().setFragmentResult("datafrom",bundle);

                Bundle bundle1 = new Bundle();
                bundle1.putString("totalpayment",totalTxt.getText().toString());
                bundle1.putString("total",totalFeeTxt.getText().toString());
                bundle1.putString("tax",taxTxt.getText().toString());
                bundle1.putString("delivery",deliveryTxt.getText().toString());
                getParentFragmentManager().setFragmentResult("naruto",bundle1);

                List<FoodDomain> cartItems = ((CartListAdapter) adapter).getFoodDomainList();
                Log.d("Cart item list",cartItems.toString());

                // Create a Bundle to pass data to PlaceOrderFragment
                Bundle bundle3 = new Bundle();

                // Process each cart item and add data to the Bundle
                for (int i = 0; i < cartItems.size(); i++) {
                    String productTitle = cartItems.get(i).getTitle();
                    String productQuantity = String.valueOf(cartItems.get(i).getNumberInCart());

                    // Add data to the Bundle
                    bundle3.putString("productTitle" + i, productTitle);
                    bundle3.putString("productQuantity" + i, productQuantity);

                }
                getParentFragmentManager().setFragmentResult("dataform4",bundle3);


            }
        });

        calculateCart();
        wishlistCountTextView = view.findViewById(R.id.wishlistBadge);

        // Retrieve the wishlist count
        int wishlistItemCount = managementCart.getWishlistItemCount();

        // Update the TextView to display the wishlist count
        wishlistCountTextView.setText(String.valueOf(wishlistItemCount));
        return view;
    }



    private void initList(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewList = view.findViewById(R.id.view);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        adapter = new CartListAdapter(managementCart.getListCart(), requireContext(), new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                calculateCart();
            }
        });

        recyclerViewList.setAdapter(adapter);
        if (managementCart.getListCart().isEmpty()) {
            replaceWithEmptyCartFragment();
        } else {

            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private void calculateCart() {
        double percentTax = 0.02;
        double delivery = 10;
        double itemTotal = managementCart.getTotalFee();
        double tax = Math.round((itemTotal * percentTax) * 100.0) / 100.0;
        double total = Math.round((itemTotal + tax + delivery) * 100.0) / 100.0;

        totalFeeTxt.setText("₹" + itemTotal);
        taxTxt.setText("₹" + tax);
        deliveryTxt.setText("₹" + delivery);
        totalTxt.setText("₹" + total);


        if (managementCart.getListCart().isEmpty()) {
            //emptyTxt.setVisibility(View.VISIBLE);
            //scrollView.setVisibility(View.GONE);
            replaceWithEmptyCartFragment();
        } else {

            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private void initView(View view) {
        totalFeeTxt = view.findViewById(R.id.totalFeeTxt);
        taxTxt = view.findViewById(R.id.taxTxt);
        deliveryTxt = view.findViewById(R.id.deliveryTxt);
        totalTxt = view.findViewById(R.id.totalTxt);
        recyclerViewList = view.findViewById(R.id.view);
        scrollView = view.findViewById(R.id.scrollview);
        emptyTxt = view.findViewById(R.id.emptyTxt);

        paybtn = view.findViewById(R.id.paybtn);

    }

    private void replaceWithEmptyCartFragment() {
        EmptyCartFragment emptyCartFragment = new EmptyCartFragment();

        // Replace the current fragment with EmptyCartFragment
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, emptyCartFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void loadPaymentSuccessFragment() {
        // Create a new instance of the PaymentSuccessFragment
        PaymentSuccess paymentSuccessFragment = new PaymentSuccess();

        // Replace the current fragment with the PaymentSuccessFragment
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, paymentSuccessFragment);
        fragmentTransaction.addToBackStack(null); // Optional: Adds the transaction to the back stack
        fragmentTransaction.commit();
    }





}
