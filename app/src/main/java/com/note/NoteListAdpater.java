package com.note;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.List;

public class NoteListAdpater extends RecyclerView.Adapter<NoteListAdpater.ViewHolder> {
    List<NoteData> noteDataList;
    Context context;
    TextInputLayout noteInput;
    DatabaseHelper databaseHelper;
    NoteListAdpater adpater;
    public  NoteListAdpater(Context context, List<NoteData> noteDataList){
        this.context =context;
        this.noteDataList = noteDataList;
        this.noteInput = noteInput;
        this.adpater = this;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.note_list_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final NoteData noteData =  noteDataList.get(position);
        holder.noteTextView.setText(noteData.getNoteText());

        holder.noteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MainActivity.class);
                intent.putExtra("noteId",noteData.getId());
                intent.putExtra("noteText",noteData.getNoteText());
                intent.putExtra("editNotice",true);
                context.startActivity(intent);
            }
        });
        holder.noteCreatedOn.setText(new PrettyTime().format(new Date(noteData.getInsertedDate())));
    }

    @Override
    public int getItemCount() {
        return noteDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView noteTextView,noteCreatedOn;
        public ImageButton deleteNoteBtn;
        public LinearLayout noteLayout;
        public ViewHolder(View view){
            super(view);
            noteCreatedOn =view.findViewById(R.id.createdOn);
            noteTextView = view.findViewById(R.id.noteText);
            noteLayout = view.findViewById(R.id.noteLayout);
        }
    }

    public NoteData  getDataFromList(int position){
        return  noteDataList.get(position);
    }



}
