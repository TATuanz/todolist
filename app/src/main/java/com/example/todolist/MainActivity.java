package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Task> taskList = new ArrayList<Task>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ContraintLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
    }

}
protected void onStart (){
    super.onStart();
    ListView lv = findViewById(R.id.listviewTask);
    TaskAdapter adapter = new TaskAdapter (this,tasklist);
    lv.setAdapter(adapter);
}
public class Adapter extends ArrayAdapter<Task>{
    public TaskAdapter(Context context, ArrayList<Task> tasks ){super(context, 0, tasks);}
    @Override
    public View getView(int position, View convertView, ViewGroup parent ){
        Task t = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item,parent,false);
        TextView tvTaskName = (TextView) convertView.findViewById(R.id.tvTaskName);
        TextView tvDeadline = (TextView) convertView.findViewById(R.id.tvDeadline);
        TextView tvDuration = (TextView) convertView.findViewById(R.id.tvDuration);
        TextView tvDescriptions = (TextView) convertView.findViewById(R.id.tvDescriptions);
        tvTaskName .setText(t.name);
        tvDeadline .setText(t.deadline.toString().substring(0,10));
        tvDuration .setText(String.valueOf(t.duration));
        tvDescriptions .setText(String.valueOf(t.descriptions));
        return convertView;
    }
}