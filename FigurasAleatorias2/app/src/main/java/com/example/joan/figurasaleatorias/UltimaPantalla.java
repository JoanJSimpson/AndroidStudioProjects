package com.example.joan.figurasaleatorias;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Joan on 6/12/15.
 */
public class UltimaPantalla extends Activity {
    static float lado1, lado2;
    static Figura tipo;
    static Double area;
    static String figura;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_ultimapantalla);

        /****
         * Recogemos el BUNDLE
         */

        Bundle miBundleRecoger = getIntent().getExtras();
        lado1 = miBundleRecoger.getFloat("LADO1");
        lado2 = miBundleRecoger.getFloat("LADO2");
        figura = miBundleRecoger.getString("FIGURA");
        area = miBundleRecoger.getDouble("AREA");

        showToast("Lado1: " + lado1 + ". Lado 2: " + lado2 + ". Figura: " + figura  + ". Area: " + area);



    }//final onCreate

    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
