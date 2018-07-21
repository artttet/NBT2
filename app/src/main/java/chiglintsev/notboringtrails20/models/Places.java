package chiglintsev.notboringtrails20.models;


import android.location.Location;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Comparator;

@Table(name = "Places")
public class Places extends Model {

    public double distance;


    @Column(name = "name")
    public String name;
    @Column(name = "image_name")
    public String img_name;
    @Column(name = "text")
    public String text;
    @Column(name = "info")
    public String info;
    @Column(name = "address")
    public String address;
    @Column(name = "lat")
    public double lat;
    @Column(name = "lng")
    public double lng;
    @Column(name = "check_favorite")
    public int check_favorite;
    @Column(name = "Id")
    public long id;

    public Places() {
        super();
    }


}