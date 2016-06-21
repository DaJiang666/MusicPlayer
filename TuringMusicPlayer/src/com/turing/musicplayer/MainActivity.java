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
import android.content.IntentFilter;
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
	/** 停止 */
	private TextView mStop;
	
	/** 音乐ListView */
	private ListView mListView;
	/** 音乐适配器 */
	private MusicAdapter mMusicAdapter;
	/** 音乐列表 */
	private List<MusicBean> nMusicList;
	/** 音乐管理者 */
	private MusicManager mMusicManagerInstance;
	/** 根据歌曲 搜索按钮 */
	private Button mSearchButtonByTitle;
	/** 根据歌手 搜索按钮 */
	private Button mSearchButtonByArtist;
	/** 歌手和歌曲 搜索按钮 */
	private Button mSearchButtonAll;
	/** 播放模式 */
	private Button mPalyModeButton;
	/** 歌曲 搜索文本 */
	private EditText mSearchEditTextTitle;
	/** 歌手 搜索文本 */
	private EditText mSearchEditTextArt;
	/** 音乐播放器的广播接受者 用于接收音乐服务在歌曲播放完成时 收到的广播*/
	private MusicBroadCastReceiver mMusicBroadCastReceiver;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//初始化视图
		initView();
		// 为按钮注册点击事件
		initSetListener();
		// 初始化数据
		initData();
	}
	
	/**
	 * 初始化控件
	 */
	private void initView() {
		mStart = (TextView) findViewById(R.id.start);                                      //开始
		mPrevious = (TextView) findViewById(R.id.previous);                                //上一曲
		mNext = (TextView) findViewById(R.id.next);                                        //下一曲
		mPause = (TextView) findViewById(R.id.pause);                                      //暂停
		mContinue = (TextView) findViewById(R.id.tv_continue);                             //继续
		mStop = (TextView) findViewById(R.id.stop);                             //继续
		
		mSearchEditTextTitle = (EditText) findViewById(R.id.et_search);                    //搜索歌曲编辑输入框
		mSearchEditTextArt = (EditText) findViewById(R.id.et_search_art);                  //搜索歌手编辑输入框
		
		mSearchButtonByTitle = (Button) findViewById(R.id.bt_search_title);                 //搜索 歌曲
		mSearchButtonByArtist = (Button) findViewById(R.id.bt_search_art);                  //搜索 歌手
		mSearchButtonAll = (Button) findViewById(R.id.bt_search_all);                       //搜索 歌手 歌曲
		
		mPalyModeButton = (Button) findViewById(R.id.play_mode);                             // 播放模式
		
		mListView = (ListView) findViewById(R.id.listview);                                  //ListView
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
		mStop.setOnClickListener(this);
		
		
		// 搜索
		mSearchButtonByTitle.setOnClickListener(this);
		mSearchButtonByArtist.setOnClickListener(this);
		mSearchButtonAll.setOnClickListener(this);
		mPalyModeButton.setOnClickListener(this);
		
	}
	
	/**
	 * 初始化数据
	 */
	private void initData() {
		// 音乐管理者的单例
		mMusicManagerInstance = MusicManager.getInstance(getApplicationContext());
		mMusicBroadCastReceiver = new MusicBroadCastReceiver();
		// 将当前的音乐管理者注入 当前的音乐观察者
		mMusicBroadCastReceiver.setmMusicManagerInstance(mMusicManagerInstance);
		registerBroadCast();
		//初始化 MusicManager 
		mMusicAdapter = new MusicAdapter(getApplicationContext()); 
		mListView.setAdapter(mMusicAdapter);
		// Adapter初始化
		initAdapterData();
		
	}
	
	/**
	 * 为Adapter添加数据
	 */
	private void initAdapterData() {
		// 获取当前的播放列表
		nMusicList = mMusicManagerInstance.getmLoadCurrentMusic();
		if (nMusicList != null && nMusicList.size() > 0) {
			// 为Adapter填充数据
			mMusicAdapter.setList(nMusicList);
		}else{
			Toast.makeText(getApplicationContext(), "歌曲个数" + nMusicList.size(), 0).show();
		}
	}

	/**
	 * 点击事件判断
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 播放
		case R.id.start:                  
			startPlay();
			break;
		// 上一首
		case R.id.previous:               
			prePlay();
			break;
		// 下一首	
		case R.id.next:                
			nextPlay();
			break;
		// 暂停
		case R.id.pause:            
			pusePlay();
			break;
		// 继续
		case R.id.tv_continue:               
			continuePlay();
			break;
			// 继续
		case R.id.stop:               
			stopPlay();
			break;
		// 搜索 歌曲
		case R.id.bt_search_title:               
			searchPlayTitle();
			break;
		// 搜索 歌手
		case R.id.bt_search_art:               
			searchPlayArt();
			break;
		// 搜索 歌曲 歌手一起搜
		case R.id.bt_search_all:               
			searchPlayAll();
			break;
		// 设置播放模式
		case R.id.play_mode:               
			selectCurrentMode();
			break;
		default:
			break;
		}
	}
	/**
	 * 停止播放
	 */
	private void stopPlay() {
		mMusicManagerInstance.stopPaly();
	}

	/**
	 * 选择当前的模式
	 */
	private void selectCurrentMode() {
		MusicManager.mPalyMode ++ ;
		if (MusicManager.mPalyMode >= MusicManager.mMusicState.length) {
			MusicManager.mPalyMode = 0;
		}
		switch (MusicManager.mPalyMode) {
		case 0:
			// 设置为列表循环
			mPalyModeButton.setText(MusicManager.mMusicState[0]);
			break;
		case 1:
			// 设置为单曲循环
			mPalyModeButton.setText(MusicManager.mMusicState[1]);
			break;
		default:
			break;
		}
	}

	/**
	 * 根据歌手 和 歌名搜索
	 */
	private void searchPlayAll() {
		String title = getSearchEditText(mSearchEditTextTitle);
		String art = getSearchEditText(mSearchEditTextArt);
		List<MusicBean> list = mMusicManagerInstance.searchMusicWithTitleAndArtist(title, art);
		updateList(list);
	}

	/**
	 * 根据歌曲名字搜索
	 */
	private void searchPlayTitle() {
		String title = getSearchEditText(mSearchEditTextTitle);
		List<MusicBean> list = mMusicManagerInstance.searchMusicWithTitle(title);
		updateList(list);
	}
	/**
	 * 根据歌手搜索
	 */
	private void searchPlayArt() {
		String art = getSearchEditText(mSearchEditTextArt);
		List<MusicBean> list = mMusicManagerInstance.searchMusicWithArtist(art);
		updateList(list);
	}
	
	/**
	 * 更新列表
	 * @param list
	 */
	private void updateList(List<MusicBean> list) {
		if (list == null  || list.size() == 0) {
			Toast.makeText(getApplicationContext(), "列表为空", 0).show();
			// 当超过两首以上的歌曲时 更新列表
		}else if (list.size() >= 2) {
			nMusicList = list;
			mMusicAdapter.setList(list);
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
	 * 获取 搜索文本 的数据
	 * @return 处理过的文本
	 */
	private String getSearchEditText(EditText view) {
		return view.getText().toString().trim();
	}
	
	/**
	 * 注册广播
	 */
	private void registerBroadCast() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(MusicBroadCastReceiver.MUSIC_BROADCAST_ACTION);  
	    this.registerReceiver(mMusicBroadCastReceiver, intentFilter);
	}
	

	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 取消注册广播
		unregisterReceiver(mMusicBroadCastReceiver);
	}
	
}
