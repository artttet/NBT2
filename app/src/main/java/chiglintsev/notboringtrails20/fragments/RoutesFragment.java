package chiglintsev.notboringtrails20.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import chiglintsev.notboringtrails20.R;
import chiglintsev.notboringtrails20.adapters.RoutesAdapter;
import chiglintsev.notboringtrails20.models.Routes;

public class RoutesFragment extends Fragment {


    private RoutesAdapter adapter;
    private Animation transition;
    private View globalView;
    private LinearLayoutManager linearLayoutManager;

    private static List<Routes> routesList() {
        ArrayList<Routes> list = new ArrayList<>();
        list.add(new Routes(0, "Вдоль по Любинскому", R.drawable.route1));
        list.add(new Routes(1, "Пешком до Питера", R.drawable.route2));
        list.add(new Routes(2, "По Омску с европейцами", R.drawable.route3));
        list.add(new Routes(3, "\"Вылитый\" омск", R.drawable.route4));
        list.add(new Routes(4, "Две крепости", R.drawable.route5));
        list.add(new Routes(5, "Соборное кольцо", R.drawable.route6));
        list.add(new Routes(6, "Вокруг \"Кадетки\"", R.drawable.route77));
        return list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        animOpen();
        recyclerWork();
    }

    @Override
    public void onResume() {
        super.onResume();
        globalView.startAnimation(transition);
    }

    private void recyclerWork() {
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_routes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void animOpen() {
        transition = AnimationUtils.loadAnimation(getActivity(), R.anim.transition);
        globalView = getView().findViewById(R.id.frag1);
    }

}
