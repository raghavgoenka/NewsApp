package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class NewsDetailActivity extends AppCompatActivity {
   String newslink;
   private SlidrInterface slidr;

   private FrameLayout frameLayout;
   private ProgressBar progressBar;
   private  WebView webView;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        slidr=Slidr.attach(this);
       frameLayout=findViewById(R.id.framelayout);
       progressBar=findViewById(R.id.progressbar);
        Intent i=getIntent();

        newslink=i.getStringExtra("url");
        webView= findViewById(R.id.web);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                frameLayout.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);

                if(newProgress==100)
                {
                    frameLayout.setVisibility(View.GONE);

                }
                super.onProgressChanged(view, newProgress);
            }
        });
         webView.getSettings().setJavaScriptEnabled(true);
         webView.setVerticalScrollBarEnabled(true);
         webView.loadUrl(newslink);
         progressBar.setProgress(0);

    }
    private class HelpClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(newslink);
            frameLayout.setVisibility(View.VISIBLE);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            Toast.makeText(NewsDetailActivity.this,error.toString(),Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }



}
