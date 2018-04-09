package com.phereapp.phere.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.phereapp.phere.pojo.SpotifyPlaylist;

import java.util.List;

public class RecyclerViewPlaylistAdapter extends RecyclerView.Adapter<RecyclerViewPlaylistAdapter.MyViewHolder> {
    private List<SpotifyPlaylist> spotifyPlaylists;
    private Context context;

    public RecyclerViewPlaylistAdapter(List spotifyPlaylists, Context context){
        this.spotifyPlaylists = spotifyPlaylists;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return spotifyPlaylists.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
