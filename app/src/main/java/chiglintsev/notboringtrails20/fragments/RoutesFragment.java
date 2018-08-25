package chiglintsev.notboringtrails20.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.List;

import chiglintsev.notboringtrails20.MainActivity;
import chiglintsev.notboringtrails20.R;
import chiglintsev.notboringtrails20.adapters.RoutesAdapter;
import chiglintsev.notboringtrails20.models.Routes;

public class RoutesFragment extends Fragment {


    private RoutesAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    //создание объектов для списка маршрутов
    private static List<Routes> routesList() {
        ArrayList<Routes> list = new ArrayList<>();
        list.add(new Routes(0, "Вдоль по Любинскому", R.drawable.route1));
        list.add(new Routes(1, "Пешком до Питера", R.drawable.route2));
        list.add(new Routes(2, "По Омску с европейцами", R.drawable.route3));
        list.add(new Routes(3, "«Вылитый» омск", R.drawable.route4));
        list.add(new Routes(4, "Две крепости", R.drawable.route5));
        list.add(new Routes(5, "Соборное кольцо", R.drawable.route6));
        list.add(new Routes(6, "Вокруг «Кадетки»", R.drawable.route77));
        return list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        adapter = new RoutesAdapter();
        adapter.addAll(routesList());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.routes_fargment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //инициалицазия и заполнение списка
        recyclerWork();

        //инициализация тулбара
        addToolbar(view);

    }

    @Override
    public void onResume() {
        super.onResume();

        //animation for open fragment
        animOpen();

        ((MainActivity)getActivity()).setBnvItem();
        
    }

    private void recyclerWork() {
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_routes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void animOpen() {
        Animation transition = AnimationUtils.loadAnimation(getActivity(), R.anim.transition);
        View view = getView().findViewById(R.id.recycler_routes);
        view.startAnimation(transition);
    }

    private void addToolbar(View view){
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Маршруты");
    }

}
