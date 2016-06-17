package com.turing.musicplayer;

import java.util.Collections;
import java.util.List;

import com.turing.musicplayer.adapter.MusicAdapter;
import com.turing.musicplayer.broad.MusicBroadCastReceiver;
import com.turing.musicplayer.interfaces.MusicConstantValue;
import com.turing.musicplayer.manager.MusicManager;
import com.turing.musicplayer.model.MusicBean;
import com.turing.musicplayer.service.MusicService;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 *
 *	Author: ZhangDanJiang
 *
 *  Date:2016年6月16日  Time: 上午11:04:10
 *
 *  Function: 主应用启动界面
 *
 */
public class MainActivity extends Activity implements OnClickListener{
	
	
	/** 开始 */
	private TextView mStart;
	/** 下一曲 */
	private TextView mNext;
	/** 暂停 */
	private TextView mPause;
	/** 继续 */
	private TextView mContinue;
	
	/** 音乐ListView列表 */
	private ListView mListView;
	/** 音乐适配器 */
	private MusicAdapter mMusicAdapter;
	private List<MusicBean> nMusicList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
		initSetListener();
		initData();
	}
	
	/**
	 * 出事话控件
	 */
	private void initView() {
		mStart = (TextView) findViewById(R.id.start);   //开始
		mNext = (TextView) findViewById(R.id.next);     //下一曲
		mPause = (TextView) findViewById(R.id.pause);     //暂停
		mContinue = (TextView) findViewById(R.id.tv_continue);     //暂停
		
		mListView = (ListView) findViewById(R.id.listview);     //ListView
	}

	/**
	 * 对控件注册点击事件
	 */
	private void initSetListener() {
		mStart.setOnClickListener(this);               //开始 
		mNext.setOnClickListener(this);                //下一曲
		mPause.setOnClickListener(this);                //下一曲
		mContinue.setOnClickListener(this);                //下一曲
	}
	
	/**
	 * 初始化数据
	 */
	private void initData() {
		mMusicAdapter = new MusicAdapter(getApplicationContext()); 
		mListView.setAdapter(mMusicAdapter);
		nMusicList = MusicManager.getInstance(getApplicationContext()).loadAllMusic();
		if (nMusicList != null && nMusicList.size() > 0) {
			mMusicAdapter.setList(nMusicList);
		}else{
			Toast.makeText(getApplicationContext(), "歌曲个数" + nMusicList.size(), 0).show();
		}
		
	}

	/**
	 * 
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start:               // 播放
			startPlay();
			break;
		case R.id.next:                // 下一首
			nextPlay();
			break;
		case R.id.pause:               // 暂停
			pusePlay();
			break;
		case R.id.tv_continue:               // 暂停
			continuePlay();
			break;
		default:
			break;
		}
	}
	/**
	 * 继续播放
	 */
	private void continuePlay() {
		choiceAndStartPlay(MusicConstantValue.OPTION_CONTINUE);
	}

	/**
	 * 开始播放
	 */
	private void startPlay() {
		// OPTION_PLAY 播放状态
		choiceAndStartPlay(MusicConstantValue.OPTION_PLAY);
	}

	/**
	 * 暂停播放
	 */
	private void pusePlay() {
		// OPTION_PAUSE 设置暂停状态
		startMusicService(null, MusicConstantValue.OPTION_PAUSE);
	}

	/**
	 * 播放下一首
	 */
	private void nextPlay() {
		MusicManager.CURRENTPOS++;  // 全局播放状态加1
		choiceAndStartPlay(MusicConstantValue.OPTION_PLAY);
	}
	
	/**
	 * 选择播放的音乐  选择其中的一个
	 */
	private void choiceAndStartPlay(int option) {
		if (nMusicList != null && nMusicList.size() > MusicManager.CURRENTPOS) {
			MusicBean musicBean = nMusicList.get(MusicManager.CURRENTPOS);
			String url = musicBean.getUrl();
			startMusicService(url, option);
		}
	}
	
	
	private void startMusicService(String url, int option) {
		Intent intent = new Intent(this, MusicService.class);
		if (url != null) {
			intent.putExtra("url", url);
		}
		intent.putExtra("option", option);
		startService(intent);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	private void startPlayService(Music music, int option) {
//		Intent intent = new Intent(getApplicationContext(), MediaService.class);
//		if (music != null) {
//			intent.putExtra("file", music.getPath());
//		}
//		intent.putExtra("option", option);
//		startService(intent);
//	}
	
//	/**
//	 * 开始随机播放
//	 */
//	private void startPlay() {
//		Intent intent = new Intent();
//		intent.setAction(MusicBroadCastReceiver.MUSIC_BROADCAST_ACTION);
//		intent.putExtra("state", "next");
//		sendBroadcast(intent);
//	}

	
	
	
}
