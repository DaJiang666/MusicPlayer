package com.turing.musicplayer.model;
/**
 *
 *	Author: ZhangDanJiang
 *
 *  Date:2016年6月16日  Time: 下午2:02:25
 *
 *  Function: 音乐  javaBean
 *
 */
public class MusicBean {

	/** 歌曲名字 */
	private String name;
	/** 歌曲大小 */
	private long size;
	/** 歌曲地址 */
	private String url;
	/** 歌曲长度 */
	private long duration;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

}
