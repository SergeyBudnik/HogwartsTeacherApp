package com.hogwarts_eng_school.hogwarts_teacher.view.app.menu;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hogwarts_eng_school.hogwarts_teacher.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.view_menu_item)
public class MenuItemView extends LinearLayout {
    @ViewById(R.id.root)
    View rootView;
    @ViewById(R.id.selection_mark)
    View selectionMarkView;
    @ViewById(R.id.icon)
    ImageView iconView;
    @ViewById(R.id.label)
    TextView labelView;

    public MenuItemView(Context context) {
        super(context);
    }

    public MenuItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void bind(int iconId, int textId, Runnable action) {
        iconView.setImageDrawable(getResources().getDrawable(iconId));
        labelView.setText(textId);
        rootView.setOnClickListener((v) -> action.run());
    }

    public void setSelected(boolean selected) {
        int color = getResources().getColor(selected ? R.color.white : R.color.white);

        selectionMarkView.setVisibility(selected ? VISIBLE : INVISIBLE);
        iconView.getDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        labelView.setTextColor(color);
    }
}
