package com.example.iis5.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IIS 5 on 29-12-2017.
 */

public class SqLiteHelper extends SQLiteOpenHelper {
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_DATE = "date";
    public static final String KEY_STATUS = "status";
    public static final String DATABASE_NAME = "toDo";
    public static final String TABLE_NAME = "TodoTable";
    public static final int DATABASE_VERSION = 12;
    Context context;
    Todos todoData;
    List<Todos>todoDataList = new ArrayList<>();
    public SqLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "create table "+TABLE_NAME+"(id integer primary key ,title text,description text,date text,status integer);";
        db.execSQL(create_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public void insertData(Todos todoData) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, todoData.getId());
        values.put(KEY_TITLE, todoData.getTitle());
        values.put(KEY_DESCRIPTION, todoData.getDescription());
        values.put(KEY_DATE, todoData.getDate());
        values.put(KEY_STATUS, todoData.getStatus());
        long status = database.insert(TABLE_NAME, null, values);
        if (status==-1){
            Toast.makeText(context, "not inserted", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "inserted...", Toast.LENGTH_SHORT).show();
        }
    }
    public List<Todos>getTodoData(){
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("select*from "+TABLE_NAME +" order by date",null);
        if (cursor.moveToFirst()){
            do{
                todoData = new Todos();
                todoData.setId(cursor.getInt(cursor.getColumnIndex("id")));
                todoData.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                todoData.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                todoData.setDate(cursor.getString(cursor.getColumnIndex("date")));
                todoData.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
                if (cursor.getInt(cursor.getColumnIndex("status"))==0){
                    todoData.setImage(R.drawable.incomplete);
                }else if (cursor.getInt(cursor.getColumnIndex("status"))==1){
                    todoData.setImage(R.drawable.completed);
                }
                todoDataList.add(todoData);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return todoDataList;
    }
    public boolean deleteData(String name){
        SQLiteDatabase database = this.getWritableDatabase();
        return   database.delete(TABLE_NAME,KEY_TITLE + "=?",new String[]{name})>=0;
    }
    public List<Todos>getCompletedTaks(){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME,new String[]{KEY_ID,KEY_TITLE,KEY_DESCRIPTION,KEY_DATE,KEY_STATUS},KEY_STATUS+"=?",new String[]{String.valueOf(1)},null,null,null);
        if (cursor.moveToFirst()){
            do {
                todoData = new Todos();
                todoData.setId(cursor.getInt(cursor.getColumnIndex("id")));
                todoData.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                todoData.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                todoData.setDate(cursor.getString(cursor.getColumnIndex("date")));
                todoData.setImage(R.drawable.completed);
                todoDataList.add(todoData);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());

        }
        cursor.close();
        return todoDataList;
    }

}
