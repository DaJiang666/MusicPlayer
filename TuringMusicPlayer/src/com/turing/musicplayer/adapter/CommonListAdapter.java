package com.turing.musicplayer.adapter;


import java.util.List;

import android.widget.BaseAdapter;

/**
 *
 *	Author: ZhangDanJiang
 *
 *  Date:2016年6月16日  Time: 下午4:09:05
 *
 *  Function: 通用的列表适配器，包含设置和添加列表入口
 *
 */
public abstract class CommonListAdapter<T> extends BaseAdapter {

    /**
     * 设置List列表
     * @param list
     */
    public abstract void setList(List<T> list);

    /**
     * 添加List列表
     * @param list
     */
    public abstract void addList(List<T> list);
}
