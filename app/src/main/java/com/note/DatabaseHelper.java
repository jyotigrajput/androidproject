package com.note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private  static final String DATABASE_NAME="noteDB";
    private static final String TABLE_NAME = "notesTbl";
    private  static final String ID="id";
    private  static final String NOTE="note";
    private  static final String INSERTEDDATE="insertedDate";

    public  DatabaseHelper(Context context){
       super(context,DATABASE_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
            String query =" CREATE TABLE "+TABLE_NAME+ "("+ID+" integer primary key  autoincrement, "+NOTE+" varchar(1000),"+ INSERTEDDATE+" datetime)";
            db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public  boolean addNote(NoteData note){
       SQLiteDatabase db  = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME,new String[]{ID,NOTE},ID+"=?",new String[]{String.valueOf(note.getId())},null,null,null,null);

        ContentValues contentValues  =new ContentValues();
        contentValues.put(NOTE,note.getNoteText());
        contentValues.put(INSERTEDDATE,new Date().toString());

        long result=0;
         if(cursor.getCount()>0){
           cursor.moveToFirst();
            result = db.update(TABLE_NAME,contentValues,ID+"=?",new String[]{note.getId()+""});

         }else{
            result = db.insert(TABLE_NAME,null,contentValues);
      }

        db.close();
        if(result!=-1 || result!=0){
            return  true;
        }
        return  false;
    }

    public List<NoteData> getListOfNote(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME;
        Cursor cursor= db.rawQuery(query,null);
        List<NoteData> arrayList =new ArrayList<>();
        Log.e("cursor size",cursor.getCount()+"");
        if (cursor.moveToFirst()) {
            do {

                NoteData note =new NoteData(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
                arrayList.add(note);
            } while (cursor.moveToNext());
        }

        return  arrayList;
    }

    public boolean deleteNote(int id){

        SQLiteDatabase db = this.getWritableDatabase();
        int res= db.delete(TABLE_NAME,ID+"=?",new String[]{id+""});
        Log.e("delete",res+""+id);
        db.close();
        return  true;
    }

}
