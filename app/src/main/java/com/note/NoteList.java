package com.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class NoteList extends AppCompatActivity  {

    DatabaseHelper databaseHelper;
    RecyclerView recyclerView;
    NoteListAdpater noteListAdpater;
    FloatingActionButton addNote;
    List<NoteData> noteDataList;
    MaterialTextView swipeDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        recyclerView = (RecyclerView) findViewById(R.id.noteList);
        databaseHelper = new DatabaseHelper(this);
        addNote =(FloatingActionButton)findViewById(R.id.addNote);
        noteDataList = databaseHelper.getListOfNote();
        noteListAdpater = new NoteListAdpater(this,noteDataList);
        noteListAdpater.notifyDataSetChanged();
        swipeDelete=(MaterialTextView)findViewById(R.id.swipedelete);

        if(noteDataList.size()>0){
            swipeDelete.setText("Swipe to delete");
        }
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                NoteList.this.startActivity(intent);
                noteListAdpater.notifyDataSetChanged();
            }
        });

        LinearLayoutManager layoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(noteListAdpater);
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView);
        noteListAdpater.notifyDataSetChanged();
    }
    ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT |ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            NoteData noteData = noteDataList.get(viewHolder.getAdapterPosition());
            databaseHelper.deleteNote(noteData.getId());
            noteDataList.remove(viewHolder.getAdapterPosition());
            noteListAdpater.notifyDataSetChanged();
        }
    };


}
