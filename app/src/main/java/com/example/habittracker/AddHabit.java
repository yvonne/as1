package com.example.habittracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by yhoang1 on 2016-09-27.
 */
public class AddHabit extends AppCompatActivity {

    private EditText bodyText;
    private EditText dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popwindow);

        dateText = (EditText) findViewById(R.id.editDate);
        bodyText = (EditText) findViewById(R.id.habitInput);

        // Automatically generate date
        //Code from http://stackoverflow.com/questions/21917107/automatic-date-and-time-in-edittext-android
        EditText autoDate = (EditText)findViewById(R.id.editDate);
        final SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        final String date = dateF.format(Calendar.getInstance().getTime());
        autoDate.setText(date);

        // Cancel and go back to previous window
        Button cancelButton = (Button) findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            //Code from http://stackoverflow.com/questions/15430787/android-go-back-to-previous-activity
            public void onClick(View v) {
               onBackPressed();
            }

        });

        // Add new habit and return to first activity
        Button addButton = (Button) findViewById(R.id.addNew);
        addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                setResult(RESULT_OK);

                // Load checkboxes
                // Referenced from http://stackoverflow.com/questions/18498296/working-with-checkbox-checkbox-string-value-will-transfer-to-edittext-when-chec
                CheckBox checkMon = (CheckBox) findViewById(R.id.monday);
                CheckBox checkTue = (CheckBox) findViewById(R.id.tuesday);
                CheckBox checkWed = (CheckBox) findViewById(R.id.wednesday);
                CheckBox checkThu = (CheckBox) findViewById(R.id.thursday);
                CheckBox checkFri = (CheckBox) findViewById(R.id.friday);
                CheckBox checkSat = (CheckBox) findViewById(R.id.saturday);
                CheckBox checkSun = (CheckBox) findViewById(R.id.sunday);
                String weekInput = "";

                // Check which day of the week are checked off
                if(checkMon.isChecked()){
                    weekInput = weekInput + "Mon";
                    if(weekInput.length() > 0){
                        weekInput = weekInput + ", ";
                    }
                }
                if(checkTue.isChecked()){
                    weekInput = weekInput + "Tues";
                    if(weekInput.length() > 0){
                        weekInput = weekInput + ", ";
                    }
                }
                if(checkWed.isChecked()){
                    weekInput = weekInput + "Wed";
                    if(weekInput.length() > 0){
                        weekInput = weekInput + ", ";
                    }
                }
                if(checkThu.isChecked()){
                    weekInput = weekInput + "Thurs";
                    if(weekInput.length() > 0){
                        weekInput = weekInput + ", ";
                    }
                }
                if(checkFri.isChecked()){
                    weekInput = weekInput + "Fri";
                    if(weekInput.length() > 0){
                        weekInput = weekInput + ", ";
                    }
                }
                if(checkSat.isChecked()){
                    weekInput = weekInput + "Sat";
                    if(weekInput.length() > 0){
                        weekInput = weekInput + ", ";
                    }
                }
                if(checkSun.isChecked()){
                    weekInput = weekInput + "Sun";
                    if(weekInput.length() > 0){
                        weekInput = weekInput + ", ";
                    }
                }

                // Delete extra comma
                // http://stackoverflow.com/questions/7438612/how-to-remove-the-last-character-from-a-string
                if (weekInput.length() > 0){
                    weekInput = weekInput.substring(0, weekInput.length()-2);
                }

                // Get the user input of the date and habit
                String dateInput = dateText.getText().toString();
                String bodyInput = bodyText.getText().toString();
                int completeHab = 0;

                // Put them together and add to habitList
                Habit newHabit = new NormalHabit(bodyInput, dateInput, weekInput, completeHab);
                HabitActivity.habitList.add(newHabit);

                saveInFile();
                finish();

            }
        });
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
