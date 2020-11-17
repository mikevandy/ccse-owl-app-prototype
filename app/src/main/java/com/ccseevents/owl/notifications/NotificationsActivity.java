package com.ccseevents.owl.notifications;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ccseevents.owl.MainActivity;
import com.ccseevents.owl.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class NotificationsActivity extends AppCompatActivity {


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
final Button button1 = findViewById(R.id.button_subscribe);
    final Button button2 = findViewById(R.id.button_unsubscribe);
   final EditText editText1 = findViewById(R.id.editTextTextSub);
    final EditText editText2= findViewById(R.id.editTextTextUnsub);

        getToken();
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unsubscribeToTopic(String.valueOf(editText2.getText()));
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscribeToTopic(String.valueOf(editText1.getText()));
            }
        });


    }
    //get the applications token

    private void getToken(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        Log.e("Token", instanceIdResult.getToken());
                    }
                });
    }

    public void subscribeToTopic(String topic1){

        FirebaseMessaging.getInstance().subscribeToTopic(topic1)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                     //   String msg = getString(R.string.msg_subscribed);
                       // if (!task.isSuccessful()) {
                         //   msg = getString(R.string.msg_subscribe_failed);
                  //      }
                    //    Log.d(TAG, msg);
                        Toast.makeText(NotificationsActivity.this, "Subcribed to topic", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void unsubscribeToTopic(String topic2){

        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic2)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //   String msg = getString(R.string.msg_subscribed);
                        // if (!task.isSuccessful()) {
                        //   msg = getString(R.string.msg_subscribe_failed);
                        //      }
                        //    Log.d(TAG, msg);
                        Toast.makeText(NotificationsActivity.this, "UnSubcribed to topic", Toast.LENGTH_SHORT).show();
                    }
                });
    }



}

