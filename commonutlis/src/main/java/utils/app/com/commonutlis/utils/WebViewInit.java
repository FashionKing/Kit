package utils.app.com.commonutlis.utils;

import android.app.Activity;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;

/**
 * WebView Cookie WebSetting
 */
public class WebViewInit {

	/**
	 * 初始化
	 * 
	 * @param web_view
	 * @param activity
	 * @return
	 */

	public static WebView WebSettingInit(WebView web_view,
			final Activity activity) {
		WebSettings ws = web_view.getSettings();
		ws.setJavaScriptEnabled(true); // 支持js
		ws.setBuiltInZoomControls(false);// 支持缩放按钮
		ws.setUseWideViewPort(true);// 设置此属性，可任意比例缩放 将图片调整到适合webview的大小
		ws.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
		ws.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		ws.setSupportZoom(false); // 支持缩放
		// 设置 缓存模式
		ws.setCacheMode(WebSettings.LOAD_NO_CACHE); // 关闭webview中缓存

		web_view.clearCache(true);
		web_view.setTag(true);
		// 开启 DOM storage API 功能
		ws.setDomStorageEnabled(true);

		ws.setRenderPriority(RenderPriority.HIGH);
		// 开启 database storage API 功能
		ws.setDatabaseEnabled(false);

		web_view.setWebChromeClient(new WebChromeClient() {

			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {
				return super.onJsAlert(view, url, message, result);
			}

		});
		return web_view;
	}
}
