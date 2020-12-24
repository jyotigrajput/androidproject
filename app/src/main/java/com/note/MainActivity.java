package com.note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextInputLayout noteInput;
    MaterialButton saveBtn;
    DatabaseHelper databaseHelper;
    boolean editNotice= false;
    int Id;
    NoteListAdpater noteListAdpater;
    String noteText;
    boolean editText= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noteInput = (TextInputLayout)findViewById(R.id.noteInput);
        saveBtn =(MaterialButton)findViewById(R.id.saveBtn);
        databaseHelper = new DatabaseHelper(this);
         Id= getIntent().getIntExtra("noteId",0);
         noteText = getIntent().getStringExtra("noteText");
         editText =  getIntent().getBooleanExtra("editText",false);
        if(noteText!=null){
            noteInput.getEditText().setText(noteText);
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText){
                    noteText = noteInput.getEditText().getText().toString();
                }

                if(noteText.length()<=0){
                    noteInput.setError("Please add some text");
                }else{
                    NoteData note =new NoteData(Id,noteText,new Date().toString());
                    boolean result = databaseHelper.addNote(note);
                    if(result) {
                        Toast.makeText(getApplicationContext(),"Note added",Toast.LENGTH_SHORT).show();
                        MainActivity.this.startActivity(new Intent(MainActivity.this, NoteList.class));
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Some thing added",Toast.LENGTH_SHORT).show();
               }
             }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String noteText  =noteInput.getEditText().getText().toString();
            if(noteText!=null && noteText.length()>0){
                NoteData note =new NoteData(Id,noteText,new Date().toString());
                boolean result = databaseHelper.addNote(note);
                if(result) {
                    Toast.makeText(getApplicationContext(),"Note added",Toast.LENGTH_SHORT).show();
                    MainActivity.this.startActivity(new Intent(MainActivity.this, NoteList.class));
                } else{
                    Toast.makeText(getApplicationContext(),"Some thing added",Toast.LENGTH_SHORT).show();
                }
            }
        }

}
