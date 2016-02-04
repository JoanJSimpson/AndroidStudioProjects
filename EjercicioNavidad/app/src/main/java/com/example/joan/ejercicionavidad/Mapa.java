package com.example.joan.ejercicionavidad;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class Mapa extends FragmentActivity {

    private GoogleMap mMap = null;


    public static final LatLng EUROPA = new LatLng(51.0777815, 5.9567456);
    public static final LatLng ASIA_Y_OCEANIA = new LatLng(7.8575679, 101.7559515);
    public static final LatLng AMERICA_Y_AFRICA = new LatLng(34.8218588, -114.9644596);
    public static LatLng USUARIO;
    public static LatLng foco;
    private LocationManager locManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        setUpMapIfNeeded();  //Configuramos el Mapa de ser necesario
        CameraPosition camPos;

        try {
            Bundle miBundleRecoger = getIntent().getExtras();
            if ((miBundleRecoger.getInt("ZONAMAPA")) == (R.drawable.asia_oceania)) {
                mMap.addMarker(new MarkerOptions()
                        .position(ASIA_Y_OCEANIA)
                        .title("ASIA Y OCEANIA")
                        .snippet("ZONA A"));
                foco = ASIA_Y_OCEANIA;
                camPos = new CameraPosition.Builder()
                        .target(foco)
                        .zoom(3)
                        .bearing(0) //Establecemos la orientación con el norte arriba
                        .tilt(70) //Bajamos el punto de vista de la camara 70 grados
                        .build();
                //setMarker(ASIA_Y_OCEANIA, "ASIA Y OCEANIA", "ZONA A");

            } else if ((miBundleRecoger.getInt("ZONAMAPA")) == (R.drawable.america_africa)) {
                mMap.addMarker(new MarkerOptions()
                        .position(AMERICA_Y_AFRICA)
                        .title("AMERICA Y AFRICA")
                        .snippet("ZONA B"));
                foco = AMERICA_Y_AFRICA;
                camPos = new CameraPosition.Builder()
                        .target(foco)
                        .zoom(3)
                        .bearing(0) //Establecemos la orientación con el norte arriba
                        .tilt(70) //Bajamos el punto de vista de la camara 70 grados
                        .build();
                //setMarker(AMERICA_Y_AFRICA, "AMERICA Y AFRICA", "ZONA B"); // Agregamos el marcador verde
            } else {
                mMap.addMarker(new MarkerOptions()
                        .position(EUROPA)
                        .title("EUROPA")
                        .snippet("ZONA C"));
                foco = EUROPA;
                camPos = new CameraPosition.Builder()
                        .target(foco)
                        .zoom(3)
                        .bearing(0) //Establecemos la orientación con el norte arriba
                        .tilt(70) //Bajamos el punto de vista de la camara 70 grados
                        .build();
                //setMarker(EUROPA, "EUROPA", "ZONA C"); // Agregamos el marcador verde
            }
        } catch (Exception e) {

            //todo poner la ubicacion actual aqui
            //Obtenemos una referencia al LocationManager
            locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            //Obtenemos la última posición conocida
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            USUARIO = new LatLng(loc.getLatitude(), loc.getLongitude());
            foco = USUARIO;
            camPos = new CameraPosition.Builder()
                    .target(foco)
                    .zoom(15)
                    .bearing(2) //Establecemos la orientación con el norte arriba
                    .tilt(70) //Bajamos el punto de vista de la camara 70 grados
                    .build();

        }

        CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);

        mMap.animateCamera(camUpd3);
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
    //Este metodo no lo utilizo pero viene bien por si quiero cambiar el color del marcador
    private void setMarker(LatLng position, String titulo, String info) {
        // Agregamos marcadores para indicar sitios de interéses.
        Marker myMaker = mMap.addMarker(new MarkerOptions()
                .position(position)
                .title(titulo)  //Agrega un titulo al marcador
                .snippet(info)   //Agrega información detalle relacionada con el marcador
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))); //Color del marcador
    }



}