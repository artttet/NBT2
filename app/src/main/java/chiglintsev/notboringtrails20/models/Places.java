package chiglintsev.notboringtrails20.models;


import com.reactiveandroid.Model;
import com.reactiveandroid.annotation.Column;
import com.reactiveandroid.annotation.PrimaryKey;
import com.reactiveandroid.annotation.Table;

@Table(name = "Places", database = Database2.class)
public class Places extends Model{

    @PrimaryKey(name = "id")
    private long ID;

    @Column(name = "_id")
    private long id;

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

    public Places() {
        super();
    }


}
