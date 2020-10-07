package com.ccseevents.owl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyEventsActivity extends AppCompatActivity {
    private List<MyViewModel> viewModelList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        Toolbar toolbar = (Toolbar)findViewById(R.id.alToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        MyAdapter adapter = new MyAdapter(viewModelList);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MyEventsActivity.this, EventDetailActivity.class);
                        intent.putExtra("TITLE",viewModelList.get(position).getTitle());
                        intent.putExtra("HOST",viewModelList.get(position).getHost());
                        intent.putExtra("MONTH",viewModelList.get(position).getMonth());
                        intent.putExtra("DAY",viewModelList.get(position).getDay());
                        intent.putExtra("YEAR",viewModelList.get(position).getYear());
                        intent.putExtra("TIMEFROM",viewModelList.get(position).getFromTime());
                        intent.putExtra("TIMETO",viewModelList.get(position).getToTime());
                        intent.putExtra("LOCATION","The Atrium Building: Room 201 (Place holder)"); // TODO: bundle.getString("LOCATION");
                        intent.putExtra("DESCRIPTION",viewModelList.get(position).getDescription());
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Intent intent = new Intent(MyEventsActivity.this, EventDetailActivity.class);
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

}