package com.example.iis5.todo;

import android.graphics.ColorSpace;
import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IIS 5 on 29-12-2017.
 */

public class Todos implements Comparable<Todos> {
    int id,status,image;
    String title,description,date;
    Date d;
    public Todos(int image,String title, String description, String date) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.date = date;
    }

    public Todos() {
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Date getD() {
        return d;
    }

    public void setD(Date d) {
        this.d = d;
    }

    @Override
    public int compareTo(@NonNull Todos o) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        int result = 0;
        try {
            return dateFormat.parse(String.valueOf(o.getD())).compareTo(dateFormat.parse(String.valueOf(o.getD())));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
