package com.trycatch.planet.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.trycatch.planet.R;
import com.trycatch.planet.database.DatabaseHelper;

import java.util.ArrayList;

public class FragmentMissions extends Fragment implements View.OnClickListener {
    Button btnBack, btnSortAll, btnSortSuccess, btnSortAvailable;
    ListView listMissions;

    DatabaseHelper databaseHelper;
    ArrayList arrayList;
    ArrayAdapter arrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_missions, container, false);
        btnSortAll = view.findViewById(R.id.btnSortAll);
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
        listMissions = view.findViewById(R.id.listMissions);

        btnSortAll = view.findViewById(R.id.btnSortAll);
        btnSortSuccess = view.findViewById(R.id.btnSortSuccess);
        btnSortAvailable = view.findViewById(R.id.btnSortAvailable);
        btnSortAll.setOnClickListener(this);
        btnSortSuccess.setOnClickListener(this);
        btnSortAvailable.setOnClickListener(this);

        btnSortAll.callOnClick();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSortAll:
                arrayList = databaseHelper.getAllMissions();
                arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arrayList);
                listMissions.setAdapter(arrayAdapter);
                break;
            case R.id.btnSortSuccess:
                arrayList = databaseHelper.getSuccessMissions();
                arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arrayList);
                listMissions.setAdapter(arrayAdapter);
                break;
            case R.id.btnSortAvailable:
                arrayList = databaseHelper.getAvailableMissions();
                arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arrayList);
                listMissions.setAdapter(arrayAdapter);
                break;
            default:
                break;
        }
    }
}
