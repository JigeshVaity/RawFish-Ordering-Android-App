package com.example.anaghafishapp;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.anaghafishapp.Domain.FoodDomain;
import com.example.anaghafishapp.retrieve.Product;

import java.util.List;

public class SelectedItemsView extends ViewModel {
    private MutableLiveData<List<FoodDomain>> selectedItems = new MutableLiveData<>();

    public MutableLiveData<List<FoodDomain>> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<FoodDomain> items) {
        selectedItems.setValue(items);
    }
}
