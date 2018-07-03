package chiglintsev.notboringtrails20;


import android.os.Bundle;

import android.view.MenuItem;



import com.google.android.material.bottomnavigation.BottomNavigationView;


import androidx.appcompat.app.AppCompatActivity;


public class TestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        BottomNavigationView bottomNavigation = findViewById(R.id.bnv);
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigation);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigation;

    {
        onNavigation = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.routesBnv:
                        return true;
                    case R.id.placesBnv:
                        return true;
                    case R.id.mapBnv:
                        return true;
                    case R.id.favoriteBnv:


                        return true;
                }
                return false;
            }
        };
    }

}
