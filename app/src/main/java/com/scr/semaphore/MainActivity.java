package com.scr.semaphore;

import android.app.Activity;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by santi on 11/10/15.
 *
 * https://openclipart.org/detail/64801/semaphore-semaforo
 */
public class MainActivity extends Activity implements View.OnClickListener {

    public static final int SEMAPHORE_IMAGE_HEIGHT = 800;
    public static final int SEMAPHORE_IMAGE_WIDTH = 360;
    public static final int LIGHT_IMAGE_HEIGHT = 225;
    public static final int LIGHT_IMAGE_WIDTH = 225;
    public static final int LIGHT_IMAGE_MARGIN = 15;

    private enum Light {
        RED, YELLOW, GREEN
    }

    private static final Light [] LIGHT_SEQUENCE = new Light[] {
            Light.RED,
            Light.GREEN,
            Light.YELLOW
    };

    private int mCurrentLight;

    private ImageView mImageViewSemaphore;
    private ImageView mImageViewRedLight;
    private ImageView mImageViewYellowLight;
    private ImageView mImageViewGreenLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageViewSemaphore = (ImageView)findViewById(R.id.ImageViewSemaphore);
        mImageViewRedLight = (ImageView)findViewById(R.id.ImageViewRedLight);
        mImageViewYellowLight = (ImageView)findViewById(R.id.ImageViewYellowLight);
        mImageViewGreenLight = (ImageView)findViewById(R.id.ImageViewGreenLight);

        View rootView = findViewById(R.id.RootView);
        rootView.setOnClickListener(this);

        mCurrentLight = 0;
        changeLight();

        adjustImages();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }

    private void adjustImages() {

        int screenHeight;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point p = new Point();
            getWindowManager().getDefaultDisplay().getSize(p);
            screenHeight = p.y;
        }
        else {
            //noinspection deprecation
            screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        }

        float resizeFactor = (float)screenHeight / SEMAPHORE_IMAGE_HEIGHT;

        mImageViewSemaphore.getLayoutParams().height = (int)(SEMAPHORE_IMAGE_HEIGHT * resizeFactor);
        mImageViewSemaphore.getLayoutParams().width = (int)(SEMAPHORE_IMAGE_WIDTH * resizeFactor);

        mImageViewRedLight.getLayoutParams().height =  (int)(LIGHT_IMAGE_HEIGHT * resizeFactor);
        mImageViewRedLight.getLayoutParams().width =  (int)(LIGHT_IMAGE_WIDTH * resizeFactor);
        ((FrameLayout.LayoutParams)mImageViewRedLight.getLayoutParams()).topMargin =
                (int)(LIGHT_IMAGE_MARGIN * resizeFactor);

        mImageViewYellowLight.getLayoutParams().height =  (int)(LIGHT_IMAGE_HEIGHT * resizeFactor);
        mImageViewYellowLight.getLayoutParams().width =  (int)(LIGHT_IMAGE_WIDTH * resizeFactor);

        mImageViewGreenLight.getLayoutParams().height =  (int)(LIGHT_IMAGE_HEIGHT * resizeFactor);
        mImageViewGreenLight.getLayoutParams().width =  (int)(LIGHT_IMAGE_WIDTH * resizeFactor);
        ((FrameLayout.LayoutParams)mImageViewGreenLight.getLayoutParams()).bottomMargin =
                (int)(LIGHT_IMAGE_MARGIN * resizeFactor);
    }

    private void changeLight() {

        Light currentLight = LIGHT_SEQUENCE[mCurrentLight];

        mImageViewRedLight.setVisibility(
                currentLight == Light.RED ? View.VISIBLE : View.GONE
        );
        mImageViewYellowLight.setVisibility(
                currentLight == Light.YELLOW ? View.VISIBLE : View.GONE
        );
        mImageViewGreenLight.setVisibility(
                currentLight == Light.GREEN ? View.VISIBLE : View.GONE
        );

        mCurrentLight++;
        if (mCurrentLight == LIGHT_SEQUENCE.length) mCurrentLight = 0;
    }

    @Override
    public void onClick(View v) {
        changeLight();
    }
}
