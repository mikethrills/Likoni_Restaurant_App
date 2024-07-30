package com.example.likonirestaurante.Helper;

import android.content.Context;
import android.widget.Toast;

import com.example.likonirestaurante.Domain.FoodDomain;
import com.example.likonirestaurante.Interface.ChangeNumberItemListener;

import java.util.ArrayList;

public class ManagementCart {
    private Context context;
    private TinyDB tinyDB;

    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

//The "insertFood" method is used to insert a FoodDomain object into an ArrayList of FoodDomain objects stored in the local device's memory.
    public void insertFood(FoodDomain item) {

    //It retrieves the list of FoodDomain objects from the local memory using the "getListCart" method.
        ArrayList<FoodDomain> listFood = getListCart();

    // It checks if the FoodDomain object that is being inserted already exists in the list using a for loop.
        boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listFood.size(); i++) {
            if (listFood.get(i).getTitle().equals(item.getTitle())) {
                existAlready = true;
                n = i;
                break;
            }
        }
    //If the FoodDomain object exists in the list, the number in cart for that item is updated.
        if (existAlready) {
            listFood.get(n).setNumberInCart(item.getNumberInCart());
            item.getTime();
        } else {
    //  If the FoodDomain object doesn't exist in the list, it is added to the list.
            listFood.add(item);
            item.getTime();

        }
    //The updated list of FoodDomain objects is then stored back in the local memory using TinyDB's "putListObject" method.
        tinyDB.putListObject("CardList", listFood); //saves the object
        Toast.makeText(context, "Item Added to your Cart", Toast.LENGTH_SHORT).show();

    }

     public ArrayList <FoodDomain> getListCart() {
        return tinyDB.getListObject("CardList");// retrieves the object from storage
    }

    public void minusNumberFood(ArrayList<FoodDomain> listfood, int position, ChangeNumberItemListener changeNumberItemListener) {
        if (listfood.get(position).getNumberInCart() == 1) {
            listfood.remove(position);
        } else {
            listfood.get(position).setNumberInCart(listfood.get(position).getNumberInCart() - 1);
        }
    tinyDB.putListObject("CardList", listfood);
    changeNumberItemListener.changed();
    }

    public void plusNumberFood(ArrayList<FoodDomain> listfood, int position, ChangeNumberItemListener changeNumberItemListener){
        listfood.get(position).setNumberInCart(listfood.get(position).getNumberInCart() +1);
        tinyDB.putListObject("CardList", listfood);
        changeNumberItemListener.changed();
    }

    public void DeleteItems (ArrayList<FoodDomain> listfood, int position, ChangeNumberItemListener changeNumberItemListener){
        listfood.remove(position);
        tinyDB.putListObject("CardList", listfood);
        changeNumberItemListener.changed();
    }


    public double getTotalFee(){
    ArrayList<FoodDomain>listfood2 = getListCart();
        double fee = 0;
        for (int i = 0; i < listfood2.size(); i++) {
            fee=fee+(listfood2.get(i).getFee()*listfood2.get(i).getNumberInCart());
        }
        return fee;

    }

    public double getTotalTax(){
        ArrayList<FoodDomain>listfood2 = getListCart();
        double tax = 0;
        for (int i = 0; i < listfood2.size(); i++) {
            tax = tax + (listfood2.get(i).getFee());



        }
        return tax;
    }

    public int getTotalETA(){
    ArrayList<FoodDomain>listfood2 = getListCart();
        int ETA = 0;
        for (int i = 0; i < listfood2.size(); i++) {
            ETA = ETA+(listfood2.get(i).getTime());



        }
        return ETA;
    }


}

