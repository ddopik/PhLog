package com.example.softmills.phlog.base.commonmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class Photographer implements Parcelable {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("user_name")
    @Expose
    public String userName;
    @SerializedName("full_name")
    @Expose
    public String fullName;

    protected Photographer(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        userName = in.readString();
        fullName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        dest.writeString(userName);
        dest.writeString(fullName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Photographer> CREATOR = new Parcelable.Creator<Photographer>() {
        @Override
        public Photographer createFromParcel(Parcel in) {
            return new Photographer(in);
        }

        @Override
        public Photographer[] newArray(int size) {
            return new Photographer[size];
        }
    };
}