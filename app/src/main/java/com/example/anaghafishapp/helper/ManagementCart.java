package com.example.anaghafishapp.helper;

import android.content.Context;
import android.widget.Filter;
import android.widget.Toast;

import com.example.anaghafishapp.Domain.FoodDomain;
import com.example.anaghafishapp.Interface.ChangeNumberItemsListener;

import java.util.ArrayList;
public class ManagementCart {
    private Context context;
    private TinyDB tinyDB;

    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertFood(FoodDomain item) {
        ArrayList<FoodDomain> listFood = getListCart();
        boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listFood.size(); i++) {
            if (listFood.get(i).getTitle().equals(item.getTitle())) {
                existAlready = true;
                n = i;
                break;
            }
        }
        if (existAlready)
        {
            listFood.get(n).setNumberInCart(item.getNumberInCart());
        }
        else
        {
            listFood.add(item);
        }

        tinyDB.putListObject("CartList",listFood);
        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<FoodDomain> getListCart()
    {
        return tinyDB.getListObject("CartList");
    }
    public void minusNumberFood(ArrayList<FoodDomain> listfood, int position, ChangeNumberItemsListener changeNumberItemsListener)
    {
        if(listfood.get(position).getNumberInCart()==1)
        {
            listfood.remove(position);
        }
        else
        {
            listfood.get(position).setNumberInCart(listfood.get(position).getNumberInCart() -1 );
        }

        tinyDB.putListObject("CartList",listfood);
        changeNumberItemsListener.changed();
    }

    public void plusNumberFood(ArrayList<FoodDomain> listfood,int position,ChangeNumberItemsListener changeNumberItemsListener)
    {
        listfood.get(position).setNumberInCart(listfood.get(position).getNumberInCart() + 1);
        tinyDB.putListObject("CartList",listfood);
        changeNumberItemsListener.changed();
    }

    public Double getTotalFee()
    {
        ArrayList<FoodDomain>listfood2=getListCart();
        double fee=0;
        for(int i = 0; i< listfood2.size(); i++)
        {
            fee = fee + (listfood2.get(i).getFee() * listfood2.get(i).getNumberInCart());
        }
        return fee;
    }

    // logic for adding item to wishlist
    public void insertToWishlist(FoodDomain item) {
        ArrayList<FoodDomain> wishlist = getWishlist();
        wishlist.add(item); // Add the item to the wishlist
        tinyDB.putListObject("Wishlist", wishlist); // Save the updated wishlist
        Toast.makeText(context, "Added to your Wishlist", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<FoodDomain> getWishlist() {
        return tinyDB.getListObject("Wishlist");
    }

    public void removeFromWish(ArrayList<FoodDomain> wishlist, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        wishlist.remove(position); // Remove the item at the specified position
        tinyDB.putListObject("Wishlist", wishlist); // Save the updated wishlist
        changeNumberItemsListener.changed(); // Notify any listeners or update UI components
    }

    public void removeFromCart(ArrayList<FoodDomain> cartlist, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        cartlist.remove(position); // Remove the item at the specified position
        tinyDB.putListObject("CartList", cartlist); // Save the updated cartlist
        changeNumberItemsListener.changed(); // Notify any listeners or update UI components
    }

    public void clearCart() {
        tinyDB.remove("CartList");
    }

    public int getCartItemCount() {
        ArrayList<FoodDomain> cartItems = getListCart();
        int count = 0;
        for (FoodDomain item : cartItems) {
            count += item.getNumberInCart();
        }
        return count;
    }

    public int getWishlistItemCount() {
        ArrayList<FoodDomain> wishlistItems = getWishlist();
        return wishlistItems.size();
    }

}