package com.example.anaghafishapp.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.anaghafishapp.R;
import com.google.android.material.textfield.TextInputEditText;

public class AddressFragment extends Fragment {

    private TextInputEditText fullnameEditText, phoneEditText, addressEditText, streetEditText,
            landmarkEditText, pincodeEditText, cityEditText, stateEditText;
    private Button submitButton;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, container, false);

        // Initialize UI components
        fullnameEditText = view.findViewById(R.id.fullname);
        phoneEditText = view.findViewById(R.id.phonno);
        addressEditText = view.findViewById(R.id.address);
        streetEditText = view.findViewById(R.id.street);
        landmarkEditText = view.findViewById(R.id.landmark);
        pincodeEditText = view.findViewById(R.id.pincode);
        cityEditText = view.findViewById(R.id.city);
        stateEditText = view.findViewById(R.id.state);
        submitButton = view.findViewById(R.id.submitButton);

        // Set onFocusChangeListeners to clear errors when gaining focus
        setFocusChangeListener(fullnameEditText);
        setFocusChangeListener(phoneEditText);
        setFocusChangeListener(addressEditText);
        setFocusChangeListener(streetEditText);
        setFocusChangeListener(landmarkEditText);
        setFocusChangeListener(pincodeEditText);
        setFocusChangeListener(cityEditText);
        setFocusChangeListener(stateEditText);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInputs()) {
                    // All EditText fields are properly filled

                    // this code for passing the value to anther fragment
                    Bundle bundle = new Bundle();
                    bundle.putString("Name",fullnameEditText.getText().toString());
                    bundle.putString("Address",addressEditText.getText().toString());

                    getParentFragmentManager().setFragmentResult("datafrom1",bundle);

                    Bundle bundle2 = new Bundle();
                    bundle2.putString("flatHouse",addressEditText.getText().toString());
                    bundle2.putString("area", streetEditText.getText().toString());
                    bundle2.putString("phone",phoneEditText.getText().toString());
                    bundle2.putString("landmark", landmarkEditText.getText().toString());
                    bundle2.putString("town",cityEditText.getText().toString());
                    bundle2.putString("pincode", pincodeEditText.getText().toString());
                    bundle2.putString("state", stateEditText.getText().toString());
                    getParentFragmentManager().setFragmentResult("datafrom3",bundle2);

                    navigateToPaymentFragment();
                }
            }
        });

        return view;
    }

    // Validate the input fields
    /*
    private boolean validateInputs() {
        boolean isValid = true;

        if (fullnameEditText.getText().toString().isEmpty()) {
            fullnameEditText.setError("Enter your fullname");
            isValid = false;
        }

        if (phoneEditText.getText().toString().isEmpty()) {
            phoneEditText.setError("Enter phone no");
            isValid = false;
        }

        if (addressEditText.getText().toString().isEmpty()) {
            addressEditText.setError("Enter address");
            isValid = false;
        }

        if (streetEditText.getText().toString().isEmpty()) {
            streetEditText.setError("Enter street");
            isValid = false;
        }

        if (landmarkEditText.getText().toString().isEmpty()) {
            landmarkEditText.setError("Enter landmark");
            isValid = false;
        }

        if (pincodeEditText.getText().toString().isEmpty()) {
            pincodeEditText.setError("Enter pincode");
            isValid = false;
        }

        if (cityEditText.getText().toString().isEmpty()) {
            cityEditText.setError("Enter city");
            isValid = false;
        }

        if (stateEditText.getText().toString().isEmpty()) {
            stateEditText.setError("Enter state");
            isValid = false;
        }

        return isValid;
    }*/
// Validate the input fields
    // Validate the input fields
    private boolean validateInputs() {
        boolean isValid = true;

        if (fullnameEditText.getText().toString().isEmpty() || fullnameEditText.getText().toString().split("\\s+").length < 2) {
            fullnameEditText.setError("Enter a valid fullname (at least 2 words)");
            isValid = false;
        }

        if (phoneEditText.getText().toString().isEmpty() || phoneEditText.getText().toString().length() < 8 || phoneEditText.getText().toString().length() > 10) {
            phoneEditText.setError("Enter a valid phone number 10 digits");
            isValid = false;
        } else if (!isValidPhoneNumber(phoneEditText.getText().toString())) {
            phoneEditText.setError("Invalid phone number");
            isValid = false;
        }

        if (addressEditText.getText().toString().isEmpty()) {
            addressEditText.setError("Enter address");
            isValid = false;
        }

        if (streetEditText.getText().toString().isEmpty()) {
            streetEditText.setError("Enter street");
            isValid = false;
        }

        if (landmarkEditText.getText().toString().isEmpty()) {
            landmarkEditText.setError("Enter landmark");
            isValid = false;
        }

        if (pincodeEditText.getText().toString().isEmpty() || pincodeEditText.getText().toString().length() != 6) {
            pincodeEditText.setError("Enter a valid pincode 6 digits");
            isValid = false;
        } else if (!isValidPincode(pincodeEditText.getText().toString())) {
            pincodeEditText.setError("Invalid pincode");
            isValid = false;
        }

        if (cityEditText.getText().toString().isEmpty()) {
            cityEditText.setError("Enter city");
            isValid = false;
        }

        if (stateEditText.getText().toString().isEmpty() || !isValidState(stateEditText.getText().toString())) {
            stateEditText.setError("Enter a valid state in India");
            isValid = false;
        }

        return isValid;
    }

    // Method to validate state
    private boolean isValidState(String state) {
        // List of Indian states for validation
        String[] indianStates = {"Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand", "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal", "Andaman and Nicobar Islands", "Chandigarh", "Dadra and Nagar Haveli and Daman and Diu", "Delhi", "Lakshadweep", "Puducherry"};

        // Check if the state exists in the list of Indian states
        for (String indianState : indianStates) {
            if (indianState.equalsIgnoreCase(state.trim())) {
                return true;
            }
        }
        return false;
    }

// Existing code...


    // Method to validate phone number
    private boolean isValidPhoneNumber(String phoneNumber) {
        // Implement your phone number validation logic here
        // For example, you can use regular expressions to check the format
        // For simplicity, let's assume a valid phone number is a 10-digit number
        return phoneNumber.length() == 10 && phoneNumber.matches("[0-9]+");
    }

    // Method to validate pincode
    private boolean isValidPincode(String pincode) {
        // Implement your pincode validation logic here
        // For simplicity, let's assume a valid pincode is a 6-digit number
        return pincode.length() == 6 && pincode.matches("[0-9]+");
    }


    private void navigateToPaymentFragment() {
        // Replace 'YourFragmentContainer' with the ID of the container (e.g., FrameLayout) in your activity's layout
        // Create the instance of the fragment where you want to pass the data
        PaymentFragment paymentFragment = new PaymentFragment();


        // Use FragmentTransaction to replace the current fragment with the new one
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, paymentFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    // Existing code...

    // Set onFocusChangeListener for clearing errors when gaining focus
    private void setFocusChangeListener(final EditText editText) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    // Clear the error message when the EditText gains focus
                    editText.setError(null);
                }
            }
        });
    }
}
