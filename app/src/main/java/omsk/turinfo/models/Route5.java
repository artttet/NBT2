package omsk.turinfo.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Route5")
public class Route5 extends Model{

    @Column(name = "place_id")
    public long place_id;
    @Column(name = "Id")
    public long id;

    public Route5() {
        super();
    }
}
