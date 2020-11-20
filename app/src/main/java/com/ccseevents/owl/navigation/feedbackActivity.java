package com.ccseevents.owl.navigation;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.ccseevents.owl.CommentDialogActivity;
import com.ccseevents.owl.R;
import com.ccseevents.owl.notifications.CustomRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class feedbackActivity extends AppCompatActivity implements CommentDialogActivity.CommentDialogListener  {
    public Button addCommentButton;
    public Button addCommentButton1;
    String name1, comment1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
            name1="Rukmini";
            comment1="Rukmini's  has a comment";

           addCommentButton1 = findViewById(R.id.buttonComment);
        addCommentButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               submitComments(name1, comment1);
            }
        });


        //ADD Comment button
   /*     addCommentButton = findViewById(R.id.addCommentButton);
        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {

                submitComments();

            }
        });
     */



    }

    public void openDialog() {
        CommentDialogActivity commentDialogActivity = new CommentDialogActivity();
        commentDialogActivity.show(getSupportFragmentManager(), "comment dialog");
    }

    @Override
    public void applyTexts(String name, String comment) {

    }

    public void submitComments (String name_Value, String comment_Value){

        RequestQueue theQueue = Volley.newRequestQueue(getApplicationContext());

        String url = "https://vps-9a05ac13.vps.ovh.ca/capstone.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", name_Value);
        params.put("comment", comment_Value);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        theQueue.add(jsObjRequest);



    }
}