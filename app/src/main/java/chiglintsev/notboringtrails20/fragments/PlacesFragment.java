package chiglintsev.notboringtrails20.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import chiglintsev.notboringtrails20.R;
import chiglintsev.notboringtrails20.adapters.PlacesAdapter;
import chiglintsev.notboringtrails20.models.Places;


public class PlacesFragment extends Fragment {

    private LinearLayoutManager linearLayoutManager;
    public PlacesAdapter adapter;
    private MaterialSearchView searchView;
    public ArrayList<Places> placesArrayList;
    private Location userLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        addLocations();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.places_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActiveAndroid.initialize(getActivity());


        //создание адаптера с данными из БД
        workWithList();

        //инициалицазия и заполнение списка
        recyclerWork();
    }


    @Override
    public void onResume() {
        super.onResume();

        //animation for open fragment
        animOpen();
    }

    private void animOpen() {
        Animation transition = AnimationUtils.loadAnimation(getActivity(), R.anim.transition);
        View globalView = getView().findViewById(R.id.places);
        globalView.startAnimation(transition);
    }

    private void recyclerWork() {
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_places);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void workWithList() {


        List<Places> placesList = new Select("Id", "name", "image_name", "lat", "lng")
                .from(Places.class)
                .execute();

        placesArrayList = new ArrayList<>();

        for (Places place : placesList) {
            place.distance = calculateDistance(place);
            placesArrayList.add(place);
        }

        Collections.sort(placesArrayList, new Comparator<Places>() {
            @Override
            public int compare(Places p1, Places p2) {
                return (int) p1.distance - (int) p2.distance;
            }
        });

        adapter = new PlacesAdapter();

        adapter.addAll(placesArrayList);
    }

    private void addLocations() {
        LocationManager locationManager = (LocationManager) getActivity()
                .getSystemService(
                        getActivity().LOCATION_SERVICE
                );
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 14);
        }
        userLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }

    public double calculateDistance(Places place) {
        Location placeLocation = new Location("placeLocation");
        placeLocation.setLatitude(place.lat);
        placeLocation.setLongitude(place.lng);
        double placeDistance = userLocation.distanceTo(placeLocation);
        return placeDistance;
    }
}
