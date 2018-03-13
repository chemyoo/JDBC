package com.chemyoo.utils;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/** 
 * @author 作者 : jianqing.liu
 * @version 创建时间：2018年1月5日 上午11:36:50 
 * @since 2018年1月5日 上午11:36:50 
 * @description 类说明 
 */
public class TimeMonitor
{
	private CountDownLatch countDownLatch = new CountDownLatch(1);
	public  void timeSatrt(String name)
	{
			Thread thread = new Thread(){
				@Override
				public void run() {
					try {
						Date date = Calendar.getInstance().getTime();
						long start = date.getTime();
						countDownLatch.await();
						date = Calendar.getInstance().getTime();
						long speed = date.getTime()-start;
						String ms = ("00"+(speed-(speed/(1000)*1000)));
						String timespeed = speed/(1000)+"."+ms.substring(ms.length()-3)+"s";
						System.err.println(this.getName().trim()+" : "+timespeed);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			thread.setName("timeMonitor-"+name);
			thread.start();
	}
	
	public void timeEnd()
	{
		countDownLatch.countDown();
	}
}