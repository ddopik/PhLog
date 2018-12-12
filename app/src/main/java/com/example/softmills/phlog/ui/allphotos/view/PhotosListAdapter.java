package com.example.softmills.phlog.ui.allphotos.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.commonmodel.BaseImage;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by abdalla_maged On Dec,2018
 */
public class PhotosListAdapter extends RecyclerView.Adapter<PhotosListAdapter.PhotosViewHolder> {


    private List<BaseImage> photosList;
    private Context context;
    private PhotosListAdapter.PhotosViewHolder photosViewHolder;
    public PhotoAction photoAction;

    public PhotosListAdapter(Context context, List<BaseImage> photosList) {
        this.context = context;
        this.photosList = photosList;
    }

    @NonNull
    @Override
    public PhotosListAdapter.PhotosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new PhotosViewHolder(layoutInflater.inflate(R.layout.view_holder_photo, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosListAdapter.PhotosViewHolder photosViewHolder, int i) {


        GlideApp.with(context)
                .load(photosList.get(i).url)
                .centerCrop()
                .error(R.drawable.default_error_img)
                .placeholder(R.drawable.default_place_holder)
                .into(photosViewHolder.photographerPhoto);
        if (photoAction != null)
            photosViewHolder.photographerPhoto.setOnClickListener(view -> {
                photoAction.onPhotoClicked(photosList.get(i));
            });
    }

    @Override
    public int getItemCount() {
        return photosList.size();
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
