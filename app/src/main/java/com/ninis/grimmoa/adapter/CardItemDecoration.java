package com.ninis.grimmoa.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by gypark on 2017. 3. 22..
 */

public class CardItemDecoration extends RecyclerView.ItemDecoration {

    private final int divHeight;

    public CardItemDecoration(int height) {
        divHeight = height;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.top = divHeight;
    }
}
