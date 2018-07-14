package chiglintsev.notboringtrails20;


import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;



import com.reactiveandroid.query.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.reactiveandroid.ReActiveAndroid;
import com.reactiveandroid.ReActiveConfig;
import com.reactiveandroid.internal.database.DatabaseConfig;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import chiglintsev.notboringtrails20.fragments.RoutesFragment;
import chiglintsev.notboringtrails20.fragments.TestFrag2;
import chiglintsev.notboringtrails20.fragments.TestFrag3;
import chiglintsev.notboringtrails20.fragments.TestFrag4;
import chiglintsev.notboringtrails20.models.Database2;
import chiglintsev.notboringtrails20.models.Places;
import chiglintsev.notboringtrails20.models.Route1;
import chiglintsev.notboringtrails20.models.Route2;


public class MainActivity extends AppCompatActivity {

    public androidx.fragment.app.FragmentTransaction trans;
    private RoutesFragment frag1;
    private TestFrag2 frag2;
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
        initDb();
        List<Places> placesList = Select.columns("name").from(Places.class).fetch();
        Log.d("place", String.valueOf(placesList));
        frag1 = new RoutesFragment();
        frag2 = new TestFrag2();
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

    private void initDb(){
        DatabaseConfig appDatabaseConfig = new DatabaseConfig.Builder(Database2.class)
                .addModelClasses(Places.class, Route1.class, Route2.class)
                .build();

        ReActiveAndroid.init(new ReActiveConfig.Builder(this)
                .addDatabaseConfigs(appDatabaseConfig)
                .build());
    }
}
