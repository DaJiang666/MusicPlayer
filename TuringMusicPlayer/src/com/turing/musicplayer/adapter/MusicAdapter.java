package com.turing.musicplayer.adapter;

import java.util.ArrayList;
import java.util.List;

import com.turing.musicplayer.R;
import com.turing.musicplayer.model.MusicBean;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MusicAdapter extends CommonListAdapter<MusicBean> {

	private Context mContext;

	private List<MusicBean> mList;

	public MusicAdapter(Context context) {
		super();
		this.mContext = context;
		mList = new ArrayList<MusicBean>();
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.item_musiclist, null);
			viewHolder = new ViewHolder();
			viewHolder.nTitle = (TextView) convertView.findViewById(R.id.txt_musicname);
			viewHolder.nTime = (TextView) convertView.findViewById(R.id.txt_musicsize);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		MusicBean musicBean = mList.get(position);
		if (musicBean != null) {
			viewHolder.nTitle.setText(musicBean.getName());
			long nDuration = musicBean.getDuration();
			viewHolder.nTime.setText(getTimeToString(nDuration));
		}
		return convertView;
	}

	@Override
	public void setList(List<MusicBean> list) {
		mList.clear();
		mList.addAll(list);
		notifyDataSetChanged();
	}

	@Override
	public void addList(List<MusicBean> list) {
		mList.addAll(list);
		notifyDataSetChanged();
	}

	private class ViewHolder {
		/** 标题 */
		private TextView nTitle;
		/** 事件 */
		private TextView nTime;
	}
	
	private String getTimeToString(long duration) {
		int minute = (int) (duration / (1000 * 60));
		int second = (int) ((duration / 1000) - minute * 60);
		return minute + ":" + second;
	}

}
