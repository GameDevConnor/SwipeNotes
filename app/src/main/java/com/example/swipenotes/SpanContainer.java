package com.example.swipenotes;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.style.StyleSpan;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class SpanContainer implements RealmModel {

    String span;
    int begin;
    int end;

    public SpanContainer() {

    }

    public SpanContainer(String span, int begin, int end) {
        this.span = span;
        this.begin = begin;
        this.end = end;
    }

    public int getBegin() {
        return this.begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        return this.end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getSpan() {
        return this.span;
    }

    public void setSpan(String span) {
        this.span = span;
    }
}