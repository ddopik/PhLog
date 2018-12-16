package com.example.softmills.phlog.ui.search.view.album.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.ui.search.view.album.model.AlbumSearch;

import java.util.List;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public class AlbumSearchAdapter extends RecyclerView.Adapter<AlbumSearchAdapter.AlbumSearchViewHolder> {

    private List<AlbumSearch> albumSearchList;
    private Context context;
    public OnAlbumPreview onAlbumPreview;

    public AlbumSearchAdapter(List<AlbumSearch> albumSearchList) {
        this.albumSearchList = albumSearchList;

    }

    @NonNull
    @Override
    public AlbumSearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.view_holder_album_search, viewGroup, false);
        return new AlbumSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumSearchViewHolder albumSearchViewHolder, int i) {

        if (albumSearchList.get(i).photos.get(0) != null)
            GlideApp.with(context)
                    .load(albumSearchList.get(i).photos.get(0))
                    .placeholder(R.drawable.default_photographer_profile)
                    .error(R.drawable.default_photographer_profile)
                    .into(albumSearchViewHolder.image1);
        if (albumSearchList.get(i).photos.get(1) != null)
            GlideApp.with(context)
                    .load(albumSearchList.get(i).photos.get(1))
                    .placeholder(R.drawable.default_photographer_profile)
                    .error(R.drawable.default_photographer_profile)
                    .into(albumSearchViewHolder.image2);
        if (albumSearchList.get(i).photos.get(2) != null)
            GlideApp.with(context)
                    .load(albumSearchList.get(i).photos.get(2))
                    .placeholder(R.drawable.default_photographer_profile)
                    .error(R.drawable.default_photographer_profile)
                    .into(albumSearchViewHolder.image3);

//        albumSearchViewHolder.albumName.setText(albumSearchList.get(i).name);
//        albumSearchViewHolder.albumPhotoCount.setText(albumSearchList.get(i).numberOfPhotos);

        albumSearchViewHolder.albumName.setText("Album name");
        albumSearchViewHolder.albumPhotoCount.setText("254 number");


        if (onAlbumPreview != null) {
            albumSearchViewHolder.albumSearchListItemContainer.setOnClickListener(v -> onAlbumPreview.openAlbumPreviewListener(albumSearchList.get(i)));
        }

    }

    @Override
    public int getItemCount() {
        return albumSearchList.size();
    }

    class AlbumSearchViewHolder extends RecyclerView.ViewHolder {

        FrameLayout albumSearchListItemContainer;
        ImageButton openAlbumPreview;
        ImageView image1, image2, image3;
        TextView albumName, albumPhotoCount;


        AlbumSearchViewHolder(View view) {
            super(view);
            albumSearchListItemContainer = view.findViewById(R.id.album_search_list_item_container);
            openAlbumPreview = view.findViewById(R.id.open_album_preview);
            image1 = view.findViewById(R.id.album_search_img_1);
            image2 = view.findViewById(R.id.album_search_img_2);
            image3 = view.findViewById(R.id.album_search_img_3);

            albumName = view.findViewById(R.id.album_name);
            albumPhotoCount = view.findViewById(R.id.album_photo_count);

        }
    }

    public interface OnAlbumPreview {
        void openAlbumPreviewListener(AlbumSearch albumSearch);
    }
}
