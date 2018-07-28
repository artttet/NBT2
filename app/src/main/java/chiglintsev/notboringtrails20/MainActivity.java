package chiglintsev.notboringtrails20;


import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import chiglintsev.notboringtrails20.fragments.FavoritesFragment;
import chiglintsev.notboringtrails20.fragments.MapFragment;
import chiglintsev.notboringtrails20.fragments.ObjectsFragment;
import chiglintsev.notboringtrails20.fragments.PlacesFragment;
import chiglintsev.notboringtrails20.fragments.RoutesFragment;


public class MainActivity extends AppCompatActivity {

    public FragmentTransaction trans;
    private RoutesFragment routesFragment;
    private PlacesFragment placesFragment;
    private MapFragment mapFragment;
    private FavoritesFragment favoritesFragment;
    private ObjectsFragment objectsFragment;
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigation;


    {
        onNavigation = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.routesBnv:
                        transition(routesFragment);
                        return true;
                    case R.id.placesBnv:
                        transition(objectsFragment);
                        return true;
                    case R.id.favoritesBnv:
                        transition(favoritesFragment);
                        return true;
                    case R.id.moreBnv:
                        return true;
                }
                return false;
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        routesFragment = new RoutesFragment();
        placesFragment = new PlacesFragment();
        mapFragment = new MapFragment();
        favoritesFragment = new FavoritesFragment();
        objectsFragment = new ObjectsFragment();

        bnvWork();
        //transition(routesFragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void bnvWork() {
        BottomNavigationView bottomNavigation = findViewById(R.id.bnv);
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigation);
    }

    private void transition(Fragment fragment) {
        trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.for_fragment, fragment);
        trans.commit();
    }
}
