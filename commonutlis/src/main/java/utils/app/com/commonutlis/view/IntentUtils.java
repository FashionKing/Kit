package utils.app.com.commonutlis.view;

import android.app.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.provider.MediaStore;

import android.support.v4.content.FileProvider;

import android.view.View;

import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.Serializable;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/9/23
 *     desc  : 意图相关工具类
 * </pre>
 */
public final class IntentUtils {
    public static final int REQUEST_CODE_GALLERY = 0x11;
    public static final int REQUEST_CODE_CAMERA = 0x12;
    public final static int REQUEST_CODE_CROP = 0x13;

    private IntentUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    /**
     * 获取安装App（支持6.0）的意图
     *
     * @param filePath 文件路径
     * @return intent
     */
    public static Intent getInstallAppIntent(String filePath) {
        return getInstallAppIntent(FileUtils.getFileByPath(filePath));
    }

    /**
     * 获取安装App(支持6.0)的意图
     *
     * @param file 文件
     * @return intent
     */
    public static Intent getInstallAppIntent(File file) {
        if (file == null) {
            return null;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        String type;

        if (Build.VERSION.SDK_INT < 23) {
            type = "application/vnd.android.package-archive";
        } else {
            type = MimeTypeMap.getSingleton()
                    .getMimeTypeFromExtension(FileUtils.getFileExtension(
                            file));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Uri contentUri = FileProvider.getUriForFile(Utils.getContext(),
                    "com.your.package.fileProvider", file);
            intent.setDataAndType(contentUri, type);
        }

        intent.setDataAndType(Uri.fromFile(file), type);

        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取卸载App的意图
     *
     * @param packageName 包名
     * @return intent
     */
    public static Intent getUninstallAppIntent(String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + packageName));

        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取打开App的意图
     *
     * @param packageName 包名
     * @return intent
     */
    public static Intent getLaunchAppIntent(String packageName) {
        return Utils.getContext().getPackageManager()
                .getLaunchIntentForPackage(packageName);
    }

    /**
     * 获取App具体设置的意图
     *
     * @param packageName 包名
     * @return intent
     */
    public static Intent getAppDetailsSettingsIntent(String packageName) {
        Intent intent = new Intent(
                "android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + packageName));

        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取分享文本的意图
     *
     * @param content 分享文本
     * @return intent
     */
    public static Intent getShareTextIntent(String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);

        return intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取分享图片的意图
     *
     * @param content   文本
     * @param imagePath 图片文件路径
     * @return intent
     */
    public static Intent getShareImageIntent(String content, String imagePath) {
        return getShareImageIntent(content, FileUtils.getFileByPath(imagePath));
    }

    /**
     * 获取分享图片的意图
     *
     * @param content 文本
     * @param image   图片文件
     * @return intent
     */
    public static Intent getShareImageIntent(String content, File image) {
        if (!FileUtils.isFileExists(image)) {
            return null;
        }

        return getShareImageIntent(content, Uri.fromFile(image));
    }

    /**
     * 获取分享图片的意图
     *
     * @param content 分享文本
     * @param uri     图片uri
     * @return intent
     */
    public static Intent getShareImageIntent(String content, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/*");

        return intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取其他应用组件的意图
     *
     * @param packageName 包名
     * @param className   全类名
     * @return intent
     */
    public static Intent getComponentIntent(String packageName, String className) {
        return getComponentIntent(packageName, className, null);
    }

    /**
     * 获取其他应用组件的意图
     *
     * @param packageName 包名
     * @param className   全类名
     * @param bundle      bundle
     * @return intent
     */
    public static Intent getComponentIntent(String packageName,
                                            String className, Bundle bundle) {
        Intent intent = new Intent(Intent.ACTION_VIEW);

        if (bundle != null) {
            intent.putExtras(bundle);
        }

        ComponentName cn = new ComponentName(packageName, className);
        intent.setComponent(cn);

        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取关机的意图
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.SHUTDOWN"/>}</p>
     *
     * @return intent
     */
    public static Intent getShutdownIntent() {
        Intent intent = new Intent(Intent.ACTION_SHUTDOWN);

        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳至拨号界面意图
     *
     * @param phoneNumber 电话号码
     */
    public static Intent getDialIntent(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:" + phoneNumber));

        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取拨打电话意图
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.CALL_PHONE"/>}</p>
     *
     * @param phoneNumber 电话号码
     */
    public static Intent getCallIntent(String phoneNumber) {
        Intent intent = new Intent("android.intent.action.CALL",
                Uri.parse("tel:" + phoneNumber));

        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳至发送短信界面的意图
     *
     * @param phoneNumber 接收号码
     * @param content     短信内容
     */
    public static Intent getSendSmsIntent(String phoneNumber, String content) {
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", content);

        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取拍照的意图
     *
     * @param outUri 输出的uri
     * @return 拍照的意图
     */
    public static Intent getCaptureIntent(Uri outUri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);

        return intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION |
                Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 打电话
     *
     * @param context
     * @param phoneNum
     */
    public static void call(Context context, int phoneNum) {
        Intent intent = new Intent();
        // 启动电话程序
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel://" + phoneNum));
        context.startActivity(intent);
    }

    /**
     * 打开浏览器
     *
     * @param context
     * @param url
     */
    public static void openBrowser(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    /**
     * 打开图片
     *
     * @param context
     * @param path
     */
    public static void openImage(Context context, String path) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        // intent.setDataAndType(Uri.parse("file:///mnt/sdcard/file/1.jgp"),
        // "image/*");
        intent.setDataAndType(Uri.parse("file:///" + path), "image/*");
        context.startActivity(intent);
    }

    /**
     * 打开音频
     *
     * @param context
     * @param path
     */
    public static void openAudio(Context context, String path) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file:///" + path), "audio/*");
        context.startActivity(intent);
    }

    /**
     * 打开视频文件
     *
     * @param context
     * @param path
     */
    public static void openVideo(Context context, String path) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file:///" + path), "video/*");
        context.startActivity(intent);
    }

    /**
     * 打开系统摄像头录像,并保存为图片
     *
     * @param context
     * @param path
     */
    public static void openCamera(Context context, String path) {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.parse(Environment.getExternalStorageDirectory() + "/Videos/" +
                        System.currentTimeMillis() + ".jpg"));
        context.startActivity(intent);
    }

    /**
     * 打开系统摄像头录像,并保存为视频
     *
     * @param context
     */
    public static void openCamera(Context context) {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.parse(Environment.getExternalStorageDirectory() + "/Videos/" +
                        System.currentTimeMillis() + ".mp4"));
        context.startActivity(intent);
    }

    /**
     * 分享
     */
    public static void shareApplication(Context context, String packname,
                                        String url) {
        // <action android:name="android.intent.action.SEND" />
        // <category android:name="android.intent.category.DEFAULT" />
        // <data android:mimeType="text/plain" />
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("text/plain");
        /*
         * intent.putExtra(Intent.EXTRA_TEXT,
         * "推荐您使用一款软件,下载地址为:https://play.google.com/store/apps/details?id=" +
         * packname);
         */
        intent.putExtra(Intent.EXTRA_TEXT,
                "推荐您使用一款软件,下载地址为:" + url + " ?id=" + packname);
        context.startActivity(intent);
    }

    /**
     * 发送对像
     *
     * @param t
     */
    public static <T extends Serializable> void sendData(Context context, T t) {
        Intent intent = new Intent(context, t.getClass());
        intent.putExtra(t.getClass().getSimpleName(), t); // 要传递对像，对像必须是经过序列化的
        context.startActivity(intent);
    }

    /**
     * 获取对像
     *
     * @param view
     */
    public static <T extends Serializable> T getData(Activity context, View view) {
        return (T) context.getIntent().getSerializableExtra("account");
    }

    /**
     * 安装文件s
     *
     * @param context
     * @param apkFile
     */
    public static void installApp(Context context, File apkFile) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(apkFile),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 卸载
     *
     * @param context
     * @param apkFile
     */
    public static void unInstallApp(Context context, File apkFile) {
        Uri packageURI = Uri.parse("package:com.andorid.main");
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        context.startActivity(uninstallIntent);
    }

    /**
     * 获得裁剪的图片从图片集合里
     * @param context
     * @param sdcardTempFile
     * @param crop
     */
    public static void getimageFromGallery(Activity context,
                                           File sdcardTempFile, int crop) {
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                "image/*");
        intent.putExtra("output", Uri.fromFile(sdcardTempFile));
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); // 裁剪框 intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", crop); // 输出图片大小
        intent.putExtra("outputY", crop);
        context.startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }

    /**
     * 获得裁剪的图片从Camera里
     * @param context
     * @param sdcardTempFile
     * @param crop
     */
    public static void getimageFromCamera(Activity context,
                                          File sdcardTempFile, int crop) {
        Uri uri = Uri.fromFile(sdcardTempFile);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("output", uri);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); // 裁剪框比�? intent.putExtra("aspectY",
        // 1);

        intent.putExtra("outputX", crop); // 输出图片大小
        intent.putExtra("outputY", crop);
        context.startActivityForResult(intent, REQUEST_CODE_CROP);
    }

    /**
     * 获得裁剪的图片从摄像头
     * @param context
     * @param sdcardTempFile
     */
    public static void getImageFromCamera(Activity context, File sdcardTempFile) {
        Uri uri = Uri.fromFile(sdcardTempFile);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", uri);
        context.startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    /*
        */
    /**
     * 获取选择照片的Intent
     *
     * @return
     */

    /*
    public static Intent getPickIntentWithGallery() {
     Intent intent = new Intent(Intent.ACTION_PICK);
     return intent.setType("image*/

    /*");
    }

    */

    /**
     * 获取从文件中选择照片的Intent
     *
     * @return
     */

    /*
    public static Intent getPickIntentWithDocuments() {
     Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
     return intent.setType("image*/

    /*");
    }


    public static Intent buildImageGetIntent(Uri saveTo, int outputX, int outputY, boolean returnData) {
    return buildImageGetIntent(saveTo, 1, 1, outputX, outputY, returnData);
    }

    public static Intent buildImageGetIntent(Uri saveTo, int aspectX, int aspectY,
           int outputX, int outputY, boolean returnData) {
    Intent intent = new Intent();
    if (Build.VERSION.SDK_INT < 19) {
    intent.setAction(Intent.ACTION_GET_CONTENT);
    } else {
    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
    intent.addCategory(Intent.CATEGORY_OPENABLE);
    }
    intent.setType("image*/

    /*");
    intent.putExtra("output", saveTo);
    intent.putExtra("aspectX", aspectX);
    intent.putExtra("aspectY", aspectY);
    intent.putExtra("outputX", outputX);
    intent.putExtra("outputY", outputY);
    intent.putExtra("scale", true);
    intent.putExtra("return-data", returnData);
    intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
    return intent;
    }

    public static Intent buildImageCropIntent(Uri uriFrom, Uri uriTo, int outputX, int outputY, boolean returnData) {
    return buildImageCropIntent(uriFrom, uriTo, 1, 1, outputX, outputY, returnData);
    }

    public static Intent buildImageCropIntent(Uri uriFrom, Uri uriTo, int aspectX, int aspectY,
                   int outputX, int outputY, boolean returnData) {
    Intent intent = new Intent("com.android.camera.action.CROP");
    intent.setDataAndType(uriFrom, "image*/

    /*");
    intent.putExtra("crop", "true");
    intent.putExtra("output", uriTo);
    intent.putExtra("aspectX", aspectX);
    intent.putExtra("aspectY", aspectY);
    intent.putExtra("outputX", outputX);
    intent.putExtra("outputY", outputY);
    intent.putExtra("scale", true);
    intent.putExtra("return-data", returnData);
    intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
    return intent;
    }

    public static Intent buildImageCaptureIntent(Uri uri) {
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
    return intent;
    }*/
}
