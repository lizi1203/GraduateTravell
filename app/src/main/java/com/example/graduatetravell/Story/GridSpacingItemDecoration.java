package com.example.graduatetravell.Story;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

//调整recyclerView的Item间隔
public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount; //列数
    private int spacing; //间隔
    private boolean includeEdge; //是否包含边缘
    private boolean isHistory;//标志位，判断是否有banner

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge, boolean isHistory) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
        this.isHistory = isHistory;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position;

        //这里是关键，需要根据你有几列来判断
        if(!isHistory) {
            position = parent.getChildAdapterPosition(view) - 1; // item position
        }else{
            position = parent.getChildAdapterPosition(view); // item position
        }

        if(position < 0) return;
        int column = position % spanCount; // item column

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = spacing;
            }
            outRect.bottom = spacing; // item bottom
        } else {
            outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing; // item top
            }
        }
    }
}
