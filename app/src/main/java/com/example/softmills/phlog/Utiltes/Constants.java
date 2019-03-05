package com.example.softmills.phlog.Utiltes;

/**
 * Created by abdalla_maged on 10/18/2018.
 */
public interface Constants {

    int PERMEATION_REQUEST_CODE__WRITE_EXTERNAL_CAMERA = 1223;
    int REQUEST_CODE_GALLERY = 1224;
    int REQUEST_CODE_CAMERA = 1225;
    int REQUEST_CODE_LOCATION = 1225;
//    String  DEFAULT_PROFILE_IMAGE =1225;

    int SOCIAL_FRAGMENT_PAGING_THRESHOLD =15;

    int QUERY_SEARCH_TIME_OUT = 600;


    //////NavigationMangerConst
    enum NavigationHelper {
        CAMPAIGN, PROFILE, HOME, NOTIFICATION, UPLOAD_PHOTO, EARNING_LIST, EARNING_INNER, LOGOUT, EDIT_PROFILE
    }


    enum UploadImageTypes {
        CAMPAIGN_IMG, NORMAL_IMG
    }

    enum UserType {

        USER_TYPE_BUSINESS, USER_TYPE_PHOTOGRAPHER;
    }
    ///////////////////////Entity-type For socialFragment
    int ENTITY_PROFILE=1;
    int ENTITY_CAMPAIGN=2;
    int ENTITY_ALBUM=4;
    int ENTITY_IMAGE=3;
    int ENTITY_BRAND=5;

    ///////////////Entity Display-types
    String PROFILE_DISPLAY_TYPE_1 = "101";
    String PROFILE_DISPLAY_TYPE_2 = "102";
    String PROFILE_DISPLAY_TYPE_3 = "103";
    String PROFILE_DISPLAY_TYPE_4 = "104";
    String PROFILE_DISPLAY_TYPE_5 = "105";
    String PROFILE_DISPLAY_TYPE_6 = "106";

    String CAMPAIGN_DISPLAY_TYPE_1 = "201";
    String CAMPAIGN_DISPLAY_TYPE_2 = "202";
    String CAMPAIGN_DISPLAY_TYPE_3 = "203";
    String CAMPAIGN_DISPLAY_TYPE_4 = "204";
    String CAMPAIGN_DISPLAY_TYPE_5 = "205";


    String ALBUM_DISPLAY_TYPE_1 = "301";
    String ALBUM_DISPLAY_TYPE_2 = "302";
    String ALBUM_DISPLAY_TYPE_3 = "303";
    String ALBUM_DISPLAY_TYPE_4 = "405";
    String ALBUM_DISPLAY_TYPE_5 = "305";

    String IMGS_DISPLAY_TYPE_1 = "401";
    String IMGS_DISPLAY_TYPE_2 = "402";
    String IMGS_DISPLAY_TYPE_3 = "403";
    String IMGS_DISPLAY_TYPE_4 = "404";
    String IMGS_DISPLAY_TYPE_5 = "405";

    String BRAND_DISPLAY_TYPE_1 = "501";
    String BRAND_DISPLAY_TYPE_2 = "502";
    String BRAND_DISPLAY_TYPE_3 = "503";
    String BRAND_DISPLAY_TYPE_4 = "504";
    String BRAND_DISPLAY_TYPE_5 = "505";

    ///////////////////////////////


    enum PhotosListType{
        SOCIAL_LIST,PHOTO_GRAPHER_LIST
    }


    enum CommentListType{
        VIEW_REPLIES, MAIN_COMMENT,REPLAY_ON_COMMENT;
    }

    interface CampaignStatus {
        int CAMPAIGN_STATUS_CANCELLED = -1;
        int CAMPAIGN_STATUS_DRAFT = 0;
        int CAMPAIGN_STATUS_REQUEST = 1;
        int CAMPAIGN_STATUS_PENDING = 2;
        int CAMPAIGN_STATUS_APPROVED = 3;
        int CAMPAIGN_STATUS_RUNNING = 4;
        int CAMPAIGN_STATUS_FINISHED = 5;
        int CAMPAIGN_STATUS_PRIZE_PROCESSING = 6;
        int CAMPAIGN_STATUS_COMPLETED = 7;
    }

    interface MainActivityRedirectionValue {
        String VALUE = MainActivityRedirectionValue.class.getSimpleName();
        String PAYLOAD = "payload";
        int TO_PROFILE = 1;
        int TO_POPUP = 2;
    }

    interface PopupType {
        int LEVEL_UP = 1;
        int WON_CAMPAIGN = 2;
        int NONE = 0;
    }

    int STATUS_LOGGED_IN = 1;
    int STATUS_LOGGED_OUT = 0;
}
