package chiglintsev.notboringtrails20.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    public PlacesAdapter adapter;
    public ArrayList<Places> placesArrayList;
    private LinearLayoutManager linearLayoutManager;
    private MaterialSearchView searchView;
    private Location userLocation;
    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
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

        //инициализация и работа с тулбаром
        workWithToolbar(view);

        //работа с поиском
        addSearch(view);
    }

    @Override
    public void onResume() {
        super.onResume();

        //animation for open fragment
        animOpen();
    }

    private void animOpen() {
        Animation transition = AnimationUtils.loadAnimation(getActivity(), R.anim.transition);
        View view = getView().findViewById(R.id.recycler_places);
        view.startAnimation(transition);
    }

    private void recyclerWork() {
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_places);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void workWithToolbar(View view){
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Места");
    }

    private void workWithList() {
        List<Places> placesList = new Select("Id", "name", "image_name", "lat", "lng")
                .from(Places.class)
                .execute();

        placesArrayList = new ArrayList<>();

        for (Places place : placesList) {
            try {
                place.distance = calculateDistance(place);
            } catch (NullPointerException e) {
            }
            Location placeLocation = new Location("placeLocation");
            placeLocation.setLatitude(place.lat);
            placeLocation.setLongitude(place.lng);
            placesArrayList.add(place);
        }

        Collections.sort(placesArrayList, new Comparator<Places>() {
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
        userLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (userLocation == null) {
            userLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
    }

    public float calculateDistance(Places place) {
        Location placeLocation = new Location("placeLocation");
        placeLocation.setLatitude(place.lat);
        placeLocation.setLongitude(place.lng);
        return (float) userLocation.distanceTo(placeLocation);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d("place2", "+++");
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
    }

    private void addSearch(View view) {
        searchView = view.findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<Places> result = new ArrayList<>();
                for (Places place : placesArrayList) {
                    if (place.name.toLowerCase().contains(query.toLowerCase())) {
                        result.add(place);
                    }
                }
                adapter.setList(result);
                searchView.hideKeyboard(searchView);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    ArrayList<Places> result = new ArrayList<>();
                    for (Places place : placesArrayList) {
                        if (place.name.toLowerCase().contains(newText.toLowerCase())) {
                            result.add(place);
                        }
                        adapter.setList(result);
                    }
                } else if (newText.isEmpty()) {
                    adapter.setList(placesArrayList);
                }
                return false;
            }
        });
    }
}
