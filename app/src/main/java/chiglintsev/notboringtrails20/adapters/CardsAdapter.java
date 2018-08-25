package chiglintsev.notboringtrails20.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import chiglintsev.notboringtrails20.R;
import chiglintsev.notboringtrails20.RouteActivity;
import chiglintsev.notboringtrails20.SingletonFonts;
import chiglintsev.notboringtrails20.models.Places;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {

    private List<Places> data;

    public CardsAdapter(List<Places> data) {
        this.data = data;
    }

    public interface OnCardClickListener {
        void onCardClick(View view, Places place);
    }

    private OnCardClickListener listener;

    public void setOnCardClickListener(OnCardClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.in_route_card, viewGroup, false);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.addPlace(data.get(i));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, text, warningName, warningText;
        private ImageView image;
        public Places place;

        public Places getPlace(){
            return place;
        }

        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.in_route_card_img);
            name = itemView.findViewById(R.id.in_route_card_name);
            text = itemView.findViewById(R.id.in_route_card_text);
            warningName = itemView.findViewById(R.id.warning_title);
            warningText = itemView.findViewById(R.id.warning_text);
        }

        void addPlace(final Places place) {
            this.place = place;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onCardClick(view, place);
                }
            });

            if (place.category == 5) {
                image.setVisibility(View.INVISIBLE);
                name.setVisibility(View.INVISIBLE);
                text.setVisibility(View.INVISIBLE);

                warningName.setVisibility(View.VISIBLE);
                warningText.setVisibility(View.VISIBLE);
                warningName.setText(place.name);
                warningName.setTypeface(SingletonFonts.getInstance(itemView.getContext()).getFont3());

                warningText.setText(place.text);
                warningText.setTypeface(SingletonFonts.getInstance(itemView.getContext()).getMainFont());
            } else {
                warningText.setVisibility(View.INVISIBLE);
                warningName.setVisibility(View.INVISIBLE);
                name.setVisibility(View.VISIBLE);
                text.setVisibility(View.VISIBLE);
                image.setVisibility(View.VISIBLE);

                name.setText(place.name);
                name.setTypeface(SingletonFonts.getInstance(itemView.getContext()).getFont3());

                text.setText(place.text);
                text.setTypeface(SingletonFonts.getInstance(itemView.getContext()).getMainFont());
                Picasso.get()
                        .load(
                                itemView.getResources().getIdentifier(place.img_name, "drawable", itemView.getContext().getPackageName())
                        )
                        .fit()
                        .centerCrop()
                        .into(image);
            }
        }
    }
}
