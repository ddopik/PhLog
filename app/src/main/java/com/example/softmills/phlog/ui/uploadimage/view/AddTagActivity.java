package com.example.softmills.phlog.ui.uploadimage.view;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.Constants;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.Utiltes.uploader.UploaderService;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.base.commonmodel.Tag;
import com.example.softmills.phlog.base.commonmodel.UploadImageData;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.ui.MainActivity;
import com.example.softmills.phlog.ui.uploadimage.model.UploadPhotoModel;
import com.example.softmills.phlog.ui.uploadimage.presenter.AddTagActivityPresenter;
import com.example.softmills.phlog.ui.uploadimage.presenter.AddTagActivityPresenterImpl;
import com.example.softmills.phlog.ui.uploadimage.view.adapter.AutoCompleteTagMenuAdapter;
import com.example.softmills.phlog.ui.uploadimage.view.adapter.SelectedTagAdapter;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent;
import com.o_bdreldin.loadingbutton.LoadingButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 10/28/2018.
 */
public class AddTagActivity extends BaseActivity implements AddTagActivityView {

    private String TAG = AddTagActivity.class.getSimpleName();
    public static String IMAGE_TYPE = "image_type";

    private AutoCompleteTextView autoCompleteTextView;
    private List<Tag> tagList = new ArrayList<Tag>();
    private List<Tag> tagMenuList = new ArrayList<Tag>();
    private SelectedTagAdapter selectedTagAdapter;
    private AutoCompleteTagMenuAdapter autoCompleteTagMenuAdapter;
    private ImageView imagePreview;
    private String imagePreviewPath;
    private ImageButton backBtn;
    private LoadingButton uploadBrn;
    private ProgressBar uploadImageProgress;
    private UploadImageData imageType;
    private String imageCaption;
    private String draftState;
    private String imageLocation;
    private ProgressBar progress;

    private AddTagActivityPresenter addTagActivityPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();


    private boolean started;
    private boolean bound;
    private boolean pendingSendingMessage;
    private Message pendingMessage;
    private Messenger messenger;

    private UploaderService.Communicator communicator = (action, objects) -> {
        switch (action) {
            case UPLOAD_STARTED:
                uploadBrn.setLoading(true);
                break;
            case UPLOAD_FAILED:
                showToast(getString(R.string.upload_failed));
                uploadImageProgress.setVisibility(View.GONE);
                uploadBrn.setLoading(false);
                break;
            case UPLOAD_FINISHED:
//                setResult(RESULT_OK);
                uploadBrn.setLoading(false);
                new AlertDialog.Builder(this)
                        .setTitle(R.string.done)
                        .setMessage(R.string.your_photo_uploaded)
                        .setPositiveButton(R.string.view_in_profile, (dialog, which) -> {
                            Intent intents = new Intent(this, MainActivity.class);
                            intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
                                    | Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intents.putExtra(Constants.MainActivityRedirectionValue.VALUE, Constants.MainActivityRedirectionValue.TO_PROFILE);
                            startActivity(intents);
                            finish();
                            dialog.dismiss();
                            finish();
                        }).show();
                break;
            case PROGRESS:
                int p = (int) objects[0];
                progress.setProgress(p);
                break;
        }
    };

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bound = true;
            messenger = new Messenger(service);
            Message message = new Message();
            message.what = UploaderService.ADD_COMMUNICATOR;
            message.obj = communicator;
            sendMessageToService(message);
            if (pendingSendingMessage) {
                sendMessageToService(pendingMessage);
                pendingSendingMessage = false;
                pendingMessage = null;
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

    private void sendMessageToService(Message message) {
        try {
            if (messenger != null)
                messenger.send(message);
        } catch (RemoteException e) {
            Log.e(TAG, e.getMessage());
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tag);

        Bundle bundle = this.getIntent().getExtras();
        assert bundle != null;
        if (bundle.getSerializable(IMAGE_TYPE) != null) {
            imageType = (UploadImageData) bundle.getSerializable(IMAGE_TYPE);
            imagePreviewPath = imageType.getImageUrl();
            draftState = String.valueOf(imageType.isDraft());

            if (imageType.getImageCaption() != null)
                imageCaption = imageType.getImageCaption();

            if (imageType.getImageLocation() != null)
                imageLocation = imageType.getImageLocation();

            initView();
            initPresenter();
            initListener();
        }


    }

    @Override
    public void initView() {


        uploadBrn = findViewById(R.id.upload_image_btn);
        uploadImageProgress = findViewById(R.id.upload_image_progress);

        imagePreview = findViewById(R.id.tag_img_preview);
        autoCompleteTextView = findViewById(R.id.tag_auto_complete);
        autoCompleteTagMenuAdapter = new AutoCompleteTagMenuAdapter(this, R.layout.item_drop_down, tagMenuList);
        autoCompleteTextView.setAdapter(autoCompleteTagMenuAdapter);
        autoCompleteTextView.setThreshold(0);
        autoCompleteTagMenuAdapter.notifyDataSetChanged();
        backBtn = findViewById(R.id.back_btn);
        GlideApp.with(getBaseContext())
                .load(imagePreviewPath)
                .centerCrop()
                .error(R.drawable.ic_launcher_foreground)
                .into(imagePreview);

        CustomRecyclerView tagsRv = findViewById(R.id.tags_rv);

        // Create the FlexboxLayoutMananger, only flexbox library version 0.3.0 or higher support.
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getApplicationContext());
        // Set flex direction.
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);

        // Set JustifyContent.
        flexboxLayoutManager.setJustifyContent(JustifyContent.SPACE_AROUND);
        tagsRv.setLayoutManager(flexboxLayoutManager);

        // Set adapter object.
        selectedTagAdapter = new SelectedTagAdapter(tagList);

        tagsRv.setAdapter(selectedTagAdapter);

        progress = findViewById(R.id.upload_progress);
    }

    @Override
    public void initPresenter() {
        addTagActivityPresenter = new AddTagActivityPresenterImpl(getBaseContext(), this);
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    private void initListener() {

        autoCompleteTagMenuAdapter.onMenuItemClicked = this::addSelectedTag;
        selectedTagAdapter.onSelectedItemClicked = tag -> {

            tagList.remove(tag);
            selectedTagAdapter.notifyDataSetChanged();
        };

        initKeyBoardListener();

        disposable.add(

                RxTextView.textChangeEvents(autoCompleteTextView)
                        .skipInitialValue()
                        .debounce(Constants.QUERY_SEARCH_TIME_OUT, TimeUnit.MILLISECONDS)
                        .distinctUntilChanged()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(getAutoCompleteKey()));


        backBtn.setOnClickListener((view) -> onBackPressed());


        uploadBrn.setOnClickListener(v -> {
//            addTagActivityPresenter.uploadPhoto(imagePreviewPath, imageCaption, imageLocation, draftState, imageType, tagList);
            if (tagList.isEmpty()) {
                showToast(getString(R.string.tag_is_required));
                return;
            }
            UploadPhotoModel model = addTagActivityPresenter.getUploadModel(imagePreviewPath, imageCaption, imageLocation, draftState, imageType, tagList);
            Intent intent = new Intent(this, UploaderService.class);
            if (!bound) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    startForegroundService(intent);
//                } else {
//                    startService(intent);
//                }
                pendingSendingMessage = true;
                pendingMessage = new Message();
                pendingMessage.what = UploaderService.UPLOAD_FILE;
                pendingMessage.obj = model;
                bindService(intent, connection, BIND_AUTO_CREATE);
            } else {
                Message message = new Message();
                message.what = UploaderService.UPLOAD_FILE;
                message.obj = model;
                sendMessageToService(message);
            }
        });
    }

    private DisposableObserver<TextViewTextChangeEvent> getAutoCompleteKey() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {

                if (textViewTextChangeEvent.getCount() == 0) {
                    autoCompleteTextView.setHint(R.string.add_new_tag);
                    return;
                }
                // user cleared search get default data
//                tagMenuList.clear();
                addTagActivityPresenter.getAutoCompleteTags(autoCompleteTextView.getText().toString().trim());
                Log.e(TAG, "search string: " + autoCompleteTextView.getText().toString());

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }

                ;
    }

    private void initKeyBoardListener() {
        final int MIN_KEYBOARD_HEIGHT_PX = 150;
        final View decorView = getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private final Rect windowVisibleDisplayFrame = new Rect();
            private int lastVisibleDecorViewHeight;

            @Override
            public void onGlobalLayout() {
                decorView.getWindowVisibleDisplayFrame(windowVisibleDisplayFrame);
                final int visibleDecorViewHeight = windowVisibleDisplayFrame.height();

                if (lastVisibleDecorViewHeight != 0) {
                    if (lastVisibleDecorViewHeight > visibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX) {
                        //Key board is visible
                    } else if (lastVisibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX < visibleDecorViewHeight) {
                        if (autoCompleteTextView.getText().toString().isEmpty())
                            return;
                        Tag newTag = new Tag();
                        newTag.name = autoCompleteTextView.getText().toString();
                        boolean tagAlreadyExsist = false;
                        for (Tag tag : tagList) {
                            if (tag.name.equals(newTag.name)) {
                                tagAlreadyExsist = true;
                                break;
                            }
                        }
                        if (!tagAlreadyExsist) {
                            addSelectedTag(newTag);
                            autoCompleteTextView.setText("");
                        }
                    }
                }
                // Сохраняем текущую высоту view до следующего вызова.
                // Save current decor view height for the next call.
                lastVisibleDecorViewHeight = visibleDecorViewHeight;
            }
        });
    }

    private void addSelectedTag(Tag tag) {
        tagList.add(tag);
        selectedTagAdapter.notifyDataSetChanged();
        autoCompleteTextView.setText("");
        autoCompleteTextView.dismissDropDown();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void viewUploadProgress(boolean state) {
        if (state) {
            uploadImageProgress.setVisibility(View.VISIBLE);
        } else {
            uploadImageProgress.setVisibility(View.GONE);
        }
    }


    @Override
    public void updateTagsList(List<Tag> tagList) {
        this.tagMenuList.clear();
        this.tagMenuList.addAll(tagList);
        autoCompleteTagMenuAdapter.notifyDataSetChanged();
//        autoCompleteTagMenuAdapter.vi
    }

    @Override
    public void viewMessage(String msg) {
        showToast(msg);
    }


    @Override
    protected void onDestroy() {
        if (bound) {
            Message message = new Message();
            message.what = UploaderService.REMOVE_COMMUNICATOR;
            sendMessageToService(message);
            unbindService(connection); //todo this line crashes app when backBtn pressed
        }
        disposable.clear();
        super.onDestroy();
    }
}
