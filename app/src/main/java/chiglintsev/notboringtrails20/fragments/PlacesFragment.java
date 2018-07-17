package chiglintsev.notboringtrails20.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import chiglintsev.notboringtrails20.R;
import chiglintsev.notboringtrails20.adapters.PlacesAdapter;
import chiglintsev.notboringtrails20.models.Places;


public class PlacesFragment extends Fragment {

    private Animation transition;
    private View globalView;
    private LinearLayoutManager linearLayoutManager;
    private PlacesAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(getActivity());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.places_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActiveAndroid.initialize(getActivity());
        workWithList();
        animOpen();
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerWork();
        globalView.startAnimation(transition);
    }


    private void animOpen() {
        transition = AnimationUtils.loadAnimation(getActivity(), R.anim.transition);
        globalView = getView().findViewById(R.id.frag2);
    }

    private void recyclerWork() {
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_places);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void workWithList() {
        Log.d("place", "hi artas");
        List<Places> placesList = new Select().from(Places.class).execute();
        Log.d("place", String.valueOf(placesList));
        ArrayList<Places> placesArrayList = new ArrayList<>();
        for (int i = 0; i < placesList.size(); i++) {
            Places place = placesList.get(i);
            Log.d("place", String.valueOf(place.name));
            placesArrayList.add(place);
        }
        adapter = new PlacesAdapter();
        adapter.addAll(placesArrayList);
    }


}
