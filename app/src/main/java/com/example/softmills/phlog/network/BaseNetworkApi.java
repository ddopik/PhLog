package com.example.softmills.phlog.network;

import com.androidnetworking.common.Priority;
import com.example.softmills.phlog.ui.login.model.LoginResponse;
import com.example.softmills.phlog.ui.login.model.SocialLoginResponse;
import com.example.softmills.phlog.ui.signup.model.AllCountersRepose;
import com.example.softmills.phlog.ui.signup.model.SignUpResponse;
import com.example.softmills.phlog.ui.welcome.model.WelcomeScreenResponse;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.HashMap;


/**
 * Created by abdalla-maged on 3/29/18.
 */

public class BaseNetworkApi {


    //Network Status
    public static String STATUS_OK = "200";
    public static String NEW_FACEBOOK_USER_STATUS = "0";
    //
    private static final String BASE_URL = "      http://178.128.162.10/public/api";
    private static final String WELCOME_SLIDES_IMAGES = BASE_URL + "/photographer/init_slider";
    private static final String ALL_COUNTRES = BASE_URL + "/photographer/countires";
    private static final String SIGNUP_USER = BASE_URL + "/photographer/signup";
    private static final String NORMAL_LOGIN = BASE_URL + "/photographer/login";
    private static final String FACEBOOK_LOGIN_URL = BASE_URL +"/photographer/check_facebook_id";


    public static io.reactivex.Observable<WelcomeScreenResponse> getWelcomeSlidesImages() {
        return Rx2AndroidNetworking.get(WELCOME_SLIDES_IMAGES)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(WelcomeScreenResponse.class);


    }

    public static io.reactivex.Observable<AllCountersRepose> getAllCounters() {
        return Rx2AndroidNetworking.get(ALL_COUNTRES)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(AllCountersRepose.class);
    }

    public static io.reactivex.Observable<SignUpResponse> signUpUser(HashMap<String, String> signUpData) {
        return Rx2AndroidNetworking.post(SIGNUP_USER)
                .addBodyParameter("full_name", signUpData.get("full_name"))
                .addBodyParameter("email", signUpData.get("email"))
                .addBodyParameter("password", signUpData.get("password"))
                .addBodyParameter("mobile", signUpData.get("mobile"))
                .addBodyParameter("country_id", signUpData.get("country_id"))
                .addBodyParameter("User_name", signUpData.get("User_name"))
                .addBodyParameter("mobile_os", signUpData.get("mobile_os"))
                .addBodyParameter("mobile_model", signUpData.get("mobile_model"))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(SignUpResponse.class);
    }

    public static io.reactivex.Observable<LoginResponse> LoginUserNormal(HashMap<String, String> loginData) {
        return Rx2AndroidNetworking.post(NORMAL_LOGIN)
                .addBodyParameter("email", loginData.get("email"))
                .addBodyParameter("password", loginData.get("password"))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(LoginResponse.class);
    }

    public static io.reactivex.Observable<SocialLoginResponse> socialLoginFacebook(HashMap<String, String> loginData) {
        return Rx2AndroidNetworking.post(FACEBOOK_LOGIN_URL)
                .addBodyParameter("facebook_id",loginData.get("facebook_id"))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(SocialLoginResponse.class);
    }


//

//    public static io.reactivex.Observable<BaseResponse> makeGetRequest(String lang, String key) {
//        return Rx2AndroidNetworking.get(REQUEST_URL)
//                .addPathParameter("lang", lang)
//                .addQueryParameter("key", String.valueOf(key))
//                .getResponseOnlyFromNetwork()
//                .build()
//                .getObjectObservable(BaseResponse.class);
//    }

//    /**
//     * --Upload Attachment
//     *
//     * @param file -->File src
//     * @message -->Request parameter you can add multible parameter to request  body along with uploaded attachment
//     */
//    public static void complaint(String message, File file, final RequestCallBack requestCallBack) {
//        AndroidNetworking.upload(REQUEST_URL)
//                .addHeaders("Content-Type", "multipart/form-data")
//                .addMultipartParameter("message", message)
//                .addMultipartFile("files[]", file) //todo "files[]" is A key According to back End
//                .setPriority(Priority.HIGH)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            boolean success = response.getBoolean("success");
//                            if (success) {
//                                requestCallBack.OnSuccsess();
//                            } else {
//                                requestCallBack.onFailer();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            requestCallBack.onFailer();
//                        }
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                        requestCallBack.onFailer();
//                    }
//                });
//    }
//

}
