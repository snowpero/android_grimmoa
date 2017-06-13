package com.ninis.grimmoa.custom;

import android.view.View;

/**
 * Created by gypark on 2017. 3. 22..
 */

public interface RecyclerViewClickEvent {
    public void onClick(View view, int position);
    public void onLongClick(View view, int position);
}
