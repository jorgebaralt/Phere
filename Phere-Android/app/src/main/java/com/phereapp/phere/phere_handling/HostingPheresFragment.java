package com.phereapp.phere.phere_handling;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.phereapp.phere.R;
import com.phereapp.phere.adapters.RecyclerViewPhereAdapter;
import com.phereapp.phere.pojo.Phere;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HostingPheresFragment extends Fragment {

    private List<Phere> hostingPheres;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private String phereCollection = "pheres";
    View rootView;

    private String TAG = "HostingPheresFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.pheres_list, container, false);

        hostingPheres = new ArrayList<>();

        //firebase variables
        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();


        //get Pheres from firebase database
        getHostingPheres();

        return rootView;
    }

    public void getHostingPheres() {

        db.collection(phereCollection)
                .whereEqualTo("host",currentUser.getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                        hostingPheres = task.getResult().toObjects(Phere.class);
                        Log.d(TAG, "onComplete: Hosting pheres ready to display");

                        RecyclerView recyclerView =  rootView.findViewById(R.id.recyclerView_pheres);
                        RecyclerViewPhereAdapter recyclerViewPhereAdapter = new RecyclerViewPhereAdapter(getContext(),hostingPheres);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(recyclerViewPhereAdapter);

                }

            }
        });

    }
}
