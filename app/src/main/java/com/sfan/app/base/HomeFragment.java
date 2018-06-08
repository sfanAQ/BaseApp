package com.sfan.app.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sfan.app.base.adapter.DrawerMenuAdapter;
import com.sfan.lib.app.DeviceUtils;
import com.sfan.lib.app.MyLog;
import com.sfan.lib.app.MyToast;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zzy on 2018/6/11.
 * 首页
 */

public class HomeFragment extends TabFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.drawerLeft)
    View drawerLeft;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btnExit)
    Button btnExit;
    Unbinder unbinder1, unbinder2;

    @Override
    protected int getContentViewLayoutResID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init(boolean create, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        unbinder1 = ButterKnife.bind(this, mLayout);
        // include
        unbinder2 = ButterKnife.bind(R.layout.layout_drawer_menu, drawerLeft);
        if (create) {
            setDrawerLayout(drawerLayout);
            DrawerMenuAdapter menuAdapter = new DrawerMenuAdapter(mActivity);
            recyclerView.setAdapter(menuAdapter);
            menuAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View v, RecyclerView.ViewHolder holder) {
                    MyToast.debug("点击了" + holder.getAdapterPosition());
                }
            });
            drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    MyLog.i("DrawerLayout----onDrawerSlide" + slideOffset);
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    MyToast.debug("打开了侧边栏");
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    MyToast.debug("关闭了侧边栏");
                }

                @Override
                public void onDrawerStateChanged(int newState) {
                    MyLog.i("DrawerLayout----onDrawerStateChanged" + newState);
                }
            });
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 初始化toolbar
        mActivity.setSupportActionBar(toolbar);
        mActivity.setStatusBarLayout(toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.selector_drawable_icon_tab);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(drawerLeft)) {
                    drawerLayout.closeDrawer(drawerLeft);
                } else {
                    drawerLayout.openDrawer(drawerLeft);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
        unbinder2.unbind();
    }

    private void setDrawerLayout(DrawerLayout drawerLayout) {
        if (drawerLayout == null) return;
        // 侧边栏下面部分阴影颜色，设置透明
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        // DrawerLayout设置滑动边距
        try {
            Field field = drawerLayout.getClass().getDeclaredField("mLeftDragger");
            field.setAccessible(true);
            ViewDragHelper mLeftDragger = (ViewDragHelper) field.get(drawerLayout);
            Field edgeSizeField = mLeftDragger.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int mEdgeSize = edgeSizeField.getInt(mLeftDragger);
            int width = DeviceUtils.getScreenWidth(mActivity);
            edgeSizeField.setInt(mLeftDragger, Math.max(mEdgeSize, (int) (width * 0.1f)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
