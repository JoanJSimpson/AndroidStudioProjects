package com.example.joan.contentprovider2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Main extends AppCompatActivity {

    private static final String TAG = "Http Connection";

    private EditText latitud;

    private EditText longitud;

    private Button boton, boton2;

    private TextView texto;

    private ListView listView = null;

    private ArrayAdapter arrayAdapter = null;

    private String[] titulosStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        listView = (ListView) findViewById(R.id.listView);
        latitud = (EditText) findViewById(R.id.latitud);
        longitud = (EditText) findViewById(R.id.longitud);
        boton = (Button) findViewById(R.id.boton);
        boton2 = (Button) findViewById(R.id.boton2);
        texto = (TextView) findViewById(R.id.texto);

        //Escuchador para el botón
        boton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    //Recuperamos los valores introducidos en los campos
                    //de latitud y longitud
                    String longitudCad = longitud.getText().toString();
                    String latitudCad = latitud.getText().toString();
                    //String resultado = busquedaGoogle(longitudCad, latitudCad);
                    //texto.setText(resultado);
                    final String url2 = "http://maps.googleapis.com/maps/api/geocode/json?" +
                            "latlng=" + latitudCad + "," + longitudCad + "&sensor=false";
                    final String url = "http://maps.googleapis.com/maps/api/geocode/json?latlng=38.1525,-0.88972&sensor=false";


                    new AsyncHttpTask().execute(url2);

                } catch (Exception e) {
                    texto.setText("Error al conectar\n");
                    e.printStackTrace();
                }
            }
        });
        boton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent miIntent = new Intent(Main.this, MainActivity.class);
                startActivity(miIntent);
            }
        });

        //final String url = "http://maps.googleapis.com/maps/api/geocode/json?" +
         //       "latlng=" + latitud + "," + longitud + "&sensor=false";


    }


    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            InputStream inputStream = null;

            HttpURLConnection urlConnection = null;

            Integer result = 0;
            try {
                /* forming th java.net.URL object */
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                 /* optional request header */
                urlConnection.setRequestProperty("Content-Type", "application/json");

                /* optional request header */
                urlConnection.setRequestProperty("Accept", "application/json");

                /* for Get request */
                urlConnection.setRequestMethod("GET");

                int statusCode = urlConnection.getResponseCode();

                /* 200 represents HTTP OK */
                if (statusCode ==  200) {

                    inputStream = new BufferedInputStream(urlConnection.getInputStream());

                    String response = convertInputStreamToString(inputStream);

                    parseResult(response);

                    result = 1; // Successful

                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }


        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Lets update UI

            String direccion="SIN DATOS PARA ESA LONGITUD Y LATITUD";
            if(result == 1){

                arrayAdapter = new ArrayAdapter(Main.this, android.R.layout.simple_list_item_1, titulosStr);

                //direccion = resultJSON.getJSONObject(0).getString("formatted_address");
                listView.setAdapter(arrayAdapter);
            }else{

                Log.e(TAG, "Failed to fetch data!");
            }
        }
    }


    private String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));

        String line = "";
        String result = "";

        while((line = bufferedReader.readLine()) != null){
            result += line;
        }

            /* Close Stream */
        if(null!=inputStream){
            inputStream.close();
        }

        return result;
    }
    private void parseResult(String result) {

        try{
            JSONObject response = new JSONObject(result);

            JSONArray posts = response.optJSONArray("results");

            titulosStr = new String[posts.length()];

            for(int i=0; i< posts.length();i++ ){
                JSONObject post = posts.optJSONObject(i);
                String title = post.optString("formatted_address");

                titulosStr[i] = title;
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

}
