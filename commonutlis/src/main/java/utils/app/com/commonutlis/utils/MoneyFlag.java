package utils.app.com.commonutlis.utils;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

public class MoneyFlag {
	public static final String MONEYFAAG = "¥";

	/**
	 * 
	 * @param context
	 * @param view
	 */
	public static void addMoneyFlag(Context context, TextView view) {
		TextView tv = new TextView(context);
		tv.setTextColor(Color.RED);
		char symbol = 165;
		tv.setText(String.valueOf(symbol));
		view.setText(tv.getText().toString() + " " + view.getText().toString());
	}

}
