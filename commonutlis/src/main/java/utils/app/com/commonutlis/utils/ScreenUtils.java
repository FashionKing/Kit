package utils.app.com.commonutlis.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * 获得屏幕相关的辅助类
 *
 * @author zhy
 */
public class ScreenUtils {
	private ScreenUtils() {
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 获得屏幕宽度
	 *
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	/**
	 * 获得屏幕高度
	 *
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}

	/**
	 * 获得状态栏的高度
	 *
	 * @param context
	 * @return
	 */
	public static int getStatusHeight(Context context) {

		int statusHeight = -1;
		try {
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height")
					.get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusHeight;
	}

	/**
	 * 获取当前屏幕截图，包含状态栏
	 *
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;

	}

	/**
	 * 获取当前屏幕截图，不包含状态栏
	 *
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithoutStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();
		return bp;

	}
	/**
	 * 获取控件的高度或者宽度  isHeight=true则为测量该控件的高度，isHeight=false则为测量该控件的宽度
	 * @param view
	 * @param isHeight
	 * @return
	 */
	public static int getViewHeight(View view, boolean isHeight){
		int result;
		if(view==null)return 0;
		if(isHeight){
			int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
			view.measure(h,0);
			result =view.getMeasuredHeight();
		}else{
			int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
			view.measure(0,w);
			result =view.getMeasuredWidth();
		}
		return result;
	}
	/**
	 * 设置控件大小
	 * @param view  控件
	 * @param width 宽度，单位：像素
	 * @param height 高度，单位：像素
	 */
	public static void setViewSize(View view,int width,int height){
		ViewGroup.LayoutParams params = view.getLayoutParams();
		params.width = width;
		params.height = height;
		view.setLayoutParams(params);
	}
	/**
	 * 以480分辨率为基础计算文字适配基数大小
	 */
	public static float getBaseSizeOn480(Context context) {
		float baseSizeOn720 = (float) getScreenWidth(context) / 480;
		return baseSizeOn720;
	}

	/**
	 * 以720分辨率为基础计算文字适配基数大小
	 */
	public static float getBaseSizeOn720(Context context) {
		float baseSizeOn720 = (float) getScreenWidth(context) / 720;
		return baseSizeOn720;
	}

	/**
	 * 以1080分辨率为基础计算文字适配基数大小
	 */
	public static float getBaseSizeOn1080(Context context) {
		float baseSizeOn720 = (float) getScreenWidth(context) / 1080;
		return baseSizeOn720;
	}

	/**
	 * TextView适配480分辨率
	 */
	public static void textAdaptationOn480(TextView textView, Context context, int textSize) {
		textView.setTextSize(AbViewUtil.px2sp(context, (int) (textSize * ScreenUtils.getBaseSizeOn480(context))));
	}

	/**
	 * TextView适配720分辨率
	 */
	public static void textAdaptationOn720(TextView textView, Context context, int textSize) {
		textView.setTextSize(AbViewUtil.px2sp(context, (int) (textSize * ScreenUtils.getBaseSizeOn720(context))));
	}

	/**
	 * TextView适配1080分辨率
	 */
	public static void textAdaptationOn1080(TextView textView, Context context, int textSize) {
		textView.setTextSize(AbViewUtil.px2sp(context, (int) (textSize * ScreenUtils.getBaseSizeOn1080(context))));
	}

	/**
	 * TextView适配720分辨率
	 */
	public static void textAdaptationOn720(ArrayList<TextView> textViews, Context context, int textSize) {
		int s = textViews.size();
		for (int i = 0; i < s; i++) {
			textViews.get(i).setTextSize(AbViewUtil.px2sp(context, (int) (textSize * ScreenUtils.getBaseSizeOn720(context))));
		}
	}
}
