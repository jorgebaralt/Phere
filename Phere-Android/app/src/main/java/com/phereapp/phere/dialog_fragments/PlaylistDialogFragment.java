package com.phereapp.phere.dialog_fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phereapp.phere.R;
import com.phereapp.phere.pojo.SpotifyPlaylist;

import java.util.List;

public class PlaylistDialogFragment extends DialogFragment {
    private static String TAG = "PlaylistDialogFragment";
    List<SpotifyPlaylist> spotifyPlaylists;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_fragment_member_popup, container);
        Log.d(TAG, "onCreateView: loading fragment");

        this.getDialog().setTitle("Select a PLaylist");
        this.getDialog().setCanceledOnTouchOutside(true);

        Bundle bundle = getArguments();
        //spotifyPlaylists = (List<SpotifyPlaylist>) bundle.getSerializable("spotifyPlaylists");

//        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view_playlist);
//        RecyclerViewPlaylistAdapter adapter = new RecyclerViewPlaylistAdapter(spotifyPlaylists,this.getActivity());
//        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
//        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
