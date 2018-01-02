package com.example.iis5.todo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText title,description;
    Button save,cancel;
    SqLiteHelper helper;
    int i=0;
    DatePicker picker;
    Todos todoData;
    MyAdapter adapter;
    ListView listView;

    Dialog dialog;
    String d;
    List<Todos>todoDataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        helper = new SqLiteHelper(MainActivity.this);
        listView = (ListView)findViewById(R.id.list);
        todoDataList = new ArrayList<>();
        Collections.sort(todoDataList);
        Todos data = new Todos();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        todoDataList=helper.getTodoData();
        adapter = new MyAdapter(MainActivity.this,R.layout.list_items,todoDataList);
        listView.setAdapter(adapter);
        Collections.sort(todoDataList);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dialogtitle = todoDataList.get(position).getTitle();
                String dialogdesc = todoDataList.get(position).getDescription();
                String dialogtopdate = todoDataList.get(position).getDate();
                String dialogdate = todoDataList.get(position).getDate();
                int dilogimage = todoDataList.get(position).getImage();
                dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.list_item_details_dialog);
                dialog.show();
                TextView dTitle = (TextView)dialog.findViewById(R.id.dialogtitle);
                TextView dDesc =(TextView)dialog.findViewById(R.id.dialogdesc);
                TextView dTopDate = (TextView)dialog.findViewById(R.id.dialogtopdate);
                TextView dDate = (TextView)dialog.findViewById(R.id.dialogdate);
                ImageView dImage = (ImageView)dialog.findViewById(R.id.dialogstat);

                dTitle.setText(dialogtitle);
                dDesc.setText(dialogdesc);
                dTopDate.setText(dialogtopdate);
                dDate.setText(dialogdate);
                dImage.setImageResource(dilogimage);

            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete!!!");
                builder.setMessage("Do you really want to delete this item?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        todoDataList.remove(position);
                        todoData = new Todos();
                        //    Toast.makeText(MainActivity.this, ""+todoDataList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                        helper.deleteData(todoDataList.get(position).getTitle());
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


                AlertDialog dialog1 = builder.create();
                dialog1.show();

                return true;
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.custom_dialog);
            dialog.show();
            title = (EditText)dialog.findViewById(R.id.editText);
            description = (EditText)dialog.findViewById(R.id.editText2);
            save = (Button)dialog.findViewById(R.id.button2);
            cancel = (Button)dialog.findViewById(R.id.button);
            picker = (DatePicker)dialog.findViewById(R.id.datePicker);

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Todos data = new Todos();


                    PositiveAtomicInteger atomicInteger = new PositiveAtomicInteger(i);
                    Toast.makeText(MainActivity.this, "" + atomicInteger.incrementAndGet(), Toast.LENGTH_SHORT).show();
                    data.setId(atomicInteger.incrementAndGet());

                    data.setTitle(title.getText().toString());
                    data.setDescription(description.getText().toString());
                    int m = picker.getMonth() + 1;
                    d = String.valueOf(picker.getYear() + "/" + m + "/" + picker.getDayOfMonth());
                    Toast.makeText(MainActivity.this, "" + d, Toast.LENGTH_SHORT).show();
                    data.setDate(d);

                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
                    Collections.sort(todoDataList);
                    try {
                        Date date = format.parse(d);
                        Toast.makeText(MainActivity.this, "" + date.toString(), Toast.LENGTH_SHORT).show();
                        if (new Date().after(date)) {
                            data.setStatus(1);
                        } else if (new Date().before(date)) {
                            data.setStatus(0);

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    helper.insertData(data);
                    Collections.sort(todoDataList);
                    todoDataList.add(data);
                    listView.setAdapter(adapter);
                }

            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            return true;
        }else if (id==R.id.dialogstat){
            Intent intent = new Intent(MainActivity.this, CompletedTaskList.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
