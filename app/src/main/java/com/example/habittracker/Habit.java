package com.example.habittracker;

/**
 * Created by yhoang1 on 2016-09-27.
 */
public abstract class Habit {
    protected String message;
    protected String date;
    protected String week;
    protected int completed;

    public Habit(String message, String date, String week, int completed){
        this.message = message;
        this.date = date;
        this.week = week;
        this.completed = completed;
    }//end Habit


    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getWeek() {
        return week;
    }

    public int getCompleted() {
        return completed;
    }

    @Override
    public String toString(){
        return  message + " | " + "Started on: " + date + " | " + "Occurs on: " + week + " | " + "Completed " + completed + " time(s)";
    }
}
