package chiglintsev.notboringtrails20.models;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Route1")
public class Route1 extends Model {


    @Column(name = "place_id")
    public long place_id;
    @Column(name = "Id")
    private long id;

    public Route1() {
        super();
    }
}
