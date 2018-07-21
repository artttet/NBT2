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
import chiglintsev.notboringtrails20.fragments.PlacesFragment;
import chiglintsev.notboringtrails20.fragments.RoutesFragment;


public class MainActivity extends AppCompatActivity {

    public FragmentTransaction trans;
    private RoutesFragment routesFragment;
    private PlacesFragment placesFragment;
    private MapFragment mapFragment;
    private FavoritesFragment frag4;
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigation;
    private MaterialSearchView searchView;


    {
        onNavigation = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.routesBnv:
                        transition(routesFragment);

                        return true;
                    case R.id.placesBnv:
                        transition(placesFragment);

                        return true;
                    case R.id.mapBnv:
                        transition(mapFragment);

                        return true;
                    case R.id.favoriteBnv:
                        transition(frag4);

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
        frag4 = new FavoritesFragment();
        bnvWork();
        transition(routesFragment);
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_search, menu);
//        MenuItem item = menu.findItem(R.id.action_search);
//        searchView.setMenuItem(item);
//        return super.onCreateOptionsMenu(menu);
//    }

//    private void addSearch(){
//        PlacesAdapter adapter = PlacesFragment
//
//        searchView = findViewById(R.id.search_view);
//
//        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                ArrayList<Places> result = new ArrayList<>();
//                for(Places place: placesArrayList){
//                    if(place.name.toLowerCase().contains(query.toLowerCase())){
//                        result.add(place);
//                    }
//                }
//                adapter.setList(result);
//                searchView.hideKeyboard(searchView);
//                searchView.clearFocus();
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if(!newText.isEmpty()) {
//                    ArrayList<Places> result = new ArrayList<>();
//                    for (Places place : placesArrayList) {
//                        if (place.name.toLowerCase().contains(newText.toLowerCase())) {
//                            result.add(place);
//                        }
//                        adapter.setList(result);
//                    }
//                }else if(newText.isEmpty()){
//                    adapter.setList(placesArrayList);
//                }
//                return false;
//            }
//        });
//    }
}
