package com.ccseevents.owl;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class EventDetailActivity extends AppCompatActivity implements CommentDialogActivity.CommentDialogListener {
    public EventsDatabaseHelper eventDB = new EventsDatabaseHelper(this);

    public int eID;
    public String titleValue;
    public String descriptionValue;
    public String listType;
    public Button addCommentButton;
    public ArrayList<Comment> commentList;
    public CommentListAdapter adapter;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        Toolbar toolbar = (Toolbar)findViewById(R.id.adToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Bundle bundle = getIntent().getExtras();
        //Check whether event has already been added to My Events and adjust button text
        eID = bundle.getInt("EVENTID");

        // TITLE
        titleValue = bundle.getString("TITLE");
        TextView titleTextView = (TextView)findViewById(R.id.titleText);
        titleTextView.setText(titleValue);

        // HOST
        String hostValue = bundle.getString("HOST");
        TextView hostTextView = (TextView)findViewById(R.id.hostedByText);
        hostTextView.setText(hostValue);

        // DATE
        String dayValue = bundle.getString("DAY");
        String monthValue = bundle.getString("MONTH");
        String yearValue = bundle.getString("YEAR");
        TextView dateTextView = (TextView)findViewById(R.id.dateText);
        dateTextView.setText(monthValue + " " + dayValue + ", " + yearValue);

        // TIME
        String fromValue = bundle.getString("TIMEFROM");
        String toValue = bundle.getString("TIMETO");
        TextView timeTextView = (TextView)findViewById(R.id.timeText);
        if(fromValue.equals(toValue)){
            timeTextView.setText(fromValue);
            if(fromValue.equals("12:00AM")){
                timeTextView.setText("All Day");
            }
        }else{
            timeTextView.setText(fromValue + " to " + toValue);
        }

        // LOCATION
        String locationValue = bundle.getString("LOCATION");
        TextView locationTextView = (TextView)findViewById(R.id.locationText);
        locationTextView.setText(locationValue);

        // DESCRIPTION
        descriptionValue = bundle.getString("DESCRIPTION");
        Spanned policy = Html.fromHtml(descriptionValue);
        TextView descriptionTextView = (TextView)findViewById(R.id.descriptionText);
        descriptionTextView.setText(policy);
        descriptionTextView.setMovementMethod(LinkMovementMethod.getInstance());

        //Photo
        String photoURL = bundle.getString("PHOTOURL");
        ImageView eventImage = findViewById(R.id.eventImage);
        Picasso.get().load(photoURL).into(eventImage);

        //List Type for Back Button
        listType = bundle.getString("LISTTYPE");

        //ADD Comment button
        addCommentButton = findViewById(R.id.addCommentButton);
        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        listView = findViewById(R.id.commentsListView);
        commentList = new ArrayList<>();

        adapter = new CommentListAdapter(this, R.layout.comment_view_layout, commentList);
        listView.setAdapter(adapter);

        setListViewHeight();
    }

    public void openDialog() {
        CommentDialogActivity commentDialogActivity = new CommentDialogActivity();
        commentDialogActivity.show(getSupportFragmentManager(), "comment dialog");
    }

    @Override
    public void applyTexts(String name, String comment) {
        Comment newComment = new Comment(name, comment);
        commentList.add(newComment);
        adapter.notifyDataSetChanged();

        setListViewHeight();
        //Set name and comment in the comments section in this view
    }

    private void setListViewHeight() {
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        totalHeight = totalHeight + 20;
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if (listType.equals("FEATURED")){
                    Intent backIntent = new Intent(EventDetailActivity.this, MainMenuActivity.class);
                    startActivity(backIntent);
                }else {
                    Intent backIntent = new Intent(EventDetailActivity.this, EventListActivity.class);
                    backIntent.putExtra("LISTTYPE", listType);
                    startActivity(backIntent);
                }
                return true;
            case R.id.option_favorite:
                boolean favorited = eventDB.existsMyEvents(eID);
                if (favorited) {
                    boolean isDeleted = eventDB.deleteMyEvents(eID);
                    if (isDeleted) {
                        Toast.makeText(EventDetailActivity.this, "Removed from My Events", Toast.LENGTH_SHORT).show();
                        item.setIcon(getResources().getDrawable(R.drawable.ic_favorite_unselected));
                    }
                    else{
                        Toast.makeText(EventDetailActivity.this, "Failed to Remove", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    boolean isInserted = eventDB.insertMyEvents(eID);
                    if (isInserted){
                        Toast.makeText(EventDetailActivity.this, "Added to My Events", Toast.LENGTH_SHORT).show();
                        item.setIcon(getResources().getDrawable(R.drawable.ic_favorite_selected));
                    }
                    else{
                        Toast.makeText(EventDetailActivity.this, "Failed to Add", Toast.LENGTH_SHORT).show();
                    }

                }
                return true;
            case R.id.option_hide:
                boolean itemHide = eventDB.eventisHidden(eID);
                if (itemHide){
                    Toast.makeText(this, "Event Shown",Toast.LENGTH_SHORT).show();
                    eventDB.deleteHideEvents(eID);
                    item.setTitle("Hide Event");
                }else{
                    Toast.makeText(this, "Event Hidden",Toast.LENGTH_SHORT).show();
                    eventDB.insertHideEvents(eID);
                    item.setTitle("Unhide Event");
                }
                return true;
            case R.id.option_calendar:
                Cursor res = eventDB.getEventDate(eID);
                int calday = 1;
                int calmonth = 0;
                int calyear = 2020;
                String startStr = "";
                String endStr = "";
                int starthour = 12;
                int startmin = 0;
                int endhour = 1;
                int endmin = 0;
                String location = "";
                String allday = "";
                while (res.moveToNext()){
                    calday = res.getInt(0);
                    calmonth = res.getInt(1)-1; //0 is Jan for Calendar, 1 is for Jan in DB so make adjustment here
                    calyear = res.getInt(2)-1;
                    startStr = res.getString(3);
                    startmin = res.getInt(4);
                    endStr = res.getString(5);
                    endmin = res.getInt(6);
                    location = res.getString(7);
                    allday = res.getString(8);
                }
                if(startStr.substring(0,1)== "0"){
                    starthour = Integer.parseInt(startStr.substring(1,1));
                }else{
                    starthour = Integer.parseInt(startStr);
                }
                if(endStr.substring(0,1) == "0"){
                    endhour = Integer.parseInt(endStr.substring(1,1));
                }else{
                    endhour = Integer.parseInt(endStr);
                }
                Calendar start = Calendar.getInstance();
                start.set(calyear, calmonth, calday, starthour, startmin);
                Calendar end = Calendar.getInstance();
                end.set(calyear, calmonth, calday, endhour, endmin);

                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setData(CalendarContract.Events.CONTENT_URI);
                intent.putExtra(CalendarContract.Events.TITLE, titleValue);
                intent.putExtra(CalendarContract.Events.DESCRIPTION, descriptionValue);
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, start.getTimeInMillis());
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end.getTimeInMillis());
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location);
                if (allday.equals("1")) {
                    intent.putExtra(CalendarContract.Events.ALL_DAY, true);
                }

                if(intent.resolveActivity(getPackageManager())!=null){
                    startActivity(intent);
                }else{
                    Toast.makeText(EventDetailActivity.this, "No Compatible App for Calendar", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_eventdetail,menu);
        boolean btnfavorited = eventDB.existsMyEvents(eID);
        if (btnfavorited) {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_favorite_selected));
        }
        boolean itemHide = eventDB.eventisHidden(eID);
        if (itemHide){
            menu.getItem(1).setTitle("Unhide Event");
        }
        return true;
    }

}