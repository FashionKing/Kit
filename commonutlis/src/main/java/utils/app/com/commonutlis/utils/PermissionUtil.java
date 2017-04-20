package utils.app.com.commonutlis.utils;

import android.Manifest;

/**
 * Created by xiaohong.peng on 2017/3/3.
 */

public class PermissionUtil {
    ;
    public static final int REQUEST_CODE_PERMISSIONS = 10000;
    public static final int REQUEST_CODE_CAPTURE_PERMISSIONS = 10001;
    public static final int REQUEST_CODE_CAPTURE_HEAD_PERMISSIONS = 10002;
    public static final int REQUEST_CODE_SMS_PERMISSIONS = 10004;
    public static final int REQUEST_CODE_PASSWORD_SMS_PERMISSIONS = 10005;
    public static final int REQUEST_CODE_LOCATION_PERMISSIONS = 10006;
    public static final int REQUEST_CODE_CAPTURE_HEADS_PERMISSIONS = 10007;
    public static final int REQUEST_CODE_MAINONE_PERMISSIONS = 10009;

    /**
     * 电话权限
     */
    public static String[] phonePermission = {Manifest.permission.CALL_PHONE};
    /**
     * 扫一扫权限
     */
    public static String[] camearPermission = {Manifest.permission.CAMERA};
    /**
     * 拍照片上传头像
     */
    public static String[] camearCamearHeadPermission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    /**
     * 从相册里取
     */
//    public static String[] camearAlbumHeadPermission = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    /**
     * 读取短信
     */
    public static String[] smsPermission = {Manifest.permission.READ_SMS};

    /**
     * 定位权限
     */
    public static String[] LocationPermissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
    /**
     * 首次启动的话，手机设备和sd卡权限
     */
    public static String[] mainOnePermissions = { Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * 所以的权限
     */
    public String[] parms = {
            Manifest.permission.GET_ACCOUNTS, Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR,
            Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS};
}
