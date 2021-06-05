package com.example.newsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class VerticalViewPager extends ViewPager {
    public VerticalViewPager(@NonNull Context context) {
        super(context);
    }

    public VerticalViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setPageTransformer(true,new VerticalPageTransformer());
        setOverScrollMode(OVER_SCROLL_NEVER);
    }
    private  class VerticalPageTransformer implements ViewPager.PageTransformer{

        @Override
        public void transformPage(@NonNull View page, float position) {
            //to check position
            if(position<-1)
            {
             // infinity to -1 when page is off the screen to left
             page.setAlpha(0);

            }
            else if(position<=0)
            {

                page.setAlpha(1);
                page.setTranslationX(page.getWidth()*-position);
                float yPosition=position*page.getHeight();
                page.setTranslationY(yPosition);
                page.setScaleX(1);page.setScaleY(1);
            }
            else if(position<=1)
            {
                // from 0 to 1
                page.setTranslationX(page.getWidth()*-position);
                float scale=0.75f + (1-0.75f)*(1-Math.abs(position));
                page.setScaleX(scale);page.setScaleY(scale);
            }
            else
            {
                // 1 to infinity
                page.setAlpha(0);
            }

        }
    }
   private MotionEvent swapXYCoordinates(MotionEvent event)
   {
       float width=getWidth();
       float height=getHeight();
       float newX=(event.getY()/height)*width;
       float newY=(event.getX()/width)*height;
       event.setLocation(newX,newY);
       return event;
   }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted=super.onInterceptTouchEvent(swapXYCoordinates(ev));
        swapXYCoordinates(ev);
        return intercepted;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapXYCoordinates(ev));
    }

}
