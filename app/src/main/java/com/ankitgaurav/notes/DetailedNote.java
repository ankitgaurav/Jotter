package com.ankitgaurav.notes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailedNote extends AppCompatActivity {

    MyDBHandler dbHandler;
    private static int n_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detailed_note_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        dbHandler = new MyDBHandler(this);
        Intent intent = getIntent();

        String note_created_at = intent.getStringExtra("note_created_at");
        final String noteText = intent.getStringExtra("noteText");
        if (savedInstanceState != null) {
            n_id = savedInstanceState.getInt("note_id");
        } else {
            n_id = intent.getIntExtra("note_id", 0);
        }

        try {
            ab.setTitle(getDateTime(note_created_at));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TextView textView = (TextView) findViewById(R.id.detailedNoteTextView);
        textView.setText(noteText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detailed_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                deleteThisNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("note_id", n_id);
        super.onSaveInstanceState(outState);
    }

    public void deleteThisNote(){
        dbHandler.deleteNote(n_id);
        Toast.makeText(this, "Note deleted successfully", Toast.LENGTH_SHORT).show();
        returnHome();
    }
    private void returnHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
    private String getDateTime(String dateStr) throws ParseException {
        SimpleDateFormat prevDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = prevDateFormat.parse(dateStr);
        SimpleDateFormat newDateFormat = new SimpleDateFormat(
                "dd MMM, HH:mm:aa", Locale.getDefault());
        return newDateFormat.format(date);
    }
}