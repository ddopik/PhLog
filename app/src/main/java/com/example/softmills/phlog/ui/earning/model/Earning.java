package com.example.softmills.phlog.ui.earning.model;

import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.base.commonmodel.Business;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Earning {


        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("business_id")
        @Expose
        public Integer businessId;
        @SerializedName("photo_id")
        @Expose
        public Integer photoId;
        @SerializedName("exclusive")
        @Expose
        public Integer exclusive;
        @SerializedName("price")
        @Expose
        public String price;
        @SerializedName("photographer_level")
        @Expose
        public Integer photographerLevel;
        @SerializedName("payment_gateway_trans_id")
        @Expose
        public String paymentGatewayTransId;
        @SerializedName("payment_gateway_trans_response")
        @Expose
        public String paymentGatewayTransResponse;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("deleted_at")
        @Expose
        public String deletedAt;
        @SerializedName("photo")
        @Expose
        public BaseImage photo;
        @SerializedName("business")
        @Expose
        public Business business;

    }

