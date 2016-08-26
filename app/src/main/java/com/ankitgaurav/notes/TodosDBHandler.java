//package com.ankitgaurav.notes;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;
//
///**
// * Created by Ankit Gaurav on 26-08-2016.
// */
//public class TodosDBHandler extends SQLiteOpenHelper {
//    private static final int DATABASE_VERSION = 3;
//    private static final String DATABASE_NAME = "notesDB.db";
//    private static final String TABLE_TODOS = "todos_table";
//    private static final String COLUMN_TODOS_TEXT = "todos_text";
//    private static final String COLUMN_CREATED_AT = "created_at";
//    private static final String COLUMN_IS_COMPLETE = "is_complete";
//    private static final String COLUMN_ID = "todos_id";
//
//    public TodosDBHandler(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        String query1 = "CREATE TABLE " + TABLE_TODOS + " ( " +
//                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                COLUMN_IS_COMPLETE + " TEXT, DEFAULT FALSE" +
//                COLUMN_TODOS_TEXT + " TEXT, " +
//                COLUMN_CREATED_AT +" DATETIME );";
//        sqLiteDatabase.execSQL(query1);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
//        Log.w(MyDBHandler.class.getName(),
//                "Upgrading database from version " + oldVersion + " to "
//                        + newVersion + ", which will destroy all old data");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOS);
//        onCreate(sqLiteDatabase);
//    }
//
//    public void addNote(Todo todo){
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_NOTE_TEXT, todo.getNoteText());
//        values.put(COLUMN_CREATED_AT, note.getCreatedAt());
//        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
//        sqLiteDatabase.insert(TABLE_NOTES, null, values);
//        sqLiteDatabase.close();
//    }
//}
