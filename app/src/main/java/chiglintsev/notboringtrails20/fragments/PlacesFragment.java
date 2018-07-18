package chiglintsev.notboringtrails20.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    private LinearLayoutManager linearLayoutManager;
    private PlacesAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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



        //создание адаптера с данными из БД
        workWithList();

        //инициалицазия и заполнение списка
        recyclerWork();
    }

    @Override
    public void onResume() {
        super.onResume();

        //animation for open fragment
        animOpen();
    }

    private void animOpen() {
        Animation transition = AnimationUtils.loadAnimation(getActivity(), R.anim.transition);
        View globalView = getView().findViewById(R.id.places);
        globalView.startAnimation(transition);
    }

    private void recyclerWork() {
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_places);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void workWithList() {
        List<Places> placesList = new Select("Id", "name", "image_name").from(Places.class).execute();
        ArrayList<Places> placesArrayList = new ArrayList<>(placesList);
        adapter = new PlacesAdapter();
        adapter.addAll(placesArrayList);
    }
}
