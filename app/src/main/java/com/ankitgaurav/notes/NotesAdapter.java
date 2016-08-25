package com.ankitgaurav.notes;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ankit Gaurav on 19-08-2016.
 */
public class NotesAdapter extends ArrayAdapter<Note> {

    private static final int WEEK_IN_MILLIS = 259200000;
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
        String noteFullText = note.getNoteText();
        note_text.setText(getNoteSnippet(noteFullText));

        String timeModified = "";
        try {
            timeModified = getDateTime(note.getCreatedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        note_created_at.setText(timeModified);

        return convertView;
    }

    private String getDateTime(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        long dateInMillis = 0L;
        try {
            Date mDate = dateFormat.parse(date);
            dateInMillis = mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateUtils dateUtils = new DateUtils();
        CharSequence dateN = dateUtils.getRelativeDateTimeString(getContext(), dateInMillis,60, WEEK_IN_MILLIS,
                0);
        return dateN.toString();
    }
    private String getNoteSnippet(String s){
        int lineBreakPos = s.indexOf('\n');
        String noteSnippet = "";
        int snippetLength = 80;
        if(lineBreakPos < 80){
            snippetLength = 50;
        }
        if(s.length()>snippetLength){
            noteSnippet = s.substring(0,snippetLength) + " ...";
        }
        else{
            noteSnippet = s;
        }
        return s;
    }
}
