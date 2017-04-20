package utils.app.com.commonutlis.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

/**
 * 
 * @author guanghui.han
 * @2015-11-28下午1:02:30
 * @
 * 
 *           富文本工具类
 */
public class SpannableuUtills {

	/**
	 * 人民币符号
	 */
	public static final String RMB = "¥";

	/**
	 * 富文本 两段
	 */
	public static void setSpannableu(TextView mTextView, String one, String two) {
		int len1 = one.length();
		String curr = one + two;
		SpannableString mSpannableString = new SpannableString(curr);
		int len = mSpannableString.length();
		// 设置字体大小（绝对值,单位：像素）
		mSpannableString.setSpan(new RelativeSizeSpan(1.4f), 0, len1,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		mSpannableString.setSpan(new RelativeSizeSpan(0.9f), len1, len,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 第二个参数boolean
		// dip，如果为true，表示前面的字体大小单位为dip，否则为像素，同上。
		mTextView.setText(mSpannableString);
	}

	/**
	 * 富文本 三段
	 */
	public static void setSpannableu(TextView mTextView, String one,
			String two, String three) {
		int len1 = one.length();
		int len2 = two.length();
		String curr = one + two + three;
		SpannableString mSpannableString = new SpannableString(curr);
		int len = mSpannableString.length();
		// 设置字体大小（绝对值,单位：像素）
		mSpannableString.setSpan(new RelativeSizeSpan(0.9f), 0, len1,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);// 第一个参数boolean
		mSpannableString.setSpan(new RelativeSizeSpan(1.4f), len1, len1 + len2,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 第二个参数boolean
		mSpannableString.setSpan(new RelativeSizeSpan(0.9f), len1 + len2, len,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 第三个参数boolean
		// dip，如果为true，表示前面的字体大小单位为dip，否则为像素，同上。
		mTextView.setText(mSpannableString);
	}
}
