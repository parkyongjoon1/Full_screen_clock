package com.tistory.sonar2.fullscreenclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextClock;

public class MainActivity extends AppCompatActivity {
    TextClock textClock_hhmm, textClock_ss, textClock_ymd, textClock_ap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        textClock_hhmm = findViewById(R.id.tc_hhmm);
        textClock_ss = findViewById(R.id.tc_ss);
        textClock_ymd = findViewById(R.id.tc_ymd);
        textClock_ap = findViewById(R.id.tc_ap);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = getResources().getDisplayMetrics().density;
        //float dpHeight = outMetrics.heightPixels / density;
        float dpWidth = outMetrics.widthPixels / density;
        float Size_hhmm = dpWidth/3.7f;
        float size_ss = dpWidth/12.0f;
        float size_ymd = dpWidth/14.0f;
        int margin_bottom = (int)(dpWidth/40.0f);
        textClock_hhmm.setTextSize(Size_hhmm);
        textClock_ss.setTextSize(size_ss);
        textClock_ymd.setTextSize(size_ymd);
        textClock_ap.setTextSize(size_ss/2);

        ConstraintLayout.LayoutParams param_s = (ConstraintLayout.LayoutParams) textClock_ss.getLayoutParams();
        ConstraintLayout.LayoutParams param_ap = (ConstraintLayout.LayoutParams) textClock_ap.getLayoutParams();
        param_s.bottomMargin=margin_bottom;
        param_ap.bottomMargin=margin_bottom;
        textClock_ss.setLayoutParams(param_s);
        textClock_ap.setLayoutParams(param_ap);
    }
}