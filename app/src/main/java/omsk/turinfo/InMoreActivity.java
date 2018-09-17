package omsk.turinfo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import omsk.turinfo.adapters.DosugAdapter;
import omsk.turinfo.adapters.StoresAdapter;
import omsk.turinfo.models.Museums;
import omsk.turinfo.models.Stores;


public class InMoreActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int fromMoreFragment = getIntent().getIntExtra("POSITION_FROM_MORE_FRAGMENT", -1);

        switch (fromMoreFragment) {
            case 0: {
                setContentView(R.layout.in_more_activity);
                addAboutOmsk();
            }
            break;
            case 1: {
                setContentView(R.layout.activity_dosug);
                addDosug();
            }
            break;
            case 2: {
                setContentView(R.layout.in_more_activity);
                addNight();
            }
            break;
            case 3: {
                setContentView(R.layout.in_more_activity);
                addStores();
            }
            break;
            case 4: {
                setContentView(R.layout.about_app);
            }
            break;
        }

        addToolbar(getIntent().getStringExtra("TITLE_FROM_MORE_FRAGMENTS"));


    }

    private void addToolbar(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar_in_more);
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    //---------------------------------------------------------------------------------------------------------
    public List<Museums> night(){
        ArrayList<Museums> list = new ArrayList<>();
        String[] name = getResources().getStringArray(R.array.night_name);
        String[] text = getResources().getStringArray(R.array.night_text);
        for(int i = 0; i < name.length; i++){
            list.add(new Museums(name[i],text[i]));
        }
        return list;
    }

    private void addNight(){
        RecyclerView recyclerView = findViewById(R.id.dosug_recycler);
        DosugAdapter adapter = new DosugAdapter(night());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }


    //---------------------------------------------------------------------------------------------------------
    public List<Museums> aboutOmsk(){
        ArrayList<Museums> list = new ArrayList<>();
        String[] name = getResources().getStringArray(R.array.about_omsk_name);
        String[] text = getResources().getStringArray(R.array.about_omsk_text);
        for(int i = 0; i < name.length; i++){
            list.add(new Museums(name[i],text[i]));
        }
        return list;
    }

    private void addAboutOmsk(){
        RecyclerView recyclerView = findViewById(R.id.dosug_recycler);
        DosugAdapter adapter = new DosugAdapter(aboutOmsk());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }


    //_--------------------------------------------------------------------------------------------
    public List<Stores> stores() {
        ArrayList<Stores> list = new ArrayList<>();
        String[] names = getResources().getStringArray(R.array.stores_name);
        String[] texts = getResources().getStringArray(R.array.stores_text);
        String[] texts2 = getResources().getStringArray(R.array.stores_text2);
        for (int i = 0; i < names.length; i++) {
            list.add(new Stores(names[i], texts[i], texts2[i]));
        }
        return list;
    }

    private void addStores() {
        RecyclerView recyclerView = findViewById(R.id.dosug_recycler);
        StoresAdapter storesAdapter = new StoresAdapter(stores());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(storesAdapter);
        recyclerView.setHasFixedSize(true);
    }

    //-----------------------------------------------------------------------------------------------
    private void addDosug() {


        ViewPager mViewPager;

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public List<Museums> museums() {
            ArrayList<Museums> list = new ArrayList<>();
            String[] names = getResources().getStringArray(R.array.museums_name);
            String[] texts = getResources().getStringArray(R.array.museums_text);
            for (int i = 0; i < names.length; i++) {
                list.add(new Museums(names[i], texts[i]));
            }
            return list;
        }

        public List<Museums> theaters() {
            ArrayList<Museums> list = new ArrayList<>();
            String[] names = getResources().getStringArray(R.array.theaters_name);
            String[] texts = getResources().getStringArray(R.array.theaters_text);
            for (int i = 0; i < names.length; i++) {
                list.add(new Museums(names[i], texts[i]));
                Log.d("more", names[i]);
            }
            return list;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main2, container, false);

            RecyclerView recyclerView = rootView.findViewById(R.id.dosug_recycler);
            DosugAdapter museumsAdapter = new DosugAdapter(museums());
            DosugAdapter theatersAdapter = new DosugAdapter(theaters());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            if (getArguments().getInt(ARG_SECTION_NUMBER, -1) == 0) {
                recyclerView.setAdapter(museumsAdapter);
            } else if (getArguments().getInt(ARG_SECTION_NUMBER, -1) == 1) {
                recyclerView.setAdapter(theatersAdapter);

            }
            recyclerView.setHasFixedSize(true);

            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
