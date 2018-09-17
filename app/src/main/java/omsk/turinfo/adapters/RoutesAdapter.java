package omsk.turinfo.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import omsk.turinfo.R;
import omsk.turinfo.models.Routes;

public class RoutesAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private ArrayList<Routes> aList = new ArrayList<>();

    public void addAll(List<Routes> list) {
        this.aList.addAll(list);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_card, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.bind(aList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return aList.size();
    }
}


