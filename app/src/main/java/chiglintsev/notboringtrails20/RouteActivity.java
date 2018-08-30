package chiglintsev.notboringtrails20;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.ui.IconGenerator;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;

import chiglintsev.notboringtrails20.adapters.CardsAdapter;
import chiglintsev.notboringtrails20.models.Places;
import chiglintsev.notboringtrails20.models.Route1;
import chiglintsev.notboringtrails20.models.Route2;
import chiglintsev.notboringtrails20.models.Route3;
import chiglintsev.notboringtrails20.models.Route4;
import chiglintsev.notboringtrails20.models.Route5;
import chiglintsev.notboringtrails20.models.Route6;
import chiglintsev.notboringtrails20.models.Route7;

public class RouteActivity extends FragmentActivity implements OnMapReadyCallback, CardsAdapter.OnCardClickListener {

    private static final int PERMISSION_CODE = 14;
    private final static String KEY_FOR_PLACE_id = "id_key";
    private static final LatLng ROUTE1_CENTR = new LatLng(54.985563, 73.374540);
    private static final LatLng ROUTE2_CENTR = new LatLng(54.982010, 73.376941);
    private static final LatLng ROUTE3_CENTR = new LatLng(54.982122, 73.374403);
    private static final LatLng ROUTE4_CENTR = new LatLng(54.982010, 73.376941);
    private static final LatLng ROUTE5_CENTR = new LatLng(54.984789, 73.370660);
    private static final LatLng ROUTE6_CENTR = new LatLng(54.990185, 73.367569);
    private static final LatLng ROUTE7_CENTR = new LatLng(54.977542, 73.377802);
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };
    View bigCard, bigCardBackground;
    private LatLng centr_route;
    private boolean checkBigCard = false;
    private boolean checkLastItem = false;
    private DiscreteScrollView discreteScrollView;
    private GoogleMap myMap;
    private long routeId;
    private ArrayList<Places> mainList;
    private LocationManager locationManager;
    private Places currentPlace;
    private int lastItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        ActiveAndroid.initialize(this);


        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mainList = new ArrayList<>();
        routeId = getIntent().getLongExtra("id", -1);

        loadPlaces(routeId);

        discreteScrollView = findViewById(R.id.cards);
        final CardsAdapter cardsAdapter = new CardsAdapter(mainList);

        cardsAdapter.setOnCardClickListener(this);

        discreteScrollView.setAdapter(cardsAdapter);

        discreteScrollView.setItemTransformer(new ScaleTransformer.Builder()
                .setMaxScale(1.05f)
                .setMinScale(0.8f)
                .setPivotX(Pivot.X.CENTER)
                .setPivotY(Pivot.Y.BOTTOM)
                .build());

        discreteScrollView.setHasFixedSize(true);

        discreteScrollView.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            @Override
            public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int i) {
                currentPlace = mainList.get(viewHolder.getAdapterPosition());

                if (viewHolder.getAdapterPosition() != lastItem) {
                    animateToMarker(currentPlace);
                    checkLastItem = false;
                } else {
                    checkLastItem = true;
                }
            }
        });


        bigCardBackground = findViewById(R.id.big_card_background);
        bigCardBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBigCard) {
                    rmBigCard();
                }
            }
        });
    }

    private void addPolyline(long routeId) {
        switch ((int) routeId) {
            case 0: {
                PolylineOptions polyline = new PolylineOptions().add(new LatLng(54.988473, 73.371037))
                        .add(new LatLng(54.988559, 73.371350))
                        .add(new LatLng(54.988454, 73.371535))
                        .add(new LatLng(54.987906, 73.372178))
                        .add(new LatLng(54.986525, 73.373557))
                        .add(new LatLng(54.986077, 73.373929))
                        .add(new LatLng(54.985356, 73.374386))
                        .add(new LatLng(54.984985, 73.374670))
                        .add(new LatLng(54.984276, 73.375167))
                        .add(new LatLng(54.983572, 73.375637))
                        .add(new LatLng(54.983571, 73.376065))
                        .add(new LatLng(54.985837, 73.374556))
                        .add(new LatLng(54.986422, 73.374189))
                        .add(new LatLng(54.986489, 73.374600))
                        .add(new LatLng(54.986760, 73.374246))
                        .add(new LatLng(54.9869346513248, 73.37403237819673))
                        .add(new LatLng(54.987665, 73.373892))
                        .add(new LatLng(54.987734, 73.373398))
                        .add(new LatLng(54.987735, 73.372981))
                        .add(new LatLng(54.988723, 73.371952))
                        .add(new LatLng(54.988812, 73.372013))
                        .add(new LatLng(54.989299, 73.373398))
                        .color(getResources().getColor(R.color.colorAccent)).width(7);
                myMap.addPolyline(polyline);
            }break;
            case 1: {
                PolylineOptions polyline = new PolylineOptions().add(new LatLng(54.977669, 73.380269))
                        .add(new LatLng(54.977764, 73.380174))
                        .add(new LatLng(54.977776, 73.379943))
                        .add(new LatLng(54.978111, 73.379876))
                        .add(new LatLng(54.978261, 73.379786))
                        .add(new LatLng(54.977789, 73.379070))
                        .add(new LatLng(54.977648, 73.379028))
                        .add(new LatLng(54.977513, 73.378103))
                        .add(new LatLng(54.978236, 73.377958))
                        .add(new LatLng(54.978650, 73.377875))
                        .add(new LatLng(54.979202, 73.377783))
                        .add(new LatLng(54.979767, 73.377636))
                        .add(new LatLng(54.980402, 73.377466))
                        .add(new LatLng(54.980373, 73.377194))
                        .add(new LatLng(54.980638, 73.377035))
                        .add(new LatLng(54.981261, 73.376948))
                        .add(new LatLng(54.981674, 73.376865))
                        .add(new LatLng(54.982031, 73.376707))
                        .add(new LatLng(54.982962, 73.376074))
                        .add(new LatLng(54.983096, 73.375899))
                        .add(new LatLng(54.983057, 73.375506))
                        .add(new LatLng(54.983015, 73.374623))
                        .add(new LatLng(54.982994, 73.374194))
                        .add(new LatLng(54.983178, 73.374123))
                        .add(new LatLng(54.983456, 73.373838))
                        .add(new LatLng(54.983569, 73.373815))
                        .add(new LatLng(54.983745, 73.372991))
                        .add(new LatLng(54.983869, 73.373076))
                        .add(new LatLng(54.984131, 73.372566))
                        .add(new LatLng(54.984602, 73.373171))
                        .add(new LatLng(54.985202, 73.374113))
                        .color(getResources().getColor(R.color.colorAccent)).width(7);
                myMap.addPolyline(polyline);
            }break;
            case 2: {
                PolylineOptions polyline = new PolylineOptions()
                        .add(new LatLng(54.985429, 73.367818))
                        .add(new LatLng(54.985075, 73.367750))
                        .add(new LatLng(54.985087, 73.366638))
                        .add(new LatLng(54.985058, 73.366409))
                        .add(new LatLng(54.984934, 73.366399))
                        .add(new LatLng(54.984582, 73.366377))
                        .add(new LatLng(54.984523, 73.366524))
                        .add(new LatLng(54.983921, 73.366454))
                        .add(new LatLng(54.984417, 73.367745))
                        .add(new LatLng(54.984461, 73.367872))
                        .add(new LatLng(54.984464, 73.368168))
                        .add(new LatLng(54.984186, 73.368237))
                        .add(new LatLng(54.983867, 73.368331))
                        .add(new LatLng(54.983863, 73.368760))
                        .add(new LatLng(54.983874, 73.368949))
                        .add(new LatLng(54.984473, 73.368975))
                        .add(new LatLng(54.984711, 73.369476))
                        .add(new LatLng(54.984842, 73.369508))
                        .add(new LatLng(54.984761, 73.370203))
                        .add(new LatLng(54.983980, 73.372124))
                        .add(new LatLng(54.983718, 73.372808))
                        .add(new LatLng(54.983557, 73.373893))
                        .add(new LatLng(54.983176, 73.375604))
                        .add(new LatLng(54.982946, 73.376061))
                        .add(new LatLng(54.982160, 73.376598))
                        .add(new LatLng(54.981697, 73.376832))
                        .add(new LatLng(54.981488, 73.377450))
                        .add(new LatLng(54.981398, 73.377301))
                        .add(new LatLng(54.981048, 73.377401))
                        .add(new LatLng(54.981149, 73.377961))
                        .add(new LatLng(54.980986, 73.378399))
                        .add(new LatLng(54.980624, 73.378445))
                        .add(new LatLng(54.980487, 73.377594))
                        .color(getResources().getColor(R.color.colorAccent)).width(7);
                myMap.addPolyline(polyline);
            }break;
            case 3: {
                PolylineOptions polyline = new PolylineOptions()
                        .add(new LatLng(54.978974, 73.380570))
                        .add(new LatLng(54.979093, 73.380354))
                        .add(new LatLng(54.978706, 73.380412))
                        .add(new LatLng(54.978266, 73.379842))
                        .add(new LatLng(54.977502, 73.379997))
                        .add(new LatLng(54.977060, 73.380085))
                        .add(new LatLng(54.977017, 73.380085))
                        .add(new LatLng(54.977337, 73.379202))
                        .add(new LatLng(54.977338, 73.378312))
                        .add(new LatLng(54.977382, 73.378091))
                        .add(new LatLng(54.978451, 73.377908))
                        .add(new LatLng(54.980491, 73.377478))
                        .add(new LatLng(54.980472, 73.377182))
                        .add(new LatLng(54.980344, 73.375057))
                        .add(new LatLng(54.980347, 73.373615))
                        .add(new LatLng(54.980344, 73.372965))
                        .add(new LatLng(54.980525, 73.372917))
                        .add(new LatLng(54.980631, 73.372556))
                        .add(new LatLng(54.980803, 73.372806))
                        .add(new LatLng(54.980941, 73.372659))
                        .add(new LatLng(54.981075, 73.372788))
                        .add(new LatLng(54.981478, 73.373112))
                        .add(new LatLng(54.981913, 73.376407))
                        .add(new LatLng(54.981682, 73.376826))
                        .add(new LatLng(54.981469, 73.377537))
                        .add(new LatLng(54.981384, 73.377267))
                        .add(new LatLng(54.981127, 73.377439))
                        .add(new LatLng(54.981384, 73.377267))
                        .add(new LatLng(54.981469, 73.377537))
                        .add(new LatLng(54.981682, 73.376826))
                        .add(new LatLng(54.982192, 73.376570))
                        .add(new LatLng(54.983127, 73.375900))
                        .add(new LatLng(54.983405, 73.375686))
                        .add(new LatLng(54.983594, 73.375599))
                        .add(new LatLng(54.985354, 73.374430))
                        .add(new LatLng(54.986078, 73.373930))
                        .add(new LatLng(54.986553, 73.373540))
                        .add(new LatLng(54.987493, 73.372580))
                        .add(new LatLng(54.988344, 73.371753))
                        .add(new LatLng(54.988352, 73.371621))
                        .add(new LatLng(54.988475, 73.371505))
                        .add(new LatLng(54.988569, 73.371294))
                        .add(new LatLng(54.988471, 73.371049))
                        .add(new LatLng(54.988569, 73.371294))
                        .add(new LatLng(54.988667, 73.371558))
                        .add(new LatLng(54.988770, 73.371911))
                        .add(new LatLng(54.987862, 73.372821))
                        .add(new LatLng(54.987727, 73.373423))
                        .add(new LatLng(54.987665, 73.373892))
                        .add(new LatLng(54.986910, 73.374002))
                        .add(new LatLng(54.986421, 73.374226))
                        .add(new LatLng(54.986461, 73.374554))
                        .add(new LatLng(54.986421, 73.374226))
                        .add(new LatLng(54.986308, 73.374226))
                        .add(new LatLng(54.985958, 73.374461))
                        .add(new LatLng(54.985398, 73.374796))
                        .add(new LatLng(54.983462, 73.376090))
                        .color(getResources().getColor(R.color.colorAccent)).width(7);
                myMap.addPolyline(polyline);
            }break;
            case 4: {
                PolylineOptions polyline = new PolylineOptions()
                        .add(new LatLng(54.981214, 73.370348))
                        .add(new LatLng(54.981323, 73.372043))
                        .add(new LatLng(54.981034, 73.372222))
                        .add(new LatLng(54.980881, 73.372495))
                        .add(new LatLng(54.980729, 73.372462))
                        .add(new LatLng(54.980632, 73.372552))
                        .add(new LatLng(54.980947, 73.373368))
                        .add(new LatLng(54.981201, 73.372893))
                        .add(new LatLng(54.981370, 73.372772))
                        .add(new LatLng(54.981480, 73.373149))
                        .add(new LatLng(54.981954, 73.376634))
                        .add(new LatLng(54.982200, 73.376577))
                        .add(new LatLng(54.983029, 73.375952))
                        .add(new LatLng(54.983149, 73.375757))
                        .add(new LatLng(54.983564, 73.373845))
                        .add(new LatLng(54.983739, 73.372899))
                        .add(new LatLng(54.984020, 73.372017))
                        .add(new LatLng(54.984518, 73.370714))
                        .add(new LatLng(54.984765, 73.370033))
                        .add(new LatLng(54.984841, 73.369502))
                        .add(new LatLng(54.984722, 73.369426))
                        .add(new LatLng(54.984486, 73.369062))
                        .add(new LatLng(54.983914, 73.368930))
                        .add(new LatLng(54.983775, 73.368358))
                        .add(new LatLng(54.983918, 73.368311))
                        .add(new LatLng(54.984459, 73.368155))
                        .add(new LatLng(54.984459, 73.367856))
                        .add(new LatLng(54.984136, 73.367036))
                        .add(new LatLng(54.982910, 73.366775))
                        .add(new LatLng(54.984136, 73.367036))
                        .add(new LatLng(54.983936, 73.366429))
                        .add(new LatLng(54.984530, 73.366515))
                        .add(new LatLng(54.984689, 73.366346))
                        .add(new LatLng(54.984963, 73.366376))
                        .add(new LatLng(54.985039, 73.363509))
                        .add(new LatLng(54.985259, 73.362636))
                        .add(new LatLng(54.985504, 73.362805))
                        .add(new LatLng(54.985344, 73.363268))
                        .add(new LatLng(54.985163, 73.363460))
                        .add(new LatLng(54.985184, 73.363960))
                        .add(new LatLng(54.985077, 73.365256))
                        .add(new LatLng(54.985079, 73.366453))
                        .add(new LatLng(54.985104, 73.366672))
                        .add(new LatLng(54.985087, 73.367791))
                        .add(new LatLng(54.987523, 73.367981))
                        .add(new LatLng(54.987792, 73.368095))
                        .add(new LatLng(54.988983, 73.368136))
                        .color(getResources().getColor(R.color.colorAccent)).width(7);
                myMap.addPolyline(polyline);
            }break;
            case 5: {
                PolylineOptions polyline = new PolylineOptions()
                        .add(new LatLng(54.990937, 73.368289))
                        .add(new LatLng(54.989622, 73.368233))
                        .add(new LatLng(54.989345, 73.368003))
                        .add(new LatLng(54.989085, 73.367919))
                        .add(new LatLng(54.989166, 73.365918))
                        .add(new LatLng(54.989288, 73.365729))
                        .add(new LatLng(54.989332, 73.364919))
                        .add(new LatLng(54.989505, 73.364495))
                        .add(new LatLng(54.989763, 73.364390))
                        .add(new LatLng(54.990166, 73.364626))
                        .add(new LatLng(54.990315, 73.364498))
                        .add(new LatLng(54.990797, 73.363909))
                        .add(new LatLng(54.990911, 73.365013))
                        .add(new LatLng(54.990772, 73.365947))
                        .add(new LatLng(54.990655, 73.366064))
                        .add(new LatLng(54.990236, 73.366171))
                        .add(new LatLng(54.990625, 73.367142))
                        .add(new LatLng(54.990980, 73.368136))
                        .add(new LatLng(54.991076, 73.368402))
                        .add(new LatLng(54.991320, 73.368484))
                        .add(new LatLng(54.991263, 73.370984))
                        .color(getResources().getColor(R.color.colorAccent)).width(7);
                myMap.addPolyline(polyline);
            }break;
            case 6: {
                PolylineOptions polyline = new PolylineOptions()
                        .add(new LatLng(54.978430, 73.377632))
                        .add(new LatLng(54.976625, 73.377940))
                        .add(new LatLng(54.976620, 73.378236))
                        .add(new LatLng(54.976750, 73.378371))
                        .add(new LatLng(54.977341, 73.378347))
                        .add(new LatLng(54.977711, 73.379125))
                        .add(new LatLng(54.977775, 73.379951))
                        .add(new LatLng(54.976988, 73.380095))
                        .add(new LatLng(54.976723, 73.380993))
                        .add(new LatLng(54.975799, 73.381350))
                        .add(new LatLng(54.974784, 73.381577))
                        .add(new LatLng(54.974589, 73.379486))
                        .add(new LatLng(54.974550, 73.378629))
                        .add(new LatLng(54.974506, 73.377382))
                        .add(new LatLng(54.974345, 73.375828))
                        .add(new LatLng(54.974302, 73.375194))
                        .add(new LatLng(54.975913, 73.374613))
                        .add(new LatLng(54.976268, 73.374778))
                        .add(new LatLng(54.976393, 73.376565))
                        .add(new LatLng(54.976470, 73.376853))
                        .add(new LatLng(54.976393, 73.376565))
                        .add(new LatLng(54.976268, 73.374778))
                        .add(new LatLng(54.977378, 73.374485))
                        .add(new LatLng(54.978203, 73.374012))
                        .add(new LatLng(54.978407, 73.373620))
                        .add(new LatLng(54.979316, 73.373112))
                        .add(new LatLng(54.979395, 73.374524))
                        .color(getResources().getColor(R.color.colorAccent)).width(7);
                myMap.addPolyline(polyline);
            }break;
        }


    }

    private void loadPlaces(long routeId) {
        List<Long> placesIdList = new ArrayList<>();

        switch ((int) routeId) {

            case 0: {
                centr_route = ROUTE1_CENTR;
                List<Route1> idList = new Select()
                        .from(Route1.class)
                        .execute();
                for (Route1 route1 : idList) {
                    placesIdList.add(route1.place_id);
                    Log.d("test2", String.valueOf(route1.place_id));
                }
            }
            break;

            case 1: {
                centr_route = ROUTE2_CENTR;
                List<Route2> idList = new Select()
                        .from(Route2.class)
                        .execute();
                for (Route2 route2 : idList) {
                    placesIdList.add(route2.place_id);
                }
            }
            break;

            case 2: {
                centr_route = ROUTE3_CENTR;
                List<Route3> idList = new Select()
                        .from(Route3.class)
                        .execute();
                for (Route3 route3 : idList) {
                    placesIdList.add(route3.place_id);
                }
            }
            break;

            case 3: {
                centr_route = ROUTE4_CENTR;
                List<Route4> idList = new Select()
                        .from(Route4.class)
                        .execute();
                for (Route4 route4 : idList) {
                    placesIdList.add(route4.place_id);
                }
            }
            break;

            case 4: {
                centr_route = ROUTE5_CENTR;
                List<Route5> idList = new Select()
                        .from(Route5.class)
                        .execute();
                for (Route5 route5 : idList) {
                    placesIdList.add(route5.place_id);
                }
            }
            break;

            case 5: {
                centr_route = ROUTE6_CENTR;
                List<Route6> idList = new Select()
                        .from(Route6.class)
                        .execute();
                for (Route6 route6 : idList) {
                    placesIdList.add(route6.place_id);
                }
            }
            break;

            case 6: {
                centr_route = ROUTE7_CENTR;
                List<Route7> idList = new Select()
                        .from(Route7.class)
                        .execute();
                for (Route7 route7 : idList) {
                    placesIdList.add(route7.place_id);
                }
            }
            break;
        }

        for (int i = 0; i < placesIdList.size(); i++) {
            Places place = new Select("Id", "name", "image_name", "text", "lat", "lng", "category")
                    .from(Places.class)
                    .where("Id = ?", placesIdList.get(i))
                    .executeSingle();
            mainList.add(place);
        }

        lastItem = mainList.size() - 1;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE);
        } else {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, locationListener);
            myMap.setMyLocationEnabled(true);
        }


        myMap.getUiSettings().setMyLocationButtonEnabled(true);
        myMap.getUiSettings().setMapToolbarEnabled(false);

        myMap.setMapType(2);

        addMarkers();
        addPolyline(routeId);

    }

    private void addMarkers() {
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centr_route, 15.5f));
        IconGenerator icon = new IconGenerator(this);
        icon.setStyle(IconGenerator.STYLE_BLUE);
        int count = 1;

        for (int i = 0; i < mainList.size(); i++) {
            if (i != mainList.size() - 1) {
                Bitmap iconBitmap;
                if (mainList.get(i).category != 5) {
                    iconBitmap = icon.makeIcon(String.valueOf(count));
                    count++;
                } else {
                    iconBitmap = icon.makeIcon("!!!");
                }

                myMap.addMarker(new MarkerOptions()
                        .position(new LatLng(mainList.get(i).lat, mainList.get(i).lng))
                        .icon(BitmapDescriptorFactory.fromBitmap(iconBitmap))
                );
            }
        }

        myMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                discreteScrollView.scrollToPosition(Integer.parseInt(marker.getId().substring(1)));
                return false;
            }
        });

    }

    public void back(View view) {
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (locationManager != null) locationManager.removeUpdates(locationListener);
    }

    public Double getMapCameraLat() {
        return myMap.getCameraPosition().target.latitude;
    }

    public void animateToMarker(Places place) {
        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(place.lat, place.lng), 17));

    }

    @Override
    public void onBackPressed() {
        if (checkBigCard) {
            rmBigCard();
            checkBigCard = false;
        } else {
            super.onBackPressed();
        }
    }

    private void scrollTo(Places place) {


        switch ((int) routeId) {
            case 0: {
                Route1 route1 = new Select("Id")
                        .from(Route1.class)
                        .where("place_id = ?", place.id)
                        .executeSingle();

                discreteScrollView.scrollToPosition((int) route1.id);
            }
            break;
            case 1: {
                Route2 route2 = new Select("Id")
                        .from(Route2.class)
                        .where("place_id = ?", place.id)
                        .executeSingle();

                discreteScrollView.scrollToPosition((int) route2.id);
            }
            break;
            case 2: {
                Route3 route3 = new Select("Id")
                        .from(Route3.class)
                        .where("place_id = ?", place.id)
                        .executeSingle();

                discreteScrollView.scrollToPosition((int) route3.id);
            }
            break;
            case 3: {
                Route4 route4 = new Select("Id")
                        .from(Route4.class)
                        .where("place_id = ?", place.id)
                        .executeSingle();

                discreteScrollView.scrollToPosition((int) route4.id);
            }
            break;
            case 4: {
                Route5 route5 = new Select("Id")
                        .from(Route5.class)
                        .where("place_id = ?", place.id)
                        .executeSingle();

                discreteScrollView.scrollToPosition((int) route5.id);
            }
            break;
            case 5: {
                Route6 route6 = new Select("Id")
                        .from(Route6.class)
                        .where("place_id = ?", place.id)
                        .executeSingle();

                discreteScrollView.scrollToPosition((int) route6.id);
            }
            break;
            case 6: {
                Route7 route7 = new Select("Id")
                        .from(Route7.class)
                        .where("place_id = ?", place.id)
                        .executeSingle();

                discreteScrollView.scrollToPosition((int) route7.id);
            }
            break;
        }

        currentPlace = place;
        if (currentPlace.id != mainList.get(lastItem).id) {
            animateToMarker(currentPlace);
            checkLastItem = false;
        } else {
            checkLastItem = true;
        }
    }

    @Override
    public void onCardClick(View view, Places place) {

        if (currentPlace.id != place.id) {
            scrollTo(place);
        } else if (currentPlace.id == place.id && checkLastItem) {
            addBigCard(place);
        } else {
            if (!getMapCameraLat().toString().substring(0, 7).equals(String.valueOf(place.lat).substring(0, 7))) {
                animateToMarker(place);
            } else {
                addBigCard(place);
            }
        }
    }

    private void addBigCard(Places place) {
        checkBigCard = true;
        TextView bigCardName = findViewById(R.id.in_route_big_card_name);
        TextView bigCardText = findViewById(R.id.in_route_big_card_text);
        bigCardName.setText(place.name);
        bigCardName.setTypeface(SingletonFonts.getInstance(this).getFont3());
        bigCardText.setText(place.text);
        bigCardText.setTypeface(SingletonFonts.getInstance(this).getFont3());

        bigCard = findViewById(R.id.in_route_big_card);
        Animation addBigCard = AnimationUtils.loadAnimation(this, R.anim.transition);


        Animation addBigCardBackground = AnimationUtils.loadAnimation(this, R.anim.shadow);
        bigCardBackground.setVisibility(View.VISIBLE);
        bigCardBackground.startAnimation(addBigCardBackground);

        bigCard.setVisibility(View.VISIBLE);
        bigCard.startAnimation(addBigCard);

        bigCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    private void rmBigCard() {
        bigCardBackground.setVisibility(View.GONE);

        Animation rmBigCard = AnimationUtils.loadAnimation(this, R.anim.transition_rev);
        bigCard.startAnimation(rmBigCard);
        bigCard.setVisibility(View.GONE);

        checkBigCard = false;
    }
}
