package com.example.joan.proysumaresta;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RadioGroup rg = (RadioGroup) findViewById(R.id.gruporb);
        final RadioButton r1=(RadioButton) findViewById(R.id.radio1);
        final RadioButton r2=(RadioButton) findViewById(R.id.radio2);
        final EditText numero1 = (EditText) findViewById(R.id.numero1);
        final EditText numero2 = (EditText) findViewById(R.id.numero2);
        final TextView resul = (TextView) findViewById(R.id.resultado);


        rg.clearCheck();
        //rg.check(R.id.radio1);
        //int idSeleccionado = rg.getCheckedRadioButtonId();

        rg.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    double resultado=0.0;
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (r1.isChecked() == true) {
                            resultado= Double.parseDouble(numero1.getText().toString()) + Double.parseDouble(numero2.getText().toString());
                            resul.setText(String.valueOf(resultado));

                        } else if (r2.isChecked() == true) {
                            resultado= Double.parseDouble(numero1.getText().toString()) - Double.parseDouble(numero2.getText().toString());
                            resul.setText(String.valueOf(resultado));

                        }
                        //lblMensaje.setText("ID opcion seleccionada: " + checkedid);

                    }
                });




    }
}
