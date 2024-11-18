package com.example.todolist;
import java.util.Date;

public class Task {
    public Task(String n, Date dl, int d, String des) {
        this.name = n;
        this.deadline = dl;
        this.duration = d;
        this.descriptions = des;
    };
    public String name;
    public Date deadline;
    public int duration;
    public String descriptions;
}