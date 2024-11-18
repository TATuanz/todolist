package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {
    public TaskAdapter (Context context, ArrayList<Task> tasks ){super(context, 0,tasks);}
    @Override
    public View getView(int position, View convertView, ViewGroup parent ){
        Task t = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_taskitem,parent,false);
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
