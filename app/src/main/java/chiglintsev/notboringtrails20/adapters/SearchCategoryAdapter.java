package chiglintsev.notboringtrails20.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import chiglintsev.notboringtrails20.MainActivity;
import chiglintsev.notboringtrails20.R;
import chiglintsev.notboringtrails20.fragments.MapFragment;
import chiglintsev.notboringtrails20.models.SearchCategory;

public class SearchCategoryAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

    private ArrayList<SearchCategory> sList = new ArrayList<>();
    private FragmentActivity fragment;

    public void addAll(List<SearchCategory> list, MapFragment fragment) {
        int pos = getItemCount();
        this.sList.addAll(list);
        this.fragment = (MainActivity)fragment.getActivity();
        notifyItemRangeInserted(pos, this.sList.size());
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_list_item, viewGroup, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        if(i == sList.size() - 1){
            recyclerViewHolder.rmDivider();
        }
        recyclerViewHolder.bind(sList.get(i), fragment);
    }

    @Override
    public int getItemCount() {
        return sList.size();
    }

    public void setList(ArrayList<SearchCategory> list) {
        this.sList = list;
        notifyDataSetChanged();
    }
}
