package com.example.todolist;

import static java.util.Calendar.*;

import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import kotlinx.coroutines.scheduling.Task;

public class AddTaskActivity extends AppCompatActivity {

    private Object MainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        DatePicker dp = (DatePicker) findViewById(R.id.dpDeadline);
        Calendar c = Calendar .getInstance();
        dp.init( c.get(Calendar.YEAR), c.get(MONTH), c.get(DAY_OF_MONTH), null);
    }
    public  void onClickAddTask(View v) throws ParseException {
        String n, des;
        Date dl;
        int d;
        n = ((EditText)findViewById(R.id.etTaskName)).getText().toString();
        des = ((EditText)findViewById(R.id.etmDescriptions)).getText().toString();
        DatePicker dp = (DatePicker) findViewById(R.id.dpDeadline);
        d = Integer.valueOf((((EditText)findViewById(R.id.etDuration)).getText().toString());
        String dateText = String.valueOf(dp.getDayOfMonth()) + "/" +
                          String.valueOf(dp.getMonth() +1) + "/" +
                          String.valueOf(dp.getYear());
        Task t = new Task(n,new SimpleDateFormat("dd/MM/yyyy").parse(dateText)),d ,des);
        MainActivity.taskList.add(t);
        Toast.makeText(getApplicationContext(),"A task is just created", Toast.LENGTH_LONG).show();
    }
}