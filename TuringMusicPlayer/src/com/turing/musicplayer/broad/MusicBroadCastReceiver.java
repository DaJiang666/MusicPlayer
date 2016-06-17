package com.turing.musicplayer.broad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MusicBroadCastReceiver extends BroadcastReceiver{
	public final static String MUSIC_BROADCAST_ACTION= "android.intent.action.MusicBroadCastReceiver";
	private static final String TAG = "MusicBroadCastReceiver";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String stringExtra = intent.getStringExtra("state");
		Log.d(TAG, stringExtra);
		if (stringExtra.equals("next")) {
			paly();
		}
	}
	
	public void paly(){
		
	}
	
}
