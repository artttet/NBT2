package chiglintsev.notboringtrails20.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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

    private PlacesAdapter adapter;
    private RecyclerView recyclerView;
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favorites_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //иницализация recycler и подключение layoutmanager
        recyclerWork();

        addToolbar(view);
    }


    @Override
    public void onResume() {
        super.onResume();

        //запрос в БД и заполение recycler
        workWithList();

        //сообщает о том, что пользователь ничего не добавлял, если он ничего добавлял
        checkVoid();

        //animation for open fragment
        animOpen();
    }

    private void recyclerWork() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = getView().findViewById(R.id.recycler_favorites);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void workWithList() {
        List<Places> placesList = new Select("Id", "name", "image_name").
                from(Places.class)
                .where("check_favorite = ?", 1)
                .execute();

        ArrayList<Places> placesArrayList = new ArrayList<>(placesList);
        adapter = new PlacesAdapter();
        adapter.addAll(placesArrayList);
        recyclerView.setAdapter(adapter);
    }

    private void animOpen() {
        Animation transition = AnimationUtils.loadAnimation(getActivity(), R.anim.transition);
        View globalView = getView().findViewById(R.id.favorite_frame);
        globalView.startAnimation(transition);
    }

    private void checkVoid() {
        TextView voidFavorite = getView().findViewById(R.id.void_favorite);
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_favorites);
        if (adapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            voidFavorite.setVisibility(View.VISIBLE);
        } else {
            voidFavorite.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void addToolbar(View view){
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Маршруты");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add, menu);
    }
}
