package com.example.softmills.phlog.ui.social.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.ui.social.model.Entite;

import java.util.List;

import static com.example.softmills.phlog.Utiltes.Constants.ALBUM_DISPLAY_TYPE_1;
import static com.example.softmills.phlog.Utiltes.Constants.PROFILE_DISPLAY_TYPE_1;

/**
 * Created by abdalla_maged on 11/13/2018.
 */
public class SocialProfileImagesAdapter extends RecyclerView.Adapter<SocialProfileImagesAdapter.SocialProfileViewHolder> {

    private Context context;
    private List<Entite> entityList;
    private String displayType;

    public  OnSocialProfileListener onSocialProfileListener;


    public SocialProfileImagesAdapter(List<Entite> entityList, String displayType) {
        this.entityList = entityList;
        this.displayType = displayType;
    }

    @NonNull
    @Override
    public SocialProfileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        switch (displayType) {
            case PROFILE_DISPLAY_TYPE_1: {
                return new SocialProfileViewHolder(layoutInflater.inflate(R.layout.view_holder_social_profile_type_1, viewGroup, false));
            }

            default: {
                return new SocialProfileViewHolder(layoutInflater.inflate(R.layout.view_holder_social_profile, viewGroup, false));

            }
        }

    }

    @Override
    public void onBindViewHolder(@NonNull SocialProfileViewHolder socialProfileViewHolder, int i) {

        Entite entite=entityList.get(i);
        GlideApp.with(context).load(entityList.get(i).thumbnail).apply(RequestOptions.circleCropTransform()).into(socialProfileViewHolder.socialProfileIcon);

        GlideApp.with(context)
                .load(entite.imgs.get(0))
                .placeholder(R.drawable.default_photographer_profile)
                .error(R.drawable.default_photographer_profile)
                .apply(new RequestOptions().centerCrop())
                .into(socialProfileViewHolder.socialProfileImg_1);
        GlideApp.with(context)
                .load(entite.imgs.get(1))
                .placeholder(R.drawable.default_photographer_profile)
                .error(R.drawable.default_photographer_profile)
                .apply(new RequestOptions().centerCrop())
                .into(socialProfileViewHolder.socialProfileImg_2);
        GlideApp.with(context)
                .load(entite.imgs.get(2))
                .placeholder(R.drawable.default_photographer_profile)
                .error(R.drawable.default_photographer_profile)
                .apply(new RequestOptions().centerCrop())
                .into(socialProfileViewHolder.socialProfileImg_3);
        GlideApp.with(context)
                .load(entite.imgs.get(3))
                .placeholder(R.drawable.default_photographer_profile)
                .error(R.drawable.default_photographer_profile)
                .apply(new RequestOptions().centerCrop())
                .into(socialProfileViewHolder.socialProfileImg_4);

        socialProfileViewHolder.socialProfileFullName.setText(entite.fullName);
        socialProfileViewHolder.socialProfileUserName.setText(entite.userName);

        if(onSocialProfileListener !=null)
        socialProfileViewHolder.followSocialProfile.setOnClickListener(v->{
            onSocialProfileListener.OnSocilProfileClck(entite);
        });


    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }

    class SocialProfileViewHolder extends RecyclerView.ViewHolder {


        ImageView socialProfileIcon, socialProfileImg_1, socialProfileImg_2, socialProfileImg_3, socialProfileImg_4;
        TextView socialProfileFullName, socialProfileUserName;
        Button followSocialProfile;


        SocialProfileViewHolder(View view) {
            super(view);
            socialProfileIcon = view.findViewById(R.id.social_profile_icon_img);
            socialProfileFullName = view.findViewById(R.id.social_profile_full_name);
            socialProfileUserName = view.findViewById(R.id.social_profile_user_name);
            followSocialProfile = view.findViewById(R.id.follow_social_profile);
            socialProfileImg_1 = view.findViewById(R.id.social_profile_img_1);
            socialProfileImg_2 = view.findViewById(R.id.social_profile_img_2);
            socialProfileImg_3 = view.findViewById(R.id.social_profile_img_3);
            socialProfileImg_4 = view.findViewById(R.id.social_profile_img_4);

        }
    }

    public interface OnSocialProfileListener{
        void OnSocilProfileClck(Entite entite);
    }

}
