package com.example.swipenotes;



import java.text.DateFormat;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Note extends RealmObject {


    RealmList<SpanContainer> spanContainers = new RealmList<SpanContainer>();

    String title;

//    long timeCreated;
    @PrimaryKey
    String timeCreated;

    String description;

    public Note() {
        this.timeCreated = DateFormat.getDateTimeInstance().format(System.currentTimeMillis());

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


//    public long getTimeCreated() {
//        return timeCreated;
//    }
//
//    public void setTimeCreated(long timeCreated) {
//        this.timeCreated = timeCreated;
//    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public /*List<SpanContainer>*/ String getSpanContainers() {

//        return this.spanContainers;

        String spans = "";

        for (SpanContainer spanContainer : this.spanContainers) {
            spans += spanContainer.getSpan() + "," + spanContainer.getBegin() + "," + spanContainer.getEnd() + "_";
        }

        return spans;
    }

    public RealmList<SpanContainer> getSpanContainerList() {

        return this.spanContainers;

    }

    public void addSpanContainers(SpanContainer spanContainer) {

        this.spanContainers.add(spanContainer);

    }
}
