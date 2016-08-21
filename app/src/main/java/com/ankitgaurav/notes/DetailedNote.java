package com.ankitgaurav.notes;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailedNote extends AppCompatActivity {

    MyDBHandler dbHandler;
    private String note_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detailed_note_toolbar);
        setSupportActionBar(toolbar);
//        Button deleteButton = (Button) .findViewById(R.id.Details_Button01);
//        deleteButton.setTag(position);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        //Setting floating button for editing current note
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_edit_note);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), NoteEditor.class);
//                startActivity(intent);
//            }
//        });

        Intent intent = getIntent();
        String note_created_at = intent.getStringExtra("note_created_at");
        ab.setTitle(note_created_at);
        String noteText = intent.getStringExtra("noteText");
        note_id  = intent.getStringExtra("note_id");
        TextView textView = (TextView) findViewById(R.id.detailedNoteTextView);
        textView.setText(noteText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detailed_note_menu, menu);
        //MenuItem deleteMenuItem = (MenuItem) menu.getItem(R.id.action_delete);
        //setMenuItemTag(deleteMenuItem, note_id);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    public void setMenuItemTag(MenuItem item, String tag)
//    {
//        View actionView = item.getActionView();
//        actionView.setTag(tag);
//    }
//    public Object getMenuItemTag(MenuItem item, String tag)
//    {
//        View actionView = item.getActionView();
//        return actionView.getTag(tag);
//    }
}