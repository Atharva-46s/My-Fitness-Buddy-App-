package com.example.trail4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Query;

public class Notes extends AppCompatActivity {

    FloatingActionButton addNoteBtn;
    RecyclerView recyclerView;
    ImageButton menuBtn;
    NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_notes);

        addNoteBtn=findViewById(R.id.add_note_btn);
        recyclerView=findViewById(R.id.recycler_view);
        menuBtn=findViewById(R.id.menu_btn);



        addNoteBtn.setOnClickListener((v)->startActivity(new Intent(Notes.this,NotesDetailsActivity.class)));

        menuBtn.setOnClickListener((v)->showMenu());
        setupRecyclerView();

    }

    void setupRecyclerView() {

        Query query= Utility.getCollectionReferenceForNotes().orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Note> options= new FirestoreRecyclerOptions.Builder<Note>().setQuery(query,Note.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(options, this);
        recyclerView.setAdapter(noteAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        noteAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        noteAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        noteAdapter.notifyDataSetChanged();
    }

    void showMenu(){
        PopupMenu popupMenu=new PopupMenu(Notes.this,menuBtn);
        popupMenu.getMenu().add("Home");
        popupMenu.getMenu().add("Search");
        popupMenu.getMenu().add("Favourites");
        popupMenu.getMenu().add("Support");
        popupMenu.getMenu().add("About");
        popupMenu.getMenu().add("Logout");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getTitle()=="Logout"){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(Notes.this,login2_SignUpDone.class));
                    finish();
                    return true;
                }
                return false;
            }
        });

    }
}