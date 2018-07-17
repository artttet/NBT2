package chiglintsev.notboringtrails20;


import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import chiglintsev.notboringtrails20.fragments.PlacesFragment;
import chiglintsev.notboringtrails20.fragments.RoutesFragment;
import chiglintsev.notboringtrails20.fragments.TestFrag3;
import chiglintsev.notboringtrails20.fragments.TestFrag4;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    public FragmentTransaction trans;
    private RoutesFragment frag1;
    private PlacesFragment frag2;
    private TestFrag3 frag3;
    private TestFrag4 frag4;
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigation;


    {
        onNavigation = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.routesBnv:
                        transition(frag1);

                        return true;
                    case R.id.placesBnv:
                        transition(frag2);

                        return true;
                    case R.id.mapBnv:
                        transition(frag3);

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


        frag1 = new RoutesFragment();
        frag2 = new PlacesFragment();
        frag3 = new TestFrag3();
        frag4 = new TestFrag4();
        bnvWork();
        transition(frag1);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void bnvWork() {
        BottomNavigationView bottomNavigation = findViewById(R.id.bnv);
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigation);
    }

    private void transition(Fragment frag) {
        trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.for_fragment, frag);
        trans.commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
