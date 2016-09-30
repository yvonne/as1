package com.example.habittracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by yhoang1 on 2016-09-28.
 */
public class EditHabitActivity extends AppCompatActivity{

    // Position of entry to be edited
    int pos;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_habit);

        // Get position from HabitActivity
        Intent edit = getIntent();
        pos = edit.getIntExtra("position",0);

        // Complete habit and + 1 to the counter
        Button completeButton = (Button) findViewById(R.id.complete);
        completeButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                // Load values from the habitList
                String newDate = HabitActivity.habitList.get(pos).getDate();
                String newBody = HabitActivity.habitList.get(pos).getMessage();
                String newWeek = HabitActivity.habitList.get(pos).getWeek();
                int newComplete = HabitActivity.habitList.get(pos).getCompleted();

                // Increment by 1 every time user click complete
                newComplete ++;

                Habit newestHabit = new NormalHabit(newBody, newDate, newWeek, newComplete);

                // Remove old list and add in new list
                HabitActivity.habitList.remove(pos);
                HabitActivity.habitList.add(pos, newestHabit);

                saveInFile();
                finish();
            }
        });

        // Reset number of completions
        Button resetButton = (Button) findViewById(R.id.resetCom);
        resetButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String newDate = HabitActivity.habitList.get(pos).getDate();
                String newBody = HabitActivity.habitList.get(pos).getMessage();
                String newWeek = HabitActivity.habitList.get(pos).getWeek();

                // Make completions back to 0
                int newComplete = 0;

                Habit newestHabit = new NormalHabit(newBody, newDate, newWeek, newComplete);

                HabitActivity.habitList.remove(pos);
                HabitActivity.habitList.add(pos, newestHabit);
                saveInFile();
                finish();
            }
        });

        // Delete current habit
        Button deleteHabButton = (Button) findViewById(R.id.deleteHab);
        deleteHabButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                HabitActivity.habitList.remove(pos);

                saveInFile();
                finish();
            }
        });

        // Cancel and go back to previous window
        Button cancelButton = (Button) findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            //Code from http://stackoverflow.com/questions/15430787/android-go-back-to-previous-activity
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    protected void onStart(){
        super.onStart();
        loadFromFile();

    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(HabitActivity.FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            // Code from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            Type listType = new TypeToken<ArrayList<NormalHabit>>(){}.getType();

            HabitActivity.habitList = gson.fromJson(in,listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            HabitActivity.habitList = new ArrayList<Habit>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    protected void saveInFile() {

        try {
            FileOutputStream fos = openFileOutput(HabitActivity.FILENAME, 0);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(HabitActivity.habitList, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

}
