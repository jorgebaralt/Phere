package com.phereapp.phere.dialog_fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phereapp.phere.R;
import com.phereapp.phere.adapters.RecyclerViewPlaylistAdapter;
import com.phereapp.phere.pojo.SpotifyPlaylist;

import java.util.List;

public class PlaylistDialogFragment extends DialogFragment {
    private static String TAG = "PlaylistDialogFragment";
    List<SpotifyPlaylist> spotifyPlaylists;
    RecyclerView mRecyclerView;
    RecyclerViewPlaylistAdapter mAdapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.playlist_list, container);
        Log.d(TAG, "onCreateView: loading fragment");
        Bundle bundle = getArguments();
        spotifyPlaylists = (List<SpotifyPlaylist>) bundle.getSerializable("spotifyPlaylists");

        if(spotifyPlaylists!= null){
            mRecyclerView = rootView.findViewById(R.id.recycler_view_playlist);

            mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

            mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(), LinearLayoutManager.VERTICAL));
            mAdapter = new RecyclerViewPlaylistAdapter(spotifyPlaylists,this.getActivity());
            mRecyclerView.setAdapter(mAdapter);
        }

        this.getDialog().setTitle("Select a PLaylist");
        this.getDialog().setCanceledOnTouchOutside(true);


        return rootView;
    }

}
