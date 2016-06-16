package com.turing.musicplayer.service;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.turing.musicplayer.R;
import com.turing.musicplayer.model.EventDataBean;
import com.turing.musicplayer.util.Constants;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
/**
 *
 *	Author: ZhangDanJiang
 *
 *  Date:2016年6月14日  Time: 下午2:46:30
 *
 *  Function: 音乐服务
 *
 */
public class MusicService extends Service{
	/** 当前类 TAG. 标示 */
	private static final String TAG = MusicService.class.getSimpleName();

	/** 当前类 DEBUG 开关 */
	private static final boolean DEBUG = Constants.DEBUG & true;
	
	private MediaPlayer mediaPlayer;

	@Override
	public IBinder onBind(Intent intent) {
		
		Log.d(TAG, "onBind .." );
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate .." );
		if (mediaPlayer == null) {
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setLooping(false);
		}
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		Log.d(TAG, "onStart");
		if (intent != null) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
//				int eventId = bundle.getInt("eventId");
//				String eventData = bundle.getString("eventData");
				
//				EventDataBean parseObject = JSONObject.parseObject(eventData,EventDataBean.class);
//				
//				if (DEBUG) {
//					Log.d(TAG, "onStart .. eventId ==" + eventId + "eventData" + eventData+ "parseObject" + parseObject.toString());
//				}
//				if (eventId != 0) {
//					play();
//				}
				String state = bundle.getString("state");
				int function = bundle.getInt("function");
				if (function == 0) {
					 play();
				}
				
			}
		}
	}
	
	
	

	public void play() {
//		try {
//
//			mediaPlayer.reset();
//			mediaPlayer.setDataSource(musicList.get(curMusic).getUrl());
//			mediaPlayer.prepare();
//			
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (IllegalStateException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		
//		if (!mediaPlayer.isPlaying()) {
//			mediaPlayer.start();
//		}
	}

	public void pause() {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
		}
	}

	public void stop() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			try {
				mediaPlayer.prepare();	// 在调用stop后如果需要再次通过start进行播放,需要之前调用prepare函数
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	

}
