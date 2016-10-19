package com.ankitgaurav.notes;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Ankit Gaurav on 24-08-2016.
 */
public class View_Holder extends RecyclerView.ViewHolder {
    CheckBox checkBox;
    CardView cv;
    TextView title;
    CheckBox starButton;
    RelativeLayout rl1;

    public View_Holder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        title = (TextView) itemView.findViewById(R.id.title);
        checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
        //starButton = (CheckBox) itemView.findViewById(R.id.star_todo);
        //rl1 = (RelativeLayout) starButton.getParent();
    }
}