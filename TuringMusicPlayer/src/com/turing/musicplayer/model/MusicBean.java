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

	@Override
	public String toString() {
		return "MusicBean [id=" + id + ", title=" + title + ", artist=" + artist + ", album=" + album + ", albumId="
				+ albumId + ", size=" + size + ", url=" + url + ", duration=" + duration + ", isMusic=" + isMusic + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((album == null) ? 0 : album.hashCode());
		result = prime * result + (int) (albumId ^ (albumId >>> 32));
		result = prime * result + ((artist == null) ? 0 : artist.hashCode());
		result = prime * result + (int) (duration ^ (duration >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + isMusic;
		result = prime * result + (int) (size ^ (size >>> 32));
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MusicBean other = (MusicBean) obj;
		if (album == null) {
			if (other.album != null)
				return false;
		} else if (!album.equals(other.album))
			return false;
		if (albumId != other.albumId)
			return false;
		if (artist == null) {
			if (other.artist != null)
				return false;
		} else if (!artist.equals(other.artist))
			return false;
		if (duration != other.duration)
			return false;
		if (id != other.id)
			return false;
		if (isMusic != other.isMusic)
			return false;
		if (size != other.size)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	
	

}
