package com.example.softmills.phlog.ui.uploadimage.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.Utiltes.GpsTracker;
import com.example.softmills.phlog.base.BaseActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import io.reactivex.annotations.NonNull;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.example.softmills.phlog.Utiltes.Constants.REQUEST_CODE_LOCATION;

/**
 * Created by abdalla_maged on 10/24/2018.
 */
public class PickedPhotoInfoActivity extends BaseActivity implements LocationListener {


    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000; // 1 second

    private final String TAG = PickedPhotoInfoActivity.class.getSimpleName();
    private String imagePath;
    private ImageView filtredImg;
    private Button backBtn, nextBtn;
    private EditText caption;
    private LocationManager locationManager;

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

//        EasyPermissions.

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


    @AfterPermissionGranted(REQUEST_CODE_LOCATION)
    private void requestLocation() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            // Have permission, do the thing!
            turnGPSOn();
            new GpsTracker(this).getLocation();
        } else {
            // Request one permission
            EasyPermissions.requestPermissions(this, getString(R.string.need_location_permation),
                    REQUEST_CODE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }
    private void turnGpsOn (Context context) {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();
    }

    private void turnGPSOn(){
        Intent intent=new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", true);
        sendBroadcast(intent);
//        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
//
//        if(!provider.contains("gps")){ //if gps is disabled
//            final Intent poke = new Intent();
//            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
//            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
//            poke.setData(Uri.parse("3"));
//            sendBroadcast(poke);
//        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        locationManager.removeUpdates(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onLocationChanged(Location location) {


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @SuppressLint("MissingPermission")
    public Location getLocation(Context mContext) {


        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.e("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            Log.e(TAG, "getLocation()  ---->Network :" + latitude + ":" + longitude);

                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.e("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                Log.e(TAG, "getLocation()  ---->GPS :" + latitude + ":" + longitude);
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

}
