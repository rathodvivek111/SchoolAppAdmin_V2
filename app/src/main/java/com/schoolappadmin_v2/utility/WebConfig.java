package com.schoolappadmin_v2.utility;

/**
 * Created by RATHOD'S on 6/10/2017.
 */

public class WebConfig {

    //live
    public static String BASE_URL = "http://schoolmanagementapp.16mb.com/schoolapp/";
    public static String ADMIN_URL = "http://schoolmanagementapp.16mb.com/admin_schoolapp/";

    //local
   // public static String BASE_URL = "http://192.168.0.108/schoolapp/";
   // public static String ADMIN_URL = "http://192.168.0.108/admin_schoolapp/";

    public static String LOGIN = BASE_URL + "getlogin.php";
    //public static String REQUEST_SMS = BASE_URL + "getlogin.php";
    public static String GETALBUMS = BASE_URL + "getalbums.php";
    public static String GETNOTICE = BASE_URL + "getnotice.php";
    public static String GETNEWS = BASE_URL + "getnews.php";
    public static String GETEVENTS = BASE_URL + "getevents.php";
    public static String GETPICTURES = BASE_URL + "getpictures.php";
    public static String GETPARENTINFO = BASE_URL + "getparentinfo.php";


    //public static String BASE_URL = "http://schoolmanagementapp.16mb.com/schoolapp/";


    public static String SAVE_EVENT = ADMIN_URL + "save_event.php";
    public static String SAVE_PHOTO = ADMIN_URL + "save_photo.php";
    public static String SAVE_NEWS = ADMIN_URL + "save_news.php";
    public static String DELETE_NEWS = ADMIN_URL + "delete_news.php";
    public static String DELETE_NOTICE = ADMIN_URL + "delete_notice.php";
    public static String DELETE_EVENT = ADMIN_URL + "delete_event.php";
    public static String UPDATE_NEWS = ADMIN_URL + "update_news.php";
    public static String UPDATE_NOTICE = ADMIN_URL + "update_notice.php";
    public static String UPDATE_EVENT = ADMIN_URL + "update_event.php";
    public static String SAVE_NOTICE = ADMIN_URL + "save_notice.php";
    public static String SAVE_GALLERY = ADMIN_URL + "save_gallery.php";
    public static String DELETE_PHOTOS = ADMIN_URL + "delete_photos.php";
    public static String DELETE_GALLERY = ADMIN_URL + "delete_gallery.php";
    //public static String REQUEST_SMS = BASE_URL + "getlogin.php";


    ////// PARAMS
    public static String PARAM_NEWS_ID = "news_id";
    public static String PARAM_NOTICE_ID = "notice_id";
    public static String PARAM_EVENT_ID = "event_id";
    public static String PARAM_PHOTO_TITLE = "title";
    public static String PARAM_PHOTO_DESC = "description";
    public static String PARAM_PHOTO_ID = "gal_id";
    public static String PARAM_START_DATE = "start_date";

    public static String PARAM_NEWS = "news";
    public static String PARAM_HOTNEWS = "hot_news";
    public static String PARAM_END_DATE = "end_date";
    public static String PARAM_TITLE = "title";
    public static String PARAM_ORGANIZED_BY = "organized_by";
    public static String PARAM_NOTICE_DESC = "notice_desc";
    public static String PARAM_NOTICE_TITLE = "notice_title";
    public static String PARAM_DATE = "date";
    public static String PARAM_ACTIVE = "active";
    public static String PARAM_VENUE = "venue";
    public static String PARAM_THEME = "theme";
    public static String PARAM_URL = "url";
    public static String PARAM_IMAGE = "image";
    public static String PARAM_ID = "id";
    ////// PARAMS
    public static String PARAM_TYPE = "type";
    public static String PARAM_MOBILE_NUMBER = "mobile_number";
    public static String PARAM_GALLERY_ID = "gallery_id";
}
