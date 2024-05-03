package com.example.anaghafishapp;

import android.content.Context;
import android.widget.Toast;

import com.example.anaghafishapp.Domain.FoodDomain;
import com.example.anaghafishapp.Domain.VendorDomain;
import com.example.anaghafishapp.Interface.ChangeNumberItemsListener;
import com.example.anaghafishapp.helper.TinyDB;
import com.example.anaghafishapp.retrieve.Product;

import java.io.Serializable;
import java.util.ArrayList;

public class ManagementCartone implements Serializable {
    private final Context context;
    private final TinyDBTwo tinyDB;

    public ManagementCartone(Context context) {
        this.context = context;
        this.tinyDB = new TinyDBTwo(context);
    }

    public void insertFood(Product item) {
        if (item == null || item.getProduct_name() == null) {
            // Handle null product or null product name gracefully
            return;
        }

        ArrayList<Product> listFood = getListCart();
        boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listFood.size(); i++) {
            String productName = listFood.get(i).getProduct_name();
            if (productName != null && productName.equals(item.getProduct_name())) {
                existAlready = true;
                n = i;
                break;
            }
        }
        if (existAlready) {
            listFood.get(n).setNumberInCart(item.getNumberInCart());
        } else {
            listFood.add(item);
        }

        tinyDB.putListObject("CartList", listFood);
        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show();
    }


    public ArrayList<Product> getListCart() {
        return tinyDB.getListObject("CartList");
    }

    public void minusNumberFood(ArrayList<Product> listfood, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        if (listfood.get(position).getNumberInCart() == 1) {
            listfood.remove(position);
        } else {
            listfood.get(position).setNumberInCart(listfood.get(position).getNumberInCart() - 1);
        }

        tinyDB.putListObject("CartList", listfood);
        changeNumberItemsListener.changed();
    }

    public void plusNumberFood(ArrayList<Product> listfood, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        listfood.get(position).setNumberInCart(listfood.get(position).getNumberInCart() + 1);
        tinyDB.putListObject("CartList", listfood);
        changeNumberItemsListener.changed();
    }

    /* public Double getTotalFee() {
         ArrayList<Product> listfood2 = getListCart();
         double fee = 0;
         for (int i = 0; i < listfood2.size(); i++) {
             Product product = listfood2.get(i);
             if (product.getPrice() != null) {
                 double price = Double.parseDouble(product.getPrice());
                 fee += price * product.getNumberInCart();
             }
         }
         return fee;
     }*/
    public Double getTotalFee() {
        ArrayList<Product> listfood2 = getListCart();
        if (listfood2 == null || listfood2.isEmpty()) {
            return 0.0; // Return 0 if the list is null or empty
        }
        double fee = 0;
        for (int i = 0; i < listfood2.size(); i++) {
            Product product = listfood2.get(i);
            if (product.getPrice() != null) {
                double price = Double.parseDouble(product.getPrice());
                fee += price * product.getNumberInCart();
            }
        }
        return fee;
    }


}
