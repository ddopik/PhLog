package com.example.softmills.phlog.ui.welcome.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WelcomeScreenResponse {



        @SerializedName("data")
        @Expose
        public List<InitSlider> data = null;

}
