package com.ccseevents.owl;

import androidx.annotation.NonNull;

public class MyViewModel {

    private Integer id;
    private int dayOfWeek;
    private String day;
    private int month;
    private String year;
    private String fromTime;
    private String toTime;
    private String title;
    private String description;
    private String host;

    public MyViewModel() {}

    public MyViewModel(MyViewModel v) {
        this.id = v.id;
        this.dayOfWeek = v.dayOfWeek;
        this.day = v.day;
        this.month = v.month;
        this.year = v.year;
        this.fromTime = v.fromTime;
        this.toTime = v.toTime;
        this.title = v.title;
        this.description = v.description;
        this.host = v.host;
    }

    public String getDayOfWeek() {
        String textDayofWeek = "Unk";
        if (this.dayOfWeek == 0) {
            textDayofWeek = "Sun";
        }
        if (this.dayOfWeek == 1) {
            textDayofWeek = "Mon";
        }
        if (this.dayOfWeek == 2) {
            textDayofWeek = "Tue";
        }
        if (this.dayOfWeek == 3) {
            textDayofWeek = "Wed";
        }
        if (this.dayOfWeek == 4) {
            textDayofWeek = "Thu";
        }
        if (this.dayOfWeek == 5) {
            textDayofWeek = "Fri";
        }
        if (this.dayOfWeek == 6) {
            textDayofWeek = "Sat";
        }
        return textDayofWeek;
    }

    public void setDayOfWeek(final int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getDay() {
        return this.day;
    }

    public void setDay(final String day) {
        this.day = day;
    }

    public String getMonth() {
        String textMonth = "Unk";
        if (this.month == 1){
            textMonth = "Jan";
        }
        if (this.month == 2){
            textMonth = "Feb";
        }
        if (this.month == 3){
            textMonth = "Mar";
        }
        if (this.month == 4){
            textMonth = "Apr";
        }
        if (this.month == 5){
            textMonth = "May";
        }
        if (this.month == 6){
            textMonth = "Jun";
        }
        if (this.month == 7){
            textMonth = "Jul";
        }
        if (this.month == 8){
            textMonth = "Aug";
        }
        if (this.month == 9){
            textMonth = "Sep";
        }
        if (this.month == 10){
            textMonth = "Oct";
        }
        if (this.month == 11){
            textMonth = "Nov";
        }
        if (this.month == 12) {
            textMonth = "Dec";
        }
        return textMonth;
    }

    public void setMonth(final int month) {
        this.month = month;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(final String year) {
        this.year = year;
    }

    public String getFromTime() {
        return this.fromTime;
    }

    public void setFromTime(final String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return this.toTime;
    }

    public void setToTime(final String toTime) {
        this.toTime = toTime;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(final String host) {
        this.host = host;
    }
}