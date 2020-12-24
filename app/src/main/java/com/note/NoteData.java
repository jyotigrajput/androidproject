package com.note;

public class NoteData {
    int id;
    String noteText;
    String insertedDate;

    public  NoteData(){}
    public NoteData(int id,String noteText, String insertedDate) {
        this.id= id;
        this.noteText = noteText;
        this.insertedDate = insertedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getInsertedDate() {
        return insertedDate;
    }

    public void setInsertedDate(String insertedDate) {
        this.insertedDate = insertedDate;
    }
}
