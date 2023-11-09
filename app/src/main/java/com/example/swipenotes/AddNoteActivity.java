package com.example.swipenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.text.DateFormat;

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


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title.setText(bundle.getString("title"));
            body.setText(bundle.getString("body"));
        }

        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleText = title.getText().toString();
                String notebody = body.getText().toString();
//                long createdTime = System.currentTimeMillis();
//                String createdTime = DateFormat.getDateTimeInstance().format(System.currentTimeMillis());


                realm.beginTransaction();

                Note note;


                if (bundle == null) {
//                    note = realm.createObject(Note.class, note.getTimeCreated());
//                    note.setTimeCreated(createdTime);
                    note = new Note();

                } else {
                    note = realm.where(Note.class).equalTo("timeCreated", bundle.getString("timeCreated")).findFirst();
                }


                note.setDescription(notebody);
                note.setTitle(titleText);


                realm.copyToRealmOrUpdate(note);

                realm.commitTransaction();

                Toast.makeText(getApplicationContext(), "Note Saved", Toast.LENGTH_SHORT).show();

                finish();

            }
        });
    }

    // This method is called when the user clicks the back button.
    // it saves the note if it is new or updates the note if notes was already saved
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        String titleText = title.getText().toString();
        String notebody = body.getText().toString();


        if (titleText.isEmpty() || notebody.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Note not saved. Title or Note content is empty", Toast.LENGTH_SHORT).show();
            finish();
        }

        else {
            Realm.init(getApplicationContext());
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            Note note;

            Bundle bundle = getIntent().getExtras();
            if (bundle == null) {
                note = new Note();
            } else {
                note = realm.where(Note.class).equalTo("timeCreated", bundle.getString("timeCreated")).findFirst();
            }

            note.setDescription(notebody);
            note.setTitle(titleText);

            realm.copyToRealmOrUpdate(note);

            realm.commitTransaction();

            Toast.makeText(getApplicationContext(), "Note Saved", Toast.LENGTH_SHORT).show();

            finish();
        }
    }
}