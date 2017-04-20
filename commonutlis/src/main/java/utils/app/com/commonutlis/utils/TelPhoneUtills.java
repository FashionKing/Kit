package utils.app.com.commonutlis.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class TelPhoneUtills {
	public static void launchPhone(Context context, String mobile) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.DIAL");
		intent.setData(Uri.parse("tel:" + mobile));
		context.startActivity(intent);
	}
}
