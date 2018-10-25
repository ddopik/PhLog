package com.example.softmills.phlog.ui.uploadimage.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.IntentSender;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.Utiltes.MapUtls;
import com.example.softmills.phlog.base.BaseActivity;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;

import io.reactivex.annotations.NonNull;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.example.softmills.phlog.Utiltes.Constants.REQUEST_CODE_LOCATION;
import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

/**
 * Created by abdalla_maged on 10/24/2018.
 */
public class PickedPhotoInfoActivity extends BaseActivity {


    private LocationRequest mLocationRequest;

    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    private final String TAG = PickedPhotoInfoActivity.class.getSimpleName();
    private String imagePath;
    private ImageView filtredImg;
    private Button backBtn, nextBtn;
    private EditText caption;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_info_activity);
        if (getIntent().getStringExtra("filtered_image_path") != null) {
            imagePath = getIntent().getStringExtra("filtered_image_path");
            initPresenter();
            initView();
            initListener();
        }
    }


    @Override
    public void initView() {

//

        filtredImg = findViewById(R.id.photo_info);
        //Header Img
        GlideApp.with(getBaseContext())
                .load(Uri.parse(imagePath).getPath())
                .centerCrop()
                .error(R.drawable.ic_launcher_foreground)
                .into(filtredImg);

        nextBtn = findViewById(R.id.activity_info_photo_next_btn);
        backBtn = findViewById(R.id.activity_info_photo_back_btn);


        caption = findViewById(R.id.photo_caption);
//        location = findViewById(R.id.photo_location);
        requestLocation();
    }

    @Override
    public void initPresenter() {
    }

    private void initListener() {
        nextBtn.setOnClickListener(v -> {

        });
        backBtn.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }


    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(REQUEST_CODE_LOCATION)
    private void requestLocation() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)) {
        new MapUtls().startLocationUpdates(this);
        } else {
            // Request one permission
            EasyPermissions.requestPermissions(this, getString(R.string.need_location_permation),
                    REQUEST_CODE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }



}
