package com.phereapp.phere.phere_handling;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phereapp.phere.R;
import com.phereapp.phere.adapters.RecyclerViewPhereAdapter;
import com.phereapp.phere.pojo.Phere;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class JoinedPheresFragment extends Fragment {

    List<Phere> joinedPheres;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.pheres_list, container, false);

        joinedPheres = new ArrayList<>();
        joinedPheres.add(new Phere("My favorite phere","in my fav place","Public","test@test.com"));

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView_pheres);
        RecyclerViewPhereAdapter recyclerViewPhereAdapter = new RecyclerViewPhereAdapter(getContext(),joinedPheres);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerViewPhereAdapter);


        return rootView;
    }

}
