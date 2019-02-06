package com.example.softmills.phlog.ui.social.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.base.commonmodel.Business;
import com.example.softmills.phlog.base.commonmodel.Campaign;
import com.example.softmills.phlog.base.commonmodel.Photographer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 11/13/2018.
 */
public class Source implements Parcelable {
    @SerializedName("source")
    @Expose
    public String source;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("story_id")
    @Expose
    public Integer storyId;
    @SerializedName("display_type")
    @Expose
    public String displayType;

    @SerializedName("profiles")
    @Expose
    public List<Photographer> profiles = null;
    @SerializedName("albums")
    @Expose
    public List<BaseImage> albums;
    @SerializedName("campaigns")
    @Expose
    public List<Campaign> campaigns;
    @SerializedName("brands")
    @Expose
    public List<Business> brands;
    @SerializedName("photos")
    @Expose
    public List<BaseImage> photos;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.source);
        dest.writeString(this.title);
        dest.writeValue(this.storyId);
        dest.writeString(this.displayType);
        dest.writeTypedList(this.profiles);
        dest.writeTypedList(this.albums);
        dest.writeList(this.campaigns);
        dest.writeTypedList(this.brands);
        dest.writeTypedList(this.photos);
    }

    public Source() {
    }

    protected Source(Parcel in) {
        this.source = in.readString();
        this.title = in.readString();
        this.storyId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.displayType = in.readString();
        this.profiles = in.createTypedArrayList(Photographer.CREATOR);
        this.albums = in.createTypedArrayList(BaseImage.CREATOR);
        this.campaigns = new ArrayList<Campaign>();
        in.readList(this.campaigns, Campaign.class.getClassLoader());
        this.brands = in.createTypedArrayList(Business.CREATOR);
        this.photos = in.createTypedArrayList(BaseImage.CREATOR);
    }

    public static final Parcelable.Creator<Source> CREATOR = new Parcelable.Creator<Source>() {
        @Override
        public Source createFromParcel(Parcel source) {
            return new Source(source);
        }

        @Override
        public Source[] newArray(int size) {
            return new Source[size];
        }
    };
}