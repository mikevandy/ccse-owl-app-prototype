package com.ccseevents.owl.notifications;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ccseevents.owl.R;

public class ReminderNotificationActivity extends AppCompatActivity {

    private static final int JOB_ID = 0;
    private JobScheduler mScheduler;
    private long millis1 =0;
    public String seconds ="0";

    //@SuppressLint("NewApi")  added because job scheduler must run with API 21 or higher. Remove and try another way later @SuppressLint("NewApi")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_notification);
       EditText editText1 = findViewById(R.id.editText_milliseconds);
        //millis1= Long.parseLong(String.valueOf(editText1.getText()));
        editText1.setText(seconds);
        //TextView textView = findViewById(R.id.textView4);
         //textView.setText(seconds);
       // textView = String.valueOf(textView.getText());
    }

    public void sendTime(View view){
        EditText editText1 = findViewById(R.id.editText_milliseconds);
        millis1= Long.parseLong(String.valueOf(editText1.getText()));
        TextView textView = findViewById(R.id.textView4);
        textView.setText(String.valueOf(editText1.getText()));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void scheduleJob(View view) {
       EditText editText1 = findViewById(R.id.editText_milliseconds);
        //millis1= Long.parseLong(String.valueOf(editText1.getText()));
        RadioGroup networkOptions = findViewById(R.id.networkOptions);

        int selectedNetworkID = networkOptions.getCheckedRadioButtonId();

        int selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;




        switch (selectedNetworkID) {
            case R.id.noNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;
                break;
            case R.id.anyNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_ANY;
                break;
            case R.id.wifiNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_UNMETERED;
                break;
        }
        mScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        ComponentName serviceName = new ComponentName(getPackageName(),
                NotificationJobService.class.getName());
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, serviceName)
                .setRequiredNetworkType(selectedNetworkOption)
                .setMinimumLatency(Long.parseLong(String.valueOf(editText1.getText())));




        boolean constraintSet = selectedNetworkOption
                != JobInfo.NETWORK_TYPE_NONE;


        // if (constraintSet) {
        JobInfo myJobInfo = builder.build();
        mScheduler.schedule(myJobInfo);
        Toast.makeText(this,"Job Scheduled, job will run when " +
                "the constraints are met." , Toast.LENGTH_SHORT)
                .show();
        // } else {
        //   Toast.makeText(this, "No Contraints",
        //         Toast.LENGTH_SHORT).show();
        //  }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void cancelJobs(View view) {

        if (mScheduler != null) {
            mScheduler.cancelAll();
            mScheduler = null;
            Toast.makeText(this, R.string.jobs_canceled, Toast.LENGTH_SHORT)
                    .show();
        }
    }




}