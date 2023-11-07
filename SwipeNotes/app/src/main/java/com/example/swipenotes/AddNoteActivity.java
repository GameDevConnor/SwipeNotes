package com.example.swipenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import io.realm.Realm;

public class AddNoteActivity extends AppCompatActivity {

    Button saveNote;
    EditText title;
    EditText body;
    private Note note;

    private boolean update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        saveNote = findViewById(R.id.savenote);
        title = findViewById(R.id.titleinput);
        body = findViewById(R.id.notebody);



        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleText = title.getText().toString();
                String notebody = body.getText().toString();
                long createdTime = System.currentTimeMillis();


                realm.beginTransaction();

                Note note = realm.createObject(Note.class);

                note.setDescription(notebody);
                note.setTitle(titleText);
                note.setTimeCreated(createdTime);

                realm.commitTransaction();

                Toast.makeText(getApplicationContext(), "Note Saved", Toast.LENGTH_SHORT).show();

                finish();

            }
        });
    }


}