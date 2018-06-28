package epod.com.main.datamodel.DetailOrder;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

import epod.com.main.application.AppController;

@Table(database = AppController.class)
public class ImageSend  extends BaseModel implements Serializable {

    @Column
    @PrimaryKey
    public String id;
    @Column
    public String orderno;
    @Column
    public String shipno;
    @Column
    public String username;

    @Column
    public String path;
    @Column
    public String name;
    @Column
    public long time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getShipno() {
        return shipno;
    }

    public void setShipno(String shipno) {
        this.shipno = shipno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public ImageSend(){

    }

    public ImageSend(String id, String orderno, String shipno, String username, String path, String name, long time) {
        this.id = id;
        this.orderno = orderno;
        this.shipno = shipno;
        this.username = username;
        this.path = path;
        this.name = name;
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        try {
           ImageSend other = (ImageSend) o;
            return this.path.equalsIgnoreCase(other.path) && this.time == other.time;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return super.equals(o);
    }

}