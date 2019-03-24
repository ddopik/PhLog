package com.example.softmills.phlog.ui.allphotos.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.commonmodel.BaseImage;

import java.util.List;

/**
 * Created by abdalla_maged on 10/1/2018.
 */
public class PhotographerPhotosGridAdapter extends RecyclerView.Adapter<PhotographerPhotosGridAdapter.PhotosViewHolder> {


    private List<BaseImage> photoGrapherPhotosList;
    private Context context;
    private PhotographerPhotosGridAdapter.PhotosViewHolder photosViewHolder;
    private PhotoSize photoSize;
    public PhotoAction photoAction;

    public PhotographerPhotosGridAdapter(Context context, List<BaseImage> photoGrapherPhotosList,PhotoSize photoSize) {
        this.context = context;
        this.photoGrapherPhotosList = photoGrapherPhotosList;
        this.photoSize=photoSize;
    }

    @NonNull
    @Override
    public PhotographerPhotosGridAdapter.PhotosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        switch (photoSize) {

            case SMALL:
                return new PhotosViewHolder(layoutInflater.inflate(R.layout.view_holder_photo, viewGroup, false));


                default:{
                    return new PhotosViewHolder(layoutInflater.inflate(R.layout.view_holder_photo_wrap, viewGroup, false));

                }
        }


    }

    @Override
    public void onBindViewHolder(@NonNull PhotographerPhotosGridAdapter.PhotosViewHolder campaignsViewHolder, int i) {


        GlideApp.with(context)
                .load(photoGrapherPhotosList.get(i).url)
                .centerCrop()
                .error(R.drawable.default_error_img)
                .placeholder(R.drawable.default_place_holder)
                .into(campaignsViewHolder.photographerPhoto);
            campaignsViewHolder.photographerPhoto.setOnClickListener(view -> {
                if (photoAction != null)
                photoAction.onPhotoClicked(photoGrapherPhotosList.get(i));
            });
    }

    @Override
    public int getItemCount() {
        return photoGrapherPhotosList.size();
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


    public  enum  PhotoSize{
        SMALL,WRAP
    }
}