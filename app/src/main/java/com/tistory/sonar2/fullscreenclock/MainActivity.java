package com.tistory.sonar2.fullscreenclock;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BlurMaskFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Dimension;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


public class MainActivity extends AppCompatActivity {
    TextClock textClock_hhmm, textClock_ss, textClock_ymd, textClock_ap;
    TextView textview;
    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE= 2323;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //안드로이드10 이상에서 자동실행을 하려면 아래 추가하여 다른앱위에 그리기 허용권한 얻기
        //if the user already granted the permission or the API is below Android 10 no need to ask for permission
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Settings.canDrawOverlays(getApplicationContext()))
        {RequestPermission();}

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        textClock_hhmm = findViewById(R.id.tc_hhmm);
        textClock_ss = findViewById(R.id.tc_ss);
        textClock_ymd = findViewById(R.id.tc_ymd);
        textClock_ap = findViewById(R.id.tc_ap);
        textview = findViewById(R.id.tv);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = getResources().getDisplayMetrics().density;
        //float dpHeight = outMetrics.heightPixels / density;
        float pxWidth = outMetrics.widthPixels;
        float dpWidth = pxWidth / density;
        float Size_hhmm = dpWidth * .3f;
        float size_ss = dpWidth * .065f;
        float size_ymd = dpWidth * .05f;
        int margin_bottom = (int) (pxWidth * .02f);
        textClock_hhmm.setTextSize(Dimension.PX, Size_hhmm);
        textClock_ss.setTextSize(size_ss);
        textClock_ymd.setTextSize(size_ymd);
        textClock_ap.setTextSize(size_ss/2);

        ConstraintLayout.LayoutParams param_t = (ConstraintLayout.LayoutParams) textview.getLayoutParams();
        ConstraintLayout.LayoutParams param_hhmm = (ConstraintLayout.LayoutParams) textClock_hhmm.getLayoutParams();
        ConstraintLayout.LayoutParams param_s = (ConstraintLayout.LayoutParams) textClock_ss.getLayoutParams();
        ConstraintLayout.LayoutParams param_ap = (ConstraintLayout.LayoutParams) textClock_ap.getLayoutParams();
        param_s.bottomMargin = margin_bottom;
        param_ap.bottomMargin=margin_bottom;
        textClock_hhmm.setLayoutParams(param_hhmm);
        textClock_ss.setLayoutParams(param_s);
        textClock_ap.setLayoutParams(param_ap);

//        param_t.width = ((int) pxWidth);
        textview.setLayoutParams(param_t);


        //textview.setText(String.format("pxWidth:%.0f dpWidth:%.0f text:%.0f", pxWidth, dpWidth, Size_hhmm));
        applyBlurMaskFilter(textClock_hhmm, BlurMaskFilter.Blur.SOLID);
    }

    // Custom method to apply BlurMaskFilter to a TextView text
    protected void applyBlurMaskFilter(TextView tv, BlurMaskFilter.Blur style) {
        /*
            MaskFilter
                Known Direct Subclasses
                    BlurMaskFilter, EmbossMaskFilter

                MaskFilter is the base class for object that perform transformations on an
                alpha-channel mask before drawing it. A subclass of MaskFilter may be installed
                into a Paint. Blur and emboss are implemented as subclasses of MaskFilter.

        */
        /*
            BlurMaskFilter
                This takes a mask, and blurs its edge by the specified radius. Whether or or not to
                include the original mask, and whether the blur goes outside, inside, or straddles,
                the original mask's border, is controlled by the Blur enum.
        */
        /*
            public BlurMaskFilter (float radius, BlurMaskFilter.Blur style)
                Create a blur maskfilter.

            Parameters
                radius : The radius to extend the blur from the original mask. Must be > 0.
                style : The Blur to use
            Returns
                The new blur maskfilter
        */
        /*
            BlurMaskFilter.Blur
                INNER : Blur inside the border, draw nothing outside.
                NORMAL : Blur inside and outside the original border.
                OUTER : Draw nothing inside the border, blur outside.
                SOLID : Draw solid inside the border, blur outside.
        */
        /*
            public float getTextSize ()
                Returns the size (in pixels) of the default text size in this TextView.
        */

        // Define the blur effect radius
        float radius = tv.getTextSize() / 20;

        // Initialize a new BlurMaskFilter instance
        BlurMaskFilter filter = new BlurMaskFilter(radius, style);

        /*
            public void setLayerType (int layerType, Paint paint)
                Specifies the type of layer backing this view. The layer can be LAYER_TYPE_NONE,
                LAYER_TYPE_SOFTWARE or LAYER_TYPE_HARDWARE.

                A layer is associated with an optional Paint instance that controls how the
                layer is composed on screen.

            Parameters
                layerType : The type of layer to use with this view, must be one of
                    LAYER_TYPE_NONE, LAYER_TYPE_SOFTWARE or LAYER_TYPE_HARDWARE
                paint : The paint used to compose the layer. This argument is optional and
                    can be null. It is ignored when the layer type is LAYER_TYPE_NONE
        */
        /*
            public static final int LAYER_TYPE_SOFTWARE
                Indicates that the view has a software layer. A software layer is backed by
                a bitmap and causes the view to be rendered using Android's software rendering
                pipeline, even if hardware acceleration is enabled.
        */

        // Set the TextView layer type
        tv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        /*
            public MaskFilter setMaskFilter (MaskFilter maskfilter)
                Set or clear the maskfilter object.

                Pass null to clear any previous maskfilter. As a convenience, the parameter
                passed is also returned.

            Parameters
                maskfilter : May be null. The maskfilter to be installed in the paint
            Returns
                maskfilter
        */

        // Finally, apply the blur effect on TextView text
        tv.getPaint().setMaskFilter(filter);
    }



    private void RequestPermission() {
        // Check if Android M or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Show alert dialog to the user saying a separate permission is needed
            // Launch the settings activity if the user prefers
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(),"다른앱위에 그리기를 허용해 주셔야 자동실행됩니다.",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}

