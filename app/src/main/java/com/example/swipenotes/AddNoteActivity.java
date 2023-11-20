package com.example.swipenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;



import java.text.DateFormat;

import io.realm.Realm;

public class AddNoteActivity extends AppCompatActivity {

    ImageButton saveNote;
    EditText title;

    FloatingActionButton boldFab, italicFab;
    EditText body;
    private Note note;

    private boolean update;

    private GestureDetector gestureDetector;
    private Button swipeButton;
    private EditText editText;

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d("Gesture", "onFling: " + e1 + ", " + e2);
            float distanceX = e2.getX() - e1.getX();
            float distanceY = e2.getY() - e1.getY();

            if (Math.abs(distanceX) > Math.abs(distanceY) &&
                    Math.abs(distanceX) > SWIPE_THRESHOLD &&
                    Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (distanceX > 0) {
                    // Swipe right (add italic)
                    setItalic(editText);
                } else {
                    // Swipe left (add bold)
                    setBold(editText);
                }
                return true;
            }

            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);


        boldFab = findViewById(R.id.boldFab);
        italicFab = findViewById(R.id.italicFab);
        saveNote = findViewById(R.id.savenote);
        title = findViewById(R.id.titleinput);
        body = findViewById(R.id.notebody);

        boldFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBold(body);
            }
        });

        italicFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setItalic(body);
            }
        });

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

                Toast.makeText(getApplicationContext(), "Note " + '"'+ titleText +'"' + " Saved", Toast.LENGTH_SHORT).show();

                finish();

            }
        });

        gestureDetector = new GestureDetector(this, new MyGestureListener());
        swipeButton = findViewById(R.id.swipeButton);
        editText = findViewById(R.id.notebody);

        swipeButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
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


        if (titleText.length() == 0 || notebody.length() == 0) {
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

            Toast.makeText(getApplicationContext(), "Note " + '"'+ titleText +'"' + " Saved", Toast.LENGTH_SHORT).show();

            finish();
        }
    }

    public void setBold(EditText edit) {
        StyleSpan bold = new StyleSpan(Typeface.BOLD);

        Editable editable = edit.getText();
        int selectionStart = edit.getSelectionStart();
        int selectionEnd = edit.getSelectionEnd();

        StyleSpan[] styleSpans = editable.getSpans(selectionStart, selectionEnd, StyleSpan.class);

        if (styleSpans.length > 0) {
            int notBoldCount = 0;
            int boldCount = 0;
            // Remove all bold spans in the selected range
            for (StyleSpan span : styleSpans) {
                int spanStart = editable.getSpanStart(span);
                int spanEnd = editable.getSpanEnd(span);



                if ((spanStart < selectionEnd && spanEnd > selectionStart)) {

                    if (span.getStyle() == Typeface.BOLD) {
                        editable.removeSpan(span);
                        boldCount++;
                    }
                    else {
                        notBoldCount++;
                    }




                    // Add a new span for the unbolded part before the selection
                    if (spanStart < selectionStart) {
                        editable.setSpan(bold, spanStart, selectionStart, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }

                    // Add a new span for the unbolded part after the selection
                    if (spanEnd > selectionEnd) {
                        editable.setSpan(bold, selectionEnd, spanEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            }

            if (boldCount == 0 && notBoldCount > 0) {
                editable.setSpan(bold, selectionStart, selectionEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                edit.setText(editable);
            }

            edit.setText(editable);
        } else {
            // If no bold spans found, add a new bold span to the entire selected range
            editable.setSpan(bold, selectionStart, selectionEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            edit.setText(editable);
        }

        edit.setSelection(selectionEnd);
    }

    public void setItalic(EditText edit) {
        StyleSpan italic = new StyleSpan(Typeface.ITALIC);

        Editable editable = edit.getText();
        int selectionStart = edit.getSelectionStart();
        int selectionEnd = edit.getSelectionEnd();

        StyleSpan[] styleSpans = editable.getSpans(selectionStart, selectionEnd, StyleSpan.class);

        if (styleSpans.length > 0) {
            // Remove all italic spans in the selected range

            int notItalicCount = 0;
            int italicCount = 0;

            for (StyleSpan span : styleSpans) {
                int spanStart = editable.getSpanStart(span);
                int spanEnd = editable.getSpanEnd(span);



                if ((spanStart < selectionEnd && spanEnd > selectionStart)) {

                    if (span.getStyle() == Typeface.ITALIC) {
                        editable.removeSpan(span);
                        italicCount++;
                    }
                    else {
                        notItalicCount++;
                    }





                    // Add a new span for the unitaliced part before the selection
                    if (spanStart < selectionStart) {
                        editable.setSpan(italic, spanStart, selectionStart, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }

                    // Add a new span for the unitaliced part after the selection
                    if (spanEnd > selectionEnd) {
                        editable.setSpan(italic, selectionEnd, spanEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            }

            if (italicCount == 0 && notItalicCount > 0) {
                editable.setSpan(italic, selectionStart, selectionEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                edit.setText(editable);
            }

            edit.setText(editable);
        } else {
            // If no italic spans found, add a new italic span to the entire selected range
            editable.setSpan(italic, selectionStart, selectionEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            edit.setText(editable);
        }

        edit.setSelection(selectionEnd);
    }
}