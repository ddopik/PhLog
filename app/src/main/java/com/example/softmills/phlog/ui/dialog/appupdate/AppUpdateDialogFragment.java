package com.example.softmills.phlog.ui.dialog.appupdate;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.softmills.phlog.BuildConfig;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseDialogFragment;
import com.example.softmills.phlog.ui.splash.model.CheckVersionData;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppUpdateDialogFragment extends BaseDialogFragment {

    private CheckVersionData data;
    private Action updateAction, skipAction;
    private boolean showSkip;
    private TextView currentVersion, latestVersionPrompt;

    private Button updateButton, skipButton;

    public AppUpdateDialogFragment() {
        // Required empty public constructor
    }

    public static AppUpdateDialogFragment newInstance(CheckVersionData data, boolean showSkip, Action updateAction, Action skipAction) {
        AppUpdateDialogFragment fragment = new AppUpdateDialogFragment();
        fragment.data = data;
        fragment.updateAction = updateAction;
        fragment.skipAction = skipAction;
        fragment.showSkip = showSkip;
        return fragment;
    }

    @Override
    protected void setViews(View view) {
        updateButton = view.findViewById(R.id.update_button);
        skipButton = view.findViewById(R.id.skip_button);
        currentVersion = view.findViewById(R.id.current_version_text_view);
        latestVersionPrompt = view.findViewById(R.id.update_prompt_text_view);
        if (showSkip) {
            skipButton.setVisibility(View.VISIBLE);
        } else {
            skipButton.setVisibility(View.GONE);
            skipButton.setEnabled(false);
        }
        currentVersion.setText(getString(R.string.current_version_value, BuildConfig.VERSION_NAME));
        latestVersionPrompt.setText(getString(R.string.latest_update_prompt, data.getLatest()));
    }

    @Override
    protected void setListeners() {
        updateButton.setOnClickListener(v -> {
            updateAction.run();
            dismiss();
        });
        skipButton.setOnClickListener(v -> skipAction.run());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_app_update_dialog, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getDialog() != null && getDialog().getWindow() != null)
            getDialog().getWindow().setBackgroundDrawableResource(R.color.transparent);
    }
}
