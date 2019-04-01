package com.example.softmills.phlog.base.commonmodel;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.softmills.phlog.Utiltes.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by abdalla_maged On Dec,2018
 */
public class UploadImageData implements Parcelable {


    private String imageId;
    private String sourceImagePath;
    private String BitMapUri;
    private Constants.UploadImageTypes uploadImageType;
    private String imageCaption;
    private String imageLocation;
    private boolean draft;
    private Map<String, String> tagsList = new HashMap<>();


    public Map<String, String> getTags() {
        return tagsList;
    }

    public void setTags(List<Tag> tags) {
        tagsList.clear();
        for (int i = 0; i < tags.size(); i++) {
            tagsList.put("tags[" + i + "]", tags.get(i).name);
        }
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }


    public Constants.UploadImageTypes getUploadImageType() {
        return uploadImageType;
    }

    public void setUploadImageType(Constants.UploadImageTypes uploadImageType) {
        this.uploadImageType = uploadImageType;
    }

    public String getImageCaption() {
        return imageCaption;
    }

    public void setImageCaption(String imageCaption) {
        this.imageCaption = imageCaption;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public boolean isDraft() {
        return draft;
    }


    public void setDraft(boolean draft) {
        this.draft = draft;
    }

    public String getSourceImagePath() {

        return sourceImagePath;

    }

    public String getBitMapUri() {
        return BitMapUri;
    }

    public void setBitMapUri(String bitMapUri) {
        BitMapUri = bitMapUri;
    }


    public void setSourceImagePath(String sourceImagePath) {
        this.sourceImagePath = sourceImagePath;
    }

    public UploadImageData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageId);
        dest.writeString(this.sourceImagePath);
        dest.writeString(this.BitMapUri);
        dest.writeInt(this.uploadImageType == null ? -1 : this.uploadImageType.ordinal());
        dest.writeString(this.imageCaption);
        dest.writeString(this.imageLocation);
        dest.writeByte(this.draft ? (byte) 1 : (byte) 0);
        dest.writeInt(this.tagsList.size());
        for (Map.Entry<String, String> entry : this.tagsList.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
    }

    protected UploadImageData(Parcel in) {
        this.imageId = in.readString();
        this.sourceImagePath = in.readString();
        this.BitMapUri = in.readString();
        int tmpUploadImageType = in.readInt();
        this.uploadImageType = tmpUploadImageType == -1 ? null : Constants.UploadImageTypes.values()[tmpUploadImageType];
        this.imageCaption = in.readString();
        this.imageLocation = in.readString();
        this.draft = in.readByte() != 0;
        int tagsListSize = in.readInt();
        this.tagsList = new HashMap<String, String>(tagsListSize);
        for (int i = 0; i < tagsListSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.tagsList.put(key, value);
        }
    }

    public static final Creator<UploadImageData> CREATOR = new Creator<UploadImageData>() {
        @Override
        public UploadImageData createFromParcel(Parcel source) {
            return new UploadImageData(source);
        }

        @Override
        public UploadImageData[] newArray(int size) {
            return new UploadImageData[size];
        }
    };
}
