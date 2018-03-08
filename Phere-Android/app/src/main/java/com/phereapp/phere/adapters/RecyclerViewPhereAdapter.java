package com.phereapp.phere.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.phereapp.phere.R;
import com.phereapp.phere.pojo.Phere;
import com.phereapp.phere.selected_phere.SelectedPhereMainActivity;

import java.util.List;

/**
 * Created by jorgebaralt on 2/28/18.
 */

public class RecyclerViewPhereAdapter extends RecyclerView.Adapter<RecyclerViewPhereAdapter.MyViewHolder> {

    Context mContext;
    List<Phere> mPheres;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    private static String TAG = "RecyclerViewPhereAdapter";

    //Constructor
    public RecyclerViewPhereAdapter(Context mContext, List<Phere> mPheres) {
        this.mContext = mContext;
        this.mPheres = mPheres;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(mContext).inflate(R.layout.phere_cardview_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        //TODO: deal with image (Still not working)
        holder.phereName.setText(mPheres.get(position).getDisplayPhereName());
        holder.phereLocation.setText(mPheres.get(position).getPhereLocation());
        storageReference = storageReference.child("phereProfileImage/" + mPheres.get(position).getPhereName() + "_profileImage");
        Log.d(TAG, "onBindViewHolder: PHERE NAME = " +  mPheres.get(position).getPhereName());
        //set background image
        Glide.with(this.mContext).using(new FirebaseImageLoader()).load(storageReference).into(holder.phereImg);

        //set click listener
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectedPhereIntent = new Intent(mContext, SelectedPhereMainActivity.class);
                Phere selectedPhere = mPheres.get(position);
                selectedPhereIntent.putExtra("SelectedPhere",  selectedPhere);
                mContext.startActivity(selectedPhereIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPheres.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView phereName;
        private ImageView phereImg;
        private TextView phereLocation;
        private TextView phereMemberSize;
        private CardView cardView;
        private RelativeLayout relativeLayout;


        public MyViewHolder(View itemView){
            super(itemView);

            phereName =  itemView.findViewById(R.id.txt_phereName_cardview);
            phereLocation = itemView.findViewById(R.id.txt_location_cardview);
            cardView = itemView.findViewById(R.id.cardview);
            phereImg = itemView.findViewById(R.id.img_profileImg_cardview);
            relativeLayout = itemView.findViewById(R.id.rl_cardview);
            //phereMemberSize = itemView.findViewById(R.id.txt_memberSize_cardview);

        }
    }
}
