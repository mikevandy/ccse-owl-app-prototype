package com.ccseevents.owl;

import androidx.annotation.NonNull;

public class MyViewModel {

    private Integer id;
    private String dayOfWeek;
    private String day;
    private String month;
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
        return this.dayOfWeek;
    }

    public void setDayOfWeek(final String dayOfWeek) {
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
        return this.month;
    }

    public void setMonth(final String month) {
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