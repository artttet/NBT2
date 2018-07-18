package chiglintsev.notboringtrails20.fragments;

import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import chiglintsev.notboringtrails20.R;
import chiglintsev.notboringtrails20.SingletonFonts;
import chiglintsev.notboringtrails20.models.Places;

import static android.content.Context.LOCATION_SERVICE;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final int PERMISSION_CODE = 14;

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

    private ArrayList<Places> placesArrayList;
    private GoogleMap myMap;
    private LocationManager locationManager;
    private CameraPosition restorePosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        //Запрос в БД за местами
        loadObjects();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.map_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //fragment for GoogleMap
        addMFragment();
    }

    @Override
    public void onResume() {
        super.onResume();

        //animation for open fragment
        animOpen();
    }

    @Override
    public void onPause() {
        super.onPause();
        restorePosition = myMap.getCameraPosition();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;

        myMap.getUiSettings().setZoomControlsEnabled(true);

        //Анимация камеры к центру города
        myMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                        new LatLng(54.974895, 73.368213), 11
                )
        );

        for (final Places place : placesArrayList) {
             myMap.addMarker(
                    new MarkerOptions().position(
                            new LatLng(place.lat, place.lng)
                    )
            );
        }

        myMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {

                int markerId = Integer.parseInt(
                        marker.getId().substring(1)
                );
                Places place = placesArrayList.get(markerId);

                View view = getLayoutInflater().inflate(R.layout.map_card, null);
                TextView tv = view.findViewById(R.id.name_map_card);
                ImageView iv = view.findViewById(R.id.img_map_card);

                tv.setText(place.name);
                tv.setTypeface(
                        SingletonFonts.getInstance(getContext())
                                .getFont1()
                );
                iv.setImageBitmap(
                        BitmapFactory.decodeResource(
                                view.getResources(), getResources().getIdentifier(
                                        place.img_name, "drawable", getActivity().getPackageName()
                                )
                        )
                );
                return view;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });

        if (ContextCompat.checkSelfPermission(
                getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {

            //запрос разрешения на получения геолокации
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE);
        } else {
            if (!myMap.isMyLocationEnabled())
                myMap.setMyLocationEnabled(true);


            if(restorePosition == null){
                Location myLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                LatLng userLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                myMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(userLocation, 16), 1500, null
                );
            }else myMap.moveCamera(CameraUpdateFactory.newCameraPosition(restorePosition));

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }

    private void animOpen() {
        Animation transition = AnimationUtils.loadAnimation(getActivity(), R.anim.transition);
        View globalView = getView().findViewById(R.id.map);
        globalView.startAnimation(transition);
    }

    private void addMFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void loadObjects() {
        List<Places> placesList = new Select("Id", "name", "image_name", "lat", "lng")
                .from(Places.class)
                .execute();
        placesArrayList = new ArrayList<>();
        placesArrayList.addAll(placesList);
    }
}

