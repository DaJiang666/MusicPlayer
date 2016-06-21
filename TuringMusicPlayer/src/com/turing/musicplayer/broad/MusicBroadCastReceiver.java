package com.turing.musicplayer.broad;

import com.turing.musicplayer.manager.MusicManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
/**
 *
 *	Author: ZhangDanJiang
 *
 *  Date:2016年6月21日  Time: 下午4:38:55
 *
 *  Function:  音乐播放器的广播接受者 用于接收音乐服务在歌曲播放完成时 收到的广播
 *
 */
public class MusicBroadCastReceiver extends BroadcastReceiver {
	/** 广播的 Action*/
	public final static String MUSIC_BROADCAST_ACTION = "android.intent.action.MusicBroadCastReceiver";
	/** TAG */
	private static final String TAG = "MusicBroadCastReceiver";
	/*** 音乐管理者  */
	private MusicManager mMusicManagerInstance;
	/**
	 * 设置当前的音乐管理者
	 * 
	 * @param mMusicManagerInstance  音乐管理者
	 */
	public void setmMusicManagerInstance(MusicManager mMusicManagerInstance) {
		this.mMusicManagerInstance = mMusicManagerInstance;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// 自动进入下一曲
		mMusicManagerInstance.nextPlay(true);	
	}

}
