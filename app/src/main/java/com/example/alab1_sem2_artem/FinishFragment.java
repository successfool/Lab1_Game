package com.example.alab1_sem2_artem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class FinishFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_finish, container, false);

        Button EschoRaz = (Button) view.findViewById(R.id.EschoRaz);
        EschoRaz.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getActivity()).ShowButton();
                ((MainActivity)getActivity()).EndGame();
                FragmentManager fm = getFragmentManager();
                Fragment fragment = fm.findFragmentById(R.id.fr_place);
                fm.beginTransaction().remove(fragment).commit();
            }
        });

        return view;
    }

}
