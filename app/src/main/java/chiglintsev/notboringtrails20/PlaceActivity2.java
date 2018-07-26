package chiglintsev.notboringtrails20;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import chiglintsev.notboringtrails20.models.Places;

public class PlaceActivity2 extends AppCompatActivity implements OnMapReadyCallback {

    private final static String KEY_FROM_PLACES = "id_key";
    private final static String KEY_FOR_PHOTO_RES = "resID_key";
    private final static String KEY_FOR_PHOTO_TITLE = "title_key";
    private TextView place_titleV, place_main_textV, place_infoV, place_addressV, place_info_titleV;
    private ImageView place_img_view_nameV;
    private ImageButton favorite;
    private long id;
    private double place_Lat, place_Lng;
    private int resID;
    private int check_favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place2);

        initialization();
        fillFromBD(id);

    }


    private void fillFromBD(long id) {


        Places place = new Select().from(Places.class).where("Id = ?", id).executeSingle();

        resID = this.getResources().getIdentifier(place.img_name, "drawable", this.getPackageName());

        place_titleV.setText(place.name);
        place_titleV.setTypeface(SingletonFonts.getInstance(this).getFont3());

        place_img_view_nameV.setImageResource(resID);

        place_main_textV.setText(place.text);
        place_main_textV.setTypeface(SingletonFonts.getInstance(this).getFont3());


        place_infoV.setText(place.info);
        place_addressV.setText(place.address);
        place_Lat = place.lat;
        place_Lng = place.lng;
        check_favorite = place.check_favorite;

        if (check_favorite == 0) {
            favorite.setImageResource(R.drawable.ic_favorite_border_white_24dp);
        } else if (check_favorite == 1) {
            favorite.setImageResource(R.drawable.ic_favorite_white_24dp);
        }

        if (place.info == null) place_info_titleV.setVisibility(View.GONE);
    }


    private void initialization() {
        id = getIntent().getLongExtra(KEY_FROM_PLACES, -1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.image_on_map);
        mapFragment.getMapAsync(this);

        place_titleV = findViewById(R.id.title_of_place);
        place_img_view_nameV = findViewById(R.id.place_view);
        place_main_textV = findViewById(R.id.main_text);
        place_infoV = findViewById(R.id.info);
        place_addressV = findViewById(R.id.address);
        place_info_titleV = findViewById(R.id.info_title);
        favorite = findViewById(R.id.favorite);

    }

    public void back(View view) {
        super.onBackPressed();
    }

    public void like(View view) {

        if (check_favorite == 1) {
            favorite.setImageResource(R.drawable.ic_favorite_border_white_24dp);
            check_favorite = 0;
            new Update(Places.class).set("check_favorite = 0").where("Id = ?", id).execute();


        } else if (check_favorite == 0) {
            favorite.setImageResource(R.drawable.ic_favorite_white_24dp);
            check_favorite = 1;
            new Update(Places.class).set("check_favorite = 1").where("Id = ?", id).execute();

        }
    }

    public void openPhoto(View view) {
        Intent intent = new Intent(PlaceActivity2.this, PhotoActivity.class);
        intent.putExtra(KEY_FOR_PHOTO_TITLE, place_titleV.getText());
        intent.putExtra(KEY_FOR_PHOTO_RES, resID);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng position = new LatLng(place_Lat, place_Lng);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.addMarker(new MarkerOptions().position(position));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }
}
