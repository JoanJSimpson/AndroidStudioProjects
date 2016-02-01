package com.example.joan.ejercicionavidad;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa extends FragmentActivity{

    private GoogleMap mMap=null;


    public static final LatLng SAGRADA_FAMILIA = new LatLng(41.40347, 2.17432);
    public static final LatLng MADRID = new LatLng(40.4378693, -3.8199648);
    public static final LatLng CIUDAD_ARTES = new LatLng(39.4532979, -0.3556489);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        setUpMapIfNeeded();  //Configuramos el Mapa de ser necesario

        //todo poner las coordenadas de los distintos continentes
        Bundle miBundleRecoger = getIntent().getExtras();
        if((miBundleRecoger.getInt("ZONAMAPA"))==(R.drawable.asia_oceania)){
            setMarker(SAGRADA_FAMILIA, "Sagrada Familia", "Distrito: Barcelona"); // Agregamos el marcador verde
        }else if((miBundleRecoger.getInt("ZONAMAPA"))==(R.drawable.america_africa)){
            setMarker(CIUDAD_ARTES,"Ciudad de las Artes y las Ciencias","Distrito: Valencia"); // Agregamos el marcador verde
        }else{
            setMarker(MADRID,"Madrid","Distrito: Madrid"); // Agregamos el marcador verde
        }


    }

    private void setUpMapIfNeeded() {
// Configuramos el objeto GoogleMaps con valores iniciales.
        if (mMap == null) {
            //Instanciamos el objeto mMap a partir del MapFragment definido bajo el Id "map"
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            // Chequeamos si se ha obtenido correctamente una referencia al objeto GoogleMap
            if (mMap != null) {
                // El objeto GoogleMap ha sido referenciado correctamente
                //ahora podemos manipular sus propiedades

                //Seteamos el tipo de mapa
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                //Activamos la capa o layer MyLocation
                mMap.setMyLocationEnabled(true);
            }
        }
    }
    private void setMarker(LatLng position, String titulo, String info) {
        // Agregamos marcadores para indicar sitios de interéses.
        Marker myMaker = mMap.addMarker(new MarkerOptions()
                .position(position)
                .title(titulo)  //Agrega un titulo al marcador
                .snippet(info)   //Agrega información detalle relacionada con el marcador
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))); //Color del marcador
    }
}
