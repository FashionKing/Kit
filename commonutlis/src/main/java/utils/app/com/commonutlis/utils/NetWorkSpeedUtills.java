package utils.app.com.commonutlis.utils;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.Message;

public class NetWorkSpeedUtills {
	private static long lastTotalRxBytes = 0;
	private static long lastTimeStamp = 0;

	private static long getTotalRxBytes(Context ct) {
		return TrafficStats.getUidRxBytes(ct.getApplicationInfo().uid) == TrafficStats.UNSUPPORTED ? 0
				: (TrafficStats.getTotalRxBytes() / 1024);// 转为KB
	}

	public static void startWork(final Handler mHandler, final Context ct) {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				showNetSpeed(mHandler, ct);
			}
		};
		lastTotalRxBytes = getTotalRxBytes(ct);
		lastTimeStamp = System.currentTimeMillis();
		new Timer().schedule(task, 1000, 2000); // 1s后启动任务，每2s执行一次
	}

	private static void showNetSpeed(Handler mHandler, Context ct) {

		long nowTotalRxBytes = getTotalRxBytes(ct);
		long nowTimeStamp = System.currentTimeMillis();
		long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));// 毫秒转换

		lastTimeStamp = nowTimeStamp;
		lastTotalRxBytes = nowTotalRxBytes;

		Message msg = mHandler.obtainMessage();
		msg.what = 100;
		msg.obj = String.valueOf(speed) + " kb/s";
		mHandler.sendMessage(msg);// 更新界面
	}

}
