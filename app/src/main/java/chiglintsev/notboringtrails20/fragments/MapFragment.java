package chiglintsev.notboringtrails20.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import chiglintsev.notboringtrails20.PlaceActivity2;
import chiglintsev.notboringtrails20.R;
import chiglintsev.notboringtrails20.SingletonFonts;
import chiglintsev.notboringtrails20.models.Places;

import static android.content.Context.LOCATION_SERVICE;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final int PERMISSION_CODE = 14;
    private final static String KEY_FOR_PLACE_id = "id_key";
    public int checkCategory = -1;
    MyAsyncTask asyncTask;
    Places searchPlace = null;
    boolean checkMarker = false;
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
        asyncTask = new MyAsyncTask();
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

        if(checkCategory != -1){
            getActivity().findViewById(R.id.title_map_card).setVisibility(View.GONE);
            getActivity().findViewById(R.id.title_list_card).setVisibility(View.GONE);
        }
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
        try {
            restorePosition = myMap.getCameraPosition();
        } catch (NullPointerException e) {

        }
        asyncTask.cancel(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
        myMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_style));

        if (checkCategory == -1) {
            asyncTask.execute();
        } else if (checkCategory > -1){
            addMarkers(checkCategory);
        }

        if (checkCategory == -2) {
            myMap.addMarker(new MarkerOptions().position(new LatLng(searchPlace.lat, searchPlace.lng)).title(searchPlace.name));

            //infoWindow and listener
            myMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {

                    String title = marker.getTitle();
                    Places place = new Select("Id", "name", "image_name")
                            .from(Places.class)
                            .where("name = ?", title)
                            .executeSingle();


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

            myMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Places place = new Select("Id", "name")
                            .from(Places.class)
                            .where("name = ?", marker.getTitle())
                            .executeSingle();
                    Intent intent = new Intent(getActivity(), PlaceActivity2.class);
                    intent.putExtra(KEY_FOR_PLACE_id, place.id);
                    startActivity(intent);
                }
            });

            if(checkMarker){
                myMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                                new LatLng(searchPlace.lat, searchPlace.lng), 16
                        ));
                checkMarker = false;
            }else {
                myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(54.974895, 73.368213), 13)
                );
            }
        }

        myMap.getUiSettings().setZoomControlsEnabled(true);
        myMap.getUiSettings().setMyLocationButtonEnabled(false);

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

            //проверка для восстановления позиции камеры при переключении между фрагментами
            if (restorePosition == null) {
                Location myLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                try {
                    LatLng userLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                    if (checkCategory == -1) myMap.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(userLocation, 16), 1500, null
                    );

                } catch (NullPointerException e) {
                }

            } else if (restorePosition != null && checkCategory == -1)
                myMap.moveCamera(CameraUpdateFactory.newCameraPosition(restorePosition));
        }


    }

    @Override
    public void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }

    private void animOpen() {
        Animation transition = AnimationUtils.loadAnimation(getActivity(), R.anim.transition);
        View view = getActivity().findViewById(R.id.for_fragment);
        view.startAnimation(transition);
    }

    private void addMFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void loadObjects() {
        List<Places> placesList = new Select("Id", "name", "image_name", "lat", "lng", "category")
                .from(Places.class)
                .execute();
        placesArrayList = new ArrayList<>();
        placesArrayList.addAll(placesList);
    }

    public Bitmap resizeMapIcons(String iconName, int width, int height) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(iconName, "drawable", getActivity().getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    public void addCategory(int category) {
        checkCategory = category;
    }

    public void setCheckMarker(boolean b){
        checkMarker = b;
    }

    public void showMarker(Places place) {
        myMap.clear();
        searchPlace = place;
    }

    private void addMarkers(int category) {
        if(checkMarker){
            myMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                            new LatLng(54.974895, 73.368213), 13
                    ));
            checkMarker = false;
        }else myMap.moveCamera(CameraUpdateFactory.newCameraPosition(restorePosition));


        for (final Places place : placesArrayList) {
            if (place.category == category) {
                myMap.addMarker(new MarkerOptions().position(new LatLng(place.lat, place.lng)).title(place.name));
            }
        }

        //infoWindow and listener
        myMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {

                String title = marker.getTitle();
                Places place = new Select("Id", "name", "image_name")
                        .from(Places.class)
                        .where("name = ?", title)
                        .executeSingle();


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

        myMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Places place = new Select("Id", "name")
                        .from(Places.class)
                        .where("name = ?", marker.getTitle())
                        .executeSingle();
                Intent intent = new Intent(getActivity(), PlaceActivity2.class);
                intent.putExtra(KEY_FOR_PLACE_id, place.id);
                startActivity(intent);
            }
        });

    }

    //Task for markers
    private class MyAsyncTask extends AsyncTask<GoogleMap, MarkerOptions, GoogleMap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected GoogleMap doInBackground(GoogleMap... googleMaps) {
            for (final Places place : placesArrayList) {
                if (place.category == 0) {
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(new LatLng(place.lat, place.lng))
                            .icon(
                                    BitmapDescriptorFactory.fromBitmap(resizeMapIcons("vectorpaint", 96, 96))
                            )
                            .title(place.name);
                    publishProgress(markerOptions);
                    continue;
                } else if (place.category == 1) {
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(new LatLng(place.lat, place.lng))
                            .icon(
                                    BitmapDescriptorFactory.fromBitmap(resizeMapIcons("sculpture2", 48, 48))
                            )
                            .title(place.name);
                    publishProgress(markerOptions);
                    continue;
                } else if (place.category == 2) {
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(new LatLng(place.lat, place.lng))
                            .icon(
                                    BitmapDescriptorFactory.fromBitmap(resizeMapIcons("church2", 96, 96))
                            )
                            .title(place.name);
                    publishProgress(markerOptions);
                    continue;
                } else if (place.category == 3) {
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(new LatLng(place.lat, place.lng))
                            .icon(
                                    BitmapDescriptorFactory.fromBitmap(resizeMapIcons("museum2", 96, 96))
                            )
                            .title(place.name);
                    publishProgress(markerOptions);
                    continue;
                } else if (place.category == 4) {
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(new LatLng(place.lat, place.lng))
                            .icon(
                                    BitmapDescriptorFactory.fromBitmap(resizeMapIcons("theatr2", 96, 96))
                            )
                            .title(place.name);
                    publishProgress(markerOptions);
                    continue;
                }
            }
            return null;
        }


        @Override
        protected void onProgressUpdate(MarkerOptions... values) {
            super.onProgressUpdate(values);

            myMap.addMarker(values[0]);


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

            myMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    int markerId = Integer.parseInt(marker.getId().substring(1));
                    Places place = placesArrayList.get(markerId);
                    Intent intent = new Intent(getActivity(), PlaceActivity2.class);
                    intent.putExtra(KEY_FOR_PLACE_id, place.id);
                    startActivity(intent);
                }
            });
        }
    }
}

