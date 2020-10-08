package com.ccseevents.owl;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EventListActivity extends AppCompatActivity {
    private List<MyViewModel> viewModelList = new ArrayList<>();
    public MyEventsDatabaseHelper myeventDB = new MyEventsDatabaseHelper(this);
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        Toolbar toolbar = (Toolbar)findViewById(R.id.alToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewModelList.addAll(generateSimpleList());
        MyAdapter adapter = new MyAdapter(viewModelList);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(EventListActivity.this, EventDetailActivity.class);
                    intent.putExtra("TITLE",viewModelList.get(position).getTitle());
                    intent.putExtra("HOST",viewModelList.get(position).getHost());
                    intent.putExtra("MONTH",viewModelList.get(position).getMonth());
                    intent.putExtra("DAY",viewModelList.get(position).getDay());
                    intent.putExtra("YEAR",viewModelList.get(position).getYear());
                    intent.putExtra("TIMEFROM",viewModelList.get(position).getFromTime());
                    intent.putExtra("TIMETO",viewModelList.get(position).getToTime());
                    intent.putExtra("LOCATION","The Atrium Building: Room 201 (Place holder)"); // TODO: bundle.getString("LOCATION");
                    intent.putExtra("DESCRIPTION",viewModelList.get(position).getDescription());
                    intent.putExtra("EVENTID",viewModelList.get(position).getId());
                    startActivity(intent);
                }

                @Override
                public void onLongItemClick(View view, int position) {
                    Intent intent = new Intent(EventListActivity.this, EventDetailActivity.class);
                    intent.putExtra("TITLE",viewModelList.get(position).getTitle());
                    startActivity(intent);
                }
            })
        );
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private List<MyViewModel> generateSimpleList() {
        List<MyViewModel> myViewModelList = new ArrayList<>();
        Cursor res = myeventDB.getAllData();
        if (res.getCount() != 0) {
            MyViewModel myViewModel[] = new MyViewModel[res.getCount()];
            int i = 0;
            while (res.moveToNext()){
                myViewModel[i] = new MyViewModel();
                myViewModel[i].setId(res.getInt(0));
                myViewModel[i].setDayOfWeek(res.getInt(1));
                myViewModel[i].setDay(res.getString(2));
                myViewModel[i].setMonth(res.getInt(3));
                myViewModel[i].setYear(res.getString(4));
                myViewModel[i].setFromTime(res.getString(5));
                myViewModel[i].setToTime(res.getString(6));
                myViewModel[i].setTitle(res.getString(7));
                myViewModel[i].setDescription(res.getString(8));
                myViewModel[i].setHost(res.getString(9));
                myViewModelList.add(myViewModel[i]);
                i++;
            }
        }
        return myViewModelList;
    }
}