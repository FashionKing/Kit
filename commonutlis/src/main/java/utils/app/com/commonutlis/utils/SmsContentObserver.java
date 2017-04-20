package utils.app.com.commonutlis.utils;

import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 短信观察者 自动获取短信验证码
 */
public class SmsContentObserver extends ContentObserver {

	public static final String SMS_URI_INBOX = "content://sms/inbox";
	private Activity activity = null;
	private String code = "";
	private EditText verifyCode = null;

	public SmsContentObserver(Activity activity, Handler handler,
			EditText editText) {
		super(handler);
		this.activity = activity;
		this.verifyCode = editText;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);

		// 读取收件箱中指定号码的短信
		/*
		 * cursor = activity.managedQuery(Uri.parse(SMS_URI_INBOX), new String[]
		 * { "_id", "address", "body", "read" }, "address=? and read=?", new
		 * String[] { "10690042229208", "0" }, "date desc");
		 */
		try {
			 getSms();
		} catch (Exception e) {
//			ContentUtils.showMsg(activity,
//					"请在系统设置-权限管理中，打开联璧老板娘的信读取权限，以获得自动填写验证码功能");
		}
	}

	/**
	 * 得到短信的内容
	 */
	public void getSms() {
		Cursor cursor = null;// 光标
		cursor = activity.managedQuery(Uri.parse(SMS_URI_INBOX),
				new String[] { "_id", "address", "body", "read" },
				"read=?", new String[] { "0" }, "date desc");

		if (cursor != null) {// 如果短信为未读模式
			cursor.moveToFirst();
			if (cursor.moveToFirst()) {
				String smsbody = cursor.getString(cursor
						.getColumnIndex("body"));
				Pattern pattern = Pattern.compile("(\\d{6})");
				Matcher matcher = pattern.matcher(smsbody);
				if (matcher.find()) {
					code = matcher.group(0);
				}
				verifyCode.setText(code);
			}
		}
	}
}