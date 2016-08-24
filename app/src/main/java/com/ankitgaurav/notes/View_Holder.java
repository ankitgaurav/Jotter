package com.ankitgaurav.notes;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Ankit Gaurav on 24-08-2016.
 */
public class View_Holder extends RecyclerView.ViewHolder {

    CardView cv;
    TextView title;

    View_Holder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        title = (TextView) itemView.findViewById(R.id.title);
    }
}