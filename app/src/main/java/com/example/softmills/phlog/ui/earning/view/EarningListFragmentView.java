package com.example.softmills.phlog.ui.earning.view;

import com.example.softmills.phlog.ui.earning.model.Earning;

import java.util.List;

/**
 * Created by abdalla_maged On Nov,2018
 */
public interface EarningListFragmentView {
    void viewEarningList(List<Earning> earningList);
    void viewEaringListProgress(boolean state);
    void viewMessage(String msg);
     void setSalesNumber(int total);
}
