package com.example.newsapp;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.transcode.BitmapBytesTranscoder;

import java.util.ArrayList;
import java.util.List;

public class ViewPageAdapter extends PagerAdapter {
    Context context;
    List<News> newsArray = new ArrayList<>();

    VerticalViewPager verticalViewPager;
    LayoutInflater mLayoutInflater;

    String category;
    int newposition;
    float x1, x2;

    public ViewPageAdapter(Context context, List<News> newArray, VerticalViewPager verticalViewPager, String type) {
        this.context = context;

        this.newsArray = newArray;
        this.verticalViewPager = verticalViewPager;
        mLayoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.category = type;

    }

    @Override
    public int getCount() {
        return newsArray.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);

    }


    @NonNull
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {


        View itemView = mLayoutInflater.inflate(R.layout.item_container, container, false);
        ImageView imageView = itemView.findViewById(R.id.image);
        LinearLayout changing = itemView.findViewById(R.id.changing);
        ImageView img3 = itemView.findViewById(R.id.image4);
        ImageView imgTop = itemView.findViewById(R.id.imageTop);
        ImageView imgbook = itemView.findViewById(R.id.imagebook);
        ImageView imageView1 = itemView.findViewById(R.id.image2);
        Glide.with(context).load(newsArray.get(position).imageUrl).error(R.drawable.unnamed).centerCrop().into(imageView);
        Glide.with(context).load(newsArray.get(position).imageUrl).centerCrop().override(12, 12).into(imageView1);
        TextView title = itemView.findViewById(R.id.title);
        title.setText(newsArray.get(position).title);
        TextView author = itemView.findViewById(R.id.title2);
        TextView dis = itemView.findViewById(R.id.dis);
        TextView t = itemView.findViewById(R.id.tt);
        t.setText(category);
        TextView last = itemView.findViewById(R.id.author2);
        author.setText("read more at " + newsArray.get(position).author);
        TextView content = itemView.findViewById(R.id.author);
        TextView name = itemView.findViewById(R.id.author3);
        String s = "short by " + newsArray.get(position).name;
        SpannableString str = new SpannableString(s);
        ForegroundColorSpan fc = new ForegroundColorSpan(Color.WHITE);
        str.setSpan(fc, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        StyleSpan bold = new StyleSpan(Typeface.BOLD_ITALIC);

        str.setSpan(bold, 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        str.setSpan(new RelativeSizeSpan(1.5f), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        name.setText(str);
        content.setText(newsArray.get(position).content);
        container.addView(itemView);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, NewsDetailActivity.class);
                i.putExtra("url", newsArray.get(newposition).url);
                context.startActivity(i);
            }
        });
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView1.getVisibility() == imageView1.VISIBLE) {
                    imageView1.setVisibility(View.INVISIBLE);
                    author.setVisibility(View.INVISIBLE);
                    last.setVisibility(View.INVISIBLE);
                    @SuppressLint("ResourceType") Animation animSlideDown = AnimationUtils.loadAnimation(context, R.xml.slide_up);
                    t.startAnimation(animSlideDown);
                    imgTop.startAnimation(animSlideDown);
                    dis.startAnimation(animSlideDown);
                    t.setVisibility(View.VISIBLE);
                    imgTop.setVisibility(View.VISIBLE);
                    dis.setVisibility(View.VISIBLE);
                    changing.startAnimation(animSlideDown);
                    changing.setVisibility(View.VISIBLE);


                } else if (imageView1.getVisibility() == imageView1.INVISIBLE) {
                    t.setVisibility(View.INVISIBLE);
                    imgTop.setVisibility(View.INVISIBLE);
                    dis.setVisibility(View.INVISIBLE);
                    changing.setVisibility(View.INVISIBLE);
                    imageView1.setVisibility(View.VISIBLE);
                    author.setVisibility(View.VISIBLE);
                    last.setVisibility(View.VISIBLE);


                }
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, newsArray.get(position).title);
                shareIntent.putExtra(Intent.EXTRA_TEXT, newsArray.get(position).url);


                Intent chooser = Intent.createChooser(shareIntent, "Share this news using ....");
                context.startActivity(chooser);
            }
        });
        imgTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, SplashScreen.class);
                context.startActivity(i);
            }
        });
        imgbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgbook.setImageResource(R.drawable.ic_baseline_bookmark_24);
            }
        });

        verticalViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //save position in newPosition on page change
                newposition = position;


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }


        });
        verticalViewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = motionEvent.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = motionEvent.getX();
                        float deltax = x1 - x2;
                        Log.i("motionnnnnnn", String.valueOf(deltax));
                        if (deltax > 300) {
                            Intent i = new Intent(context, NewsDetailActivity.class);
                            if (position == 1) {
                                i.putExtra("url", newsArray.get(0).url);

                                context.startActivity(i);
                            } else {
                                i.putExtra("url", newsArray.get(newposition).url);
                                context.startActivity(i);
                            }
                        } else if (deltax >= -1000 && deltax <= -700) {
//                            Intent i = new Intent(context, SplashScreen.class);
//                            context.startActivity(i);
                        }

                        break;

                }
                return false;
            }
        });


        return itemView;


    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }


}
