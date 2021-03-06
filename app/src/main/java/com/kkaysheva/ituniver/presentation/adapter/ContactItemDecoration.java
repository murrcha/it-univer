package com.kkaysheva.ituniver.presentation.adapter;

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
public final class ContactItemDecoration extends RecyclerView.ItemDecoration {

    private final int margin;

    public ContactItemDecoration(int margin) {
        this.margin = margin;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(margin + margin, margin, margin + margin, margin);
        int position = parent.getChildAdapterPosition(view);
        if (position == parent.getAdapter().getItemCount() - 1) {
            outRect.bottom = margin + margin;
        }
        if (position == 0) {
            outRect.top = margin + margin;
        }
    }
}
