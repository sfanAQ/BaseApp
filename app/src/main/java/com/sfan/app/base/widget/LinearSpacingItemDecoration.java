package com.sfan.app.base.widget;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zzy on 2018/4/6.
 * RecyclerView Linear Item间距
 */

public class LinearSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int left, top, right, bottom;

    public LinearSpacingItemDecoration(int spacing) {
        this.left = spacing;
        this.top = spacing;
        this.right = spacing;
        this.bottom = spacing;
    }

    public LinearSpacingItemDecoration(int spacingVertical, int spacingHorizontal) {
        this.left = spacingHorizontal;
        this.top = spacingVertical;
        this.right = spacingHorizontal;
        this.bottom = spacingVertical;
    }

    public LinearSpacingItemDecoration(int left, int top, int right, int bottom) {
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
            if (layoutManager instanceof LinearLayoutManager) {
                outRect.left = left / 2;
                outRect.top = top / 2;
                outRect.right = right / 2;
                outRect.bottom = bottom / 2;
            }
        }
    }
}