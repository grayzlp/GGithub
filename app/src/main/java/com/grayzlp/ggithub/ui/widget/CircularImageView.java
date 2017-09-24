package com.grayzlp.ggithub.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.grayzlp.ggithub.util.ViewUtils;

/**
 * An extension to image view that has a circle outline.
 */

public class CircularImageView extends ForegroundImageView {

    public CircularImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOutlineProvider(ViewUtils.CIRCULAR_OUTLINE);
        setClipToOutline(true);
    }
}
