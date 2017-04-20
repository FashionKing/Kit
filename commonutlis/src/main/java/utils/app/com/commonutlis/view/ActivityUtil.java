package utils.app.com.commonutlis.view;

import android.app.Activity;
import android.app.ActivityManager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import android.util.ArrayMap;

import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.view.inputmethod.InputMethodManager;

import android.widget.EditText;

import com.vise.log.ViseLog;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by xyy on 16/4/10.
 */
public class ActivityUtil {
    private ActivityUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void startForwardActivity(Activity context,
                                            Class<?> forwardActivity) {
        startForwardActivity(context, forwardActivity, false);
    }

    public static void startForwardActivity(Activity context,
                                            Class<?> forwardActivity, Boolean isFinish) {
        Intent intent = new Intent(context, forwardActivity);
        context.startActivity(intent);

        if (isFinish) {
            context.finish();
        }
    }

    public static void startForwardActivity(Activity context,
                                            Class<?> forwardActivity, Bundle bundle, Boolean isFinish, int animIn,
                                            int animOut) {
        Intent intent = new Intent(context, forwardActivity);

        if (bundle != null) {
            intent.putExtras(bundle);
        }

        context.startActivity(intent);

        if (isFinish) {
            context.finish();
        }

        try {
            context.overridePendingTransition(animIn, animOut);
        } catch (Exception e) {
            e.printStackTrace();
            ViseLog.e(e);
        }
    }

    public static void startForwardActivity(Activity context,
                                            Class<?> forwardActivity, Bundle bundle, Boolean isFinish) {
        Intent intent = new Intent(context, forwardActivity);

        if (bundle != null) {
            intent.putExtras(bundle);
        }

        context.startActivity(intent);

        if (isFinish) {
            context.finish();
        }
    }

    public static void startForResultActivity(Activity context,
                                              Class<?> forwardActivity, int requestCode, Bundle bundle,
                                              Boolean isFinish) {
        Intent intent = new Intent(context, forwardActivity);

        if (bundle != null) {
            intent.putExtras(bundle);
        }

        context.startActivityForResult(intent, requestCode);

        if (isFinish) {
            context.finish();
        }
    }

    public static void startForResultActivity(Activity context,
                                              Class<?> forwardActivity, int requestCode, Bundle bundle,
                                              Boolean isFinish, int animIn, int animOut) {
        Intent intent = new Intent(context, forwardActivity);

        if (bundle != null) {
            intent.putExtras(bundle);
        }

        context.startActivityForResult(intent, requestCode);

        if (isFinish) {
            context.finish();
        }

        try {
            context.overridePendingTransition(animIn, animOut);
        } catch (Exception e) {
            e.printStackTrace();
            ViseLog.e(e);
        }
    }

    /**
     * @param @param  context
     * @param @return 设定文件
     * @return String    返回类名
     * @Title: getTopActivity
     * @Description: 获取栈顶activity
     */
    public static String getTopActivity(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfo = manager.getRunningTasks(1);

        if (runningTaskInfo != null) {
            return (runningTaskInfo.get(0).topActivity.getClassName()).toString();
        } else {
            return "";
        }
    }

    /**
     * 判断某一Activity是否在当前栈顶
     *
     * @return true 当前Activity在栈顶，即在最前端显示
     * false 当前Activity不在栈顶，即在后台运行
     */
    public static boolean isTopActivity(Context context, String className) {
        final String topActivity = getTopActivity(context);

        if (className.equals(topActivity)) {
            return true;
        }

        return false;
    }

    /**
     * 设置Activity全屏显示。
     *
     * @param activity Activity引用
     * @param isFull   true为全屏，false为非全屏
     */
    public static void setFullScreen(Activity activity, boolean isFull) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        if (isFull) {
            params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            window.setAttributes(params);
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.setAttributes(params);
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    /**
     * 默认隐藏软键盘
     *
     * @param activity
     */
    public static void hideSoftInput(Activity activity) {
        activity.getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    public static void hideSoftInput(Activity activity, IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if ((v != null) && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);

            int left = l[0];
            int top = l[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();

            if ((event.getX() > left) && (event.getX() < right) &&
                    (event.getY() > top) && (event.getY() < bottom)) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }

        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 隐藏Activity的系统默认标题栏
     *
     * @param activity Activity对象
     */
    public static void hideTitleBar(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 强制设置Activity的显示方向为垂直方向。
     *
     * @param activity Activity对象
     */
    public static void setScreenVertical(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 强制设置Activity的显示方向为横向。
     *
     * @param activity Activity对象
     */
    public static void setScreenHorizontal(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 使UI适配输入法
     *
     * @param activity
     */
    public static void adjustSoftInput(Activity activity) {
        activity.getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    /**
     * 判断是否存在Activity
     *
     * @param packageName 包名
     * @param className   activity全路径类名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isActivityExists(String packageName, String className) {
        Intent intent = new Intent();
        intent.setClassName(packageName, className);

        return !((Utils.getContext().getPackageManager()
                .resolveActivity(intent, 0) == null) ||
                (intent.resolveActivity(Utils.getContext().getPackageManager()) == null) ||
                (Utils.getContext().getPackageManager().queryIntentActivities(intent, 0)
                        .size() == 0));
    }

    /**
     * 打开Activity
     *
     * @param packageName 包名
     * @param className   全类名
     */
    public static void launchActivity(String packageName, String className) {
        launchActivity(packageName, className, null);
    }

    /**
     * 打开Activity
     *
     * @param packageName 包名
     * @param className   全类名
     * @param bundle      bundle
     */
    public static void launchActivity(String packageName, String className,
                                      Bundle bundle) {
        Utils.getContext()
                .startActivity(getComponentIntent(packageName, className, bundle));
    }

    /**
     * 获取launcher activity
     *
     * @param packageName 包名
     * @return launcher activity
     */
    public static String getLauncherActivity(String packageName) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PackageManager pm = Utils.getContext().getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);

        for (ResolveInfo info : infos) {
            if (info.activityInfo.packageName.equals(packageName)) {
                return info.activityInfo.name;
            }
        }

        return "no " + packageName;
    }

    /**
     * 获取栈顶Activity
     *
     * @return 栈顶Activity
     */
    public static Activity getTopActivity() {
        try {
            Class activityThreadClass = Class.forName(
                    "android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod(
                    "currentActivityThread").invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField(
                    "mActivities");
            activitiesField.setAccessible(true);

            Map activities = null;

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                activities = (HashMap) activitiesField.get(activityThread);
            } else {
                activities = (ArrayMap) activitiesField.get(activityThread);
            }

            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField(
                        "paused");
                pausedField.setAccessible(true);

                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField(
                            "activity");
                    activityField.setAccessible(true);

                    return (Activity) activityField.get(activityRecord);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Intent getComponentIntent(String packageName,
                                             String className, Bundle bundle) {
        Intent intent = new Intent(Intent.ACTION_VIEW);

        if (bundle != null) {
            intent.putExtras(bundle);
        }

        ComponentName cn = new ComponentName(packageName, className);
        intent.setComponent(cn);

        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
