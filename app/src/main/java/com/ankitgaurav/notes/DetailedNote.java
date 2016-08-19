package com.ankitgaurav.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailedNote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_note);

        Intent intent = getIntent();
        String noteText = intent.getStringExtra("noteText");
        TextView textView = (TextView) findViewById(R.id.detailedNoteTextView);
        textView.setText(noteText);

    }
}
