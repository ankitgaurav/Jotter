package com.ankitgaurav.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Ankit Gaurav on 15-08-2016.
 */
public class MyDBHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "notesDB.db";
    public static final String TABLE_NOTES = "notes_table";
    public static final String COLUMN_NOTE_TEXT = "note_text";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_ID = "_id";


    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query1 = "CREATE TABLE " + TABLE_NOTES + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOTE_TEXT + " TEXT, " +
                COLUMN_CREATED_AT +" DATETIME );";
        sqLiteDatabase.execSQL(query1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(MyDBHandler.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(sqLiteDatabase);
    }

    public Note getNoteById(int id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        //Todo replace getWritableDB to getReadableDB
        String query = "SELECT * FROM " + TABLE_NOTES + " WHERE " + COLUMN_ID + " = " + id;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        Note note = new Note();
        note.set_id(Integer.parseInt(cursor.getString(0)));
        note.setNoteText(cursor.getString(1));
        note.setCreatedAt(cursor.getString(2));
        return note;
    }
    public void addNote(String note){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_TEXT, note);
        values.put(COLUMN_CREATED_AT, getDateTime());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert(TABLE_NOTES, null, values);
        sqLiteDatabase.close();
    }

    public void deleteNote(int note_id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_NOTES + " WHERE " + COLUMN_ID + " = \"" +
                note_id + "\";" ;
        sqLiteDatabase.execSQL(deleteQuery);
        sqLiteDatabase.close();
    }

    public void updateNote(int note_id, String note_text){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String deleteQuery = "UPDATE " + TABLE_NOTES + " SET "+ COLUMN_NOTE_TEXT +" = + note_text + WHERE " +
                "" + COLUMN_ID + " = \"" +
                note_id + "\";" ;
        sqLiteDatabase.execSQL(deleteQuery);
        sqLiteDatabase.close();
    }

    public ArrayList<Note> dbToNoteObjectArrayList(){

        ArrayList<Note> noteArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String query = "SELECT * FROM "+ TABLE_NOTES +" WHERE 1 ORDER BY "+ COLUMN_CREATED_AT +
                "" + " DESC";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Note note = new Note();
            note.set_id(Integer.parseInt(cursor.getString(0)));
            note.setNoteText(cursor.getString(1));
            note.setCreatedAt(cursor.getString(2));
            noteArrayList.add(note);

            cursor.moveToNext();
        }
        return noteArrayList;
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
