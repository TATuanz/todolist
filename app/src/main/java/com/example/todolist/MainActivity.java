package com.example.todolist;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Task> taskList = new ArrayList<Task>();
    private DatabaseHelper dbHelper;
    private Task task;
    private ListView lv;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(getApplicationContext());
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ContraintLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
    }

    protected void onStart() {
        super.onStart();
        lv = findViewById(R.id.listviewTask);
        taskList = dbHelper.readTask();
        TaskAdapter adapter = new TaskAdapter(this, taskList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener((adapterView, view, position, l) -> {
            PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);

            popupMenu.getMenu().add("Edit");
            popupMenu.getMenu().add("Delete");
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    task = (Task) adapterView.getItemAtPosition(position);

                    switch (menuItem.getTitle().toString()) {
                        case "Edit" :
                            Intent i = new Intent(getApplicationContext(), EditTask.class);
                            i.putExtra("taskId", task.id);
                            startActivity(i);
                            break;
                        case "Delete" :
                            dialog = new Dialog(MainActivity.this);
                            dialog.setContentView(R.layout.activity_delete_task);

                            int width = (int)(getResources().getDisplayMetrics().widthPixels*0.50);
                            int height = (int)(getResources().getDisplayMetrics().heightPixels*0.50);

                            dialog.getWindow().setLayout(width, height);

                            TextView tv = dialog.findViewById(R.id.tvTask);
                            tv.setText(String.format("Do you want to delete %s", task.name));
                            dialog.show();
                            break;
                    }
                    return false;
                }
            });
            popupMenu.show();
        });
    }

    public void onClickAdd(View v) {
        Intent i = new Intent(getApplicationContext(), AddTaskActivity.class);
                startActivity(i);
    }

    public void onClickDelete(View v) {
        dbHelper.deleteTask(task.id);
        taskList = dbHelper.readTask();
        TaskAdapter adapter = new TaskAdapter(this, taskList);
        lv.setAdapter(adapter);
        dialog.dismiss();
    }

    public void onClickCancel (View v) {
        dialog.dismiss();
    }
}