package com.ccseevents.owl.navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.ccseevents.owl.R;

public class Comments_Nav extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments__nav);
        WebView webView = (WebView) findViewById(R.id.webview_comments_nav);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.loadUrl("https://vps-9a05ac13.vps.ovh.ca/feedback.php");

    }
}