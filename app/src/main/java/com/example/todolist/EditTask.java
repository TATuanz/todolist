package com.example.todolist;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditTask extends AppCompatActivity {
    Task task = new Task();
    private DatabaseHelper dbHelper;
    private DatePicker dp;
    private EditText n, des, d;
    Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extra = getIntent().getExtras();
        dbHelper = new DatabaseHelper(getApplicationContext());
        i  = new Intent(getApplicationContext(), MainActivity.class);

        if (extra != null) {
            int id = extra.getInt("taskId");
            task = dbHelper.readTask(id);
        }

        setContentView(R.layout.activity_edit_task);

        dp = (DatePicker) findViewById(R.id.dpDeadline);
        Calendar c = Calendar.getInstance();

        c.setTime(task.deadline);

        n = (EditText)findViewById(R.id.etTaskName);
        des = (EditText)findViewById(R.id.etmDescriptions);
        d = (EditText)findViewById(R.id.etDuration);

        n.setText(String.valueOf(task.name));
        des.setText(String.valueOf(task.descriptions));
        d.setText(String.valueOf(task.duration));
        dp.init( c.get(Calendar.YEAR), c.get(MONTH), c.get(DAY_OF_MONTH), null);
    }

    public void onClickEdit (View v) {
        String n, des;
        Integer d;

        n = this.n.getText().toString();
        des = this.des.getText().toString();;
        d = Integer.parseInt((((EditText)findViewById(R.id.etDuration)).getText().toString()));
        String dateText = String.valueOf(dp.getDayOfMonth()) + "/" +
                String.valueOf(dp.getMonth() + 1) + "/" +
                String.valueOf(dp.getYear());

        if (des == null || n == null || dateText == null || d == null) return;
        try {
            dbHelper.updateTask(task.id ,n, new SimpleDateFormat("dd/MM/yyyy").parse(dateText), d, des);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        startActivity(i);
    }

    public void onClickBack (View v) {
        startActivity(i);
    }
}