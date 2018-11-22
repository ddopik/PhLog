package com.example.softmills.phlog.ui.album.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.ui.album.model.AlbumImgCommentData;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public class AlbumCommentActivity extends BaseActivity implements AlbumCommentActivityView{

    private EditText comment;
    private Button sendBtn;
    private ProgressBar addCommentProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_commnet);

        initPresenter();
        initView();
        initListener();
    }


    @Override
    public void initView() {
        comment=findViewById(R.id.img_comment);
        sendBtn=findViewById(R.id.send_comment);
        addCommentProgress=findViewById(R.id.add_comment_progress);
    }

    @Override
    public void initPresenter() {

    }
    private void initListener(){
        sendBtn.setOnClickListener(v -> {

        });


    }

    @Override
    public void viewPhotoComment(AlbumImgCommentData albumImgCommentData) {
        //todo add comments to recycler here
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    @Override
    public void viewAddCommentProgress(Boolean state) {


    }
}
