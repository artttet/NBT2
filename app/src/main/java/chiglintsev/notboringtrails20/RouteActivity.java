package chiglintsev.notboringtrails20;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;

import chiglintsev.notboringtrails20.adapters.CardsAdapter;
import chiglintsev.notboringtrails20.models.Places;
import chiglintsev.notboringtrails20.models.Route1;
import chiglintsev.notboringtrails20.models.Route2;
import chiglintsev.notboringtrails20.models.Route3;
import chiglintsev.notboringtrails20.models.Route4;
import chiglintsev.notboringtrails20.models.Route5;
import chiglintsev.notboringtrails20.models.Route6;
import chiglintsev.notboringtrails20.models.Route7;

public class RouteActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int PERMISSION_CODE = 14;
    private LatLng centr_route;
    private static final LatLng ROUTE1_CENTR = new LatLng(54.985563, 73.374540);
    private static final LatLng ROUTE2_CENTR = new LatLng(54.982010, 73.376941);
    private static final LatLng ROUTE3_CENTR = new LatLng(54.982122, 73.374403);
    private static final LatLng ROUTE4_CENTR = new LatLng(54.982010, 73.376941);
    private static final LatLng ROUTE5_CENTR = new LatLng(54.984789, 73.370660);
    private static final LatLng ROUTE6_CENTR = new LatLng(54.990185, 73.367569);
    private static final LatLng ROUTE7_CENTR = new LatLng(54.977542, 73.377802);




    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };
    private GoogleMap myMap;
    private long routeId;
    private ArrayList<Places> mainList;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        ActiveAndroid.initialize(this);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mainList = new ArrayList<>();
        routeId = getIntent().getLongExtra("id", -1);

        loadPlaces(routeId);

        DiscreteScrollView discreteScrollView = findViewById(R.id.cards);
        discreteScrollView.setAdapter(new CardsAdapter(mainList));

        discreteScrollView.setItemTransformer(new ScaleTransformer.Builder()
        .setMaxScale(1.05f)
        .setMinScale(0.8f)
        .setPivotX(Pivot.X.CENTER)
        .setPivotY(Pivot.Y.BOTTOM)
        .build());

        discreteScrollView.setHasFixedSize(true);
    }

    private void loadPlaces(long routeId) {
        List<Long> placesIdList = new ArrayList<>();

        switch ((int)routeId) {

            case 0 : {
                centr_route = ROUTE1_CENTR;
                List<Route1> idList = new Select()
                        .from(Route1.class)
                        .execute();
                for(Route1 route1 : idList){
                    placesIdList.add(route1.place_id);
                    Log.d("test2", String.valueOf(route1.place_id));
                }
            }break;

            case 1 : {
                centr_route = ROUTE2_CENTR;
                List<Route2> idList = new Select()
                        .from(Route2.class)
                        .execute();
                for(Route2 route2 : idList){
                    placesIdList.add(route2.place_id);
                }
            }break;

            case 2 : {
                centr_route = ROUTE3_CENTR;
                List<Route3> idList = new Select()
                        .from(Route3.class)
                        .execute();
                for(Route3 route3 : idList){
                    placesIdList.add(route3.place_id);
                }
            }break;

            case 3 : {
                centr_route = ROUTE4_CENTR;
                List<Route4> idList = new Select()
                        .from(Route4.class)
                        .execute();
                for(Route4 route4 : idList){
                    placesIdList.add(route4.place_id);
                }
            }break;

            case 4 : {
                centr_route = ROUTE5_CENTR;
                List<Route5> idList = new Select()
                        .from(Route5.class)
                        .execute();
                for(Route5 route5 : idList){
                    placesIdList.add(route5.place_id);
                }
            }break;

            case 5 : {
                centr_route = ROUTE6_CENTR;
                List<Route6> idList = new Select()
                        .from(Route6.class)
                        .execute();
                for(Route6 route6 : idList){
                    placesIdList.add(route6.place_id);
                }
            }break;

            case 6 : {
                centr_route = ROUTE7_CENTR;
                List<Route7> idList = new Select()
                        .from(Route7.class)
                        .execute();
                for(Route7 route7 : idList){
                    placesIdList.add(route7.place_id);
                }
            }break;
        }

        for (int i = 0; i < placesIdList.size(); i++) {
            Places place = new Select("Id", "name", "image_name", "text", "lat", "lng", "category")
                    .from(Places.class)
                    .where("Id = ?", placesIdList.get(i))
                    .executeSingle();
            mainList.add(place);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE);
        } else {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, locationListener);
            myMap.setMyLocationEnabled(true);
        }


        myMap.getUiSettings().setMyLocationButtonEnabled(true);

        myMap.setMapType(2);

        addMarkers();


    }

    private void addMarkers() {
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centr_route, 15.5f));
        IconGenerator icon = new IconGenerator(this);
        icon.setStyle(IconGenerator.STYLE_BLUE);
        int count = 1;
        for(Places place : mainList){
            Bitmap iconBitmap = icon.makeIcon(String.valueOf(count));
            myMap.addMarker(new MarkerOptions()
                .position(new LatLng(place.lat, place.lng))
                .icon(BitmapDescriptorFactory.fromBitmap(iconBitmap))
            );
            count++;
        }
    }


    public void back(View view) {
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(locationManager != null)locationManager.removeUpdates(locationListener);
    }

    public CameraPosition getMapCamera(){
        return myMap.getCameraPosition();
    }

    public void animateToMarker(Places place){
        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(place.lat, place.lng), 16));

    }

}
