package com.example.softmills.phlog.Utiltes;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.example.softmills.phlog.base.commonmodel.ErrorMessageResponse;
import com.google.gson.Gson;

import static com.example.softmills.phlog.network.BaseNetworkApi.ERROR_STATE_1;
import static com.example.softmills.phlog.network.BaseNetworkApi.STATUS_401;
import static com.example.softmills.phlog.network.BaseNetworkApi.STATUS_404;
import static com.example.softmills.phlog.network.BaseNetworkApi.STATUS_500;
import static com.example.softmills.phlog.network.BaseNetworkApi.STATUS_BAD_REQUEST;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public class ErrorUtils {

    private static String TAG = ErrorUtils.class.getSimpleName();

    public ErrorUtils() {

    }

    //Bad RequestHandling
    public static void setError(Context context, String contextTAG, String error) {
        Log.e(TAG, contextTAG + "------>" + error);
        Toast.makeText(context, "Request error", Toast.LENGTH_SHORT).show();
    }

    //Universal Error State From Server
    public static void setError(Context context, String contextTAG, Throwable throwable) {
        try {
            String errorData = ((ANError) throwable).getErrorBody();
            int statusCode = ((ANError) throwable).getErrorCode();
            Gson gson = new Gson();
            switch (statusCode){
                case STATUS_BAD_REQUEST :{
                    viewError(context, contextTAG, gson.fromJson(errorData, ErrorMessageResponse.class));
                    break;
                }
                case STATUS_404 :{
                    Log.e(TAG, contextTAG + "------>" + STATUS_404 +"---"+((ANError) throwable).getResponse());
                    break;
                }
                case STATUS_401 :{
                    Log.e(TAG, contextTAG + "------>" + STATUS_401+"---"+((ANError) throwable).getResponse());
                    break;
                }
                case STATUS_500 :{
                    Log.e(TAG, contextTAG + "------>" + STATUS_500+"---"+((ANError) throwable).getResponse());
                    break;
                }

            }
        }catch (Exception e){
            Log.e(TAG,contextTAG+"--------------->"+((ANError) throwable).getResponse());
        }




    }

    ///PreDefined Error Code From Server
    private static void viewError(Context context, String contextTAG, ErrorMessageResponse errorMessageResponse) {
        for (int i = 0; i < errorMessageResponse.errors.size(); i++) {
            if(errorMessageResponse.errors.get(i).code !=null)
            switch (errorMessageResponse.errors.get(i).code ) {
                case (ERROR_STATE_1): {
                    Toast.makeText(context, errorMessageResponse.errors.get(i).message, Toast.LENGTH_SHORT).show();
                    break;
                }
                default: {
                    Log.e(TAG, contextTAG + "------>" + errorMessageResponse.errors.get(i).message);
                }
            }
        }

    }
}
// ErrorUtils.setError(context, TAG, throwable);