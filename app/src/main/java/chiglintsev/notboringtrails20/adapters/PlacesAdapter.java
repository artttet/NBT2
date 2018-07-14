package chiglintsev.notboringtrails20.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import chiglintsev.notboringtrails20.R;
import chiglintsev.notboringtrails20.SingletonFonts;
import chiglintsev.notboringtrails20.models.Places;
import chiglintsev.notboringtrails20.models.Routes;

public class PlacesAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private ArrayList<Places> aList = new ArrayList<>();

    public void addAll(List<Places> list) {
        this.aList.addAll(list);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.places_card, parent, false);
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
}



