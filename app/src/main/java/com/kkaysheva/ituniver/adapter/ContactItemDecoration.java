package com.kkaysheva.ituniver.adapter;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * ContactItemDecoration
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
public class ContactItemDecoration extends RecyclerView.ItemDecoration {

    private static final int margin = 16;

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(margin, margin, margin, margin);
    }
}
