package com.sfan.app.base.widget;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zzy on 2018/4/6.
 * RecyclerView 边距，解决不能显示边界问题
 */

public class SpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int left, top, right, bottom;

    public SpacingItemDecoration(int padding) {
        this.left = padding;
        this.top = padding;
        this.right = padding;
        this.bottom = padding;
    }

    public SpacingItemDecoration(int paddingVertical, int paddingHorizontal) {
        this.left = paddingHorizontal;
        this.top = paddingVertical;
        this.right = paddingHorizontal;
        this.bottom = paddingVertical;
    }

    public SpacingItemDecoration(int left, int top, int right, int bottom) {
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
                int orientation = ((GridLayoutManager) layoutManager).getOrientation();
                int count = layoutManager.getItemCount();
                int spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
                int position = parent.getChildAdapterPosition(view); // item position
                int line = position / spanCount; // item line
                int column = position % spanCount; // item column
                if (orientation == GridLayoutManager.VERTICAL) {
                    if (line == 0) outRect.top = top;
                    if (line == (count - 1) / spanCount) outRect.bottom = bottom;
                    if (column == 0) outRect.left = left;
                    if (column == spanCount - 1) outRect.right = right;
                } else if (orientation == GridLayoutManager.HORIZONTAL) {
                    if (line == 0) outRect.left = left;
                    if (line == (count + spanCount - 1) / spanCount) outRect.right = right;
                    if (column == 0) outRect.top = top;
                    if (column == spanCount - 1) outRect.bottom = bottom;
                }
            } else if (layoutManager instanceof LinearLayoutManager) {
                int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
                int count = layoutManager.getItemCount();
                int position = parent.getChildAdapterPosition(view); // item position
                if (orientation == LinearLayoutManager.VERTICAL) {
                    outRect.left = left;
                    outRect.right = right;
                    if (position == 0) outRect.top = top;
                    if (position == count - 1) outRect.bottom = bottom;
                } else if (orientation == LinearLayoutManager.HORIZONTAL) {
                    outRect.top = top;
                    outRect.bottom = bottom;
                    if (position == 0) outRect.left = left;
                    if (position == count - 1) outRect.right = right;
                }
            }
        }
    }
}