package com.example.softmills.phlog.Utiltes;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.softmills.phlog.base.commonmodel.Photographer;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ayman Abouzeid on 6/12/17.
 */

public abstract class PrefUtils {
    public static final String ARABIC_LANG = "ar";
    public static final String ENGLISH_LANG = "en";
    public static final String GUEST_USER_ID = "-1";
    private static final String LOGIN_PROVIDER = "LOGIN_PROVIDER";
    private static final String USER_ID = "USER_ID";
    private static final String USER_NAME = "USER_NAME";
    private static final String USER_TOKEN = "USER_TOKEN";
    private static final String NOTIFICATION_TOKEN = "NOTIFICATION_TOKEN";
    private static final String IS_FIRST_LAUNCH = "IS_FIRST_LAUNCH";
    private static final String IS_LANGUAGE_SELECTED = "IS_LANGUAGE_SELECTED";
    private static final String IS_TOKEN_SAVED = "IS_TOKEN_SAVED";
    private static final String APP_LANG = "APP_LANG";
    private static final String CURRENT_USER_OBJ = "current_user_obj";
    private static final String FIREBASE_TOKEN = "FIREBASE_TOKEN";
    private static String PREF_FILE_NAME;

    private static final String FIREBASE_TOKEN_SENT = "firebase_token_sent";

    public PrefUtils() {
        PREF_FILE_NAME = getProjectName();
    }

    private static SharedPreferences getSharedPref(Context context) {
        return context.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
    }

    public static void setUserID(Context context, String userID) {
        getSharedPref(context)
                .edit()
                .putString(USER_ID, userID)
                .apply();
    }

    public static void setUserName(Context context, String userName) {
        getSharedPref(context).edit().putString(USER_NAME, userName).apply();
    }

    public static void setUserToken(Context context, String userToken) {
        getSharedPref(context).edit().putString(USER_TOKEN, userToken).apply();
    }

    public static void setNotificationToken(Context context, String notificationToken) {
        getSharedPref(context).edit().putString(NOTIFICATION_TOKEN, notificationToken).apply();
    }

    public static void setIsFirstLaunch(Context context, boolean isFirstLaunch) {
        getSharedPref(context).edit().putBoolean(IS_FIRST_LAUNCH, isFirstLaunch).apply();
    }

    public static String getUserId(Context mContext) {
        return getSharedPref(mContext).getString(USER_ID, "0");

    }

    public static String getUserName(Context mContext) {
        return getSharedPref(mContext).getString(USER_NAME, GUEST_USER_ID);
    }

    public static String getUserToken(Context mContext) {
        return getSharedPref(mContext).getString(USER_TOKEN, "0");

    }

    public static String getNotificationToken(Context mContext) {
        return getSharedPref(mContext).getString(NOTIFICATION_TOKEN, "");
    }

    public static boolean isFirstLaunch(Context context) {
        return getSharedPref(context).getBoolean(IS_FIRST_LAUNCH, true);
    }

    public static String getAppLang(Context context) {
        return getSharedPref(context).getString(APP_LANG, ENGLISH_LANG);
    }

    public static void setAppLang(Context context, String appLang) {
        getSharedPref(context).edit().putString(APP_LANG, appLang).apply();
    }

    public static boolean isLanguageSelected(Context context) {
        return getSharedPref(context).getBoolean(IS_LANGUAGE_SELECTED, false);
    }

    public static void setIsLanguageSelected(Context context, boolean isSelected) {
        getSharedPref(context).edit().putBoolean(IS_LANGUAGE_SELECTED, isSelected).apply();
    }

    public static boolean isTokenSaved(Context context) {
        return getSharedPref(context).getBoolean(IS_TOKEN_SAVED, false);
    }

    public static void setLoginState(Context context, boolean state) {
        getSharedPref(context).edit().putBoolean(LOGIN_PROVIDER, state).apply();

    }

    public static boolean isLoginProvided(Context context) {
        return getSharedPref(context).getBoolean(LOGIN_PROVIDER, false);
    }

    public static void firstTimeAskingPermission(Context context, String permission, boolean isFirstTime) {
        SharedPreferences sharedPreference = context.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
        sharedPreference.edit().putBoolean(permission, isFirstTime).apply();
    }

    public static boolean isFirstTimeAskingPermission(Context context, String permission) {
        return context.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE).getBoolean(permission, true);
    }

    public static void saveFirebaseToken(Context applicationContext, String token) {
        applicationContext.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE)
                .edit()
                .putString(FIREBASE_TOKEN, token)
                .apply();
    }

    public static String getFirebaseToken(Context context) {
        return getSharedPref(context).getString(FIREBASE_TOKEN, null);
    }

    public String getProjectName() {
        return PREF_FILE_NAME;
    }

    public abstract void setPrefFileName(String projectName);


    public static void setFirebaseTokenSentToServer(Context context, boolean isSent) {
        getSharedPref(context)
                .edit()
                .putBoolean(FIREBASE_TOKEN_SENT, isSent)
                .apply();
    }

    public static boolean isFirebaseTokenSentToServer(Context context) {
        return getSharedPref(context)
                .getBoolean(FIREBASE_TOKEN_SENT, false);
    }


    public static void setCurrentUser(Context context, Photographer photographer) {
        Gson gson = new Gson();
        String json = gson.toJson(photographer);
        getSharedPref(context).edit().putString(CURRENT_USER_OBJ, json).apply();
    }

    public static Photographer getCurrentUser(Context context) {
        Gson gson = new Gson();
        String json = getSharedPref(context).getString(CURRENT_USER_OBJ, "");
        Photographer photographer = gson.fromJson(json, Photographer.class);
        return photographer;
    }
}
