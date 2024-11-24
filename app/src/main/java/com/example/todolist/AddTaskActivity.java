package com.example.todolist;

import static java.util.Calendar.*;

import android.content.Intent;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        i = new Intent(getApplicationContext(), MainActivity.class);
        setContentView(R.layout.activity_add_task);
        DatePicker dp = (DatePicker) findViewById(R.id.dpDeadline);
        Calendar c = Calendar .getInstance();
        dp.init( c.get(Calendar.YEAR), c.get(MONTH), c.get(DAY_OF_MONTH), null);
    }
    public void onClickAddTask(View v) throws ParseException {
        String n, des;
        Integer d;
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());

        n = ((EditText)findViewById(R.id.etTaskName)).getText().toString();
        des = ((EditText)findViewById(R.id.etmDescriptions)).getText().toString();
        DatePicker dp = (DatePicker) findViewById(R.id.dpDeadline);
        d = Integer.parseInt((((EditText)findViewById(R.id.etDuration)).getText().toString()));
        String dateText = String.valueOf(dp.getDayOfMonth()) + "/" +
                          String.valueOf(dp.getMonth() + 1) + "/" +
                          String.valueOf(dp.getYear());

        if (des == null || n == null || dateText == null || d == null) return;
        dbHelper.insertTask(n, new SimpleDateFormat("dd/MM/yyyy").parse(dateText), d, des);
        startActivity(i);

        Toast.makeText(getApplicationContext(),"A task is just created", Toast.LENGTH_LONG).show();
    }

    public void onClickBack (View v) {
        EditText n = (EditText)findViewById(R.id.etTaskName);
        EditText des = (EditText)findViewById(R.id.etmDescriptions);
        EditText d = (EditText)findViewById(R.id.etDuration);

        n.setText("");
        des.setText("");
        d.setText("");
        startActivity(i);
    }
}