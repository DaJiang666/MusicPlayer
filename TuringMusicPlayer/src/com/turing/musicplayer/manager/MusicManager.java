package com.turing.musicplayer.manager;

import java.util.ArrayList;
import java.util.List;

import com.turing.musicplayer.interfaces.MusicConstantValue;
import com.turing.musicplayer.model.MusicBean;
import com.turing.musicplayer.util.Constants;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.provider.MediaStore;

/**
 *
 * Author: ZhangDanJiang
 *
 * Date:2016年6月16日 Time: 下午2:12:43
 *
 * Function: 音乐的管理者
 *
 */
public final class MusicManager {
	
	/** debug开关 */
	private static final boolean DEBUG = true & Constants.DEBUG;
	/** TAG */
	private static final String TAG = "MusicManager";
	/** 当前播放的位置  */
	public static int CURRENTPOS = 0;
	/** 当前音乐播放的状态  */
	public static int PLAYSTATE = MusicConstantValue.OPTION_PAUSE;
	/** 单例 */
	private static MusicManager instance = null;
	/** Context */
	private Context mContext;

	/**
	 * 私有构造
	 */
	private MusicManager(Context context) {
		mContext = context;
	}

	/**
	 * 单例方法
	 * 
	 * @return {@link MusicManager}
	 */
	public static MusicManager getInstance(Context context) {
		if (instance == null) {
			synchronized (MusicManager.class) {
				if (instance == null) {
					instance = new MusicManager(context.getApplicationContext());
				}
			}
		}
		return instance;
	}

	/**
	 * 加载所有的音乐
	 * @return  List<MusicBean> 音乐列表 
	 */
	public List<MusicBean> loadAllMusic() {
		ContentResolver resolver = mContext.getContentResolver();
		Cursor cursor = null;
		try {
			// 获取游标
			cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
			if (cursor != null) {
				int nTitle = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
				int nSize = cursor.getColumnIndex(MediaStore.Audio.Media.SIZE);
				int nData = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
				int nDuration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
				// 创建列表
				List<MusicBean> musicList = new ArrayList<>();
				while (cursor.moveToNext()) {
					long duration = cursor.getLong(nDuration); // 时长
					// 如果不符合要求直接 continue 减少对象的创建
					if (!isFit(duration)) {
						continue ;
					}
					String title = cursor.getString(nTitle); // 标题
					long size = cursor.getLong(nSize); // 大小
					String url = cursor.getString(nData); // url
					
					// 创建Music对象
					MusicBean musicRecord = new MusicBean();
					musicRecord.setName(title);
					musicRecord.setSize(size);
					musicRecord.setUrl(url);
					musicRecord.setDuration(duration);
					// 添加到列表中
					musicList.add(musicRecord);
				}
				cursor.close();
	            cursor = null;
				return musicList;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally { 
			try {
				if (cursor != null) {
					cursor.close();
					cursor = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 音频的长度是否符合要求
	 * @param duration 时长
	 * @return
	 */
	private boolean isFit(long duration) {
		return duration >= 1000 && duration <= 900000;
	}

	

}
