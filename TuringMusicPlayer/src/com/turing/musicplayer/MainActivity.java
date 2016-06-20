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
import android.preference.EditTextPreference;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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
	/** 上一曲 */
	private TextView mPrevious;
	/** 下一曲 */
	private TextView mNext;
	/** 暂停 */
	private TextView mPause;
	/** 继续 */
	private TextView mContinue;
	
	/** 音乐ListView */
	private ListView mListView;
	/** 音乐适配器 */
	private MusicAdapter mMusicAdapter;
	/** 音乐列表 */
	private List<MusicBean> nMusicList;
	/** 音乐管理者 */
	private MusicManager mMusicManagerInstance;
	/** 搜索按钮 */
	private Button mSearchButton;
	/** 搜索文本 */
	private EditText mSearchEditText;

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
		mStart = (TextView) findViewById(R.id.start);           //开始
		mPrevious = (TextView) findViewById(R.id.previous);     //上一曲
		mNext = (TextView) findViewById(R.id.next);             //下一曲
		mPause = (TextView) findViewById(R.id.pause);           //暂停
		mContinue = (TextView) findViewById(R.id.tv_continue);  //继续
		
		mSearchEditText = (EditText) findViewById(R.id.et_search);  //搜索
		mSearchButton = (Button) findViewById(R.id.bt_search);  //搜索
		
		mListView = (ListView) findViewById(R.id.listview);     //ListView
	}

	/**
	 * 对控件注册点击事件
	 */
	private void initSetListener() {
		 // 开始
		mStart.setOnClickListener(this);
		 // 上一曲
		mPrevious.setOnClickListener(this);
		// 下一曲
		mNext.setOnClickListener(this); 
		// 暂停
		mPause.setOnClickListener(this); 
		 // 继续
		mContinue.setOnClickListener(this);
		// 搜索
		mSearchButton.setOnClickListener(this);
	}
	
	/**
	 * 初始化数据
	 */
	private void initData() {
		//初始化 MusicManager 
		mMusicManagerInstance = MusicManager.getInstance(getApplicationContext());
		
		mMusicAdapter = new MusicAdapter(getApplicationContext()); 
		mListView.setAdapter(mMusicAdapter);
		initAdapterData();
		
	}
	
	/**
	 * 为Adapter添加数据
	 */
	private void initAdapterData() {
		nMusicList = mMusicManagerInstance.getmLoadAllMusic();
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
		case R.id.previous:                // 上一首
			prePlay();
			break;
		case R.id.next:                // 下一首
			nextPlay();
			break;
		case R.id.pause:               // 暂停
			pusePlay();
			break;
		case R.id.tv_continue:               // 继续
			continuePlay();
			break;
		case R.id.bt_search:               // 搜索
			searchPlay();
			break;
		default:
			break;
		}
	}
	/**
	 * 根据歌曲名字搜索
	 */
	private void searchPlay() {
		String searchEditText = getSearchEditText();
		List<MusicBean> list = mMusicManagerInstance.searchMusicWithTitle(searchEditText);
		if (list != null  && list.size()>0) {
			mMusicAdapter.setList(list);
		}else{
			Toast.makeText(getApplicationContext(), "列表为空", 0).show();
		}
	}

	/**
	 * 开始播放
	 */
	private void startPlay() {
		// OPTION_PLAY 播放状态
		mMusicManagerInstance.startPlay();
	}

	/**
	 * 暂停播放
	 */
	private void pusePlay() {
		// OPTION_PAUSE 设置暂停状态
		mMusicManagerInstance.pusePlay();
	}
	
	/**
	 * 继续播放
	 */
	private void continuePlay() {
		mMusicManagerInstance.continuePlay();
	}
	
	/**
	 * 上一首
	 */
	private void prePlay() {
		//大于0
		mMusicManagerInstance.previousPlay();
	}
	
	/**
	 * 播放下一首
	 */
	private void nextPlay() {
		mMusicManagerInstance.nextPlay();
	}

	/**
	 * 获取搜索文本数据
	 * @return
	 */
	private String getSearchEditText() {
		return mSearchEditText.getText().toString().trim();
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
