package chiglintsev.notboringtrails20;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import chiglintsev.notboringtrails20.adapters.DosugAdapter;
import chiglintsev.notboringtrails20.models.Museums;
import chiglintsev.notboringtrails20.models.Routes;


public class InMoreActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int fromMoreFragment = getIntent().getIntExtra("POSITION_FROM_MORE_FRAGMENT", -1);

        switch (fromMoreFragment){
            case 0:{

            }break;
            case 1:{
                setContentView(R.layout.activity_dosug);
                addDosug();
            }break;
            case 2:{

            }break;
            case 3:{

            }break;
            case 4:{

            }break;
        }

        addToolbar(getIntent().getStringExtra("TITLE_FROM_MORE_FRAGMENTS"));


    }

    private void addToolbar(String title){
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



    private void addDosug(){



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

        public List<Museums> museums() {
            ArrayList<Museums> list = new ArrayList<>();
            String[] names = getResources().getStringArray(R.array.museums_name);
            String[] texts = getResources().getStringArray(R.array.museums_text);
            for (int i = 0; i < names.length; i++){
                list.add(new Museums(names[i], texts[i]));
            }
            return list;
        }

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main2, container, false);

            RecyclerView recyclerView = rootView.findViewById(R.id.dosug_recycler);
            DosugAdapter dosugAdapter = new DosugAdapter(museums());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            Log.d("more", String.valueOf(museums()));
            recyclerView.setAdapter(dosugAdapter);
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
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
