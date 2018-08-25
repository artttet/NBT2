package chiglintsev.notboringtrails20.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import chiglintsev.notboringtrails20.MainActivity;
import chiglintsev.notboringtrails20.PlaceActivity2;
import chiglintsev.notboringtrails20.R;
import chiglintsev.notboringtrails20.RouteActivity;
import chiglintsev.notboringtrails20.SingletonFonts;
import chiglintsev.notboringtrails20.fragments.MapFragment;
import chiglintsev.notboringtrails20.models.Places;
import chiglintsev.notboringtrails20.models.Routes;
import chiglintsev.notboringtrails20.models.SearchCategory;


class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private final static String KEY_FOR_PLACE_id = "id_key";
    private int remove;
    private TextView routeName, placeName, placeDistance, searchListText;
    private ImageView routeImg, placeImg, searchListIcon;
    private View divider;
    private Context context;
    private MapFragment mapFragment;
    private MainActivity mainActivity;

    RecyclerViewHolder(View itemView) {
        super(itemView);
         mapFragment = new MapFragment();

        //FOR ROUTES
        routeName = itemView.findViewById(R.id.name_route_card);
        routeImg = itemView.findViewById(R.id.img_route_card);
        //FOR PLACES
        placeName = itemView.findViewById(R.id.name_place_card);
        placeImg = itemView.findViewById(R.id.img_place_card);
        placeDistance = itemView.findViewById(R.id.distance);
        //FOR SEARCHLIST
        searchListText = itemView.findViewById(R.id.search_list_text);
        searchListIcon = itemView.findViewById(R.id.search_list_icon);

        divider = itemView.findViewById(R.id.divider);

        context = itemView.getContext();
    }

    //BIND FOR ROUTES
    public void bind(final Routes routes, int id) {
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

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long id = routes.id;
                Intent intent = new Intent(context, RouteActivity.class);
                intent.putExtra("id", id);
                Log.d("route", "long id --- " + id);
                context.startActivity(intent);
            }
        });
    }

    //BIND FOR PLACES
    public void bind(final Places place) {
        placeName.setText(place.name);
        placeName.setTypeface(SingletonFonts.getInstance(context).getFont1());
        int test = (int) place.distance;
        double test2 = (double) test / 1000;
        String distance = String.format("%.2f", test2);
        placeDistance.setText(distance + "km");
        placeDistance.setTypeface(SingletonFonts.getInstance(context).getFont1());

        Picasso.get()
                .load(
                        itemView.getResources().getIdentifier(place.img_name, "drawable", context.getPackageName())
                )
                .fit()
                .centerCrop()
                .into(placeImg);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long id = place.id;
                Intent intent = new Intent(context, PlaceActivity2.class);
                intent.putExtra(KEY_FOR_PLACE_id, id);
                context.startActivity(intent);
            }
        });
    }

    public void bind(final Places place, final FragmentActivity fragmentActivity){
        if(remove == 1){
            divider.setVisibility(View.INVISIBLE);
            remove = 0;
        }
        searchListText.setText(place.name);
        searchListText.setTypeface(SingletonFonts.getInstance(context).getFont3());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) fragmentActivity).getMarker(place);
                ((MainActivity) fragmentActivity).getMapFragment(-2);
                ((MainActivity) fragmentActivity).msvClose(place.name, "place");

            }
        });
    }

    public void bind(final SearchCategory searchCategory, final FragmentActivity fragment){
        if(remove == 1){
            divider.setVisibility(View.INVISIBLE);
            remove = 0;
        }
        searchListText.setText(searchCategory.title);
        searchListText.setTypeface(SingletonFonts.getInstance(context).getFont3());
        searchListIcon.setImageResource(searchCategory.img);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) fragment).getMapFragment(searchCategory.getId());
                ((MainActivity) fragment).msvClose(searchCategory.title, "category");
            }
        });
    }

    public void rmDivider(){
        remove = 1;
    }
}
