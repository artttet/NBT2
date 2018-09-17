package omsk.turinfo.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import omsk.turinfo.R;
import omsk.turinfo.adapters.PlacesAdapter;
import omsk.turinfo.models.Places;


public class PlacesFragment extends Fragment {
    public PlacesAdapter adapter;
    public ArrayList<Places> placesArrayList;
    private Animation translateMain, translateLeft, translateRight, translateMainRev, translateLeftRev, translateRightRev;
    private View leftCard, rightCard, mainCard;
    private LinearLayoutManager linearLayoutManager;
    private Location userLocation;
    private int checkOpenSearch;
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


    private void topBarOff() {
        mainCard.setVisibility(View.GONE);
        leftCard.setVisibility(View.GONE);
        rightCard.setVisibility(View.GONE);
    }

    private void topBarOn() {
        leftCard.setVisibility(View.VISIBLE);
        rightCard.setVisibility(View.VISIBLE);
        mainCard.setVisibility(View.VISIBLE);
    }

    public void topBarAnim() {
        leftCard.startAnimation(translateLeft);
        rightCard.startAnimation(translateRight);
        mainCard.startAnimation(translateMain);
        topBarOn();
    }

    public void topBarAnimRev() {
        leftCard.startAnimation(translateLeftRev);
        rightCard.startAnimation(translateRightRev);
        mainCard.startAnimation(translateMainRev);
        topBarOff();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        checkOpenSearch = 0;
        linearLayoutManager = new LinearLayoutManager(getActivity());
        addLocations();

        loadAnims();
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

        mainCard = getActivity().findViewById(R.id.search_place);
        leftCard = getActivity().findViewById(R.id.title_map_card);
        rightCard = getActivity().findViewById(R.id.title_list_card);

    }

    @Override
    public void onResume() {
        super.onResume();

        //animation for open fragment
        animOpen();
    }

    private void loadAnims() {
        translateMain = AnimationUtils.loadAnimation(getContext(), R.anim.search_anim);
        translateLeft = AnimationUtils.loadAnimation(getContext(), R.anim.title_map_card);
        translateRight = AnimationUtils.loadAnimation(getContext(), R.anim.title_list_card);
        translateMainRev = AnimationUtils.loadAnimation(getContext(), R.anim.search_anim_rev);
        translateLeftRev = AnimationUtils.loadAnimation(getContext(), R.anim.title_map_card_rev);
        translateRightRev = AnimationUtils.loadAnimation(getContext(), R.anim.title_list_card_rev);
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
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (checkOpenSearch == 0) {
                    if (dy > 10) {
                        if (mainCard.getVisibility() == View.VISIBLE) {
                            topBarAnimRev();
                        }
                    } else if (dy < -10) {
                        if (mainCard.getVisibility() == View.GONE) {
                            topBarAnim();
                        }
                    }
                }

//   > - rev | < - anim
            }
        });
    }

    private void workWithList() {
        List<Places> placesList = new Select("Id", "name", "image_name", "lat", "lng")
                .from(Places.class)
                .where("Id < ?", 59)
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
        } else {
            userLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (userLocation == null) {
                userLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }
    }

    public float calculateDistance(Places place) {
        Location placeLocation = new Location("placeLocation");
        placeLocation.setLatitude(place.lat);
        placeLocation.setLongitude(place.lng);
        return (float) userLocation.distanceTo(placeLocation);
    }


    public void placesSearch(final MaterialSearchView msv) {
        msv.showSearch();


        msv.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<Places> result = new ArrayList<>();
                for (Places place : placesArrayList) {
                    if (place.name.toLowerCase().contains(query.toLowerCase())) {
                        result.add(place);
                    }
                }
                adapter.setList(result);
                msv.hideKeyboard(msv);
                msv.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (getActivity().findViewById(R.id.what).getVisibility() == View.VISIBLE) {
                    getActivity().findViewById(R.id.what).setVisibility(View.GONE);
                }
                if (!newText.isEmpty()) {
                    ArrayList<Places> result = new ArrayList<>();
                    for (Places place : placesArrayList) {
                        if (place.name.toLowerCase().contains(newText.toLowerCase())) {
                            result.add(place);
                        }
                        adapter.setList(result);
                    }
                } else if (newText.isEmpty()) {
                    getActivity().findViewById(R.id.what).setVisibility(View.VISIBLE);
                    adapter.setList(placesArrayList);
                }
                return false;
            }
        });

        msv.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                checkOpenSearch = 1;
                getActivity().findViewById(R.id.search_icon).setVisibility(View.GONE);
                getActivity().findViewById(R.id.bnv).setVisibility(View.GONE);
                leftCard.startAnimation(translateLeftRev);
                leftCard.setVisibility(View.GONE);
                rightCard.startAnimation(translateRightRev);
                rightCard.setVisibility(View.GONE);
            }

            @Override
            public void onSearchViewClosed() {
                checkOpenSearch = 0;
                getActivity().findViewById(R.id.what).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.search_icon).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.bnv).setVisibility(View.VISIBLE);
                leftCard.startAnimation(translateLeft);
                leftCard.setVisibility(View.VISIBLE);
                rightCard.startAnimation(translateRight);
                rightCard.setVisibility(View.VISIBLE);
            }
        });
    }
}
