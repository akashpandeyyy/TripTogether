package com.example.tt;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tt.adapter.RideAdapter;
import com.example.tt.RideModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class RideListFragment extends Fragment {

    RecyclerView recyclerView;

    ArrayList<RideModel> list = new ArrayList<>();

    String type;

    // CREATE INSTANCE

    public static RideListFragment newInstance(String type){

        RideListFragment fragment = new RideListFragment();

        Bundle bundle = new Bundle();

        bundle.putString("type", type);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_rides,
                container,
                false);

        recyclerView = view.findViewById(R.id.recycler_rides);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));

        // GET TYPE

        type = getArguments().getString("type");

        // LOAD DATA

        loadData();

        return view;
    }

    // LOAD DATA FROM SHAREDPREF

    private void loadData(){

        SharedPreferences pref = requireContext()
                .getSharedPreferences("TT_APP",
                        getContext().MODE_PRIVATE);

        String key;

        if(type.equals("posted")){
            key = "POSTED_RIDES";
        } else {
            key = "BOOKED_RIDES";
        }

        String data = pref.getString(key, "[]");

        try {

            JSONArray array = new JSONArray(data);

            for(int i=0; i<array.length(); i++){

                JSONObject obj = array.getJSONObject(i);

                RideModel model = new RideModel(
                        obj.getString("name"),
                        obj.getString("source"),
                        obj.getString("destination"),
                        obj.getString("amount"),
                        obj.getInt("seats")
                );

                list.add(model);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        // ADAPTER

        RideAdapter adapter =
                new RideAdapter(getContext(),
                        list);

        recyclerView.setAdapter(adapter);
    }
}
