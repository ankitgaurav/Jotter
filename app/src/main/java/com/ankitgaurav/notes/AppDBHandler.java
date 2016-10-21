package com.ankitgaurav.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankit Gaurav on 31-Aug-16.
 */
public class AppDBHandler extends SQLiteOpenHelper {
    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    //Database name and version
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "appDB.db";

    //Table names
    private static final String TABLE_TODOS = "todos";
    private static final String TABLE_NOTES = "notes";

    //Common columns
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_CREATED_AT = "created_at";
    private static final String COLUMN_IS_FLAGGED = "is_flagged";

    //todos table colums
    private static final String COLUMN_TODO_TEXT = "todos_text";
    private static final String COLUMN_IS_COMPLETE = "is_complete";

    //notes table columns
    private static final String COLUMN_NOTE_TEXT = "notes_text";
    private static final String COLUMN_IS_LOCKED = "is_locked";

    // Table Create Statements
    // Todos table create statement
    private static final String CREATE_TABLE_TODOS = "CREATE TABLE "+ TABLE_TODOS + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TODO_TEXT + " TEXT, " +
            COLUMN_IS_COMPLETE + " TEXT, " +
            COLUMN_IS_FLAGGED + " TEXT, " +
            COLUMN_CREATED_AT + " DATETIME " + ")";
    //Notes table create statement
    private static final String CREATE_TABLE_NOTES = "CREATE TABLE " + TABLE_NOTES + " ( " +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NOTE_TEXT + " TEXT, " +
            COLUMN_IS_LOCKED + " INTEGER DEFAULT 0, " +
            COLUMN_IS_FLAGGED + " INTEGER DEFAULT 0, " +
            COLUMN_CREATED_AT +" DATETIME );";


    public AppDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // creating required tables
        sqLiteDatabase.execSQL(CREATE_TABLE_TODOS);
        sqLiteDatabase.execSQL(CREATE_TABLE_NOTES);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        // create new tables
        onCreate(sqLiteDatabase);
    }

    // -------------- Notes table methods ------------------ //
    //create note
    public void addNote(Note note){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_TEXT, note.getNoteText());
        values.put(COLUMN_CREATED_AT, note.getCreatedAt());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert(TABLE_NOTES, null, values);
        sqLiteDatabase.close();
    }
    //get single note
    public Note getNoteById(int id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NOTES + " WHERE " + COLUMN_ID + " = " + id;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        Note note = new Note();
        note.set_id(Integer.parseInt(cursor.getString(0)));
        note.setNoteText(cursor.getString(1));
        note.setCreatedAt(cursor.getString(2));
        return note;
    }
    //get multiple notes
    public ArrayList<Note> getMultipleNotesArrayList(){

        ArrayList<Note> noteArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NOTES + " WHERE " + COLUMN_IS_LOCKED + " = 0 " +
                "ORDER BY " + COLUMN_CREATED_AT + "" + " DESC";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Note note = new Note();
            note.set_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
            note.setNoteText(cursor.getString(cursor.getColumnIndex(COLUMN_NOTE_TEXT)));
            note.setCreatedAt(cursor.getString(cursor.getColumnIndex(COLUMN_CREATED_AT)));
            note.setIsLocked(cursor.getInt(cursor.getColumnIndex(COLUMN_IS_LOCKED)));
            noteArrayList.add(note);

            cursor.moveToNext();
        }
        return noteArrayList;
    }
    //delete single note
    public void deleteNote(int note_id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_NOTES + " WHERE " + COLUMN_ID + " = \"" +
                note_id + "\";" ;
        sqLiteDatabase.execSQL(deleteQuery);
        sqLiteDatabase.close();
    }
    //update single note
    public void updateNote(int note_id, Note note){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = "UPDATE " + TABLE_NOTES + " SET " +
                COLUMN_NOTE_TEXT +" = \"" + note.getNoteText() +
                "\" WHERE " +  COLUMN_ID +
                " = " + note_id + ";" ;
        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.close();
    }

    //toggle lock state of notes
    public int toggleNoteLock(int n_id, int n_isLocked) {
        int new_lock_state = 1 - n_isLocked;
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = "UPDATE " + TABLE_NOTES + " SET " + COLUMN_IS_LOCKED +
                " = " + new_lock_state + " WHERE " + COLUMN_ID + " = " + n_id + " ;";
        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.close();
        return new_lock_state;
    }


    // ------------- Todos table methods -------------------- //4
    //Add single todoItem
    public void addTodo(Todo todo){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TODO_TEXT, todo.getTodoText());
        contentValues.put(COLUMN_CREATED_AT, todo.getCreatedAt());
        contentValues.put(COLUMN_IS_COMPLETE, "false");
        contentValues.put(COLUMN_IS_FLAGGED, "false");
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert(TABLE_TODOS, null, contentValues);
        sqLiteDatabase.close();
    }
    //get single item from todos
    public Todo getTodo(int id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_TODOS + " WHERE "
                + COLUMN_ID + " = " + id;
        Log.e(LOG, selectQuery);

        Cursor c = sqLiteDatabase.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();

        Todo todo = new Todo();
        todo.set_id(c.getInt(c.getColumnIndex(COLUMN_ID)));
        todo.setTodoText((c.getString(c.getColumnIndex(COLUMN_TODO_TEXT))));
        todo.setCreatedAt(c.getString(c.getColumnIndex(COLUMN_CREATED_AT)));
        todo.setIs_complete((c.getString(c.getColumnIndex(COLUMN_IS_COMPLETE))));
        return todo;
    }
    //get multiple todos
    public ArrayList<Todo> getMultipleTodosArrayList(String filter){
        ArrayList<Todo> todosList = new ArrayList<>();
        String condition = "";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        if(filter.equals("all")){
            condition = "1";
        }
        else if(filter.equals("incomplete")){
            condition = COLUMN_IS_COMPLETE +"  = \"false\" ";
        }
        String query = "SELECT * FROM " + TABLE_TODOS + " WHERE " + condition + " ORDER BY "+
                COLUMN_CREATED_AT +
                "" + " DESC";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Todo todo = new Todo();
                todo.set_id(cursor.getInt((cursor.getColumnIndex(COLUMN_ID))));
                todo.setTodoText((cursor.getString(cursor.getColumnIndex(COLUMN_TODO_TEXT))));
                todo.setCreatedAt(cursor.getString(cursor.getColumnIndex(COLUMN_CREATED_AT)));
                todo.setIs_complete(cursor.getString(cursor.getColumnIndex(COLUMN_IS_COMPLETE)));
                todo.setIs_flagged(cursor.getString(cursor.getColumnIndex(COLUMN_IS_FLAGGED)));
                todosList.add(todo);
            } while (cursor.moveToNext());
        }

        return todosList;
    }
    //delete single todoitem
    public void deleteTodo(int id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_TODOS + " WHERE " + COLUMN_ID + " = " +
                id + ";" ;
        sqLiteDatabase.execSQL(deleteQuery);
        sqLiteDatabase.close();
    }
    //update single todoItem
    public void updateTodo(int id, Todo todo){
        String status = todo.getIs_complete();
        if(status.equals("false")){
            status = "true";
        }
        else{
            status = "false";
        }
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = "UPDATE " + TABLE_TODOS + " SET " + COLUMN_IS_COMPLETE +
                " = \"" + status + "\" WHERE " + COLUMN_ID + " = " + id + " ;";
        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.close();
    }
    //flag todoItem
    public void flagTodo(int id, String isFlagged){
        if(isFlagged.equals("true")){
            isFlagged = "false";
        }
        else{
            isFlagged = "true";
        }
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = "UPDATE " + TABLE_TODOS + " SET " + COLUMN_IS_FLAGGED +
                " = \"" + isFlagged + "\" WHERE " + COLUMN_ID + " = " + id + " ;";
        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.close();
    }

}
