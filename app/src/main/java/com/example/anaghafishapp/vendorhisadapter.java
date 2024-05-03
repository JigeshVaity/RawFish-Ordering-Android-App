package com.example.anaghafishapp;

import static java.security.AccessController.getContext;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anaghafishapp.Useractivity.SeeMVendorFragment;
import com.example.anaghafishapp.retrieve.Product;
import com.example.anaghafishapp.vendor.vendorhistoryFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import android.view.LayoutInflater;


public class vendorhisadapter extends FirebaseRecyclerAdapter<Product, vendorhisadapter.myviewholder>
{

    private final vendorhistoryFragment fragment;


    public vendorhisadapter(@NonNull FirebaseRecyclerOptions<Product> options, vendorhistoryFragment fragment) {
        super(options);
        this.fragment = fragment;
    }
    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, final int position, @NonNull Product model) {

        // Bind the data to the ViewHolder views
        holder.productNameTextView.setText(model.getProduct_name());
        holder.vendorNameTextView.setText(model.getVendor_name());
        // Load image using a library like Picasso or Glide
        Picasso.get().load(model.getImage_url()).into(holder.imageView);
        holder.priceTextView.setText(model.getPrice());

        holder.editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.imageView.getContext())
                        .setContentHolder(new ViewHolder(LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.dialogcontent, null)))
                        .setExpanded(true, 1100)
                        .create();

                View myview = dialogPlus.getHolderView();
                final EditText location = myview.findViewById(R.id.uloc);
                final EditText phoneno = myview.findViewById(R.id.uphno);
                final EditText price = myview.findViewById(R.id.uprice);
                final EditText productname = myview.findViewById(R.id.upname);
                final EditText quantity = myview.findViewById(R.id.uqauntity);
                final EditText size = myview.findViewById(R.id.usize);

                Button submit = myview.findViewById(R.id.usubmit);

                // Set data from the model to the EditText fields
                location.setText(model.getLocation());
                phoneno.setText(model.getPhone_number());
                price.setText(model.getPrice());
                productname.setText(model.getProduct_name());
                quantity.setText(model.getQuantity());
                size.setText(model.getSize());

                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("location", location.getText().toString());
                        map.put("phone_number", phoneno.getText().toString());
                        map.put("price", price.getText().toString());
                        map.put("prodcut_name", productname.getText().toString());
                        map.put("quantity", quantity.getText().toString());
                        map.put("size", size.getText().toString());

                        // Use holder.getAdapterPosition() to get the current position
                        FirebaseDatabase.getInstance().getReference("VendorProductlist").child("VendorName")
                                .child(getRef(holder.getAdapterPosition()).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });

        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.imageView.getContext());
                builder.setTitle("Delete Panel");
                builder.setMessage("Delete...?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Use holder.getAdapterPosition() to get the current position
                        FirebaseDatabase.getInstance().getReference("VendorProductlist").child("VendorName")
                                .child(getRef(holder.getAdapterPosition()).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Do nothing if "No" is clicked
                    }
                });

                builder.show();
            }
        });
    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vendorhis,parent,false);
       return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        public TextView productNameTextView;
        public TextView vendorNameTextView;
        public ImageView imageView;
        public TextView phoneNumberTextView;
        public TextView priceTextView;

        public TextView deletebtn,editbtn;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productName);
            vendorNameTextView = itemView.findViewById(R.id.vendorName);
            imageView = itemView.findViewById(R.id.productImage);
            //phoneNumberTextView = itemView.findViewById(R.id.phone_number);
            priceTextView = itemView.findViewById(R.id.productPrice);

            deletebtn = itemView.findViewById(R.id.deletebtn);
            editbtn = itemView.findViewById(R.id.editbtn);
        }
    }
}
