package com.hogwarts_eng_school.hogwarts_teacher.view.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.annimon.stream.Stream;
import com.hogwarts_eng_school.hogwarts_teacher.R;

import lombok.Getter;

public class FontableTextView extends AppCompatTextView {
    private enum AppFont {
        REGULAR(1, "fonts/sourcesanspro.ttf"),
        LIGHT(2, "fonts/sourcesansprolight.ttf"),
        BOLD(3, "fonts/sourcesansprosemibold.ttf");

        @Getter private int index;
        @Getter private String fontName;

        AppFont(int index, String fontName) {
            this.index = index;
            this.fontName = fontName;
        }

        public static AppFont findByIndex(int index) {
            return Stream.of(values())
                    .filter(it -> it.index == index)
                    .findFirst()
                    .orElseThrow(RuntimeException::new);
        }
    }

    public FontableTextView(Context context) {
        super(context);
    }

    public FontableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FontableTextView, 0, 0);

        try {
            AppFont font = AppFont.findByIndex(ta.getInt(R.styleable.FontableTextView_app_font, 1));

            setTypeface(getTypeface(font.getFontName(), getContext()));
        } finally {
            ta.recycle();
        }
    }

    private static Typeface getTypeface(String fontName, Context context) {
        try {
            return Typeface.createFromAsset(context.getAssets(), fontName);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
