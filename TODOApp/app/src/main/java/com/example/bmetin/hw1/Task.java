package com.example.bmetin.hw1;

/**
 * Created by bmetin on 2018-03-24.
 */

public class Task {

    private String name,time;

    public Task()
    {

    }

    public Task(String name, String time)
    {
        this.name=name;
        this.time=time;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
