package com.example.alab1_sem2_artem;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>
{
    ArrayList<Item> items;

    public ItemAdapter(ArrayList<Item> items)
    {
        this.items = items;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img;
        public ItemViewHolder(View view)
        {
            super(view);
            img = view.findViewById(R.id.ImageForCell);
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position)
    {
        holder.img.setImageURI(items.get(position).getPic());
        holder.img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("Number Equal = ", String.valueOf(items.get(position).getNumber()));
                Log.d("Moi Index = ", String.valueOf(position));
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }
}

