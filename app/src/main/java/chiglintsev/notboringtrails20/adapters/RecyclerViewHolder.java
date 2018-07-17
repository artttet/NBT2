package chiglintsev.notboringtrails20.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import chiglintsev.notboringtrails20.R;
import chiglintsev.notboringtrails20.SingletonFonts;
import chiglintsev.notboringtrails20.models.Places;
import chiglintsev.notboringtrails20.models.Routes;

class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView routeName, placeName;
    private ImageView routeImg, placeImg;
    private Context context;

    RecyclerViewHolder(View itemView) {
        super(itemView);
        routeName = itemView.findViewById(R.id.title_route_card);
        routeImg = itemView.findViewById(R.id.img_route_card);
        placeName = itemView.findViewById(R.id.title_place_card);
        placeImg = itemView.findViewById(R.id.img_place_card);
        context = itemView.getContext();
    }

    public void bind(Routes routes, int id) {
        routeName.setText(routes.title);
        routeName.setTypeface(SingletonFonts.getInstance(context).getFont1());
        if (id == 4) {
            CardView.LayoutParams params = new CardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.TOP | Gravity.START;
            routeName.setLayoutParams(params);
        }
        Picasso.get().load(routes.img).fit().into(routeImg);
    }

    public void bind(Places places) {
        Log.d("place", String.valueOf(itemView.getResources().getIdentifier(places.img_name, "drawable", context.getPackageName())));
        placeName.setText(places.name);
        placeName.setTypeface(SingletonFonts.getInstance(context).getFont1());
        Picasso.get()
                .load(
                        itemView.getResources().getIdentifier(places.img_name, "drawable", context.getPackageName())
                )
                .fit()
                .centerCrop()
                .into(placeImg);
    }


}
