package com.example.softmills.phlog.ui.uploadimage.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.ui.uploadimage.view.fillters.FiltersListFragment;
import com.example.softmills.phlog.ui.uploadimage.view.fillters.PickImageViewPagerAdapter;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;


import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.example.softmills.phlog.Utiltes.BitmapUtils.getBitmapFromGallery;
import static com.example.softmills.phlog.Utiltes.Constants.PERMEATION_REQUEST_CODE__WRITE_EXTERNAL_CAMERA;

/**
 * Created by abdalla_maged on 10/18/2018.
 */
public class ImageFilterActivity extends BaseActivity implements FiltersListFragment.FiltersListFragmentListener, EditPickedImageFragment.EditImageFragmentListener {
    private static final String TAG = ImageFilterActivity.class.getSimpleName();
    public static final int SELECT_GALLERY_IMAGE = 106;

    private ImageView imagePreview;
    private ImageButton openCameraBtn;
    private Bitmap originalImage;
    private Bitmap filteredImage; //Image get filtered from filter list
    // the final image after applying
    // brightness, saturation, contrast
    private Bitmap finalImage;
    private FiltersListFragment filtersListFragment;
    private EditPickedImageFragment editImageFragment;

    // modified image values
    private int brightnessFinal = 0;
    private float saturationFinal = 1.0f;
    private float contrastFinal = 1.0f;


    // load native image filters library
    static {
        System.loadLibrary("NativeImageProcessor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_filter);

        if (getIntent().getStringExtra("image_uri") != null) {
            initView();
            initListeners();
            // load the default image from assets on app launch
            loadImage(getIntent().getStringExtra("image_uri"));
        }


    }


    @Override
    public void initView() {
//        RequestPermutations(); //todo back to GalleryImageActivity
        imagePreview = findViewById(R.id.upload_image_preview);
//        openCameraBtn = findViewById(R.id.open_camera_btn);
        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void initPresenter() {

    }


    private void initListeners() {
//        openCameraBtn.setOnClickListener(view -> {
//            ImagePicker.cameraOnly().start(this);
////            openPickerDialog();
//        }); //todo back to GalleryImageActivity




    }

    private void setupViewPager(ViewPager viewPager) {
        PickImageViewPagerAdapter adapter = new PickImageViewPagerAdapter(getSupportFragmentManager());

        // adding filter list fragment
        filtersListFragment = new FiltersListFragment();
        filtersListFragment.setListener(this);

        // adding edit image fragment
        editImageFragment = new EditPickedImageFragment();
        editImageFragment.setListener(this);

        adapter.addFragment(filtersListFragment, getString(R.string.tab_filters));
        adapter.addFragment(editImageFragment, getString(R.string.tab_edit));

        viewPager.setAdapter(adapter);
    }

    /**
     * Resets image edit controls to normal when new filter
     * is selected
     */
    private void resetControls() {
        if (editImageFragment != null) {
            editImageFragment.resetControls();
        }
        brightnessFinal = 0;
        saturationFinal = 1.0f;
        contrastFinal = 1.0f;
    }

    private void loadImage(String img) {
//        originalImage = BitmapUtils.getBitmapFromAssets(getBaseContext(), DEFAULT_PROFILE_IMAGE, 300, 300);
//        filteredImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
//        finalImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
//        imagePreview.setImageBitmap(originalImage);

        Uri imgPath = Uri.parse(img);
        //Header Img
        GlideApp.with(getBaseContext())
                .load(imgPath)
                .error(R.drawable.ic_launcher_foreground)
                .override(612, 816)
                .into(imagePreview);

        Bitmap bitmap = getBitmapFromGallery(getBaseContext(), imgPath, 800, 800);
        originalImage.recycle();
        finalImage.recycle();
        originalImage = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        filteredImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
        finalImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
        imagePreview.setImageBitmap(originalImage);
        bitmap.recycle();

        filtersListFragment.prepareThumbnail(originalImage, imgPath.getPath());

    }


//    private void openPickerDialog() {
//        CharSequence photoChooserOptions[] = new CharSequence[]{getResources().getString(R.string.general_photo_chooser_camera), getResources().getString(R.string.general_photo_chooser_gallery)};
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(getResources().getString(R.string.general_photo_chooser_title));

//        builder.setItems(photoChooserOptions, (dialog, option) -> {
//            if (option == 0) {
//                ImagePicker.cameraOnly().start(this);
//            } else if (option == 1) {
//                ImagePicker.create(this)
//                        .returnMode(ReturnMode.GALLERY_ONLY) // set whether pick and / or camera action should return immediate result or not.
//                        .folderMode(false) // folder mode (false by default)
//                        .toolbarFolderTitle("Folder") // folder selection title
//                        .toolbarImageTitle("Tap to select") // image selection title
//                        .toolbarArrowColor(Color.BLACK) // Toolbar 'up' arrow color
//                        .single() // single mode
//                        .showCamera(true) // show camera or not (true by default)
//                        .enableLog(false) // disabling log
//                        .start(); // start image picker activity with request code
//            }
//        }).show();  //todo back to GalleryImageActivity
//    }


//    @AfterPermissionGranted(PERMEATION_REQUEST_CODE__WRITE_EXTERNAL_CAMERA)
//    private void RequestPermutations() {
//        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
//
//        if (EasyPermissions.hasPermissions(getBaseContext(), perms)) {
//            // Already have permission, do the thing
//            // ...
//            Toast.makeText(getBaseContext(), "Already have permission, do the thing", Toast.LENGTH_SHORT).show();
//        } else {
//            // Do not have permissions, request them now
//            EasyPermissions.requestPermissions(this, getString(R.string.camera_and_location_rationale),
//                    PERMEATION_REQUEST_CODE__WRITE_EXTERNAL_CAMERA, perms);
//        }
//    } //todo back to GalleryImageActivity
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
//
//            Image image = ImagePicker.getFirstImageOrNull(data);
//
//            //Header Img
//            GlideApp.with(getBaseContext())
//                    .load(image.getPath())
//                    .error(R.drawable.ic_launcher_foreground)
//                    .override(612, 816)
//                    .into(imagePreview);
//
//            Bitmap bitmap = getBitmapFromGallery(getBaseContext(), image.getPath(), 800, 800);
//            originalImage.recycle();
//            finalImage.recycle();
//            originalImage = bitmap.copy(Bitmap.Config.ARGB_8888, true);
//            filteredImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
//            finalImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
//            imagePreview.setImageBitmap(originalImage);
//            bitmap.recycle();
//
//            filtersListFragment.prepareThumbnail(originalImage, image.getPath());
//
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//
//    }


    @Override
    public void onBrightnessChanged(int brightness) {
        brightnessFinal = brightness;
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new BrightnessSubFilter(brightness));
        imagePreview.setImageBitmap(myFilter.processFilter(finalImage.copy(Bitmap.Config.ARGB_8888, true)));

    }

    @Override
    public void onSaturationChanged(float saturation) {
        saturationFinal = saturation;
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new SaturationSubfilter(saturation));
        imagePreview.setImageBitmap(myFilter.processFilter(finalImage.copy(Bitmap.Config.ARGB_8888, true)));
    }

    @Override
    public void onContrastChanged(float contrast) {
        contrastFinal = contrast;
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new ContrastSubFilter(contrast));
        imagePreview.setImageBitmap(myFilter.processFilter(finalImage.copy(Bitmap.Config.ARGB_8888, true)));
    }

    @Override
    public void onEditStarted() {

    }

    @Override
    public void onEditCompleted() {
// once the editing is done i.e seekbar is drag is completed,
// apply the values on to filtered image
        final Bitmap bitmap = filteredImage.copy(Bitmap.Config.ARGB_8888, true);
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new BrightnessSubFilter(brightnessFinal));
        myFilter.addSubFilter(new ContrastSubFilter(contrastFinal));
        myFilter.addSubFilter(new SaturationSubfilter(saturationFinal));
        finalImage = myFilter.processFilter(bitmap);
    }

    @Override
    public void onFilterSelected(Filter filter) {
        // reset image controls
        resetControls();

        // applying the selected filter
        filteredImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
        // preview filtered image
        imagePreview.setImageBitmap(filter.processFilter(filteredImage));

        finalImage = filteredImage.copy(Bitmap.Config.ARGB_8888, true);
    }


    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        // Forward results to EasyPermissions
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
//    }
}
