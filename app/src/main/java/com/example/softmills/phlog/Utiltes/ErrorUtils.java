package com.example.softmills.phlog.Utiltes;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.example.softmills.phlog.base.model.ErrorMessageResponse;
import com.google.gson.Gson;

import static com.example.softmills.phlog.network.BaseNetworkApi.ERROR_STATE_1;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public class ErrorUtils {

    private static String TAG = ErrorUtils.class.getSimpleName();

    public ErrorUtils() {

    }

    //Bad RequestHandling
    public static void setError(Context context, String contextTAG, String error, String stateCode) {
        Log.e(TAG, contextTAG + "------>" + error);
        Toast.makeText(context, "Request error", Toast.LENGTH_SHORT).show();
    }

    //Bad Request Calling
    public static void setError(Context context, String contextTAG, String error) {

        Log.e(TAG, contextTAG + "------>" + error);
    }

    public static void setError(Context context, String contextTAG, Throwable throwable) {
        String errorData = ((ANError) throwable).getErrorBody();
        Gson gson = new Gson();
        viewErrorType_1(context, contextTAG, gson.fromJson(errorData, ErrorMessageResponse.class));

    }

    private static void viewErrorType_1(Context context, String contextTAG, ErrorMessageResponse errorMessageResponse) {
        for (int i = 0; i < errorMessageResponse.errors.size(); i++) {
            switch (errorMessageResponse.errors.get(i).code) {
                case (ERROR_STATE_1): {
                    Toast.makeText(context, errorMessageResponse.errors.get(i).message, Toast.LENGTH_SHORT).show();
                }
                default: {
                    Log.e(TAG, contextTAG + "------>" + errorMessageResponse.errors.get(i).message);
                }
            }
        }

    }
}
// ErrorUtils.setError(context, TAG, albumPreviewResponse.msg, albumPreviewResponse.state);
// ErrorUtils.setError(context, TAG, throwable.toString());