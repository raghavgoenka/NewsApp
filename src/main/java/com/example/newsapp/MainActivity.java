package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GestureDetectorCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<News> newsArray = new ArrayList<>();

    DatabaseReference mRef;
    VerticalViewPager verticalViewPager;
    String category = "News";
    String type;


    SwipeRefreshLayout mySwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();


        type = i.getStringExtra("news_category");

        TextView t = findViewById(R.id.author);


        verticalViewPager = findViewById(R.id.verticalViewPager);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        fetch();

        mySwipeRefreshLayout = findViewById(R.id.swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {


                        mySwipeRefreshLayout.setRefreshing(false);

                        fetch();
                    }


                }
        );




    }


    public void fetch() {

        if (type == null) {
            type = "News";
            Log.i("cttt", type);
        }

        mRef = FirebaseDatabase.getInstance().getReference(type);
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {
                    News news = new News();

                    news.setTitle(ds.child("heading").getValue(String.class));
                    news.setContent(ds.child("content").getValue(String.class));
                    news.setImageUrl(ds.child("img_url").getValue(String.class));
                    news.setUrl(ds.child("news_url").getValue(String.class));
                    news.setAuthor(ds.child("news_source").getValue(String.class));
                    news.setName(ds.child("name").getValue(String.class));
                    newsArray.add(news);

                }

                verticalViewPager.setAdapter(new ViewPageAdapter(MainActivity.this, newsArray, verticalViewPager, type));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}