    package com.example.anaghafishapp.vendor;
    
    import android.app.Activity;
    import android.content.Intent;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.graphics.drawable.BitmapDrawable;
    import android.net.Uri;
    import android.os.Build;
    import android.os.Bundle;
    import android.provider.MediaStore;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.ProgressBar;
    import android.widget.RelativeLayout;
    import android.widget.Toast;
    
    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.core.content.ContextCompat;
    import androidx.fragment.app.Fragment;
    
    import com.example.anaghafishapp.R;
    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.android.material.textfield.TextInputEditText;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;
    import com.google.firebase.storage.FirebaseStorage;
    import com.google.firebase.storage.StorageReference;
    import com.google.firebase.storage.UploadTask;
    
    import java.io.ByteArrayOutputStream;
    import java.util.HashMap;
    import java.util.Map;
    
    public class vendorhomefragment extends Fragment {
    
        private static final int REQUEST_IMAGE_CAPTURE = 1;
        private ImageView selectedImageView;
        private TextInputEditText vendorNameEditText;
        private  TextInputEditText productNameEditText;
        private  TextInputEditText priceEditText;
        private  TextInputEditText phoneNumberEditText;
        private TextInputEditText quantityEdiText;
        private  TextInputEditText sizeEdiText;
        private ProgressBar progressBar;
        private TextInputEditText locationtext;
        private Button uploadButton;
        private FirebaseStorage storage;
        private StorageReference storageRef;
        private DatabaseReference databaseRef;
    
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_vendorhomefragment, container, false);
    
            // Initialize UI elements
            selectedImageView = view.findViewById(R.id.selected_image_view);
            vendorNameEditText = view.findViewById(R.id.vendor_name_edittext);
            productNameEditText = view.findViewById(R.id.product_name_edittext);
            priceEditText = view.findViewById(R.id.price_edittext);
            phoneNumberEditText = view.findViewById(R.id.phone_edittext);
            quantityEdiText = view.findViewById(R.id.quantity_edittext);
            sizeEdiText = view.findViewById(R.id.size_edittext);
            Button selectImageButton = view.findViewById(R.id.select_image_button);
            uploadButton = view.findViewById(R.id.upload_button);
            progressBar = view.findViewById(R.id.progress_sending_otp);
            locationtext = view.findViewById(R.id.location_edittext);
    
            // Initialize Firebase Storage
            storage = FirebaseStorage.getInstance();
            storageRef = storage.getReference();
    
    
            RelativeLayout rel1 = view.findViewById(R.id.relativ1);
    
            // Set background color for rel1
            rel1.setBackgroundResource(R.color.skyblue);
    
            // Set status bar color
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.skyblue));
            }
    
    
            // Initialize Firebase Realtime Database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            databaseRef = database.getReference("VendorProductlist");
    
            // Set a click listener for the "Select Image" button
            selectImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, REQUEST_IMAGE_CAPTURE);
                }
            });
    
            // Set a click listener for the "Upload" button
            uploadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE); // Show progress bar when upload starts
                    String productName = productNameEditText.getText().toString();
                    String vendorName = vendorNameEditText.getText().toString();
                    String price = priceEditText.getText().toString();
                    String phoneNumber = phoneNumberEditText.getText().toString();
                    String quantity = quantityEdiText.getText().toString();
                    String size = sizeEdiText.getText().toString();
                    String location = locationtext.getText().toString();
                  /*  if (productName.isEmpty() || vendorName.isEmpty() || price.isEmpty() || phoneNumber.isEmpty() || quantity.isEmpty() || size.isEmpty()) {
                        Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_SHORT).show();
    
                        progressBar.setVisibility(View.INVISIBLE);
                        uploadButton.setVisibility(View.VISIBLE);
                    } else {
    
                        progressBar.setVisibility(View.VISIBLE);
                        uploadButton.setVisibility(View.INVISIBLE);
                        uploadImageToStorage(productName, vendorName, price, phoneNumber, quantity, size,location);
                    }*/
                    if (!isValidQuantity(quantity)) {
                        quantityEdiText.setError("Enter a valid quantity (e.g., 1kg, 500g, --)");
                        return;
                    }
                    if (!isValidSize(size)) {
                        sizeEdiText.setError("Enter a valid size (S, M, L, Small, Medium, Large)");
                        return;
                    }
                    if (!isValidPhoneNumber(phoneNumber)) {
                        phoneNumberEditText.setError("Enter a valid 10-digit phone number");
                        return;
                    }
                    //
                    if (productName.isEmpty() || vendorName.isEmpty() || price.isEmpty() || phoneNumber.isEmpty() || quantity.isEmpty() || size.isEmpty() || location.isEmpty()) {
                        Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_SHORT).show();

                        progressBar.setVisibility(View.INVISIBLE);
                        uploadButton.setVisibility(View.VISIBLE);
                    } else {

                        progressBar.setVisibility(View.VISIBLE);
                        uploadButton.setVisibility(View.INVISIBLE);
                        uploadImageToStorage(productName, vendorName, price, phoneNumber, quantity, size,location);
                    }

                }
            });
    
            return view;
        }
        // Method to validate quantity
        // Method to validate quantity
        private boolean isValidQuantity(String quantity) {
            return quantity.matches("\\d+kg") || quantity.matches("\\d+g") || quantity.matches("\\d+pieces")|| quantity.equals("--");
        }

        // Method to validate size
        private boolean isValidSize(String size) {
            return size.equals("s") || size.equals("m") || size.equals("l") || size.equals("small") || size.equals("medium") || size.equals("large") || size.equals("--");
        }

        private boolean isValidPhoneNumber(String phoneNumber) {
            return phoneNumber.matches("\\d{10}");
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK && data != null) {
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 4;
                    Bitmap selectedImage = BitmapFactory.decodeStream(
                            requireContext().getContentResolver().openInputStream(data.getData()),
                            null,
                            options
                    );
    
                    if (selectedImage != null) {
                        selectedImageView.setImageBitmap(selectedImage);
                        selectedImageView.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(requireContext(), "Failed to load selected image", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(requireContext(), "Error loading selected image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    
        private void uploadImageToStorage(final String productName, final String vendorName, final String price, final String phoneNumber, final String quantity, final String size,final String location) {
            Bitmap selectedImage = ((BitmapDrawable) selectedImageView.getDrawable()).getBitmap();
    
            String timestamp = String.valueOf(System.currentTimeMillis());
            String imageName = "image_" + timestamp + ".jpg";
    
            StorageReference imageRef = storageRef.child(imageName);
    
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageData = baos.toByteArray();
    
            UploadTask uploadTask = imageRef.putBytes(imageData);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressBar.setVisibility(View.GONE);
                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageUrl = uri.toString();
                            DatabaseReference vendorRef = databaseRef.child("VendorName");
    
                            vendorRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    long vendorCount = snapshot.getChildrenCount(); // Get the current count of vendors
                                    String vendorId = "vendor" + (vendorCount + 1); // Generate the next vendor ID

                                    DatabaseReference newVendorRef = vendorRef.child(vendorId);


                                    Map<String, Object> itemData = new HashMap<>();
                                    itemData.put("image_url", imageUrl);
                                    itemData.put("product_name", productName);
                                    itemData.put("vendor_name", vendorName);
                                    itemData.put("price", price);
                                    itemData.put("phone_number", phoneNumber);
                                    itemData.put("quantity", quantity);
                                    itemData.put("size", size);
                                    itemData.put("location",location);
                                    vendorRef.child(vendorId).setValue(itemData);
    
                                    productNameEditText.setText("");
                                    vendorNameEditText.setText("");
                                    priceEditText.setText("");
                                    phoneNumberEditText.setText("");
                                    quantityEdiText.setText("");
                                    sizeEdiText.setText("");
                                    locationtext.setText("");
    
                                    progressBar.setVisibility(View.GONE);
                                    uploadButton.setVisibility(View.VISIBLE);
                                    Toast.makeText(requireContext(), "Upload successful", Toast.LENGTH_SHORT).show();
                                }
    
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Log.e("YourTag", "Error getting vendor count: " + error.getMessage());
                                }
                            });
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("YourTag", "Image upload failed: " + e.getMessage(), e);
                }
            });
        }
    }
