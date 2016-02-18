package com.example.joan.codigoremotas1;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.support.v7.app.ActionBar;
        import android.support.v7.app.ActionBarActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ListAdapter;
        import android.widget.ListView;
        import android.widget.SimpleAdapter;
        import android.widget.TextView;
        import android.widget.Toast;

//import org.apache.http.NameValuePair;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> empresaList;

    // url to get all products list
    private static String url_all_empresas = "http://basededatosremotas.esy.es/joanconnect/get_all_empresas.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "pedidos";
    private static final String TAG_ID = "id";
    private static final String TAG_NOMBRE = "usuarioDNI";
    private static final String TAG_ZONAID = "zonaId";
    private static final String TAG_TARIFA = "tarifa";
    private static final String TAG_PESO = "peso";
    private static final String TAG_DECORACION = "decoracion";
    private static final String TAG_PRECIO = "precio";

    // products JSONArray
    JSONArray products = null;

    ListView lista;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hashmap para el ListView
        empresaList = new ArrayList<HashMap<String, String>>();
        Toast.makeText(this, "ACCESO A DATOS", Toast.LENGTH_SHORT).show();

        // Cargar los productos en el Background Thread
        lista = (ListView) findViewById(R.id.listAllProducts);
        new LoadAllProducts().execute();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }//fin onCreate


    class LoadAllProducts extends AsyncTask<String, String, String> {
        /** Antes de empezar el background thread Show Progress Dialog  * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Cargando pedidos. Por favor espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * obteniendo todos los productos
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List params = new ArrayList();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_empresas, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(TAG_PRODUCTS);

                    // looping through All Products
                    //Log.i("ramiro", "produtos.length" + products.length());
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString(TAG_ID);
                        String dni = c.getString(TAG_NOMBRE);
                        String zonaId = c.getString(TAG_ZONAID);
                        String tarifa = c.getString(TAG_TARIFA);
                        String precio = c.getString(TAG_PRECIO);
                        String peso = c.getString(TAG_PESO);
                        String decoracion = c.getString(TAG_DECORACION);

                        // creating new HashMap
                        HashMap map = new HashMap();

                        // adding each child node to HashMap key => value
                        map.put(TAG_ID, id);
                        map.put(TAG_NOMBRE, dni);
                        map.put(TAG_ZONAID, zonaId);
                        map.put(TAG_TARIFA, tarifa);
                        map.put(TAG_PESO, peso);
                        map.put(TAG_DECORACION, decoracion);
                        map.put(TAG_PRECIO, precio);

                        empresaList.add(map);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /** Updating parsed JSON data into ListView * */
                    ListAdapter adapter = new SimpleAdapter( MainActivity.this, empresaList, R.layout.single_post,
                            new String[] {
                                    TAG_ID,
                                    TAG_NOMBRE,
                                    TAG_ZONAID,
                                    TAG_TARIFA,
                                    TAG_PRECIO,
                                    TAG_PESO,
                                    TAG_DECORACION,
                            },
                            new int[] {
                                    R.id.single_post_tv_id,
                                    R.id.single_post_tv_nombre,
                                    R.id.single_post_tv_zonaId,
                                    R.id.single_post_tv_tarifa,
                                    R.id.single_post_tv_precio,
                                    R.id.single_post_tv_peso,
                                    R.id.single_post_tv_decoracion,
                            });
                    // updating listview
                    //setListAdapter(adapter);
                    lista.setAdapter(adapter);
                }
            });
        }
    }// Class Asincktst
}