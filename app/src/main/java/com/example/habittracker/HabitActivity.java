package com.example.habittracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class HabitActivity extends AppCompatActivity {

    protected static final String FILENAME = "file.sav";
    protected ListView oldHabitsList;

    public static ArrayList<Habit> habitList = new ArrayList<Habit>();
    public static ArrayAdapter<Habit> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        oldHabitsList = (ListView) findViewById(R.id.oldHabitsList);

        // When you click a habit
        // Code from http://stackoverflow.com/questions/19662233/how-open-new-activity-clicking-an-item-in-listview
        oldHabitsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent edit = new Intent(HabitActivity.this, EditHabitActivity.class);
                int pos = position;

                edit.putExtra("position", pos);
                startActivity(edit);

            }});

        // Add new Habit button
        Button saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Code from http://stackoverflow.com/questions/10407159/how-to-manage-startactivityforresult-on-android
                Intent i = new Intent(HabitActivity.this, AddHabit.class);
                startActivityForResult(i, 1);
            }
        });

        // Delete all habit button
        Button deleteAllButton = (Button) findViewById(R.id.deleteAll);
        deleteAllButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                habitList.clear();
                deleteFile(FILENAME);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
        adapter = new ArrayAdapter<Habit>(this,
                R.layout.list_item, habitList);
        oldHabitsList.setAdapter(adapter);
    }

    @Override
    protected void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            // Code from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            Type listType = new TypeToken<ArrayList<NormalHabit>>(){}.getType();

            habitList = gson.fromJson(in,listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            habitList = new ArrayList<Habit>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}

