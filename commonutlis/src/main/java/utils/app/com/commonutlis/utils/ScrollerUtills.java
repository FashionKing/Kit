package utils.app.com.commonutlis.utils;

import android.os.Handler;
import android.widget.ScrollView;

public class ScrollerUtills {
	private static final long TIME = 10;

	/**
	 * 设置滚动到底部
	 */
	public static void scrollerdown(final ScrollView scroll) {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				scroll.fullScroll(ScrollView.FOCUS_DOWN);

			}
		}, TIME);
	}

	/**
	 * 设置滚动到顶部
	 */
	public static void scrollerup(final ScrollView scroll) {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				scroll.fullScroll(ScrollView.FOCUS_UP);

			}
		}, TIME);
	}
}
