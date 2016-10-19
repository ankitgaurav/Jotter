package com.ankitgaurav.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailedNote extends AppCompatActivity {
    private String noteText = "";
    AppDBHandler dbHandler;
    private static int n_id;
    private static int n_isLocked;
    private String note_created_at = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detailed_note_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        dbHandler = new AppDBHandler(this);
        Intent intent = getIntent();
        try {
            note_created_at = getDetailedDateTime(intent.getStringExtra("note_created_at"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        n_isLocked = intent.getIntExtra("note_is_locked", 0);

        noteText = intent.getStringExtra("noteText");
        if (savedInstanceState != null) {
            n_id = savedInstanceState.getInt("note_id");
        } else {
            n_id = intent.getIntExtra("note_id", 0);
        }

        TextView info = (TextView) findViewById(R.id.detailed_note_info);
        info.setText(note_created_at);
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
            case R.id.action_edit:
                editThisNote();
                return true;
            case R.id.action_delete:
                deleteThisNote();
                return true;
            case R.id.action_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, noteText);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;
            case R.id.action_lock:
                lockNote(n_isLocked);
                Toast.makeText(this, "Note locked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_save_to_txt:
                savedNoteAsTextFile(noteText);
                return true;
            case R.id.action_info:
                showInfo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void lockNote(int n_isLocked) {
        int newFlag = dbHandler.toggleNoteLock(n_id, n_isLocked);
        n_isLocked = newFlag;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("note_id", n_id);
        super.onSaveInstanceState(outState);
    }

    private void editThisNote(){
        Intent intent = new Intent(this, NoteEditor.class);
        intent.putExtra("note_id", n_id);
        intent.putExtra("type", "edit");
        startActivity(intent);
    }

    private void showInfo(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        alertDialogBuilder.setTitle("Note Info")
                .setCancelable(true)
                .setMessage("Total Characters: " + noteText.length() +
                        "\nTotal words: " + noteText.split(" ").length +
                        "\nCreated at: " + note_created_at);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void deleteThisNote(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        alertDialogBuilder.setTitle("Delete this note?")
                .setCancelable(true)
                .setPositiveButton("Delete",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dbHandler.deleteNote(n_id);
                        Toast.makeText(getApplicationContext(), "Note deleted successfully",
                                Toast.LENGTH_SHORT).show();
                        returnHome();
                    }
                })
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void returnHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
    private String getDateTime(String dateStr) throws ParseException {
        SimpleDateFormat prevDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        Date date = prevDateFormat.parse(dateStr);
        SimpleDateFormat newDateFormat = new SimpleDateFormat(
                "dd MMM", Locale.getDefault());
        return newDateFormat.format(date);
    }
    private String getDetailedDateTime(String dateStr) throws ParseException {
        SimpleDateFormat prevDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = prevDateFormat.parse(dateStr);
        SimpleDateFormat newDateFormat = new SimpleDateFormat(
                "dd MMM, hh:mm aa", Locale.getDefault());
        return newDateFormat.format(date);
    }

    /*Check if external storage is available for read/write operations */
    private boolean isExternalStorageWritable(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            return true;
        }
        return false;
    }
    /* Save Note data as a text file to External Storage */
    private boolean savedNoteAsTextFile(String note){
        if(isExternalStorageWritable()){
            int end=10;
            if(note.length()<end){
                end = note.length();
            }
            String fileName = note.substring(0, end) + ".txt";
            try{
                File folder = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOCUMENTS), "JotterBackups");
                if(!folder.exists()){
                    folder.mkdir();
                }
                File file = new File(folder, fileName);
                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                outputStreamWriter.append(note);
                outputStreamWriter.close();
                fileOutputStream.close();
                Toast.makeText(this, "Note saved in " + folder, Toast.LENGTH_SHORT)
                        .show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        else{
            Toast.makeText(this, "Write permission forbidden.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}