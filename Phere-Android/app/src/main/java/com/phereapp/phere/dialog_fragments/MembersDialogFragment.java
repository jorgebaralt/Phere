package com.phereapp.phere.dialog_fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phereapp.phere.R;
import com.phereapp.phere.adapters.RecyclerViewMembersAdapter;
import com.phereapp.phere.pojo.Phere;

import java.util.List;

public class MembersDialogFragment extends DialogFragment {

    List<String> members;
    RecyclerView mRecyclerView;
    RecyclerViewMembersAdapter mAdapter;
    Phere mSelectedPhere;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_fragment_member_popup, container);
        Bundle bundle = getArguments();
        mSelectedPhere = (Phere) bundle.getSerializable("SelectedPhere");
        members = mSelectedPhere.getMembers();


        //Recycler
        mRecyclerView = rootView.findViewById(R.id.recycler_view_members_popup);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(), LinearLayoutManager.VERTICAL));

        //Adapter
        mAdapter = new RecyclerViewMembersAdapter(this.getActivity(), members);
        mRecyclerView.setAdapter(mAdapter);

        this.getDialog().setCanceledOnTouchOutside(true);
        this.getDialog().setTitle("Phere Members");

        return rootView;
    }
}
