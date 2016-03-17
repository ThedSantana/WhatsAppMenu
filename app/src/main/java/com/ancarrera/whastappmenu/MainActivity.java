package com.ancarrera.whastappmenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity{

    private LinearLayout revealItems;
    private boolean hiddenView = true;
    private static final int ANIMATION_TIME = 400;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        revealItems = (LinearLayout) findViewById(R.id.reveal_items);

        setSupportActionBar(toolbar);
        revealItems.setVisibility(View.INVISIBLE);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings) {
            return true;
        }else if (id == R.id.attach){

            int cx = (int)getAttachButtonPos(item);
            int cy = 0;

            int startradius=0;
            int endradius = Math.max(revealItems.getWidth(), revealItems.getHeight());

            Animator animator = ViewAnimationUtils.createCircularReveal(revealItems, cx, cy, startradius, endradius);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(ANIMATION_TIME);

            int reverse_startradius = Math.max(revealItems.getWidth(),revealItems.getHeight());
            int reverse_endradius=0;

            Animator animate = ViewAnimationUtils.createCircularReveal(revealItems,cx,cy,reverse_startradius,reverse_endradius);

            if(hiddenView) {
                revealItems.setVisibility(View.VISIBLE);
                animator.start();
                hiddenView = false;
            }
            else {

                revealItems.setVisibility(View.VISIBLE);

                animate.addListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        revealItems.setVisibility(View.INVISIBLE);
                        hiddenView = true;

                    }
                });
                animate.start();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private double getAttachButtonPos(MenuItem item){
        View view = findViewById(R.id.attach);
        int[] location = new int[2];
        view.getLocationInWindow(location);
        int size = view.getHeight();

        return location[0]+(size/2);
    }
}
