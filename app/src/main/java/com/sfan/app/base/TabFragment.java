package com.sfan.app.base;

import android.os.Bundle;

import com.sfan.lib.app.BaseFragment;

/**
 * Created by zzy on 2018/6/7.
 * 主页 片段 父类Fragment
 */

public abstract class TabFragment extends BaseFragment {

    protected int mPosition = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt("position");
        }
        setHasOptionsMenu(true);// 加上这句话，toolbar、menu才会显示出来
    }

    @Override
    public void onDestroy() {
        setHasOptionsMenu(false);
        super.onDestroy();
    }

}
