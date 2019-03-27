package com.example.softmills.phlog.network;

import com.androidnetworking.common.Priority;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.example.softmills.phlog.base.commonmodel.BaseStateResponse;
import com.example.softmills.phlog.base.commonmodel.Device;
import com.example.softmills.phlog.base.commonmodel.UploadImageData;
import com.example.softmills.phlog.ui.album.model.AlbumPreviewImagesResponse;
import com.example.softmills.phlog.ui.album.model.AlbumPreviewResponse;
import com.example.softmills.phlog.ui.album.model.SavePhotoResponse;
import com.example.softmills.phlog.ui.allphotos.model.PhotoGrapherPhotosResponse;
import com.example.softmills.phlog.ui.brand.model.BrandInnerResponse;
import com.example.softmills.phlog.ui.brand.model.FollowBrandResponse;
import com.example.softmills.phlog.ui.campaigns.inner.model.CampaignInnerPhotoResponse;
import com.example.softmills.phlog.ui.campaigns.inner.model.CampaignInnerResponse;
import com.example.softmills.phlog.ui.campaigns.model.CampaignResponse;
import com.example.softmills.phlog.ui.campaigns.model.FollowCampaignResponse;
import com.example.softmills.phlog.ui.earning.model.EarningDetailsResponse;
import com.example.softmills.phlog.ui.earning.model.EarningListResponse;
import com.example.softmills.phlog.ui.commentimage.model.AddImageToCartResponse;
import com.example.softmills.phlog.ui.commentimage.model.ImageCommentsResponse;
import com.example.softmills.phlog.ui.commentimage.model.LikeImageResponse;
import com.example.softmills.phlog.ui.commentimage.model.SocialAutoCompleteResponse;
import com.example.softmills.phlog.ui.commentimage.model.SubmitImageCommentResponse;
import com.example.softmills.phlog.ui.login.model.LoginResponse;
import com.example.softmills.phlog.ui.login.model.SocialLoginResponse;
import com.example.softmills.phlog.ui.notification.model.NotificationResponse;
import com.example.softmills.phlog.ui.photographerprofile.model.ProfilePhotoGrapherInfoResponse;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_camaigns.model.PhotoGrapherCampaignResponse;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.brand.model.PhotographerFollowingBrandResponse;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.following.model.PhotoGrapherFollowingInResponse;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.model.PhotoGrapherSavedPhotosResponse;
import com.example.softmills.phlog.ui.search.view.album.model.AlbumSearchResponse;
import com.example.softmills.phlog.ui.search.view.album.model.SearchFiltersResponse;
import com.example.softmills.phlog.ui.search.view.brand.model.BrandSearchResponse;
import com.example.softmills.phlog.ui.search.view.profile.model.ProfileSearchResponse;
import com.example.softmills.phlog.ui.signup.model.AllCountersRepose;
import com.example.softmills.phlog.ui.signup.model.SignUpResponse;
import com.example.softmills.phlog.ui.signup.model.UploadImgResponse;
import com.example.softmills.phlog.ui.social.model.SocialResponse;
import com.example.softmills.phlog.ui.uploadimage.model.AutoCompleteTagResponse;
import com.example.softmills.phlog.ui.userprofile.model.FollowUserResponse;
import com.example.softmills.phlog.ui.userprofile.model.UserPhotosResponse;
import com.example.softmills.phlog.ui.welcome.model.InitSlider;
import com.rx2androidnetworking.Rx2ANRequest;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;


/**
 * Created by abdalla-maged on 3/29/18.
 */

public class BaseNetworkApi {


    //Network Status
    public static String STATUS_OK = "200";
    public static String DEFAULT_USER_TYPE = "0";
    public static final int STATUS_BAD_REQUEST = 400;
    public static final int STATUS_401 = 401;
    public static final int STATUS_404 = 404;
    public static final int STATUS_500 = 500;
    public static String STATUS_ERROR = "405";
    public static final String ERROR_STATE_1 = "1";

    public static String IMAGE_TYPE_PHOTOS = "image";
    public static String IMAGE_TYPE_CAMPAIGN = "campaign_id";
    public static String IMAGE_TYPE_PROFILE = "image_profile";

    public static String STATUS_IN_VALID_RESPONSE = "401";
    public static String NEW_FACEBOOK_USER_STATUS = "0";
    //
//    private static final String BASE_URL = "http://178.128.162.10/public/api/photographer";
    private static final String BASE_URL = "http://178.128.162.10/api/photographer";
    private static final String BASE_URL_COMMON = "http://178.128.162.10/api/common";
    private static final String WELCOME_SLIDES_IMAGES = BASE_URL + "/init_slider";
    private static final String ALL_COUNTRES = BASE_URL_COMMON + "/countries/list";
    private static final String TAG_AUTO_COMPLETE = BASE_URL_COMMON + "/tags/search";
    private static final String SIGNUP_USER = BASE_URL + "/auth/signup";
    private static final String UPLOAD_CAMPAIGN_EXSISTING_PHOTO = BASE_URL + "/campaign/photo/assign";
    private static final String UPLOAD_PROFILE = BASE_URL + "/profile/upload";
    private static final String NORMAL_LOGIN = BASE_URL + "/auth/login";
    private static final String FACEBOOK_LOGIN_URL = BASE_URL + "/auth/signup_facebook";
    private static final String GOOGLE_LOGIN_URL = BASE_URL + "/auth/signup_google";
    private static final String USER_PROFILE_URL = BASE_URL + "/details";
    private static final String PHOTOGRAPHER_SAVED_PHOTO_URL = BASE_URL + "/photo/saved";
    private static final String PHOTOGRAPHER_ALL_PHOTO_URL = BASE_URL + "/photo/list";
    private static final String PHOTOGRAPHER_PROFILE_INFO = BASE_URL + "/details";
    private static final String PHOTOGRAPHER_ALL_CAMPAIGN_URL = BASE_URL + "/campaign/list";
    private static final String GET_ALBUM_IMAGES_PREVIEW = BASE_URL + "/album/photos";
    private static final String PHOTOGRAPHER_FOLLOW_USER_URL = BASE_URL + "/follow";
    private static final String PHOTOGRAPHER_UN_FOLLOW_USER_URL = BASE_URL + "/unfollow";
    private static final String ALL_CAMPAIGN_URL = BASE_URL + "/campaign/running";
    private static final String ALL_BRAND_CAMPAIGN_URL = BASE_URL + "/business/campaigns";
    private static final String CAMPAIGN_DETAILS_URL = BASE_URL + "/campaign/details";
    private static final String CAMPAIGN_PHOTOS_URL = BASE_URL + "/campaign/photos";
    private static final String FOLLOW_CAMPAIGN_URL = BASE_URL + "/campaign/join";
    private static final String USER_PROFILE_PHOTOS = BASE_URL + "/photo/list";
    private static final String USER_SEARCH_FILTERS = BASE_URL_COMMON + "/filters/list";
    private static final String SEARCH_ALBUM = BASE_URL + "/album/search";
    private static final String PHOTOGRAPHER_SEARCH_URL = BASE_URL + "/list";
    private static final String PROFILE_FOLLOWING_SEARCH_URL = BASE_URL + "/following/list";
    private static final String GET_ALBUM_DETAILS = BASE_URL + "/album/details";
    private static final String BRAND_SEARCH_URL = BASE_URL + "/business/search";
    private static final String PROFILE_BRAND_SEARCH_URL = BASE_URL + "/business/following/list";
    private static final String INNER_BRAND_URL = BASE_URL + "/business/details";
    private static final String BRAND_FOLLOW_URL = BASE_URL + "/business/follow";
    private static final String BRAND_UN_FOLLOW_URL = BASE_URL + "/business/unfollow";
    private static final String SOCIAL_DATA_URL = BASE_URL + "/social";
    private static final String SOCIAL_DATA_URL_RESET = BASE_URL + "/social?reset=1";
    private static final String GET_IMAGE_COMMENT = BASE_URL + "/photo/comment/list";
    private static final String SUBMIT_IMAGE_COMMENT = BASE_URL + "/photo/comment";
    private static final String GET_ALL_NOTIFICATION = BASE_URL + "/notification/list";
    private static final String GET_EARNING = BASE_URL + "/earning/list";
    private static final String UPLOAD_PHOTOGRAPHER_PHOTO = BASE_URL + "/photo/upload";

    private static final String DELETE_PHOTOGRAPHER_PHOTO = BASE_URL + "/photo/delete";
    private static final String LIKE_PHOTOGRAPHER_PHOTO = BASE_URL + "/photo/like";
    private static final String LIKE_IMAGE = BASE_URL + "/photo/like";
    private static final String UN_LIKE_IMAGE = BASE_URL + "/photo/unlike";
    private static final String RATE_IMAGE = BASE_URL + "/photo/rate";
    private static final String ADD_IMG_TO_CART_URL = BASE_URL + "/cart/add";


    private static final String SAVE_PHOTO_URL = BASE_URL + "/photo/save";
    private static final String UNSAVE_PHOTO_URL = BASE_URL + "/photo/unsave";
    private static final String FORGOT_PASSWORD_URL = BASE_URL + "/auth/forgot_password";
    private static final String UPDATE_PROGILE_URL = BASE_URL + "/profile/update";
     private static final String COMMENT_REPLAY_URL = BASE_URL + "/photo/comment/list";
    private static final String EARNING_DETAILS_URL = BASE_URL + "/earning/details";
    private static final String SOCIAL_AUTO_COMPLETE = BASE_URL_COMMON + "/social/search";
    private static final String UPDATE_FIREBASE_TOKEN_URL = BASE_URL + "/auth/device/set";


    //Path Parameters
    private static final String PAGER_QUERY_PARAMETER = "page";


    //Body Parameters
    private static final String TOKEN_BODY_PARAMETER = "token";


    public static Observable<List<InitSlider>> getWelcomeSlidesImages() {
        return Rx2AndroidNetworking.get(WELCOME_SLIDES_IMAGES)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectListObservable(InitSlider.class);
    }

    public static io.reactivex.Observable<AllCountersRepose> getAllCounters() {
        return Rx2AndroidNetworking.get(ALL_COUNTRES)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(AllCountersRepose.class);
    }


    public static io.reactivex.Observable<AutoCompleteTagResponse> getTagsAutoComplete(String keyword) {
        return Rx2AndroidNetworking.post(TAG_AUTO_COMPLETE)
                .addBodyParameter("keyword", keyword)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(AutoCompleteTagResponse.class);
    }

    public static io.reactivex.Observable<SignUpResponse> signUpUser(HashMap<String, String> signUpData) {
        return Rx2AndroidNetworking.post(SIGNUP_USER)
                .addBodyParameter("full_name", signUpData.get("full_name"))
                .addBodyParameter("password", signUpData.get("password"))
                .addBodyParameter("email", signUpData.get("email"))

                .addBodyParameter("mobile", signUpData.get("mobile"))
                .addBodyParameter("country_id", signUpData.get("country_id"))

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

    public static Observable<SocialLoginResponse> socialLoginFacebook(HashMap<String, String> loginData) {
        return Rx2AndroidNetworking.post(FACEBOOK_LOGIN_URL)
//                .addBodyParameter("fullName", loginData.get("fullName"))
//                .addBodyParameter("facebook_id", loginData.get("facebook_id"))
//                .addBodyParameter("mobile_os", loginData.get("mobile_os"))
//                .addBodyParameter("mobile_model", loginData.get("mobile_model"))
//                .addBodyParameter("email", loginData.get("email"))
//                .addBodyParameter("image_profile", loginData.get("image_profile"))
                .addBodyParameter(loginData)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(SocialLoginResponse.class);
    }

    public static Observable<SocialLoginResponse> socialLoginGoogle(HashMap<String, String> loginData) {
        return Rx2AndroidNetworking.post(GOOGLE_LOGIN_URL)
                .addBodyParameter(loginData)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(SocialLoginResponse.class);
    }


    public static io.reactivex.Observable<ProfilePhotoGrapherInfoResponse> getUserProfile(String userID) {
        return Rx2AndroidNetworking.post(USER_PROFILE_URL)
                .addBodyParameter("photographer_id", userID)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(ProfilePhotoGrapherInfoResponse.class);
    }

    public static io.reactivex.Observable<FollowUserResponse> followUser(String userID) {
        return Rx2AndroidNetworking.post(PHOTOGRAPHER_FOLLOW_USER_URL)
                .addBodyParameter("photographer_id", userID)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(FollowUserResponse.class);
    }

    public static io.reactivex.Observable<FollowUserResponse> unFollowUser(String userID) {
        return Rx2AndroidNetworking.post(PHOTOGRAPHER_UN_FOLLOW_USER_URL)
                .addBodyParameter("photographer_id", userID)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(FollowUserResponse.class);
    }

    public static io.reactivex.Observable<PhotoGrapherSavedPhotosResponse> getPhotoGrapherSavedPhotos(int pageNumber) {
        return Rx2AndroidNetworking.post(PHOTOGRAPHER_SAVED_PHOTO_URL)
                .addQueryParameter(PAGER_QUERY_PARAMETER, String.valueOf(pageNumber))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(PhotoGrapherSavedPhotosResponse.class);
    }


    public static io.reactivex.Observable<PhotoGrapherPhotosResponse> getPhotoGrapherPhotos(int pageNumber) {
        return Rx2AndroidNetworking.post(PHOTOGRAPHER_ALL_PHOTO_URL)
                .addQueryParameter(PAGER_QUERY_PARAMETER, String.valueOf(pageNumber))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(PhotoGrapherPhotosResponse.class);
    }

    public static io.reactivex.Observable<PhotoGrapherCampaignResponse> getPhotoGrapherProfileCampaign(String token, int pageNumber) {
        return Rx2AndroidNetworking.post(PHOTOGRAPHER_ALL_CAMPAIGN_URL)
                .addBodyParameter(TOKEN_BODY_PARAMETER, token)
                .addQueryParameter(PAGER_QUERY_PARAMETER, String.valueOf(pageNumber))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(PhotoGrapherCampaignResponse.class);
    }

    public static io.reactivex.Observable<ProfilePhotoGrapherInfoResponse> getProfileInfo() {
        return Rx2AndroidNetworking.post(PHOTOGRAPHER_PROFILE_INFO)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(ProfilePhotoGrapherInfoResponse.class);
    }


    public static io.reactivex.Observable<PhotoGrapherFollowingInResponse> getPhotoGrapherProfileFollowingSearch(String token, String key, int page) {
        return Rx2AndroidNetworking.post(PROFILE_FOLLOWING_SEARCH_URL)
                .addBodyParameter(TOKEN_BODY_PARAMETER, token)
                .addBodyParameter("keyword", key)
                .addQueryParameter(PAGER_QUERY_PARAMETER, String.valueOf(page))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(PhotoGrapherFollowingInResponse.class);
    }

    public static io.reactivex.Observable<BrandSearchResponse> getBrandSearch(String key, int page) {
        return Rx2AndroidNetworking.post(BRAND_SEARCH_URL)
                .addBodyParameter("keyword", key)
                .addQueryParameter(PAGER_QUERY_PARAMETER, String.valueOf(page))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(BrandSearchResponse.class);
    }

    public static io.reactivex.Observable<ProfileSearchResponse> getProfileSearch(String key, int page) {
        return Rx2AndroidNetworking.post(PHOTOGRAPHER_SEARCH_URL)
                .addBodyParameter("keyword", key)
                .addQueryParameter(PAGER_QUERY_PARAMETER, String.valueOf(page))
                .getResponseOnlyFromNetwork()
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(ProfileSearchResponse.class);
    }

    public static io.reactivex.Observable<PhotographerFollowingBrandResponse> getProfileBrand(String key, String page) {
        return Rx2AndroidNetworking.post(PROFILE_BRAND_SEARCH_URL)
                .addBodyParameter("keyword", key)
                .addQueryParameter(PAGER_QUERY_PARAMETER, String.valueOf(page))
                .getResponseOnlyFromNetwork()
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(PhotographerFollowingBrandResponse.class);
    }

    public static io.reactivex.Observable<CampaignResponse> getAllRunningCampaign(String page) {
        return Rx2AndroidNetworking.post(ALL_CAMPAIGN_URL)
                .addQueryParameter(PAGER_QUERY_PARAMETER, String.valueOf(page))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(CampaignResponse.class);
    }

    public static io.reactivex.Observable<CampaignResponse> getCampaignBrands(String brandId, String page) {
        return Rx2AndroidNetworking.post(ALL_BRAND_CAMPAIGN_URL)
                .addQueryParameter(PAGER_QUERY_PARAMETER, String.valueOf(page))
                .addQueryParameter("business_id", brandId)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(CampaignResponse.class);
    }

    public static io.reactivex.Observable<CampaignInnerResponse> getCampaignDetails(String token, String campaignID) {
        return Rx2AndroidNetworking.post(CAMPAIGN_DETAILS_URL)
                .addBodyParameter(TOKEN_BODY_PARAMETER, token)
                .addBodyParameter("campaign_id", campaignID)
                .getResponseOnlyFromNetwork()
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(CampaignInnerResponse.class);
    }


    public static io.reactivex.Observable<CampaignInnerPhotoResponse> getCampaignInnerPhotos(String token, String campaignID, int page) {
        return Rx2AndroidNetworking.post(CAMPAIGN_PHOTOS_URL)
                .addBodyParameter(TOKEN_BODY_PARAMETER, token)
                .addBodyParameter("campaign_id", campaignID)
                .addQueryParameter(PAGER_QUERY_PARAMETER, String.valueOf(page))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(CampaignInnerPhotoResponse.class);
    }

    public static io.reactivex.Observable<FollowCampaignResponse> followCampaign(String campaignID) {
        return Rx2AndroidNetworking.post(FOLLOW_CAMPAIGN_URL)
                .addBodyParameter("campaign_id", campaignID)
                .setPriority(Priority.HIGH)
                .getResponseOnlyFromNetwork()
                .build()
                .getObjectObservable(FollowCampaignResponse.class);
    }


    public static io.reactivex.Observable<UserPhotosResponse> getUserProfilePhotos(String userID, int page) {
        return Rx2AndroidNetworking.post(USER_PROFILE_PHOTOS)
                .addBodyParameter("photographer_id", userID)
                .addQueryParameter(PAGER_QUERY_PARAMETER, String.valueOf(page))
                .getResponseOnlyFromNetwork()
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(UserPhotosResponse.class);

    }

    public static io.reactivex.Observable<BaseStateResponse> likePhotoGrapherPhotoPhoto(String imageId) {
        return Rx2AndroidNetworking.post(LIKE_PHOTOGRAPHER_PHOTO)
                .addBodyParameter("photo_id", imageId)
                .getResponseOnlyFromNetwork()
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(BaseStateResponse.class);
    }


    public static io.reactivex.Observable<BaseStateResponse> deleteImage(String imageId) {
        return Rx2AndroidNetworking.post(DELETE_PHOTOGRAPHER_PHOTO)
                .addBodyParameter("photo_id", imageId)
                .getResponseOnlyFromNetwork()
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(BaseStateResponse.class);
    }

    public static io.reactivex.Observable<SearchFiltersResponse> getFilters() {
        return Rx2AndroidNetworking.get(USER_SEARCH_FILTERS)

                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(SearchFiltersResponse.class);
    }

    public static io.reactivex.Observable<AlbumSearchResponse> getSearchAlbum(String key, String page) {
        return Rx2AndroidNetworking.post(SEARCH_ALBUM)
                .addQueryParameter(PAGER_QUERY_PARAMETER, page)
                .addQueryParameter("keyword", key)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(AlbumSearchResponse.class);
    }

    public static Observable<AlbumPreviewResponse> getAlbumDetails(String albumId) {
        return Rx2AndroidNetworking.post(GET_ALBUM_DETAILS)
                .addBodyParameter("album_id", albumId)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(AlbumPreviewResponse.class);
//        .getStringSingle();
    }

    public static io.reactivex.Observable<AlbumPreviewImagesResponse> getAlbumImagesPreview(String albumId, String page) {
        return Rx2AndroidNetworking.post(GET_ALBUM_IMAGES_PREVIEW)
                .addQueryParameter(PAGER_QUERY_PARAMETER, page)
                .addBodyParameter("album_id", albumId)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(AlbumPreviewImagesResponse.class);
    }

    public static io.reactivex.Observable<ImageCommentsResponse> getImageComments(String image_id, String page) {
        return Rx2AndroidNetworking.post(GET_IMAGE_COMMENT)
                .addQueryParameter("photo_id", image_id)
                .addQueryParameter(PAGER_QUERY_PARAMETER, page)
                .getResponseOnlyFromNetwork()
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(ImageCommentsResponse.class);
    }

    public static io.reactivex.Observable<SubmitImageCommentResponse> submitImageComment(String image_id, String imageComment) {
        return Rx2AndroidNetworking.post(SUBMIT_IMAGE_COMMENT)
                .addQueryParameter("photo_id", image_id)
                .addQueryParameter("comment", imageComment)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(SubmitImageCommentResponse.class);
    }


    public static io.reactivex.Observable<BrandInnerResponse> getBrandInnerData(String token, String brandId) {
        return Rx2AndroidNetworking.post(INNER_BRAND_URL)
                .addBodyParameter("business_id", brandId)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(BrandInnerResponse.class);
    }

    public static io.reactivex.Observable<FollowBrandResponse> followBrand(String brandId) {
        return Rx2AndroidNetworking.post(BRAND_FOLLOW_URL)
                .getResponseOnlyFromNetwork()
                .addBodyParameter("business_id", brandId)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(FollowBrandResponse.class);
    }

    public static io.reactivex.Observable<FollowBrandResponse> unFollowBrand(String brandId) {
        return Rx2AndroidNetworking.post(BRAND_UN_FOLLOW_URL)
                .getResponseOnlyFromNetwork()
                .addBodyParameter("business_id", brandId)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(FollowBrandResponse.class);
    }

    public static io.reactivex.Observable<SocialResponse> getSocialData(boolean firstTime) {

        String url;
        if (firstTime) {
            url = SOCIAL_DATA_URL_RESET;
        } else {
            url = SOCIAL_DATA_URL;
        }
        return Rx2AndroidNetworking.post(url)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(SocialResponse.class);
    }

    public static Observable<NotificationResponse> getNotification(String token, String page) {
        return Rx2AndroidNetworking.post(GET_ALL_NOTIFICATION)
                .addBodyParameter(TOKEN_BODY_PARAMETER, token)
                .addQueryParameter(PAGER_QUERY_PARAMETER, page)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(NotificationResponse.class);
    }

    public static Observable<EarningListResponse> geEarningList(String token, String page) {
        return Rx2AndroidNetworking.post(GET_EARNING)
                .addBodyParameter(TOKEN_BODY_PARAMETER, token)
                .getResponseOnlyFromNetwork()
                .addQueryParameter(PAGER_QUERY_PARAMETER, page)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(EarningListResponse.class);
    }


    public static io.reactivex.Observable<UploadImgResponse> uploadCampaignExistingPhoto(Map<String, String> data) {
        return Rx2AndroidNetworking.post(UPLOAD_CAMPAIGN_EXSISTING_PHOTO)
                .addBodyParameter(data)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(UploadImgResponse.class);
    }


    public static io.reactivex.Observable<UploadImgResponse> uploadProfileImg(File imgPath) {
        return Rx2AndroidNetworking.upload(UPLOAD_PROFILE)
                .addHeaders("x-user-type", DEFAULT_USER_TYPE)
                .addHeaders("x-lang-code", "en-us")
                .addMultipartFile(IMAGE_TYPE_PROFILE, imgPath)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(UploadImgResponse.class);
    }


    public static io.reactivex.Observable<UploadImgResponse> uploadPhotoGrapherPhoto(String token, String caption, String location, File imgPath, Map<String, String> tagList, UploadProgressListener progressListener) {
        return Rx2AndroidNetworking.upload(UPLOAD_PHOTOGRAPHER_PHOTO)
                .addHeaders("x-auth-token", token)
                .addHeaders("x-user-type", DEFAULT_USER_TYPE)
                .addHeaders("x-lang-code", "en-us")
                .addMultipartParameter("caption", caption)
                .addMultipartParameter("location", location)
                .addMultipartParameter(tagList)
                .addMultipartFile("image", imgPath)
                .setPriority(Priority.HIGH)
                .setOkHttpClient(new OkHttpClient.Builder()
//                        .connectTimeout(5, TimeUnit.MINUTES)
                        .readTimeout(1, TimeUnit.MINUTES)
                        .writeTimeout(1, TimeUnit.MINUTES)
                        .build())
                .build()
                .setUploadProgressListener(progressListener)
                .getObjectObservable(UploadImgResponse.class);
    }

    public static io.reactivex.Observable<UploadImgResponse> uploadCampaignPhoto(String token, String caption, String location, File imgPath, Map<String, String> tagList, UploadImageData uploadImageData) {
        return Rx2AndroidNetworking.upload(UPLOAD_PHOTOGRAPHER_PHOTO)
                .addHeaders("x-auth-token", token)
                .addHeaders("x-user-type", DEFAULT_USER_TYPE)
                .addHeaders("x-lang-code", "en-us")
                .addMultipartParameter("caption", caption)
                .addMultipartParameter("location", location)
                .addMultipartParameter(tagList)
                .addMultipartParameter(IMAGE_TYPE_CAMPAIGN, uploadImageData.getImageId())
                .addMultipartFile("image", imgPath)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(UploadImgResponse.class);
    }

    public static io.reactivex.Observable<SavePhotoResponse> savePhoto(int id) {
        return Rx2AndroidNetworking.post(SAVE_PHOTO_URL)
                .addBodyParameter("photo_id", String.valueOf(id))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(SavePhotoResponse.class);
    }

    public static io.reactivex.Observable<SavePhotoResponse> unSavePhoto(int id) {
        return Rx2AndroidNetworking.post(UNSAVE_PHOTO_URL)
                .addBodyParameter("photo_id", String.valueOf(id))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(SavePhotoResponse.class);
    }

    public static io.reactivex.Observable<AlbumSearchResponse> getSearchAlbum(String key, Map<String, String> filtersMap, String page) {
        return Rx2AndroidNetworking.post(SEARCH_ALBUM)
                .addQueryParameter(PAGER_QUERY_PARAMETER, page)
                .addBodyParameter("keyword", key)
                .addBodyParameter(filtersMap)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(AlbumSearchResponse.class);
    }

    public static io.reactivex.Observable<String> forgotPassword(String email) {
        return Rx2AndroidNetworking.post(FORGOT_PASSWORD_URL)
                .setPriority(Priority.HIGH)
                .addBodyParameter("email", email)
                .build()
                .getStringObservable();
    }

    public static io.reactivex.Observable<String> updateProfile(HashMap<String, File> files, HashMap<String, String> data, String userToken) {
        Rx2ANRequest.MultiPartBuilder builder = Rx2AndroidNetworking.upload(UPDATE_PROGILE_URL).setPriority(Priority.HIGH);
        if (files != null)
            builder.addMultipartFile(files);
        builder.addMultipartParameter(data);
        return builder
                .setOkHttpClient(new OkHttpClient.Builder()
//                        .connectTimeout(5, TimeUnit.MINUTES)
                        .readTimeout(1, TimeUnit.MINUTES)
                        .writeTimeout(1, TimeUnit.MINUTES)
                        .build())
                .addHeaders("x-auth-token", userToken)
                .addHeaders("x-user-type", DEFAULT_USER_TYPE)
                .addHeaders("x-lang-code", "en-us")
                .build().getStringObservable();
    }

    public static io.reactivex.Observable<EarningDetailsResponse> getEarningDetails(String earningId) {
        return Rx2AndroidNetworking.post(EARNING_DETAILS_URL)
                .addBodyParameter("transaction_id", earningId)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(EarningDetailsResponse.class);
    }

    public static Observable<SocialAutoCompleteResponse> getSocialAutoComplete(String keyword) {
        return Rx2AndroidNetworking.post(SOCIAL_AUTO_COMPLETE)
                .addBodyParameter("keyword", keyword)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(SocialAutoCompleteResponse.class);
    }


    public static io.reactivex.Observable<LikeImageResponse> likeImage(String imageId) {
        return Rx2AndroidNetworking.post(LIKE_IMAGE)
                .addBodyParameter("photo_id", imageId)
                .getResponseOnlyFromNetwork()
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(LikeImageResponse.class);
    }

    public static io.reactivex.Observable<LikeImageResponse> unlikeImage(String imageId) {
        return Rx2AndroidNetworking.post(UN_LIKE_IMAGE)
                .addBodyParameter("photo_id", imageId)
                .getResponseOnlyFromNetwork()
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(LikeImageResponse.class);
    }

    public static io.reactivex.Observable<AddImageToCartResponse> addImageToCart(String imageID) {
        return Rx2AndroidNetworking.post(ADD_IMG_TO_CART_URL)
                .setPriority(Priority.HIGH)
                .addBodyParameter("photo_id", imageID)
                .build()
                .getObjectObservable(AddImageToCartResponse.class);
    }

    public static Observable<LoginResponse> updateFirebaseToken(String token, String firebaseToken) {
        return Rx2AndroidNetworking.upload(UPDATE_PROGILE_URL)
                .addHeaders("x-auth-token", token)
                .addHeaders("x-user-type", DEFAULT_USER_TYPE)
                .addHeaders("x-lang-code", "en-us")
                .addMultipartParameter("firebase_token", firebaseToken)
                .build()
                .getObjectObservable(LoginResponse.class);
    }

    public static Observable<String> updateFirebaseToken(Device device, String token) {
        return Rx2AndroidNetworking.post(UPDATE_FIREBASE_TOKEN_URL)
                .addHeaders("x-auth-token", token)
                .addHeaders("x-user-type", DEFAULT_USER_TYPE)
                .addHeaders("x-lang-code", "en-us")
                .addBodyParameter(device)
                .build()
                .getStringObservable();
    }

//    public static io.reactivex.Observable<GeoCodeAutoCompleteResponse> getGeoGodeAutoCompleteResponse(String key){
//        return Rx2AndroidNetworking.get()
//
//    }
//
    public static Observable<ImageCommentsResponse> getCommentReplies(int repliesId, int imageID, String page) {
        return Rx2AndroidNetworking.post(COMMENT_REPLAY_URL)
                .setPriority(Priority.HIGH)
//                .addHeaders("x-lang-code","en-us")
//                .addHeaders("x-auth-token","a72907a015f69ca277cfb0953107289d")
//                .addHeaders("x-user-type","0")
                .addBodyParameter("photo_id", String.valueOf(imageID))
                .addBodyParameter("parent_comment_id", String.valueOf(repliesId))
                .addQueryParameter(PAGER_QUERY_PARAMETER, page)
                .build()
                .getObjectObservable(ImageCommentsResponse.class);
    }

    public static io.reactivex.Observable<SubmitImageCommentResponse> submitImageCommentReplay(String image_id,String parentCommnetId ,String imageComment) {
        return Rx2AndroidNetworking.post(SUBMIT_IMAGE_COMMENT)
                .addBodyParameter("parent_comment_id", parentCommnetId)
                .addBodyParameter("photo_id", image_id)
                .addBodyParameter("comment", imageComment)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(SubmitImageCommentResponse.class);
    }



}
