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
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import chiglintsev.notboringtrails20.R;
import chiglintsev.notboringtrails20.adapters.PlacesAdapter;
import chiglintsev.notboringtrails20.models.Places;

public class FavoritesFragment extends Fragment {

    private LinearLayoutManager linearLayoutManager;
    private PlacesAdapter adapter;
    private Animation transition;
    private View globalView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favorites_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView voidFavorite = getView().findViewById(R.id.void_favorite);

        workWithList();
        recyclerWork();

        if(adapter.getItemCount() == 0){
            voidFavorite.setVisibility(View.VISIBLE);
        }else voidFavorite.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();

        //animation for open fragment
        animOpen();
    }

    private void recyclerWork() {
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_favorites);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void workWithList() {

        List<Places> placesList = new Select("Id", "name", "image_name").
                from(Places.class)
                .where("check_favorite = ?", 1)
                .execute();

        ArrayList<Places> placesArrayList = new ArrayList<>();
        for (int i = 0; i < placesList.size(); i++) {
            Places place = placesList.get(i);
            placesArrayList.add(place);
        }
        adapter = new PlacesAdapter();
        adapter.addAll(placesArrayList);
    }

    private void animOpen() {
        transition = AnimationUtils.loadAnimation(getActivity(), R.anim.transition);
        globalView = getView().findViewById(R.id.favorites);
        globalView.startAnimation(transition);
    }
}
