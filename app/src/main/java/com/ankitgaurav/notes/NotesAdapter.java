package com.ankitgaurav.notes;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Ankit Gaurav on 19-08-2016.
 */
public class NotesAdapter extends ArrayAdapter<Note> {

    public NotesAdapter(Context context, ArrayList<Note> notes){
        super(context, 0, notes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Note note = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.notes_list_item, parent, false);
        }
        TextView note_text = (TextView) convertView.findViewById(R.id.note_text);
        TextView note_created_at = (TextView) convertView.findViewById(R.id.note_created_at);
        note_text.setText(note.getNoteText());
        note_created_at.setText(note.getCreatedAt());

        return convertView;

    }
}
