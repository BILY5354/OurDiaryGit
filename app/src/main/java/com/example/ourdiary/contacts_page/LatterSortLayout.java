package com.example.ourdiary.contacts_page;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.ourdiary.R;
import com.example.ourdiary.shared.ColorTools;

import java.util.ArrayList;
import java.util.List;

public class LatterSortLayout extends LinearLayout {

    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s);
    }

    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;

    private Context mContext;
    private List<String> sortTextList = new ArrayList();
    private int Choose = -1;
    private TextView sortTextView;

    public void setSortTextView(TextView sortTextView) {
        this.sortTextView = sortTextView;
    }

    public LatterSortLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setOrientation(VERTICAL);
        initSortText();
    }

    public void initSortText() {
        addView(buildTextLayout("#"));//自己写的布局
        for (char i = 'A'; i <= 'Z'; i++) {
            final String character = i + "";
            TextView tv = buildTextLayout(character);
            addView(tv);
        }
    }

    private TextView buildTextLayout(final String character) {
        sortTextList.add(character);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1);

        TextView sortTextView = new TextView(mContext);
        sortTextView.setLayoutParams(layoutParams);
        sortTextView.setGravity(Gravity.CENTER);
        sortTextView.setClickable(true);
        sortTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
        sortTextView.setText(character);
        sortTextView.setTextColor(ColorTools.getColor(getContext(), R.color.color_black));//返回当前页面的上下文，通常是当前页面的Activity
        sortTextView.setShadowLayer(1, 1, 1, R.color.color_gray);
        return sortTextView;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final float y = ev.getY();
        final int oldChoose = Choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int clickItem = (int) (y / getHeight() * sortTextList.size());

        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                Choose = -1;//如果未被选中则不会显示（用于显示字母的）文本框
                invalidate();
                if (sortTextView != null) {
                    sortTextView.setVisibility(View.GONE);
                }
                break;
            //Touch move & down
            default:
                if (oldChoose != clickItem) {
                    if (clickItem >= 0 && clickItem < sortTextList.size()) {
                        if(listener != null) {
                            listener.onTouchingLetterChanged(sortTextList.get(clickItem));
                        }
                        if (sortTextView != null) {
                            sortTextView.setText(sortTextList.get(clickItem));
                            sortTextView.setVisibility(View.VISIBLE);
                        }
                        Choose = clickItem;
                        invalidate();//提示界面变动
                    }
                }
                break;
        }
        return true;
    }

    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }
}
