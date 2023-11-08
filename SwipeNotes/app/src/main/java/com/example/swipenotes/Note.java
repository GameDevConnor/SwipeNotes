package com.example.swipenotes;



import io.realm.RealmObject;

public class Note extends RealmObject {

//    public int getNoteId() {
//        return noteId;
//    }
//
//    public void setNoteId(int noteId) {
//        this.noteId = noteId;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }

//    @PrimaryKey(autoGenerate = true)
//            private int noteId;
//
//    @ColumnInfo(name="note_body")
//            private String content;

    String title;
    long timeCreated;
    String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
