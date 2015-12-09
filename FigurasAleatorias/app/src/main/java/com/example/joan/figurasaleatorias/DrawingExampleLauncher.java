package com.example.joan.figurasaleatorias;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.app.ActionBar;

public class DrawingExampleLauncher extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        /*ActionBar actionBar = getActionBar();
        actionBar.show();*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_example_launcher);
    }

    public void launchDrawShapes1(View clickedButton) {
        Intent activityIntent =  new Intent(this, DrawShapes1.class);
        startActivity(activityIntent);
    }

    public void launchDrawShapes2(View clickedButton) {
        Intent activityIntent = new Intent(this, DrawShapes2.class);
        startActivity(activityIntent);
    }

    public void launchDrawShapes3(View clickedButton) {
        Intent activityIntent = new Intent(this, DrawShapes3.class);
        startActivity(activityIntent);
    }
}
