package chiglintsev.notboringtrails20.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import com.reactiveandroid.ReActiveConfig;
import com.reactiveandroid.internal.database.DatabaseConfig;
import com.reactiveandroid.query.*;
import com.reactiveandroid.ReActiveAndroid;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import chiglintsev.notboringtrails20.R;
import chiglintsev.notboringtrails20.adapters.PlacesAdapter;
import chiglintsev.notboringtrails20.models.Database2;
import chiglintsev.notboringtrails20.models.Places;
import chiglintsev.notboringtrails20.models.Route1;
import chiglintsev.notboringtrails20.models.Route2;

public class TestFrag2 extends Fragment {

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
        return inflater.inflate(R.layout.test_frag2, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDb();
        workWithList();
        animOpen();
        recyclerWork();
    }

    @Override
    public void onResume() {
        super.onResume();
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

        List<Places> placesList = Select.columns("name", "image_name").from(Places.class).fetch();
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
    private void initDb(){
        DatabaseConfig appDatabaseConfig = new DatabaseConfig.Builder(Database2.class)
                .addModelClasses(Places.class, Route1.class, Route2.class)
                .build();

        ReActiveAndroid.init(new ReActiveConfig.Builder(getActivity())
                .addDatabaseConfigs(appDatabaseConfig)
                .build());
    }
}
