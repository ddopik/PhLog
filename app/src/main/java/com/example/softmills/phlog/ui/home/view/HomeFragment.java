package com.example.softmills.phlog.ui.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.ui.search.view.SearchActivity;

public class HomeFragment extends BaseFragment {

    private View mainView;
    private EditText homeSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mainView =inflater.inflate(R.layout.fragment_home,container,false);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        return mainView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initListener();
    }


    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initViews() {
        homeSearch=mainView.findViewById(R.id.home_search);

    }

    private void initListener(){
        homeSearch.setOnClickListener((v)->{
            Intent intent=new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });
    }
    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

}
