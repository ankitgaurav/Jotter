package com.ankitgaurav.notes;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteEditor extends AppCompatActivity {

    AppDBHandler dbHandler;
    private String editType;
    private static int n_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        dbHandler = new AppDBHandler(this);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                editType = "new";
                EditText editText = (EditText) findViewById(R.id.editTextNote);
                editText.setText(intent.getStringExtra(Intent.EXTRA_TEXT));
            }
        }
        else{
            editType = intent.getStringExtra("type");
        }


        if(editType.equals("new")){
            n_id = -1;
            try {
                ab.setTitle(getDateTime(getDateTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else if(editType.equals("edit")){
            n_id = intent.getIntExtra("note_id", 0);
            Note note = dbHandler.getNoteById(n_id);
            EditText editText = (EditText) findViewById(R.id.editTextNote);
            editText.setText(note.getNoteText());
            try {
                ab.setTitle(getDateTime(note.getCreatedAt()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //code to show softkeyboard while entering into noteEditor activity
        showKeyboard(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note_editor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                saveNote();
                return true;
            case R.id.action_delete:
                returnHome();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        showKeyboard(false);
        super.onDestroy();
    }

    private void saveNote(){
        EditText editText = (EditText) findViewById(R.id.editTextNote);
        String note = editText.getText().toString();
        if (note.equals("")) {//Empty note discarded
            returnHome();
        }
        else{
            Note note1 = new Note();
            note1.setNoteText(note);
            note1.setCreatedAt(getDateTime());
            if(n_id != -1){//Code to edit existing note
                dbHandler.updateNote(n_id, note1);
            }
            else{//code to add new note to database
                saveNoteToDB(note1);
            }
        }

        //code to hide the soft keyboard while leaving activity
        showKeyboard(false);
        returnHome();
    }
    private void returnHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void showKeyboard(boolean state){
        if(state){
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
            );
        }
        else{
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
            );
        }
    }

    /* Save Note to SQLite Database */
    public void saveNoteToDB(Note note){
        try{
            dbHandler.addNote(note);
        }catch (Exception e){
            Log.e("saveNoteToDB", String.valueOf(e));
        }
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    private String getDateTime(String dateStr) throws ParseException {
        SimpleDateFormat prevDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = prevDateFormat.parse(dateStr);
        SimpleDateFormat newDateFormat = new SimpleDateFormat(
                "dd MMM, hh:mm aa", Locale.getDefault());
        return newDateFormat.format(date);
    }
}
