package com.turing.musicplayer.broad;

import com.turing.musicplayer.manager.MusicManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MusicBroadCastReceiver extends BroadcastReceiver {
	public final static String MUSIC_BROADCAST_ACTION = "android.intent.action.MusicBroadCastReceiver";
	private static final String TAG = "MusicBroadCastReceiver";

	private MusicManager mMusicManagerInstance;
	/**
	 * 设置当前的音乐管理者
	 * 
	 * @param mMusicManagerInstance
	 */
	public void setmMusicManagerInstance(MusicManager mMusicManagerInstance) {
		this.mMusicManagerInstance = mMusicManagerInstance;
	}

	// private String[] mMusicState = {"随机播放","全部循环","单曲循环","循环列表"};
	@Override
	public void onReceive(Context context, Intent intent) {
		// 自动进入下一曲
		mMusicManagerInstance.nextPlay(true);	
	}

}
