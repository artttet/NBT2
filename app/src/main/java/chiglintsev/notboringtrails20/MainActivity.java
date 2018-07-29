package chiglintsev.notboringtrails20;


import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import chiglintsev.notboringtrails20.fragments.FavoritesFragment;
import chiglintsev.notboringtrails20.fragments.MapFragment;
import chiglintsev.notboringtrails20.fragments.PlacesFragment;
import chiglintsev.notboringtrails20.fragments.RoutesFragment;


public class MainActivity extends AppCompatActivity {

    private TextView what;
    public FragmentTransaction trans;
    private Animation translateMain, translateLeft, translateRight, translateMainRev, translateLeftRev, translateRightRev;
    private int fragementCheck;
    private ImageView searchIcon;
    private BottomNavigationView bottomNavigation;
    private MaterialSearchView msv;
    private View searchView, leftCard, rightCard;
    private RoutesFragment routesFragment;
    private PlacesFragment placesFragment;
    private MapFragment mapFragment;
    private FavoritesFragment favoritesFragment;
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigation;

    {
        onNavigation = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.routesBnv:
                        transition(routesFragment);
                        topBarOff();
                        return true;
                    case R.id.placesBnv:
                        if (fragementCheck == 0) {
                            transition(mapFragment);
                        } else {
                            transition(placesFragment);
                        }

                        if (bottomNavigation.getSelectedItemId() != R.id.placesBnv) {
                            searchIcon = findViewById(R.id.search_icon);
                            searchView.setVisibility(View.VISIBLE);
                            leftCard.setVisibility(View.VISIBLE);
                            rightCard.setVisibility(View.VISIBLE);
                            topBarAnim();
                        }
                        return true;
                    case R.id.favoritesBnv:
                        transition(favoritesFragment);
                        topBarOff();
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



        loadAnims();

        fragementCheck = 0;
        searchView = findViewById(R.id.search_place);
        leftCard = findViewById(R.id.title_map_card);
        rightCard = findViewById(R.id.title_list_card);
        what = findViewById(R.id.what);
        routesFragment = new RoutesFragment();
        placesFragment = new PlacesFragment();
        mapFragment = new MapFragment();
        favoritesFragment = new FavoritesFragment();

        bnvWork();
        //transition(routesFragment);
        msv = findViewById(R.id.search);

        transition(routesFragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void bnvWork() {
        bottomNavigation = findViewById(R.id.bnv);
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigation);
    }

    private void transition(Fragment fragment) {
        trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.for_fragment, fragment);
        trans.commit();
    }


    public void openSearch(View view) {
        msv.showSearch();
    }

    private void topBarOff() {
        searchView.setVisibility(View.GONE);
        leftCard.setVisibility(View.GONE);
        rightCard.setVisibility(View.GONE);
    }

    private void topBarOn() {
        leftCard.setVisibility(View.VISIBLE);
        rightCard.setVisibility(View.VISIBLE);
        searchView.setVisibility(View.VISIBLE);
    }

    public void getMapFragment(View view) {
        fragementCheck = 0;
        trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.for_fragment, mapFragment);
        trans.commit();
    }

    public void getPlacesFragment(View view) {
        fragementCheck = 1;
        trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.for_fragment, placesFragment);
        trans.commit();
    }

    public void topBarAnim() {
        leftCard.startAnimation(translateLeft);
        rightCard.startAnimation(translateRight);
        searchView.startAnimation(translateMain);
        topBarOn();
    }


    private void loadAnims() {
        translateMain = AnimationUtils.loadAnimation(this, R.anim.search_anim);
        translateLeft = AnimationUtils.loadAnimation(this, R.anim.title_map_card);
        translateRight = AnimationUtils.loadAnimation(this, R.anim.title_list_card);
    }

    @Override
    public void onBackPressed() {
        if(msv.isSearchOpen()){
            msv.closeSearch();
        }else super.onBackPressed();
    }
}
