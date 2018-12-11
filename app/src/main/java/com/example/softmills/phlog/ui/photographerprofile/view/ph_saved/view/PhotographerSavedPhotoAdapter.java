package com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.commonmodel.BaseImage;

import java.util.List;

/**
 * Created by abdalla_maged on 9/30/2018.
 */
public class PhotographerSavedPhotoAdapter extends RecyclerView.Adapter<PhotographerSavedPhotoAdapter.PhotosViewHolder> {


    private List<BaseImage> photoGrapherSavedPhotosList;
    private Context context;
    private PhotographerSavedPhotoAdapter.PhotosViewHolder photosViewHolder;
    public PhotoAction photoAction;

    public PhotographerSavedPhotoAdapter(Context context, List<BaseImage> photoGrapherSavedPhotosList) {
        this.context = context;
        this.photoGrapherSavedPhotosList = photoGrapherSavedPhotosList;
    }

    @NonNull
    @Override
    public PhotographerSavedPhotoAdapter.PhotosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new PhotosViewHolder(layoutInflater.inflate(R.layout.view_holder_photo, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotographerSavedPhotoAdapter.PhotosViewHolder campaignsViewHolder, int i) {



        GlideApp.with(context)
                .load(photoGrapherSavedPhotosList.get(i).url)
                .centerCrop()
//                .override(450,450)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(campaignsViewHolder.photographerPhoto);
        if (photoAction != null)
            campaignsViewHolder.photographerPhoto.setOnClickListener(view -> {
                photoAction.onPhotoClicked(photoGrapherSavedPhotosList.get(i));
            });
    }

    @Override
    public int getItemCount() {
        return photoGrapherSavedPhotosList.size();
    }

    class PhotosViewHolder extends RecyclerView.ViewHolder {

        ImageView photographerPhoto;

        public PhotosViewHolder(View view) {
            super(view);
            photographerPhoto = view.findViewById(R.id.photographer_photo);
        }
    }

    public interface PhotoAction {
        void onPhotoClicked(BaseImage photoGrapherSavedPhoto);
    }
}