package com.example.softmills.phlog.ui.social.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.softmills.phlog.R;

import java.util.List;

/**
 * Created by abdalla_maged on 11/13/2018.
 */
public class SocialImagesAdapter extends RecyclerView.Adapter<SocialImagesAdapter.SocialImageViewHolder> {

    private Context context;
    private List<String> imageList;

    public SocialImagesAdapter(List<String> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public SocialImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        return new SocialImageViewHolder(layoutInflater.inflate(R.layout.view_holder_social_img, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SocialImageViewHolder socialImageViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    class SocialImageViewHolder extends RecyclerView.ViewHolder {
        public SocialImageViewHolder(View view) {
            super(view);
        }
    }
}
