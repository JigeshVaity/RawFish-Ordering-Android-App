package com.example.anaghafishapp.order;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.anaghafishapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CartPaymentFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Spinner monthSpinner;
    private Spinner yearSpinner;
    private EditText inputCardNumber;
    private TextView cardNoTextView;

    private EditText inputcardholdername;

    private TextView cardholdername;

    private TextView expireDateTextView;

    private Spinner cardTypeSpinner;
    public CartPaymentFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CartPaymentFragment newInstance(String param1, String param2) {
        CartPaymentFragment fragment = new CartPaymentFragment();
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart_payment, container, false);

        monthSpinner = view.findViewById(R.id.monthSpinner);
        yearSpinner = view.findViewById(R.id.yearSpinner);
        inputCardNumber = view.findViewById(R.id.inputcardnumber);
        cardNoTextView = view.findViewById(R.id.cardno);
        cardholdername = view.findViewById(R.id.cardholdername);
        inputcardholdername =view.findViewById(R.id.inputcardholdername);
        expireDateTextView = view.findViewById(R.id.expiredate);
        cardTypeSpinner = view.findViewById(R.id.inputcardtype);


        // Populate month spinner
        List<String> months = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            months.add(String.format("%02d", i)); // Adding leading zeros to single digit months
        }
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, months);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);

        // Populate year spinner
        List<String> years = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i <= currentYear + 10; i++) { // Considering 10 years in future
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);



        // Add TextWatcher to inputCardNumber EditText
        inputCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No implementation needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Format the input text with gaps after every 4 digits
                String formattedText = formatCardNumber(s.toString());
                // Update card number TextView with the formatted text
                cardNoTextView.setText(formattedText);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No implementation needed
            }
        });


        // Add TextWatcher to inputCardNumber EditText
        inputcardholdername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No implementation needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // Update card number TextView with the formatted text
                cardholdername.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No implementation needed
            }
        });


        // Set up spinner listeners
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateExpireDateTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateExpireDateTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Initial update of expire date TextView
        updateExpireDateTextView();


        // Populate card type spinner
        List<String> cardTypes = new ArrayList<>();
        cardTypes.add("Credit Card");
        cardTypes.add("Debit Card");

        ArrayAdapter<String> cardTypeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, cardTypes);
        cardTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardTypeSpinner.setAdapter(cardTypeAdapter);

        // Get reference to the card type TextView
        TextView cardTypeTextView = view.findViewById(R.id.cardtype);

        // Set listener for card type spinner
        cardTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Update card type TextView based on selected item
                String selectedCardType = cardTypes.get(position);
                cardTypeTextView.setText(selectedCardType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        Button b1 = view.findViewById(R.id.paynow);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new PaymentSuccess());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        return view;
    }

    private String formatCardNumber(String cardNumber) {
        StringBuilder formattedNumber = new StringBuilder();
        for (int i = 0; i < cardNumber.length(); i++) {
            if (i > 0 && i % 4 == 0) {
                formattedNumber.append(" "); // Add a gap after every 4 digits
            }
            formattedNumber.append(cardNumber.charAt(i));
        }
        return formattedNumber.toString();
    }

    // Update the expire date TextView with values from spinners
    private void updateExpireDateTextView() {
        String month = (String) monthSpinner.getSelectedItem();
        String year = (String) yearSpinner.getSelectedItem();
        expireDateTextView.setText(month + "/" + year.substring(2));
    }

    // Helper method to get a list of years
    private String[] getYears() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        String[] years = new String[10];
        for (int i = 0; i < 10; i++) {
            years[i] = String.valueOf(currentYear + i);
        }
        return years;
    }
}