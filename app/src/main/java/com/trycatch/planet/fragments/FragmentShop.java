package com.trycatch.planet.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.trycatch.planet.R;
import com.trycatch.planet.database.DatabaseHelper;

public class FragmentShop extends Fragment {
    TextView textLevel, countGold, countBlackMatter;
    ImageView imageFirst;
    Button btnBack;

    DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.removeView(getView());
                (getActivity().findViewById(R.id.btnShop)).setVisibility(View.VISIBLE);
                (getActivity().findViewById(R.id.btnBack)).setVisibility(View.VISIBLE);
                (getActivity().findViewById(R.id.btnMissions)).setVisibility(View.VISIBLE);
            }
        });
        databaseHelper = new DatabaseHelper(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        textLevel = getView().findViewById(R.id.textLevel);
        countGold = getView().findViewById(R.id.textCountGold);
        countBlackMatter = getView().findViewById(R.id.textCountBlackMatter);

        getCountResources();

        imageFirst = getView().findViewById(R.id.productFirst);
        imageFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkTransaction(500)) {
                    databaseHelper.setSecond(12);
                    getCountResources();
                } else {
                }
            }
        });
    }

    private void getCountResources() {
        textLevel.setText("" + databaseHelper.getLevel());
        countGold.setText(String.valueOf(databaseHelper.getGold()));
        countBlackMatter.setText(String.valueOf(databaseHelper.getMatter()));
    }

    public boolean checkTransaction(int cost) {
        if (databaseHelper.getGold() >= cost) {
            databaseHelper.setGold(databaseHelper.getGold() - cost);
            return true;
        } else return false;
    }
}
