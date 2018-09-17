package omsk.turinfo;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import omsk.turinfo.fragments.FavoritesFragment;
import omsk.turinfo.fragments.MapFragment;
import omsk.turinfo.fragments.MoreFragment;
import omsk.turinfo.fragments.PlacesFragment;
import omsk.turinfo.fragments.RoutesFragment;
import omsk.turinfo.fragments.SearchListFragment;
import omsk.turinfo.models.Places;


public class MainActivity extends AppCompatActivity {

    public FragmentTransaction trans;
    public boolean checkMarker = false;
    public String userQuery;
    private SharedPreferences sPref;
    private TextView what, titleMap, titleList;
    private Animation translateMain, translateLeft, translateRight, translateMainRev, translateLeftRev, translateRightRev;
    private int fragmentCheck;
    private BottomNavigationView bottomNavigation;
    private MaterialSearchView msv;
    private View mainCard, leftCard, rightCard;
    private RoutesFragment routesFragment;
    private PlacesFragment placesFragment;
    private MapFragment mapFragment;
    private FavoritesFragment favoritesFragment;
    private SearchListFragment searchListFragment;
    private MoreFragment moreFragment;
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
                        if (fragmentCheck == 0) {
                            transition(mapFragment);
                        } else {
                            transition(placesFragment);
                        }

                        if (bottomNavigation.getSelectedItemId() != R.id.placesBnv) {
                            mainCard.setVisibility(View.VISIBLE);
                            if (!checkMarker) {
                                leftCard.setVisibility(View.VISIBLE);
                                rightCard.setVisibility(View.VISIBLE);
                            }
                            topBarAnim();
                        }
                        return true;
                    case R.id.favoritesBnv:
                        transition(favoritesFragment);
                        topBarOff();
                        return true;
                    case R.id.moreBnv:
                        transition(moreFragment);
                        topBarOff();
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

        sPref = getPreferences(MODE_PRIVATE);
        if(sPref.getInt("first", 0) == 0) {

            SharedPreferences.Editor ed = sPref.edit();
            ed.putInt("first",1);
            ed.apply();

            Intent intent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(intent);
        }



        loadAnims();

        fragmentCheck = 0;

        routesFragment = new RoutesFragment();
        placesFragment = new PlacesFragment();
        mapFragment = new MapFragment();
        favoritesFragment = new FavoritesFragment();
        searchListFragment = new SearchListFragment();
        moreFragment = new MoreFragment();
        mainCard = findViewById(R.id.search_place);
        leftCard = findViewById(R.id.title_map_card);
        rightCard = findViewById(R.id.title_list_card);
        what = findViewById(R.id.what);
        what.setTypeface(SingletonFonts.getInstance(this).getFont3());
        titleMap = findViewById(R.id.title_map);
        titleMap.setTypeface(SingletonFonts.getInstance(this).getFont3());
        titleList = findViewById(R.id.title_list);
        titleList.setTypeface(SingletonFonts.getInstance(this).getFont3());

        msv = findViewById(R.id.search);

        //transition(routesFragment);
        transition(routesFragment);

        bnvWork();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    private void bnvWork() {
        bottomNavigation = findViewById(R.id.bnv);
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigation);
    }

    public void transition(Fragment fragment) {
        trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.for_fragment, fragment);
        trans.commit();
    }

    public void openSearch(View view) {
        findViewById(R.id.search_icon).setVisibility(View.INVISIBLE);

        if (fragmentCheck == 0) {
            what.setText("Что вы ищете?");

            if (mapFragment.checkCategory != -1) {
                mapFragment.addCategory(-1);
            }
            transition(searchListFragment);
            searchListFragment.mapSearch(msv);
            if (checkMarker) {
                what.setVisibility(View.INVISIBLE);
                checkMarker = false;
                mapFragment.setCheckMarker(false);
            }
        } else if (fragmentCheck == 1) {
            what.setText("Что вы ищете?");
            placesFragment.placesSearch(msv);
        }
    }

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

    public void getMapFragment(View view) {
        fragmentCheck = 0;
        trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.for_fragment, mapFragment);
        trans.commit();
    }

    public void getPlacesFragment(View view) {
        fragmentCheck = 1;
        trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.for_fragment, placesFragment);
        trans.commit();
    }

    public void topBarAnim() {
        if (!checkMarker) {
            leftCard.startAnimation(translateLeft);
            rightCard.startAnimation(translateRight);
        }
        mainCard.startAnimation(translateMain);
        topBarOn();
    }

    private void loadAnims() {
        translateMain = AnimationUtils.loadAnimation(this, R.anim.search_anim);
        translateLeft = AnimationUtils.loadAnimation(this, R.anim.title_map_card);
        translateRight = AnimationUtils.loadAnimation(this, R.anim.title_list_card);
    }

    @Override
    public void onBackPressed() {
        if (msv.isSearchOpen()) {
            msv.closeSearch();
        } else super.onBackPressed();
    }

    public MapFragment getFragment() {
        return this.mapFragment;
    }

    public void getMapFragment(int category) {
        fragmentCheck = 0;
        mapFragment.addCategory(category);
        checkMarker = true;
        mapFragment.setCheckMarker(true);
        trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.for_fragment, mapFragment);
        trans.commit();
    }

    public void msvClose(String name, String from) {
        leftCard.setVisibility(View.INVISIBLE);
        rightCard.setVisibility(View.INVISIBLE);
        msv.closeSearch();
        what.setText(name);
        if (from.equals("place")) {
            searchListFragment.addQuery(userQuery);
        }
        Log.d("place", "from MainActivity --- " + userQuery);
    }

    public void getMarker(Places place) {
        mapFragment.showMarker(place);
    }

    public void setBnvItem() {
        bottomNavigation.getMenu().getItem(0).setChecked(true);

    }

}
