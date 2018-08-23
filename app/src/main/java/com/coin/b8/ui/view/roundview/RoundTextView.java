package com.coin.b8.ui.view.roundview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by zhangyi on 2018/8/20.
 */
public class RoundTextView extends TextView implements RHelper<RTextViewHelper> {

    private RTextViewHelper mHelper;

    public RoundTextView(Context context) {
        this(context, null);
    }

    public RoundTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHelper = new RTextViewHelper(context, this, attrs);
    }

    @Override
    public RTextViewHelper getHelper() {
        return mHelper;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mHelper.setEnabled(enabled);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mHelper.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


}
