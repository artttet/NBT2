package chiglintsev.notboringtrails20.models;


import com.reactiveandroid.annotation.Column;
import com.reactiveandroid.Model;
import com.reactiveandroid.annotation.PrimaryKey;
import com.reactiveandroid.annotation.Table;

@Table(name = "Route1", database = Database2.class)
public class Route1 extends Model {

    @PrimaryKey(name = "id")
    private long ID;

    @Column(name = "_id")
    private long id;

    @Column(name = "place_id")
    public long place_id;

    public Route1() {
        super();
    }
}
