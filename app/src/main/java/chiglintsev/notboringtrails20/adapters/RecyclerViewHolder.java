package chiglintsev.notboringtrails20.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import chiglintsev.notboringtrails20.PlaceActivity2;
import chiglintsev.notboringtrails20.R;
import chiglintsev.notboringtrails20.SingletonFonts;
import chiglintsev.notboringtrails20.models.Places;
import chiglintsev.notboringtrails20.models.Routes;


class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private final static String KEY_FOR_PLACE_id = "id_key";
    private TextView routeName, placeName, placeDistance;
    private ImageView routeImg, placeImg;
    private Context context;

    RecyclerViewHolder(View itemView) {
        super(itemView);
        //FOR ROUTES
        routeName = itemView.findViewById(R.id.name_route_card);
        routeImg = itemView.findViewById(R.id.img_route_card);
        //FOR PLACES
        placeName = itemView.findViewById(R.id.name_place_card);
        placeImg = itemView.findViewById(R.id.img_place_card);
        placeDistance = itemView.findViewById(R.id.distance);

        context = itemView.getContext();
    }

    //BIND FOR ROUTES
    public void bind(Routes routes, int id) {
        routeName.setText(routes.title);
        routeName.setTypeface(SingletonFonts.getInstance(context).getFont1());

        //В 5 маршруте убирает текст в левый верхний угол
        if (id == 4) {
            CardView.LayoutParams params = new CardView.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.gravity = Gravity.TOP | Gravity.START;
            routeName.setLayoutParams(params);
        }

        Picasso.get().load(routes.img).fit().centerCrop(1).into(routeImg);
    }

    //BIND FOR PLACES
    public void bind(final Places places) {
        placeName.setText(places.name);
        placeName.setTypeface(SingletonFonts.getInstance(context).getFont1());
        int test = (int) places.distance;
        double test2 = (double) test / 1000;
        String distance = String.format("%.2f", test2);
        placeDistance.setText(distance + "km");
        placeDistance.setTypeface(SingletonFonts.getInstance(context).getFont1());

        Picasso.get()
                .load(
                        itemView.getResources().getIdentifier(places.img_name, "drawable", context.getPackageName())
                )
                .fit()
                .centerCrop()
                .into(placeImg);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long id = places.id;
                Intent intent = new Intent(context, PlaceActivity2.class);
                intent.putExtra(KEY_FOR_PLACE_id, id);
                context.startActivity(intent);
            }
        });
    }
}
