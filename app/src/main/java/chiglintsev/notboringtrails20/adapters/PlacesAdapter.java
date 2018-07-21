package chiglintsev.notboringtrails20.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import chiglintsev.notboringtrails20.R;
import chiglintsev.notboringtrails20.models.Places;


public class PlacesAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private ArrayList<Places> aList = new ArrayList<>();

    public void addAll(List<Places> list) {
        this.aList.addAll(list);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_card, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.bind(aList.get(position));
    }


    @Override
    public int getItemCount() {
        return aList.size();
    }

    public void setList(ArrayList<Places> list){
        this.aList = list;
        notifyDataSetChanged();
    }
}



