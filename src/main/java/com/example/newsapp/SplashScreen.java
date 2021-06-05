package com.example.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Arrays;
import java.util.List;

public class SplashScreen extends AppCompatActivity {
String[] news_category= {"national", "sports", "technology", "business", "politics", "entertainment", "science", "world", "automobile","startup","miscellaneous","hatke"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.splashscreen);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.newsdetail,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.back:
                Intent i =new Intent(this,MainActivity.class);
                i.putExtra("news_category","News");
                startActivity(i);

        }
        return true;
    }

    public void main(View view) {
        ImageView counter = (ImageView) view;

        counter.getTag();
        int tg=Integer.parseInt(counter.getTag().toString());
        Log.i("tagggggggg", (String) counter.getTag());
        Intent i =new Intent(this,MainActivity.class);

        i.putExtra("news_category",news_category[tg-1]);
        startActivity(i);
    }
}
