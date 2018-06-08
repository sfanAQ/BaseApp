package com.sfan.app.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zzy on 2018/6/11.
 * RecyclerView Item点击监听回调
 */

public interface OnItemClickListener {

    void onItemClick(View v, RecyclerView.ViewHolder holder);
}
