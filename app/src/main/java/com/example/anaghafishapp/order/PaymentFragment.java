package com.example.anaghafishapp.order;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import com.example.anaghafishapp.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PaymentFragment extends Fragment implements PaymentResultListener {

    private RadioButton radioButtonOption1;
    private RadioButton radioButtonOption2;
    private RadioButton radioButtonOption3;
    private RadioButton radioButtonOption4;
    private Button submitButton;
    private RadioButton lastSelectedRadioButton;
    private TextView totalPaymentTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        // Initialize UI components
        radioButtonOption1 = view.findViewById(R.id.radioButtonOption1);
        radioButtonOption2 = view.findViewById(R.id.radioButtonOption2);
        radioButtonOption3 = view.findViewById(R.id.radioButtonOption3);
        radioButtonOption4 = view.findViewById(R.id.radioButtonOption4);
        submitButton = view.findViewById(R.id.submitButton);
        totalPaymentTextView = view.findViewById(R.id.totalpayment);

        getParentFragmentManager().setFragmentResultListener("datafrom", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String data = result.getString("totalpayment");
                totalPaymentTextView.setText(data);
            }
        });

        radioButtonOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleRadioButtonClick(radioButtonOption1);
            }
        });

        radioButtonOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleRadioButtonClick(radioButtonOption2);
            }
        });

        radioButtonOption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleRadioButtonClick(radioButtonOption3);
            }
        });

        radioButtonOption4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleRadioButtonClick(radioButtonOption4);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radioButtonOption1.isChecked()) {
                    //startRazorpayPayment();
                    navigateToCartPaymnetFragment();
                } else if (radioButtonOption2.isChecked()) {
                    //initiateUpiPayment();
                    showUpiAddressDialog();
                } else if (radioButtonOption3.isChecked()) {
                    startRazorpayPayment();
                } else if (radioButtonOption4.isChecked()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Payment", lastSelectedRadioButton.getText().toString());
                    getParentFragmentManager().setFragmentResult("datafrom2", bundle);

                    PlaceOrderFragment paymentSuccessFragment = new PlaceOrderFragment();
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, paymentSuccessFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });

        return view;
    }

    private void handleRadioButtonClick(RadioButton radioButton) {
        if (lastSelectedRadioButton != null) {
            lastSelectedRadioButton.setChecked(false);
        }
        radioButton.setChecked(true);
        lastSelectedRadioButton = radioButton;
    }

  private void startRazorpayPayment() {
      String samount = String.valueOf(totalPaymentTextView.getText()).replace("₹", "");
      int amount = Math.round(Float.parseFloat(samount) * 100);

      Checkout checkout = new Checkout();
      checkout.setKeyID("rzp_test_18P57LdQikECv4"); // Replace with your Razorpay key ID

      try {
          JSONObject object = new JSONObject();
          JSONArray paymentMethods = new JSONArray();
          object.put("name", "Anaghafish");
          object.put("description", "Fish order payment");
          object.put("theme.color", "");
          object.put("amount", amount);
          object.put("prefill.contact", "1234567890"); // Replace with user's contact information
          object.put("prefill.email", "demo@gmail.com"); // Replace with user's email



          JSONObject options = new JSONObject();
          options.put("payment_method", paymentMethods);
          object.put("options", options);

          // Set the PaymentResultListener before calling checkout.open()
          //checkout.setImage(R.drawable.logo); // You can set your logo here
          checkout.setFullScreenDisable(false); // Optional: Allow full-screen payment UI

          // Set the listener for payment success and failure
          FragmentActivity activity = getActivity();
          if (activity != null) {
              // Set the listener for payment success and failure
              checkout.open(activity, object);
          }
      } catch (JSONException e) {
          e.printStackTrace();
      }
  }
   /*private void initiateUpiPayment(String customerUpiVpa) {
        String amount = totalPaymentTextView.getText().toString().replace("₹", "");

        // Create UPI payment Intent
        Uri uri = Uri.parse("upi://pay?pa=" + customerUpiVpa +
                "&pn=CustomerName&mc=1234&tid=12345&tr=testing123&tn=Test&am=" + amount + "&cu=INR" +
                "&url=https://example.com"); // Replace 'https://example.com' with your callback URL

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        startActivityForResult(intent, 0);  // You may handle the result in onActivityResult
    }*/
   private void initiateUpiPayment(String adminUpiVpa) {
       String amount = totalPaymentTextView.getText().toString().replace("₹", "");

       // Create UPI payment Intent
       Uri uri = Uri.parse("upi://pay?pa=" + adminUpiVpa +
               "&pn=AdminName&mc=1234&tid=12345&tr=testing123&tn=Test&am=" + amount + "&cu=INR" +
               "&url=https://example.com"); // Replace 'https://example.com' with your callback URL

       Intent intent = new Intent(Intent.ACTION_VIEW, uri);

       startActivityForResult(intent, 0);  // You may handle the result in onActivityResult
   }

    private void showUpiAddressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Enter UPI ID");

        // Set up the input
        final EditText input = new EditText(getActivity());
        input.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String upiVpa = input.getText().toString();
                if (!TextUtils.isEmpty(upiVpa)) {
                    initiateUpiPayment(upiVpa);
                } else {
                    Toast.makeText(getActivity(), "UPI Virtual Address cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Create and show the dialog
        final AlertDialog dialog = builder.create();
        dialog.show();

        // Enable the "OK" button only if the input is not empty
        final Button okButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        okButton.setEnabled(false);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                okButton.setEnabled(!TextUtils.isEmpty(editable));
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == getActivity().RESULT_OK) {
                String transactionId = data.getStringExtra("response");
                Toast.makeText(getActivity(), "UPI Payment Successful. Transaction ID: " + transactionId, Toast.LENGTH_SHORT).show();
                loadPaymentSuccessFragment();
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                Toast.makeText(getActivity(), "UPI Payment Canceled", Toast.LENGTH_SHORT).show();
            } else {
                String error = data.getStringExtra("response");
                Toast.makeText(getActivity(), "UPI Payment Failed. Error: " + error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadPaymentSuccessFragment() {
        PlaceOrderFragment paymentSuccessFragment = new PlaceOrderFragment();
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, paymentSuccessFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onPaymentSuccess(String s) {
        loadPaymentSuccessFragment();
        Log.d("PaymentFragment", "Payment successful. Transaction ID: " + s);

    }

    @Override
    public void onPaymentError(int i, String s) {

    }

    private void navigateToCartPaymnetFragment() {
        // Replace 'YourFragmentContainer' with the ID of the container (e.g., FrameLayout) in your activity's layout
        // Create the instance of the fragment where you want to pass the data
        CartPaymentFragment orderConfirmed = new CartPaymentFragment();

        // Use FragmentTransaction to replace the current fragment with the new one
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, orderConfirmed);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
