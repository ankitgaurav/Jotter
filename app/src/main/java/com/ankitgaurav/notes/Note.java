package com.ankitgaurav.notes;

import java.util.Date;

/**
 * Created by Ankit Gaurav on 17-08-2016.
 */
public class Note {
    private int _id;
    private String noteText;
    private String createdAt;
    private int isLocked;

    public Note() {
    }

    public Note(int _id, String noteText, String createdAt) {
        this._id = _id;
        this.noteText = noteText;
        this.createdAt = createdAt;
    }

    public int get_id() {
        return _id;
    }

    public int getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(int isLocked) {
        this.isLocked = isLocked;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
