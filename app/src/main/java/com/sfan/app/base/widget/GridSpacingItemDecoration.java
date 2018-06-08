package com.sfan.app.base.widget;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zzy on 2018/4/6.
 * RecyclerView Grid Item间距
 */

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int left, top, right, bottom;

    public GridSpacingItemDecoration(int spacing) {
        this.left = spacing;
        this.top = spacing;
        this.right = spacing;
        this.bottom = spacing;
    }

    public GridSpacingItemDecoration(int spacingVertical, int spacingHorizontal) {
        this.left = spacingHorizontal;
        this.top = spacingVertical;
        this.right = spacingHorizontal;
        this.bottom = spacingVertical;
    }

    public GridSpacingItemDecoration(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager != null) {
            if (layoutManager instanceof GridLayoutManager) {
                outRect.left = left / 2;
                outRect.top = top / 2;
                outRect.right = right / 2;
                outRect.bottom = bottom / 2;
            }
        }
    }
}