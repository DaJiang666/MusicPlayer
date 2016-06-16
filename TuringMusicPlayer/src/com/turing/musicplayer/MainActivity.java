package com.turing.musicplayer;

import java.util.Collections;
import java.util.List;

import com.turing.musicplayer.adapter.MusicAdapter;
import com.turing.musicplayer.manager.MusicManager;
import com.turing.musicplayer.model.MusicBean;
import com.turing.musicplayer.service.MusicService;

import android.app.Activity;
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
	
	/** 音乐ListView列表 */
	private ListView mListView;
	/** 音乐适配器 */
	private MusicAdapter mMusicAdapter;

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
		
		
		mListView = (ListView) findViewById(R.id.listview);     //下一曲
	}

	/**
	 * 对控件注册点击事件
	 */
	private void initSetListener() {
		mStart.setOnClickListener(this);               //开始 
		mNext.setOnClickListener(this);                //下一曲
	}
	
	/**
	 * 初始化数据
	 */
	private void initData() {
		mMusicAdapter = new MusicAdapter(getApplicationContext()); 
		mListView.setAdapter(mMusicAdapter);
		//加载所有的音乐
		List<MusicBean> nMusicList = MusicManager.getInstance(getApplicationContext()).loadAllMusic();
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
		case R.id.start:
			// 开启服务播放
			startPlay();
			break;

		default:
			break;
		}
	}
	
	/**
	 * 开始随机播放
	 */
	private void startPlay() {
		Intent intent = new Intent(this, MusicService.class);
		intent.putExtra("state", "next");
		intent.putExtra("function", 0);
		startService(intent);
	}
}
