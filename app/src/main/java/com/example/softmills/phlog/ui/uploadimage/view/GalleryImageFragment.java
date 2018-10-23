package com.example.softmills.phlog.ui.uploadimage.view;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.ui.MainActivity;

import java.util.ArrayList;
import java.util.Objects;


/**
 * Created by abdalla_maged on 10/22/2018.
 */
public class GalleryImageFragment extends BaseFragment {

    private View mainView;
    private GalleryImageAdapter galleryImageAdapter;
    private CustomRecyclerView galleryRv;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mainView = inflater.inflate(R.layout.fragment_gallery_image, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initListeners();
    }


    @Override
    public void initViews() {

        galleryRv = mainView.findViewById(R.id.gallery_img_rv);
        galleryImageAdapter = new GalleryImageAdapter(getContext(), getAllShownImagesPath());
        galleryRv.setAdapter(galleryImageAdapter);
    }

    @Override
    public void initPresenter() {
    }


    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().hide();

    }

    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity)getActivity()).getSupportActionBar().show();
    }

    private void initListeners() {
       galleryImageAdapter.onGalleryImageClicked= imagePath -> {
           Intent intent =new Intent(getContext(),ImageFilterActivity.class);
           intent.putExtra("image_uri",imagePath);
           startActivity(intent);
       };
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }


    private ArrayList<String> getAllShownImagesPath() {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = getContext().getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(absolutePathOfImage);
        }
        return listOfAllImages;
    }

}
