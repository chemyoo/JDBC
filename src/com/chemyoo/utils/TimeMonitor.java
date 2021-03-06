package com.chemyoo.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
  * @author jianqing.liu
 * @version 2018/5/25 11:51:11 AM
 * @since 2018/5/25 11:51:11 AM
 * @description none.
 */
public class TimeMonitor {
	private CountDownLatch countDownLatch = new CountDownLatch(1);

	public void timeStart(String name) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					Date date = Calendar.getInstance().getTime();
					long start = date.getTime();
					countDownLatch.await();
					date = Calendar.getInstance().getTime();
					long speed = date.getTime() - start;
					String ms = ("00" + (speed - (speed / (1000) * 1000)));
					String timespeed = speed / (1000) + "." + ms.substring(ms.length() - 3) + "s";
					LoggerUtils.logInfo(getClass(), this.getName().trim() + " : " + timespeed);
				} catch (InterruptedException e) {
					LoggerUtils.logError(getClass(), e.getMessage(), e);
					Thread.currentThread().interrupt();
				}
			}
		};
		thread.setName("timeMonitor-" + name);
		thread.start();
	}

	public void timeEnd() {
		countDownLatch.countDown();
	}
}