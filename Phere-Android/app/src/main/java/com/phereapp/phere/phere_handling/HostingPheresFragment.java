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
public class HostingPheresFragment extends Fragment {

    List<Phere> hostingPheres;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.pheres_list, container, false);

        hostingPheres = new ArrayList<>();
        hostingPheres.add(new Phere("Phere 1","Home","Public","test@test.com"));
        hostingPheres.add(new Phere("Phere 2","not home","Public","test@test.com"));
        hostingPheres.add(new Phere("Phere 3","somewhere","Private","test@test.com"));
        hostingPheres.add(new Phere("Phere 4","around here","Private","test@test.com"));

        RecyclerView recyclerView =  rootView.findViewById(R.id.recyclerView_pheres);
        RecyclerViewPhereAdapter recyclerViewPhereAdapter = new RecyclerViewPhereAdapter(getContext(),hostingPheres);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerViewPhereAdapter);



        return rootView;
    }

}
