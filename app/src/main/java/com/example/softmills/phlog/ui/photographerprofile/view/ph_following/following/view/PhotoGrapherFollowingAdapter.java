package com.example.softmills.phlog.ui.photographerprofile.view.ph_following.following.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_following.following.model.PhotoGrapherFollowingObj;

import java.util.List;

/**
 * Created by abdalla_maged on 10/11/2018.
 */
public class PhotoGrapherFollowingAdapter extends RecyclerView.Adapter<PhotoGrapherFollowingAdapter.PhotoGrapherFollowingInViewHolder> {


    public Context context;
    private List<PhotoGrapherFollowingObj> photoGrapherFollowingInList;

    public PhotoGrapherFollowingAdapter(Context context, List<PhotoGrapherFollowingObj> photoGrapherFollowingInList) {
        this.context = context;
        this.photoGrapherFollowingInList = photoGrapherFollowingInList;
    }

    @NonNull
    @Override
    public PhotoGrapherFollowingInViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new PhotoGrapherFollowingInViewHolder(layoutInflater.inflate(R.layout.view_holder_following, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoGrapherFollowingInViewHolder photoGrapherFollowingInViewHolder, int i) {



    }


    @Override
    public int getItemCount() {
        return photoGrapherFollowingInList.size();
    }

    public class PhotoGrapherFollowingInViewHolder extends RecyclerView.ViewHolder {

        private ImageView followersImg;
        private TextView followersFullName, followersUserName, followersCount, followingCount, photosCount;

        public PhotoGrapherFollowingInViewHolder(View view) {
            super(view);
            followersImg = view.findViewById(R.id.followers_img);
            followersFullName = view.findViewById(R.id.followers_full_name);
            followersUserName = view.findViewById(R.id.followers_user_name);
            followersCount = view.findViewById(R.id.followers_count);
            followingCount=view.findViewById(R.id.following_count);
            photosCount = view.findViewById(R.id.photos_count);
        }

    }
}
