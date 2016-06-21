package com.turing.musicplayer.manager;

import java.util.ArrayList;
import java.util.List;

import com.turing.musicplayer.interfaces.MusicConstantValue;
import com.turing.musicplayer.model.MusicBean;
import com.turing.musicplayer.service.MusicService;
import com.turing.musicplayer.util.Constants;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.CallLog;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

/**
 *
 * Author: ZhangDanJiang
 *
 * Date:2016年6月16日 Time: 下午2:12:43
 *
 * Function: 音乐的管理者 控制音乐的播放 暂停 继续 上一曲 下一曲 和 搜素
 */
public final class MusicManager {

	/** debug开关 */
	private static final boolean DEBUG = true & Constants.DEBUG;
	/** TAG */
	private static final String TAG = "MusicManager";
	/** 当前播放的位置 */
	public static int CURRENT_POSITION = 0;
	/** 当前音乐播放的状态 */
	public static int PLAY_STATE = MusicConstantValue.OPTION_PAUSE;
	/** 单例 */
	private static MusicManager instance = null;
	/** Context */
	private Context mContext;
	/** 音乐总列表 */
	private List<MusicBean> mLoadAllMusic;
	/** 音乐当前列表 */
	private List<MusicBean> mLoadCurrentMusic;

	public static int mPalyMode = 0;

	public static String[] mMusicState = { "全部循环", "单曲循环" };

	/**
	 * 私有构造
	 */
	private MusicManager(Context context) {
		mContext = context;
		mLoadAllMusic = loadAllMusic();
		mLoadCurrentMusic = mLoadAllMusic;
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
	 * 
	 * @return List<MusicBean> 音乐列表
	 */
	private List<MusicBean> loadAllMusic() {
		// 无条件 及查询所有
		String selection = null;
		// ？ 与值一一对应
		String[] selectionArgs = null;
		return queryMusicWithCondition(selection, selectionArgs);
	}

	/**
	 * 音频的长度是否符合要求
	 * 
	 * @param duration
	 *            时长
	 * @return
	 */
	private boolean isFit(long duration) {
		return duration >= 1000 && duration <= 900000;
	}

	/**
	 * 开始播放
	 */
	public void startPlay() {
		// OPTION_PLAY 播放状态
		choiceAndStartPlay(MusicConstantValue.OPTION_PLAY);
	}

	/**
	 * 暂停播放
	 */
	public void pusePlay() {
		// OPTION_PAUSE 设置暂停状态
		startMusicService(null, MusicConstantValue.OPTION_PAUSE);
	}

	/**
	 * 继续播放
	 */
	public void continuePlay() {
		// OPTION_CONTINUE 继续播放状态
		choiceAndStartPlay(MusicConstantValue.OPTION_CONTINUE);
	}
	/**
	 * 停止播放
	 */
	public void stopPaly(){
		choiceAndStartPlay(MusicConstantValue.OPTION_STOP);
	}

	/**
	 * 上一首
	 */
	public void previousPlay() {
		// 循环列表
		if (CURRENT_POSITION > 0) {
			CURRENT_POSITION--; // 全局播放状态加1
		} else {
			List<MusicBean> nMusicList = loadAllMusic();
			if (nMusicList != null && nMusicList.size() > 0) {
				CURRENT_POSITION = nMusicList.size();
			}
		}
		choiceAndStartPlay(MusicConstantValue.OPTION_PLAY);
	}

	/**
	 * 播放下一首
	 */
	public void nextPlay() {
		nextPlay(false);
	}

	/**
	 * 
	 * @param flag
	 *            是否 自动下一曲 是就播放当前 如果是 主动执行则 换下一曲 按列表循环加1
	 * 
	 */
	public void nextPlay(boolean flag) {
		switch (mPalyMode) {
		case 0:
			// 全部循环
			allLooperPlay();
			break;
		case 1:
			// 单曲循环 这个是主动点击下一曲 就按主动播放 播放完成时 才按自动跳转时 为单曲循环
			if (flag) {
				singleLooperPlay();
			} else {
				// 主动换一首 则继续 按列表循环
				allLooperPlay();
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 选择播放的音乐 选择其中的一个 MusicManager.CURRENTPOS 当前的位置
	 */
	private void choiceAndStartPlay(int option) {
		if (mLoadCurrentMusic != null && mLoadCurrentMusic.size() > CURRENT_POSITION) {
			MusicBean musicBean = mLoadCurrentMusic.get(CURRENT_POSITION);
			if (musicBean != null) {
				if (DEBUG) {
					Log.d(TAG, "musicBean==" + musicBean.toString());
				}
				String url = musicBean.getUrl();
				startMusicService(url, option);
			}
		}
	}

	/**
	 * 选择播放的音乐 选择其中的一个 MusicManager.CURRENTPOS 当前的位置
	 */
	private void choiceAndStartPlay(MusicBean musicBean, int option) {
		if (musicBean != null) {
			if (DEBUG) {
				Log.d(TAG, "musicBean==" + musicBean.toString());
			}
			String url = musicBean.getUrl();
			startMusicService(url, option);
		}
	}
	/**
	 * 根据播放地址 和播放状态开启服务
	 * @param url 音乐的本地地址
	 * @param option 音乐的播放状态  播放状态  暂停状态  
	 */
	private void startMusicService(String url, int option) {
		Intent intent = new Intent(mContext, MusicService.class);
		if (url != null) {
			intent.putExtra("url", url);
		}
		intent.putExtra("option", option);
		mContext.startService(intent);
	}

	/**
	 * 获取音乐总列表
	 * 
	 * @return
	 */
	public List<MusicBean> getmLoadAllMusic() {
		return mLoadAllMusic;
	}

	/**
	 * 获取音乐播放当前列表
	 * 
	 * @return
	 */
	public List<MusicBean> getmLoadCurrentMusic() {
		return mLoadCurrentMusic;
	}

	/**
	 * 设置音乐播放当前列表
	 * 
	 * @param mLoadCurrentMusic
	 */
	public void setmLoadCurrentMusic(List<MusicBean> mLoadCurrentMusic) {
		this.mLoadCurrentMusic = mLoadCurrentMusic;
	}

	/**
	 * 根据歌曲名字 进行搜索
	 * 
	 * @param text
	 *            搜索的歌曲名
	 * @return List<MusicBean> 音乐列表
	 */
	public List<MusicBean> searchMusicWithTitle(String title) {
		if (TextUtils.isEmpty(title)) {
			return null;
		}
		// 条件语句
		String selection = MediaStore.Audio.Media.TITLE + "=?";
		// ？ 与值一一对应
		String[] selectionArgs = { title };
		List<MusicBean> queryMusicWithCondition = queryMusicWithCondition(selection, selectionArgs);
		return handleList(queryMusicWithCondition);
	}

	/**
	 * 根据歌手 艺术家 进行搜索
	 * 
	 * @param text
	 *            歌手 艺术家 名字
	 * @return List<MusicBean> 音乐列表
	 */
	public List<MusicBean> searchMusicWithArtist(String artist) {
		if (TextUtils.isEmpty(artist)) {
			return null;
		}
		// 条件语句
		String selection = MediaStore.Audio.Media.ARTIST + "=?";
		// ？ 与值一一对应
		String[] selectionArgs = { artist };
		List<MusicBean> queryMusicWithCondition = queryMusicWithCondition(selection, selectionArgs);

		return handleList(queryMusicWithCondition);
	}

	/**
	 * 对获取List音乐列表进行不同的处理
	 * 
	 * @param list 传入的音乐列表
	 * @return 当前音乐列表
	 */
	private List<MusicBean> handleList(List<MusicBean> list) {
		if (list != null) {
			// 如果列表不为空且 大于2 并且随机播放其中的一首
			if (list.size() >= 2) {
				mLoadCurrentMusic = list;
				randomPlay();
				// 如果 搜索到一首歌
				return mLoadCurrentMusic;
			} else if (list.size() == 1) {
				MusicBean musicBean = list.get(0);
				if (mLoadCurrentMusic.contains(musicBean)) {
					// 获取当前的位置
					int indexOf = mLoadCurrentMusic.indexOf(musicBean);
					CURRENT_POSITION = indexOf;
					// 播放当前歌曲
					choiceAndStartPlay(musicBean, MusicConstantValue.OPTION_PLAY);
				} else {
					Log.d(TAG, "没有这首歌");
				}
				// 返回 当前列表
				return mLoadCurrentMusic;
			} else {
				return list;
			}
		} else {
			return null;
		}

	}

	/**
	 * 根据 歌名和歌手 进行搜索
	 * 
	 * @param text
	 *            歌手 艺术家 名字
	 * @return List<MusicBean> 音乐列表
	 */
	public List<MusicBean> searchMusicWithTitleAndArtist(String title, String artist) {
		if (TextUtils.isEmpty(title) && !TextUtils.isEmpty(artist)) {
			return searchMusicWithArtist(artist);
		}
		if (!TextUtils.isEmpty(title) && TextUtils.isEmpty(artist)) {
			return searchMusicWithArtist(title);
		}
		if (TextUtils.isEmpty(title) && TextUtils.isEmpty(artist)) {
			return null;
		}

		// 条件语句
		String selection = MediaStore.Audio.Media.TITLE + "=? and " + MediaStore.Audio.Media.ARTIST + "=?";
		// ？ 与值一一对应
		String[] selectionArgs = { title, artist };
		List<MusicBean> queryMusicWithCondition = queryMusicWithCondition(selection, selectionArgs);
		if (DEBUG) {
			Log.d(TAG, "queryMusicWithCondition" + queryMusicWithCondition + "size" + queryMusicWithCondition.size());
		}

		return handleList(queryMusicWithCondition);
	}

	/**
	 * 根据条件查询 信息
	 * 
	 * @param selection
	 *            查询条件
	 * @param selectionArgs
	 *            查询参数
	 * @return 返回音乐列表
	 */
	private List<MusicBean> queryMusicWithCondition(String selection, String[] selectionArgs) {
		ContentResolver resolver = mContext.getContentResolver();
		Cursor cursor = null;
		try {
			/** 可用可不用 */
			String[] projection = { MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media._ID,
					MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM,
					MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.SIZE, MediaStore.Audio.Media.DATA,
					MediaStore.Audio.Media.IS_MUSIC };

			// 获取游标
			cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs,
					MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
			if (cursor != null) {
				// 创建列表
				List<MusicBean> musicList = new ArrayList<>();
				while (cursor.moveToNext()) {
					long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));// 时长
					// 如果不符合要求直接 continue 减少对象的创建
					if (!isFit(duration)) {
						continue;
					}
					long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));// 音乐id
					String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));// 音乐标题
					String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));// 艺术家
					String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));// 专辑
					long albumid = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));// 专辑id
					long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));// 文件大小
					String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));// 文件路径
					int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));// 是否为音乐

					// 创建Music对象
					MusicBean musicRecord = new MusicBean();
					musicRecord.setId(id);
					musicRecord.setTitle(title);
					musicRecord.setArtist(artist);
					musicRecord.setAlbum(album);
					musicRecord.setAlbumId(albumid);
					musicRecord.setSize(size);
					musicRecord.setUrl(url);
					musicRecord.setIsMusic(isMusic);
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
	 * 循环列表
	 */
	public void allLooperPlay() {
		// 播放记录 加1
		CURRENT_POSITION++;
		if (CURRENT_POSITION == mLoadCurrentMusic.size()) {
			CURRENT_POSITION = 0;
		}
		choiceAndStartPlay(MusicConstantValue.OPTION_PLAY);
	}

	/**
	 * 单曲循环
	 */
	public void singleLooperPlay() {
		choiceAndStartPlay(MusicConstantValue.OPTION_PLAY);
	}
	
	/**
	 * 随机播放
	 */
	public void randomPlay() {
		CURRENT_POSITION = (int) (Math.random() * mLoadCurrentMusic.size());
		choiceAndStartPlay(MusicConstantValue.OPTION_PLAY);
	}
	
	

}
