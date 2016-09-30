package com.example.habittracker;


/**
 * Created by yhoang1 on 2016-09-27.
 */
public class NormalHabit extends Habit implements Habitable{
    public NormalHabit(String message, String date, String week, int completed){
        super(message, date, week, completed);
    }
}
