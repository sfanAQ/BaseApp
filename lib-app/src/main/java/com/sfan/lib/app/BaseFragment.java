package com.sfan.lib.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhazhiyong on 2018/6/1.
 * Fragment
 */

public abstract class BaseFragment extends Fragment {

    protected abstract int getContentViewLayoutResID();

    protected abstract void init(boolean create, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected BaseActivity mActivity;
    protected View mLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            mActivity = (BaseActivity) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement BaseActivity");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mLayout == null) {
            mLayout = inflater.inflate(getContentViewLayoutResID(), container, false);
            // 第一次创建
            init(true, inflater, container, savedInstanceState);
        } else {
            init(false, inflater, container, savedInstanceState);
        }
        return mLayout;
    }

    @Override
    public void onDestroyView() {
        if (mLayout != null) {
            ViewGroup parent = (ViewGroup) mLayout.getParent();
            if (parent != null) {
                parent.removeView(mLayout);
            }
        }
        super.onDestroyView();
    }

    /**
     * 获取根Fragment
     *
     * @return Fragment
     */
    protected Fragment getRootFragment() {
        Fragment fragment = BaseFragment.this;
        while (fragment.getParentFragment() != null) {
            fragment = fragment.getParentFragment();
        }
        return fragment;
    }

}
