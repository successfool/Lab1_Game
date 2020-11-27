package com.example.alab1_sem2_artem;

import android.net.Uri;

public class Item
{
    //private String Cell;
    private Uri Pic;
    private int Number;

    //public Item(String Cell, Uri Pic, double Number)
    public Item(Uri Pic, int Number)
    {
        //this.Cell = Cell;
        this.Pic = Pic;
        this.Number = Number;
    }

    //public String getCell(){return this.Cell;}
    public Uri getPic(){return  this.Pic;}
    public int getNumber(){return  this.Number;}
}
