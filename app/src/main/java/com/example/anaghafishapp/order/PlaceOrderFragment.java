package com.example.anaghafishapp.order;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anaghafishapp.Domain.FoodDomain;
import com.example.anaghafishapp.Domain.Order;
import com.example.anaghafishapp.Adapter.MyAdapter;
import com.example.anaghafishapp.R;

import com.example.anaghafishapp.helper.ManagementCart;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PlaceOrderFragment extends Fragment {

    private RecyclerView.Adapter adapter;

    private ManagementCart managementCart;

    private RecyclerView recyclerViewList;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private TextView name1TextView;
    private TextView address1TextView;
    private static TextView totalPriceTextView;
    private static TextView price1TextView;
    private static TextView taxttext;
    private static TextView delivery1TextView;

    private TextView name1;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private Bundle fragmentResultBundle;
    private TextView payMethodTextView;

    private static final String CHANNEL_ID = "anagha";
    private static final String CHANNEL_NAME = "Anaghafish";
    private static final String CHANNEL_DESC = "Anagha raw fish app";
    private static final int SMS_PERMISSION_REQUEST_CODE = 100;
    ProgressBar progressBar;

    private String a;
    private Button submitButton;


    ///////////////////////////

    // TODO: Rename and change types and number of parameters
    public static PlaceOrderFragment newInstance(String param1, String param2) {
        PlaceOrderFragment fragment = new PlaceOrderFragment();
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
        managementCart = new ManagementCart(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place_order, container, false);


        // Initialize UI components
        name1 = view.findViewById(R.id.name1);
        name1TextView = view.findViewById(R.id.nametxt2);
        address1TextView = view.findViewById(R.id.address1);
        price1TextView = view.findViewById(R.id.price1);
        delivery1TextView = view.findViewById(R.id.delivery1);
        totalPriceTextView = view.findViewById(R.id.totalprice);
        radioButton1 = view.findViewById(R.id.radioButton1);
        radioButton2 = view.findViewById(R.id.radioButton2);
        radioButton3 = view.findViewById(R.id.radioButton3);
        //payMethodTextView = view.findViewById(R.id.paymethod);
        progressBar = view.findViewById(R.id.progressBar);
        submitButton = view.findViewById(R.id.submitButton);
        taxttext = view.findViewById(R.id.tax);
        // Set listeners or perform any other operations as needed
        initList(view);
        RadioGroup deliveryTimeRadioGroup = view.findViewById(R.id.deliveryTimeRadioGroup);
       // TextView totalPriceTextView = view.findViewById(R.id.totalprice);

        deliveryTimeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            boolean additionalChargeApplied = false; // Flag to keep track of whether additional charge has been applied

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                double totalPrice = Double.parseDouble(totalPriceTextView.getText().toString().replace("₹", ""));
                if (checkedId == R.id.radioButton1 && !additionalChargeApplied) {
                    totalPrice += 20.0; // Add 20 rupees only if radioButton1 is selected and additional charge is not applied already
                    additionalChargeApplied = true; // Set the flag to true to indicate that the additional charge has been applied
                } else if (checkedId != R.id.radioButton1 && additionalChargeApplied) {
                    totalPrice -= 20.0; // Remove 20 rupees if radioButton1 is unselected and additional charge is applied
                    additionalChargeApplied = false; // Set the flag to false to indicate that the additional charge has been removed
                }
                totalPriceTextView.setText("₹" + totalPrice); // Update the total price TextView
            }
        });




        // this code for setting the value from another fragment
        getParentFragmentManager().setFragmentResultListener("naruto", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                // Handle the fragment result here
                // The 'requestKey' will be "datafrom" and 'result' will contain the data

                String data1 = result.getString("totalpayment");
                String data2 = result.getString("total");
                String data3 = result.getString("tax");
                String data4 = result.getString("delivery");
                // String data5 = result.getString("Name");
                // String data6 = result.getString("Address");

                totalPriceTextView.setText(data1);
                price1TextView.setText(data2);
                taxttext.setText(data3);
                delivery1TextView.setText(data4);


            }
        });
        getParentFragmentManager().setFragmentResultListener("datafrom1", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                String data5 = result.getString("Name");
                String data6 = result.getString("Address");

                name1TextView.setText(data5);
                address1TextView.setText(data6);
                name1.setText(data5);

            }
        });

        getParentFragmentManager().setFragmentResultListener("datafrom2", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                // Check if the Payment data is present in the result bundle
                if (result.containsKey("Payment")) {
                    String data7 = result.getString("Payment");
                    // payMethodTextView.setText(data7);
                } else {
                    // Handle the case when Payment data is not present, set a default value or handle it as needed
                    //payMethodTextView.setText("Default Payment Method");
                }
            }
        });


        getParentFragmentManager().setFragmentResultListener("datafrom3", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                String flatHouse = result.getString("flatHouse");
                String area = result.getString("area");
                String landmark = result.getString("landmark");
                String town = result.getString("town");
                String pincode = result.getString("pincode");
                String state = result.getString("state");
                String phone = result.getString("phone");
                a = phone;
                // Now, you can set the data to the TextViews or wherever needed
                TextView flatHouseTextView = view.findViewById(R.id.address2);
                flatHouseTextView.setText(flatHouse + ", " + area + ", " + landmark + ", " + town + ", " + pincode + ", " + state + "\nPhone no-" + phone);
                fragmentResultBundle = result;
            }
        });

        getParentFragmentManager().setFragmentResultListener("datafrom4", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                // Iterate over the keys and extract product titles and quantities
                for (String key : result.keySet()) {
                    if (key.startsWith("productTitle")) {
                        String productTitle = result.getString(key);
                        String productQuantity = result.getString("productQuantity" + key.substring("productTitle".length()));

                        // Now, you can use productTitle and productQuantity as needed
                        // For example, you might want to display them in a TextView or log them
                        Log.d("PlaceOrderFragment", "Product Title: " + productTitle + ", Quantity: " + productQuantity);
                    }
                }
            }
        });

      /*  submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve data from views
                String name = name1.getText().toString();
                String address = address1TextView.getText().toString();
                String deliveryTime = "";
                if (radioButton1.isChecked()) {
                    deliveryTime = radioButton1.getText().toString();
                } else if (radioButton2.isChecked()) {
                    deliveryTime = radioButton2.getText().toString();
                } else if (radioButton3.isChecked()) {
                    deliveryTime = radioButton3.getText().toString();
                }
                String totalPrice = totalPriceTextView.getText().toString();
                // Assuming you have a method to get the list of products
                List<FoodDomain> products = managementCart.getListCart();
////////////////////////////////////////
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // User is signed in
                    String phoneNumber = user.getPhoneNumber();
                    String email = user.getEmail();
                    // You can use either phone number or email as user login data
                } else {
                    // No user is signed in
                }
////////////////////////////////////

                String phoneNumber = fragmentResultBundle.getString("phone");// Replace this with the actual phone number
                // String phoneNumber =a;

                // Create a data model object
                Order order = new Order(name, address, deliveryTime, totalPrice, products);

                // Get a reference to your Firebase database
                DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders");

                // Push the order to Firebase database
                ordersRef.push().setValue(order)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //to clear the cartframent the recylerview
                                managementCart.clearCart();
                                // call fucntion to display the notification
                                displayNotification();
                                navigateToPaymentFragment();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle error
                                Toast.makeText(getContext(), "Failed to place order. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });*/
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve data from views
                String name = name1.getText().toString();
                String address = address1TextView.getText().toString();
                String deliveryTime = "";
                if (radioButton1.isChecked()) {
                    deliveryTime = radioButton1.getText().toString();
                } else if (radioButton2.isChecked()) {
                    deliveryTime = radioButton2.getText().toString();
                } else if (radioButton3.isChecked()) {
                    deliveryTime = radioButton3.getText().toString();
                }
                String totalPrice = totalPriceTextView.getText().toString();
                // Assuming you have a method to get the list of products
                List<FoodDomain> products = managementCart.getListCart();
                progressBar.setVisibility(View.VISIBLE);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String loginData = "";
                if (user != null) {
                    if (user.getPhoneNumber() != null) {
                        loginData = user.getPhoneNumber(); // User logged in with phone number
                    } else if (user.getEmail() != null) {
                        loginData = user.getEmail(); // User logged in with email
                    }
                } else {
                    // No user is signed in
                    // Handle this case accordingly, such as prompting the user to sign in
                    Toast.makeText(getContext(), "Please sign in to place the order", Toast.LENGTH_SHORT).show();
                    // You might also want to return or exit the method here
                }


                // Create a data model object
                Order order = new Order(name, address, deliveryTime, totalPrice, products, loginData);

                // Get a reference to your Firebase database
                DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders");

                // Push the order to Firebase database
                ordersRef.push().setValue(order)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Clear the cart
                                managementCart.clearCart();
                                // Display the notification
                                progressBar.setVisibility(View.GONE);
                                displayNotification();
                                // Navigate to the payment fragment
                                navigateToPaymentFragment();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressBar.setVisibility(View.GONE);
                                // Handle error
                                Toast.makeText(getContext(), "Failed to place order. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });




        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("PlaceOrderFragment", "onResume called");
    }

    private void navigateToPaymentFragment() {
        // Replace 'YourFragmentContainer' with the ID of the container (e.g., FrameLayout) in your activity's layout
        // Create the instance of the fragment where you want to pass the data
        OrderConfirmed orderConfirmed = new OrderConfirmed();


        // Use FragmentTransaction to replace the current fragment with the new one
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, orderConfirmed);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    private void initList(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewList = view.findViewById(R.id.displayproduct);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        // adapter = new CartListAdapter(managementCart.getListCart(), requireContext(), new ChangeNumberItemsListener() {
        adapter = new MyAdapter(managementCart.getListCart());

        recyclerViewList.setAdapter(adapter);

    }

    private void displayNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannelCompat channel = new NotificationChannelCompat.Builder(CHANNEL_ID, NotificationManagerCompat.IMPORTANCE_DEFAULT)
                    .setName(CHANNEL_NAME)
                    .setDescription(CHANNEL_DESC)
                    .build();
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(requireContext());
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.applogo)
                        .setContentTitle("Anaghafish")
                        .setContentText("Your order is confirmed. Thank you for shopping with us! Total Price: " + totalPriceTextView.getText().toString())
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(requireContext());
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManagerCompat.notify(1, mBuilder.build());

        String message = "Your order from Anaghafish is confirmed. Thank you for shopping with us!";
        String phoneNumber = "9757115779"; // " a " Replace with the recipient's phone number

        // Check SMS permission
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, send the message
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        } else {
            // Permission not granted, request it
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_REQUEST_CODE);
        }
    }


}