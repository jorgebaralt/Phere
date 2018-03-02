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
import com.google.firebase.firestore.DocumentSnapshot;
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
public class JoinedPheresFragment extends Fragment {

    private List<Phere> joinedPheres;
    private FirebaseUser currentUser;
    private FirebaseFirestore db;
    private final String TAG = "JoinedPheresFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.pheres_list, container, false);

        joinedPheres = new ArrayList<>();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        getJoinedPheres();

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView_pheres);
        RecyclerViewPhereAdapter recyclerViewPhereAdapter = new RecyclerViewPhereAdapter(getContext(), joinedPheres);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerViewPhereAdapter);


        return rootView;
    }

    public void getJoinedPheres() {
        db.collection("pheres").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                   for(DocumentSnapshot document : task.getResult()){
                       Log.d(TAG, document.getId() + " => " + document.getData());
                   }
                }
            }
        });
    }
}
