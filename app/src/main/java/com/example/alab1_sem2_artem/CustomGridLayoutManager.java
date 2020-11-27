package com.example.alab1_sem2_artem;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;

public class CustomGridLayoutManager extends GridLayoutManager
{
    private boolean isScrollEnabled = false;

    public CustomGridLayoutManager(Context context)
    {
        super(context, 3);
    }

    public void setScrollEnabled(boolean flag)
    {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically()
    {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}
