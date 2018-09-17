package omsk.turinfo.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import omsk.turinfo.MainActivity;
import omsk.turinfo.R;
import omsk.turinfo.fragments.MapFragment;
import omsk.turinfo.models.Places;
import omsk.turinfo.models.SearchCategory;

public class SearchListAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private ArrayList<Places> aList = new ArrayList<>();
    private ArrayList<SearchCategory> sList = new ArrayList<>();
    private FragmentActivity fragmentActivity;

    public void addAll(List<Places> list, MapFragment mapFragment) {
        int pos = getItemCount();
        this.aList.addAll(list);
        this.fragmentActivity = (MainActivity)mapFragment.getActivity();
        notifyItemRangeInserted(pos, this.aList.size());
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_list_item, viewGroup, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        if(i == aList.size() - 1){
            recyclerViewHolder.rmDivider();
        }
        recyclerViewHolder.bind(aList.get(i), fragmentActivity);
    }

    @Override
    public int getItemCount() {
        return aList.size();
    }

    public void setList(ArrayList<Places> list) {
        this.aList = list;

        notifyDataSetChanged();
    }



}
