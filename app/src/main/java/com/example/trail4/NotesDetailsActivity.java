package com.example.trail4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class NotesDetailsActivity extends AppCompatActivity {

    EditText titleEditText,contentEditText;
    ImageButton saveNoteBtn;
    TextView pageTitleTextView;
    String title,content,docId;
    TextView deleteNoteTextViewBtn;
    boolean isEditMode=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_notes_details);

        titleEditText=findViewById(R.id.notes_title_text);
        contentEditText=findViewById(R.id.notes_content_text);
        saveNoteBtn=findViewById(R.id.save_note_btn);
        pageTitleTextView=findViewById(R.id.page_title);
        deleteNoteTextViewBtn=findViewById(R.id.delete_note_text_view_btn);

        //receive data
        title=getIntent().getStringExtra("title");
        content=getIntent().getStringExtra("content");
        docId=getIntent().getStringExtra("docId");

        if (docId!=null && !docId.isEmpty()){
            isEditMode=true;
        }

        titleEditText.setText(title);
        contentEditText.setText(content);

        if (isEditMode){
            pageTitleTextView.setText("Edit Your Note");
            deleteNoteTextViewBtn.setVisibility(View.VISIBLE);
        }

        saveNoteBtn.setOnClickListener((v)->savenote());

        deleteNoteTextViewBtn.setOnClickListener((v)-> deleteNoteFromFirebase());

    }

    void savenote(){
        String noteTitle= titleEditText.getText().toString();
        String noteContent= contentEditText.getText().toString();

        if (noteTitle==null || noteTitle.isEmpty()){
            titleEditText.setError("Title Is Required");
            return;
        }

        Note note = new Note();
        note.setTitle(noteTitle);
        note.setContent(noteContent);
        note.setTimestamp(Timestamp.now());

        saveNoteToFirebase(note);

    }

    void saveNoteToFirebase(Note note){

        DocumentReference documentReference;
        documentReference= Utility.getCollectionReferenceForNotes().document();
        if(isEditMode){
            //update the note
            documentReference=Utility.getCollectionReferenceForNotes().document(docId);
        }else{
            //create the note
            documentReference=Utility.getCollectionReferenceForNotes().document();
        }

        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Utility.showToast(NotesDetailsActivity.this,"Note Added Successfully");
                    finish();
                }else{
                    Utility.showToast(NotesDetailsActivity.this,"Failed To Add The Note");
                }
            }
        });

    }


    void deleteNoteFromFirebase(){
        DocumentReference documentReference;
        documentReference= Utility.getCollectionReferenceForNotes().document();
        documentReference=Utility.getCollectionReferenceForNotes().document(docId);
        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //Note is deleted
                    Utility.showToast(NotesDetailsActivity.this,"Note Deleted Successfully");
                    finish();
                }else{
                    Utility.showToast(NotesDetailsActivity.this,"Failed To Delete The Note");
                }
            }
        });

    }


}