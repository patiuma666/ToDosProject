package com.example.iis5.todo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CompletedTaskList extends AppCompatActivity {

    ListView listView;
    MyAdapter adapter2;
    List<Todos> todoDataList1;

    SqLiteHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_task_list);
        listView = (ListView)findViewById(R.id.completedtaskslist);
        helper = new SqLiteHelper(CompletedTaskList.this);
        todoDataList1 = new ArrayList<>();
        todoDataList1= helper.getCompletedTaks();
        Toast.makeText(this, ""+todoDataList1.get(0).getStatus(), Toast.LENGTH_SHORT).show();
     /* adapter2 = new MyAdapter(CompletedTaskList.this,R.layout.activity_completed_task_list,todoDataList1);
        listView.setAdapter(adapter2);*/
    }
}
