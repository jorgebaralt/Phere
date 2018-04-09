package com.phereapp.phere.dialog_fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phereapp.phere.R;
import com.phereapp.phere.adapters.RecyclerViewPlaylistAdapter;
import com.phereapp.phere.pojo.SpotifyPlaylist;

import java.util.List;

public class PlaylistDialogFragment extends DialogFragment {

    List<SpotifyPlaylist> spotifyPlaylists;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_fragment_member_popup, container);

        this.getDialog().setTitle("Select a PLaylist");
        this.getDialog().setCanceledOnTouchOutside(true);

        Bundle bundle = getArguments();
        //spotifyPlaylists = (List<SpotifyPlaylist>) bundle.getSerializable("spotifyPlaylists");

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view_playlist);
        RecyclerViewPlaylistAdapter adapter = new RecyclerViewPlaylistAdapter(spotifyPlaylists,this.getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
