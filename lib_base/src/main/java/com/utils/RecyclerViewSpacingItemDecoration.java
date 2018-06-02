package com.utils;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.socks.library.KLog;

/**
 * Created by chennuo on 2018/2/10.
 * 不绘制分割线，只留出 span
 * 搭配 BaseQuickAdapter
 */
public class RecyclerViewSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpanCount;//网格数,即有多少列

    private int mSpanVerticalPixels;//水平间隔像素
    private int mSpanHorizontalPixels;//竖直间隔像素

    private boolean mIncludeVerticalEdge;//是否绘制竖直边框
    private boolean mIncludeHorizontalEdge;//是否绘制左右边框

    /**
     * 无边框，定制内部间距
     *
     * @param spanPixels
     */
    public RecyclerViewSpacingItemDecoration(int spanPixels) {
        this(spanPixels, spanPixels, false, false);
    }

    /**
     * 可控制左右边框，定制内部间距
     *
     * @param spanPixels
     * @param includeHorizontalEdge
     */
    public RecyclerViewSpacingItemDecoration(int spanPixels, boolean includeHorizontalEdge) {
        this(spanPixels, spanPixels, includeHorizontalEdge, false);
    }

    /**
     * 可控制左右边框，定制横竖距离
     *
     * @param spanVerticalPixels
     * @param spanHorizontalPixels
     * @param includeHorizontalEdge
     */
    public RecyclerViewSpacingItemDecoration(int spanVerticalPixels, int spanHorizontalPixels, boolean includeHorizontalEdge) {
        this(spanVerticalPixels, spanHorizontalPixels, includeHorizontalEdge, false);
    }

    /**
     * 可控制上下左右边框，定制横竖距离
     *
     * @param spanVerticalPixels
     * @param spanHorizontalPixels
     * @param includeHorizontalEdge
     * @param includeVerticalEdge
     */
    public RecyclerViewSpacingItemDecoration(int spanVerticalPixels, int spanHorizontalPixels, boolean includeHorizontalEdge, boolean includeVerticalEdge) {
        mSpanVerticalPixels = spanVerticalPixels;
        mSpanHorizontalPixels = spanHorizontalPixels;
        mIncludeHorizontalEdge = includeHorizontalEdge;
        mIncludeVerticalEdge = includeVerticalEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        try {
            if (parent.getAdapter() instanceof BaseQuickAdapter) {
            } else {
                throw new Exception("adapter need extend BaseQuickAdapter");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (view.equals(((BaseQuickAdapter) parent.getAdapter()).getHeaderLayout())) {
            KLog.e("getHeaderLayout");
            return;
        }
        if (view.equals(((BaseQuickAdapter) parent.getAdapter()).getFooterLayout())) {
            KLog.e("getFooterLayout");
            return;
        }

        int position = parent.getChildAdapterPosition(view) - ((BaseQuickAdapter) parent.getAdapter()).getHeaderLayoutCount();
        int itemCount = ((BaseQuickAdapter) parent.getAdapter()).getData().size();

        //处理最后的加载完成
        if (position >= itemCount) {
            KLog.e("loadMoreEnd");
            return;
        }

        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            mSpanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
            isGridLayoutManager(outRect, parent, position);
        } else {
            LinearLayoutManager manager = (LinearLayoutManager) parent.getLayoutManager();
            if (manager.getOrientation() == LinearLayout.HORIZONTAL) {
                mSpanCount = itemCount;
                isHorizontalLinearLayout(outRect, parent, position);
                return;
            } else if (manager.getOrientation() == LinearLayoutManager.VERTICAL) {
                mSpanCount = 1;
                isGridLayoutManager(outRect, parent, position);
            }
        }
    }

    private void isHorizontalLinearLayout(Rect outRect, RecyclerView parent, int position) {

        //是否绘制上下边框
        if (mIncludeVerticalEdge) {
            outRect.top = mSpanVerticalPixels;
            outRect.bottom = mSpanVerticalPixels;
        } else {

        }

        //是否绘制左右边框
        if (mIncludeHorizontalEdge) {
            //绘制第一列
            if (position == 0) {
                outRect.left = mSpanHorizontalPixels;
            } else {
                outRect.left = 0;
            }
            outRect.right = mSpanHorizontalPixels;
        } else {
            //绘制第一列
            if (position == 0) {
                outRect.left = 0;
            } else {
                outRect.left = mSpanHorizontalPixels;
            }
        }

    }

    private void isGridLayoutManager(Rect outRect, RecyclerView parent, int position) {
        int column = position % mSpanCount;//第几列
        int row = position / mSpanCount;//第几行

        //是否绘制上下边框
        if (mIncludeVerticalEdge) {
            //绘制第一行
            if (position < mSpanCount) {
                outRect.top = mSpanVerticalPixels;
            } else {
                outRect.top = 0;
            }
            outRect.bottom = mSpanVerticalPixels;
        } else {
            //绘制第一行
            if (position < mSpanCount) {
                outRect.top = 0;
            } else {
                outRect.top = mSpanVerticalPixels;
            }
        }

        //是否绘制左右边框
        if (mIncludeHorizontalEdge) {
            outRect.left = mSpanHorizontalPixels - column * mSpanHorizontalPixels / mSpanCount;
            outRect.right = (column + 1) * mSpanHorizontalPixels / mSpanCount;
        } else {
            outRect.left = column * mSpanHorizontalPixels / mSpanCount;
            outRect.right = mSpanHorizontalPixels - (column + 1) * mSpanHorizontalPixels / mSpanCount;
        }
    }

}