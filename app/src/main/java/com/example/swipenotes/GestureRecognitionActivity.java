package com.example.swipenotes;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class GestureRecognitionActivity extends AppCompatActivity {

    private GestureDetector gestureDetector;
    private Button swipeButton;
    private EditText editText;
    private AddNoteActivity addNoteActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        // Retrieve AddNoteActivity instance from Intent
        addNoteActivity = (AddNoteActivity) getIntent().getSerializableExtra("AddNoteActivity");

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
                    addNoteActivity.setItalic(editText);
                } else {
                    // Swipe left (add bold)
                    addNoteActivity.setBold(editText);
                }
                return true;
            }

            return false;
        }
    }
}


