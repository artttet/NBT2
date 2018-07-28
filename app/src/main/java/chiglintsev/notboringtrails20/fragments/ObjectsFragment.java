package chiglintsev.notboringtrails20.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TableLayout;
import android.widget.Toast;

import chiglintsev.notboringtrails20.R;

public class ObjectsFragment extends Fragment {
    public FragmentTransaction trans;
    private PlacesFragment placesFragment;
    private MapFragment mapFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        placesFragment = new PlacesFragment();
        mapFragment = new MapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.objects_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tabs = view.findViewById(R.id.tablayout);
        tabs.addTab(tabs.newTab().setText(R.string.map).setIcon(R.drawable.ic_map_black_24dp));
        tabs.addTab(tabs.newTab().setText("Список").setIcon(R.drawable.ic_subject_black_24dp));

//        if(tabs.getSelectedTabPosition() == 0){
//            transition(mapFragment);
//        }else if (tabs.getSelectedTabPosition() == 1){
//            transition(placesFragment);
//        }

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
//                    transition(mapFragment);
                } else if (tab.getPosition() == 1) {
                    //transition(placesFragment);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void transition(Fragment fragment) {
        trans = getActivity().getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.for_fragment_objects, fragment);
        trans.commit();
    }

    @Override
    public void onResume() {
        super.onResume();

        //animation for open fragment
        animOpen();
    }

    private void animOpen() {
        Animation transition = AnimationUtils.loadAnimation(getActivity(), R.anim.transition);
        View view = getView().findViewById(R.id.objects);
        view.startAnimation(transition);
    }

}
