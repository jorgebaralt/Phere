package com.phereapp.phere.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phereapp.phere.R;
import com.phereapp.phere.pojo.SpotifyPlaylist;

import java.util.List;

public class RecyclerViewPlaylistAdapter extends RecyclerView.Adapter<RecyclerViewPlaylistAdapter.MyViewHolder> {
    private List<SpotifyPlaylist> spotifyPlaylists;
    private Context context;
    private static String TAG = "RecyclerViewPlaylistAdapter";

    public RecyclerViewPlaylistAdapter(List<SpotifyPlaylist> spotifyPlaylists, Context context){
        this.spotifyPlaylists = spotifyPlaylists;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.playlist_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.playlistName.setText(spotifyPlaylists.get(position).getName());
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + spotifyPlaylists.get(position).getName() + " selected" );
            }
        });

    }

    @Override
    public int getItemCount() {
        return spotifyPlaylists.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView playlistName;
        private TextView add;

        public MyViewHolder(View itemView) {
            super(itemView);
            playlistName = itemView.findViewById(R.id.playlist_name);
            add = itemView.findViewById(R.id.playlist_add);

        }
    }
}
