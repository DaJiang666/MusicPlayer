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

	/** 歌曲id*/
	private long id;
	/** 歌曲名字 */
	private String title;
	/** 艺术家 */
	private String artist;
	/** 专辑 */
	private String album;
	/** 专辑 id*/
	private long albumId;
	/** 歌曲大小 */
	private long size;
	/** 歌曲地址 */
	private String url;
	/** 歌曲长度 */
	private long duration;
	/** 是否是歌曲  */
	private int isMusic;
	
	public int getIsMusic() {
		return isMusic;
	}

	public void setIsMusic(int isMusic) {
		this.isMusic = isMusic;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAlbumId() {
		return albumId;
	}

	public void setAlbumId(long albumId) {
		this.albumId = albumId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
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
