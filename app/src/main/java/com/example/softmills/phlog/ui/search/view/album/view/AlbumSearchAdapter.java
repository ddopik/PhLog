package com.example.softmills.phlog.ui.search.view.album.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.softmills.phlog.ui.search.view.album.model.AlbumSearch;

import java.util.List;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public class AlbumSearchAdapter extends RecyclerView.Adapter<AlbumSearchAdapter.AlbumSearchViewHolder> {

    private List<AlbumSearch> albumSearchList;


    public AlbumSearchAdapter(List<AlbumSearch> albumSearchList){
        this.albumSearchList=albumSearchList;
    }

    @NonNull
    @Override
    public AlbumSearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumSearchViewHolder albumSearchViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return albumSearchList.size();
    }

    class AlbumSearchViewHolder extends RecyclerView.ViewHolder {
        public AlbumSearchViewHolder(View view) {

            super(view);

        }
    }
}
