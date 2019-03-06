package com.example.softmills.phlog.ui.earning.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EarningListData {


    @SerializedName("transactions")
    @Expose
    public EarningTransactions transactions;
    @SerializedName("total")
    @Expose
    public Integer total;
}
