package chiglintsev.notboringtrails20.models;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Route2")
public class Route2 extends Model {


    @Column(name = "place_id")
    public long place_id;
    @Column(name = "Id")
    public long id;

    public Route2() {
        super();
    }
}
