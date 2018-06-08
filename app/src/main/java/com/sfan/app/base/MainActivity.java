package com.sfan.app.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.sfan.app.base.widget.FragmentTabHost;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zzy on 2018/6/11.
 * 主页
 */

public class MainActivity extends MyActivity implements TabHostController {

    @BindView(android.R.id.tabhost)
    FragmentTabHost tabhost;
    @BindView(android.R.id.tabs)
    TabWidget tabs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 透明状态栏，状态栏字体深色
        setStatusBarTranslucent(Color.TRANSPARENT, true);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.bind(MainActivity.this);
        tabhost.setup(MainActivity.this, getSupportFragmentManager(), android.R.id.tabcontent);
        final String[] homeTabs = getResources().getStringArray(R.array.home_tabs);
        addTab(0, homeTabs[0], homeTabs[0], R.drawable.selector_drawable_icon_tab, HomeFragment.class);
        addTab(1, homeTabs[1], homeTabs[1], R.drawable.selector_drawable_icon_tab, RankingsFragment.class);
        addTab(2, homeTabs[2], homeTabs[2], R.drawable.selector_drawable_icon_tab, EmptyFragment.class);
        addTab(3, homeTabs[3], homeTabs[3], R.drawable.selector_drawable_icon_tab, EmptyFragment.class);
        addTab(4, homeTabs[4], homeTabs[4], R.drawable.selector_drawable_icon_tab, EmptyFragment.class);
        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
            }
        });
        tabhost.setCurrentTab(0);// 默认
    }

    @Override
    public void handleCallback(Message msg) {

    }

    @Override
    public void receiveCallback(Context context, Intent intent) {

    }

    @Override
    public void setCurrentTab(int index) {
        if (tabhost != null) {
            tabhost.setCurrentTab(index);
        }
    }

    private void addTab(int position, String tag, String tabName, int resId, Class<?> arg) {
        View view;
        if (position == 2) {
            view = getLayoutInflater().inflate(R.layout.layout_tab_menu, null);
        } else {
            view = getLayoutInflater().inflate(R.layout.layout_tab_item, null);
        }
        ImageView imgTab = view.findViewById(R.id.imgTab);
        TextView txtTab = view.findViewById(R.id.txtTab);
        txtTab.setText(tabName);
        imgTab.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, resId));
        TabHost.TabSpec tabSpec = tabhost.newTabSpec(tag);
        // 设置indicator
        tabSpec.setIndicator(view);
        Bundle args = new Bundle();
        args.putInt("position", position);
        tabhost.addTab(tabSpec, arg, args);
    }
}
