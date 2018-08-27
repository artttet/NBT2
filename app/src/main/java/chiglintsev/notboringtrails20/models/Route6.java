package chiglintsev.notboringtrails20.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Route6")
public class Route6 extends Model{

    @Column(name = "place_id")
    public long place_id;
    @Column(name = "Id")
    public long id;

    public Route6() {
        super();
    }
}
