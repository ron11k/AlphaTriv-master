package com.example.ronik.myticketproject1;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.widget.RadioButton;

public class MyRadioButton extends AppCompatRadioButton {

    boolean isTheCorrectAnswer = false;

    public boolean isTheCorrectAnswer() {
        return isTheCorrectAnswer;
    }

    public void setTheCorrectAnswer(boolean theCorrectAnswer) {
        isTheCorrectAnswer = theCorrectAnswer;
    }

    public MyRadioButton(Context context) {
        super(context);
    }

    public MyRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
