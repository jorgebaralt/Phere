package com.phereapp.phere.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phereapp.phere.R;

import java.util.List;

public class RecyclerViewMembersAdapter extends RecyclerView.Adapter<RecyclerViewMembersAdapter.MyViewHolder> {

    private List<String> membersList;
    private Context c;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView member;

        public MyViewHolder(View v) {
            super(v);
            member = v.findViewById(R.id.member_list );
        }
    }

    public RecyclerViewMembersAdapter(Context c, List<String> membersList) {
        this.c = c;
        this.membersList = membersList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.members_list_row, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.member.setText(membersList.get(position));
    }

    @Override
    public int getItemCount() {
        return membersList.size();
    }
}
