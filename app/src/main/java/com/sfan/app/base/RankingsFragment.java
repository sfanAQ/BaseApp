package com.sfan.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sfan.app.base.adapter.DrawerMenuAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zzy on 2018/6/11.
 * 运动排名
 */

public class RankingsFragment extends TabFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;

    @Override
    protected int getContentViewLayoutResID() {
        return R.layout.fragment_rankings;
    }

    @Override
    protected void init(boolean create, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, mLayout);
        if (create) {
            DrawerMenuAdapter menuAdapter = new DrawerMenuAdapter(mActivity);
            recyclerView.setAdapter(menuAdapter);
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
                // 返回首页
                if (mActivity instanceof TabHostController) {
                    ((TabHostController) mActivity).setCurrentTab(0);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
