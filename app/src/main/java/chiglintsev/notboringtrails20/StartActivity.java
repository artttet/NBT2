package chiglintsev.notboringtrails20;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import chiglintsev.notboringtrails20.models.Places;


public class StartActivity extends AppCompatActivity {
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        radioButton = findViewById(R.id.radio0);
        radioButton.setChecked(true);

        ViewPager viewPager = findViewById(R.id.start_pager);

        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(customPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                switch (i){
                    case 0: {
                        radioButton = findViewById(R.id.radio0);
                        radioButton.setChecked(true);
                        radioButton = findViewById(R.id.radio1);
                        radioButton.setChecked(false);
                        radioButton = findViewById(R.id.radio2);
                        radioButton.setChecked(false);
                        radioButton = findViewById(R.id.radio3);
                        radioButton.setChecked(false);
                    }break;
                    case 1: {
                        radioButton = findViewById(R.id.radio0);
                        radioButton.setChecked(false);
                        radioButton = findViewById(R.id.radio1);
                        radioButton.setChecked(true);
                        radioButton = findViewById(R.id.radio2);
                        radioButton.setChecked(false);
                        radioButton = findViewById(R.id.radio3);
                        radioButton.setChecked(false);
                    }break;
                    case 2: {
                        radioButton = findViewById(R.id.radio0);
                        radioButton.setChecked(false);
                        radioButton = findViewById(R.id.radio1);
                        radioButton.setChecked(false);
                        radioButton = findViewById(R.id.radio2);
                        radioButton.setChecked(true);
                        radioButton = findViewById(R.id.radio3);
                        radioButton.setChecked(false);
                    }break;
                    case 3: {
                        radioButton = findViewById(R.id.radio0);
                        radioButton.setChecked(false);
                        radioButton = findViewById(R.id.radio1);
                        radioButton.setChecked(false);
                        radioButton = findViewById(R.id.radio2);
                        radioButton.setChecked(false);
                        radioButton = findViewById(R.id.radio3);
                        radioButton.setChecked(true);
                    }break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.start_fragment, container, false);
            TextView startText = view.findViewById(R.id.start_text);
            ImageView startImage = view.findViewById(R.id.start_image);

            switch (getArguments().getInt(ARG_SECTION_NUMBER)){
                case 0: {
                    startText.setText(R.string.omsk);
                    startImage.setImageResource(R.drawable.omsk);
                }break;
                case 1: {
                    startText.setText(R.string.history_center_1);
                    startImage.setImageResource(R.drawable.history_omsk_1);
                }break;
                case 2: {
                    startText.setText(R.string.history_center_2);
                    startImage.setImageResource(R.drawable.history_omsk_2);
                }break;
                case 3: {
                    startText.setText(R.string.modern_omsk);
                    startImage.setImageResource(R.drawable.modern_omsk);
                }break;
            }

            return view;
        }
    }
    public class CustomPagerAdapter extends FragmentPagerAdapter {

        public CustomPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
