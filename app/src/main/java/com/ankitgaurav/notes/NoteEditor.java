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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class NoteEditor extends AppCompatActivity {

    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(getDateTime());

        dbHandler = new MyDBHandler(this);

      //code to show softkeyboard while entering into noteEditor activity
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
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
    private void saveNote(){
        EditText editText = (EditText) findViewById(R.id.editTextNote);
        String note = editText.getText().toString();
        if (note.equals("")) {
            returnHome();
        }
        else{
            saveNoteToDB(note);
            savedNoteAsTextFile(note);
        }


        //code to hide the soft keyboard while leaving activity
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        returnHome();
    }
    private void returnHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /*Check if external storage is available for read/write operations */
    public  boolean isExternalStorageWritable(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            return true;
        }
        return false;
    }

    /* Save Note to SQLite Database */
    public void saveNoteToDB(String note){
        try{
            dbHandler.addNote(note);
        }catch (Exception e){
            Log.e("saveNoteToDB", String.valueOf(e));
        }
    }

    /* Save Note data as a test file to External Storage */
    public void savedNoteAsTextFile(String note){
        if(isExternalStorageWritable()){
//            String fileName = String.valueOf(new Random().nextInt()) + ".txt";

            int end=10;
            if(note.length()<end){
                end = note.length();
            }
            String fileName = note.substring(0, end) + ".txt";
            FileInputStream fileInputStream;
            try{
                File file = new File("/sdcard/notes/"+fileName);
                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                outputStreamWriter.append(note);
                outputStreamWriter.close();
                fileOutputStream.close();
                Toast.makeText(this, "/sdcard/"+fileName+": Note saved", Toast.LENGTH_SHORT).show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(this, "Write permission forbidden.", Toast.LENGTH_SHORT).show();
        }
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd MMM, HH:mm:aa", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
