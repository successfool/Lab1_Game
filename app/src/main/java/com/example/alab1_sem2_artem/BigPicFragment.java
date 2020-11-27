package com.example.alab1_sem2_artem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class BigPicFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_big_pic, container, false);

        ImageView BigPic = (ImageView) view.findViewById(R.id.BigPic);
        BigPic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getActivity()).ShowButton();
                FragmentManager fm = getFragmentManager();
                Fragment fragment = fm.findFragmentById(R.id.fr_place);
                fm.beginTransaction().remove(fragment).commit();
            }
        });

        return view;
    }
}
