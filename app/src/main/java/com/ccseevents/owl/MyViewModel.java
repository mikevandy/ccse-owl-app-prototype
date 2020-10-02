package com.ccseevents.owl;

import androidx.annotation.NonNull;

public class MyViewModel {
    private String myText;

    public MyViewModel(@NonNull final String myText) {
        setMyText(myText);
    }

    @NonNull
    public String getMyText() {
        return myText;
    }

    public void setMyText(@NonNull final String myText) {
        this.myText = myText;
    }
}