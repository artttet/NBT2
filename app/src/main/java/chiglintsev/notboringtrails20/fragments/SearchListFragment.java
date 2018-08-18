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

import com.activeandroid.query.Select;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import chiglintsev.notboringtrails20.MainActivity;
import chiglintsev.notboringtrails20.R;
import chiglintsev.notboringtrails20.adapters.SearchCategoryAdapter;
import chiglintsev.notboringtrails20.adapters.SearchListAdapter;
import chiglintsev.notboringtrails20.models.Places;
import chiglintsev.notboringtrails20.models.SearchCategory;


public class SearchListFragment extends Fragment {
    public boolean clicked;
    MapFragment mapFragment;
    String query;
    private ArrayList<Places> placesArrayList;
    private SearchListAdapter adapter;
    private SearchCategoryAdapter cAdapter;
    private View searchPlace;
    private RecyclerView listView;
    private LinearLayoutManager linearLayoutManager;
    private Animation translateMain, translateLeft, translateRight, translateMainRev, translateLeftRev, translateRightRev;
    private View leftCard, rightCard, mainCard;
    private RecyclerView recyclerView;

    private static List<SearchCategory> categoryList() {
        ArrayList<SearchCategory> list = new ArrayList<>();
        list.add(new SearchCategory(0, "Исторические места", R.drawable.test_monument));
        list.add(new SearchCategory(1, "Памятники, скульптуры", R.drawable.test_sculpture));
        list.add(new SearchCategory(2, "Храмы", R.drawable.test_church));
        list.add(new SearchCategory(3, "Музеи", R.drawable.museum3));
        list.add(new SearchCategory(4, "Театры", R.drawable.test_theater));
        return list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        loadAnims();
        linearLayoutManager = new LinearLayoutManager(getActivity());


        workWithList();

        adapter = new SearchListAdapter();
        cAdapter = new SearchCategoryAdapter();
        mapFragment = ((MainActivity) getActivity()).getFragment();
        adapter.addAll(placesArrayList, mapFragment);
        cAdapter.addAll(categoryList(), mapFragment);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_list_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchPlace = getView().findViewById(R.id.search_list);
        mainCard = getActivity().findViewById(R.id.search_place);
        leftCard = getActivity().findViewById(R.id.title_map_card);
        rightCard = getActivity().findViewById(R.id.title_list_card);

        recyclerView = getView().findViewById(R.id.search_list_view);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                //DividerItemDecoration.VERTICAL));

        recyclerView.setLayoutManager(linearLayoutManager);



    }

    @Override
    public void onResume() {
        super.onResume();

        //animation for open fragment
        animOpen();


    }

    private void workWithList() {
        List<Places> placesList = new Select("Id", "name")
                .from(Places.class)
                .execute();

        placesArrayList = new ArrayList<>();

        for (Places place : placesList) {
            placesArrayList.add(place);
        }

    }

    private void animOpen() {
        Animation shadow = AnimationUtils.loadAnimation(getActivity(), R.anim.shadow);
        searchPlace.startAnimation(shadow);
    }

    private void loadAnims() {
        translateMain = AnimationUtils.loadAnimation(getContext(), R.anim.search_anim);
        translateLeft = AnimationUtils.loadAnimation(getContext(), R.anim.title_map_card);
        translateRight = AnimationUtils.loadAnimation(getContext(), R.anim.title_list_card);
        translateMainRev = AnimationUtils.loadAnimation(getContext(), R.anim.search_anim_rev);
        translateLeftRev = AnimationUtils.loadAnimation(getContext(), R.anim.title_map_card_rev);
        translateRightRev = AnimationUtils.loadAnimation(getContext(), R.anim.title_list_card_rev);
    }

    public void addQuery(String userQuery) {
        query = userQuery;
    }

    public void mapSearch(final MaterialSearchView msv) {

        msv.showSearch();



        msv.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<Places> result = new ArrayList<>();
                for (Places place : placesArrayList) {
                    if (place.name.toLowerCase().contains(query.toLowerCase())) {
                        result.add(place);
                    }
                }
                adapter.setList(result);
                msv.hideKeyboard(msv);
                msv.clearFocus();
                return true;
            }


            @Override
            public boolean onQueryTextChange(String newText) {

                try {
                    if (getActivity().findViewById(R.id.what).getVisibility() == View.VISIBLE) {
                        getActivity().findViewById(R.id.what).setVisibility(View.INVISIBLE);
                    }
                } catch (NullPointerException e) {}

                if (!newText.isEmpty()) {

                    try {
                        ((MainActivity) getActivity()).userQuery = newText;
                    } catch (NullPointerException e) {}

                    recyclerView.setAdapter(adapter);
                    ArrayList<Places> result = new ArrayList<>();
                    for (Places place : placesArrayList) {
                        if (place.name.toLowerCase().contains(newText.toLowerCase())) {
                            result.add(place);
                        }
                    }
                    adapter.setList(result);



                } else if (newText.isEmpty()) {
                    recyclerView.setAdapter(cAdapter);

                    try {
                        getActivity().findViewById(R.id.what).setVisibility(View.VISIBLE);
                    } catch (NullPointerException e) {}
                }


                return false;
            }

        });



        msv.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {

            @Override
            public void onSearchViewShown() {


                if(query != null){
                    recyclerView.setAdapter(adapter);
                }else if(query == null){
                    recyclerView.setAdapter(cAdapter);
                    Log.d("place", "HI");
                }

                if (query != null) {
                    Log.d("place", "query --- " + query);
                    msv.setQuery(query, false);
                    query = null;
                }

                try {getActivity().findViewById(R.id.search_icon).setVisibility(View.GONE);}
                catch (NullPointerException e) {}
                try {getActivity().findViewById(R.id.bnv).setVisibility(View.GONE); }
                catch (NullPointerException e) {}



            }

            @Override
            public void onSearchViewClosed() {
                getActivity().findViewById(R.id.what).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.search_icon).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.bnv).setVisibility(View.VISIBLE);
                ((MainActivity) getActivity()).getMapFragment(null);

            }
        });


    }
}
