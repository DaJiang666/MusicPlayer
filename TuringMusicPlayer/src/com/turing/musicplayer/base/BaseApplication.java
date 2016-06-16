package com.turing.musicplayer.base;

import com.turing.musicplayer.service.MusicService;
import com.turing.musicplayer.util.Constants;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import turing.os.app.TuringOsApplication;
/**
 *
 *	Author: ZhangDanJiang
 *
 *  Date:2016年6月15日  Time: 下午1:54:49
 *
 *  Function:  本工程的全局的Application 
 *  
 *  BaseApplication 继承于 TuringOsApplication
 *  实现 onEventReceived(int eventId, String eventData)方法
 *  通过此方法与OS系统进行通信，接收系统发来的JSON信息
 *
 */
public class BaseApplication extends TuringOsApplication {

	/** 当前类 TAG. 标示 */
	private static final String TAG = BaseApplication.class.getSimpleName();

	/** 当前类 DEBUG 开关 */
	private static final boolean DEBUG = Constants.DEBUG & true;
	/** 服务是否开启 */
	private boolean mIsStartService = false;

	@Override
	public void onEventReceived(int eventId, String eventData) {
		if (DEBUG) {
			Log.d(TAG, "onEventReceived .. eventId ==" + eventId + "eventData" + eventData);
		}
		// 判断应用是否为开启状态， 如果没有开启，开启音乐服务，并设置其状态为开启
		if (!mIsStartService) {
			startMusicService(eventId, eventData);
			mIsStartService = true;
		}
	}
	
	/**
	 * 开启音乐服务
	 * @param eventId 事件id
	 * @param eventData Json数据
	 */
	private void startMusicService(int eventId, String eventData) {
		Intent intent = new Intent(this.getApplicationContext(), MusicService.class);
		if (DEBUG) {
			Log.d(TAG, "startMusicService .. eventId ==" + eventId + "eventData" + eventData);
		}
		intent.putExtra("eventId", eventId);
		intent.putExtra("eventData", eventData);
		startService(intent);
	}

}
