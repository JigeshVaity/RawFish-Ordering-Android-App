package com.example.anaghafishapp.order;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.anaghafishapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentSuccess#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentSuccess extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Button b1;
    public PaymentSuccess() {
        // Required empty public constructor
    }


    public static PaymentSuccess newInstance(String param1, String param2) {
        PaymentSuccess fragment = new PaymentSuccess();
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
        View view = inflater.inflate(R.layout.fragment_payment_success, container, false);

        b1 = view.findViewById(R.id.goplaceorder);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new PlaceOrderFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }
}