package com.turing.musicplayer.service;

import java.io.IOException;

import com.turing.musicplayer.R;
import com.turing.musicplayer.broad.MusicBroadCastReceiver;
import com.turing.musicplayer.interfaces.MusicConstantValue;
import com.turing.musicplayer.manager.MusicManager;
import com.turing.musicplayer.util.Constants;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
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

	private MusicBroadCastReceiver mMusicBroadCastReceiver;
	/**
	 * 播放音乐记住当前的位置
	 */
	private int currentPosition = 0;

	private String mUrl;

	@Override
	public IBinder onBind(Intent intent) {
		
		Log.d(TAG, "onBind .." );
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate .." );
		mMusicBroadCastReceiver = new MusicBroadCastReceiver();
		registerBroadCast();
	    
		if (mediaPlayer == null) {
			mediaPlayer = new MediaPlayer();
//			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//			mediaPlayer.setLooping(false);
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
				int option = bundle.getInt("option", -1);
				
				switch (option) {
				case MusicConstantValue.OPTION_PLAY:
					mUrl = bundle.getString("url");
					play(mUrl);
					MusicManager.PLAY_STATE = option;
					break;
				case MusicConstantValue.OPTION_PAUSE:	
					pause();
					MusicManager.PLAY_STATE = option;
					break;
				case MusicConstantValue.OPTION_CONTINUE:
					if (!TextUtils.isEmpty(mUrl)) {
						mUrl = bundle.getString("url");
						play(mUrl);
					}
					cuntinue();
					MusicManager.PLAY_STATE = option;
					break;
				}
				
			}
		}
	}
	
	
	
	/**
	 * 继续播放
	 */
	private void cuntinue() {
		playerToPosiztion(currentPosition);
	}
	
	private void playerToPosiztion(int posiztion) {
		if (posiztion > 0 && posiztion < mediaPlayer.getDuration()) {
			mediaPlayer.seekTo(posiztion);
		}		
	}

	/**
	 * 开始播放
	 * @param url
	 */
	private void play(String url) {
		if (mediaPlayer == null) {
			mediaPlayer = new MediaPlayer();
		}
			try {
				mediaPlayer.reset();
				mediaPlayer.setDataSource(url);
				mediaPlayer.prepare();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		if (!mediaPlayer.isPlaying()) {
			mediaPlayer.start();
		}	
	}
	/**
	 * 暂停播放
	 */
	private void pause() {
		currentPosition = mediaPlayer.getCurrentPosition();
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

	@Override
	public void onDestroy() {
		super.onDestroy();
		unRegisterBroadCast(); //取消注册
	}
	
	
	/**
	 * 注册广播
	 */
	private void registerBroadCast() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(MusicBroadCastReceiver.MUSIC_BROADCAST_ACTION);  
	    this.registerReceiver(mMusicBroadCastReceiver, intentFilter);
	}
	
	/**
	 * 取消注册广播
	 */
	private void unRegisterBroadCast() {
		unregisterReceiver(mMusicBroadCastReceiver);
	}
	
	

	
	

}
