
package epod.com.main.datamodel.DetailOrder;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import epod.com.main.application.AppController;

@Table(database = AppController.class)
public class Detailorder extends BaseModel implements Serializable, Parcelable
{
    @Column
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private String id;
    @Column
    @SerializedName("orderno")
    @Expose
    private String orderno;
    @Column
    @SerializedName("shipto")
    @Expose
    private String shipto;
    @Column
    @SerializedName("shipname")
    @Expose
    private String shipname;
    @Column
    @SerializedName("location")
    @Expose
    private String location;
    @Column
    @SerializedName("recieveddate")
    @Expose
    private String recieveddate;
    @Column
    @SerializedName("poddate")
    @Expose
    private String poddate;
    @Column
    @SerializedName("img1")
    @Expose
    private String img1;
    @Column
    @SerializedName("img2")
    @Expose
    private String img2;
    @Column
    @SerializedName("img3")
    @Expose
    private String img3;
    @Column
    @SerializedName("status")
    @Expose
    private String status;
    @Column
    @SerializedName("verification")
    @Expose
    private String verification;


    @Column
    @SerializedName("shipno")
    @Expose
    private String shipno;

    @Column
    @SerializedName("driver")
    @Expose
    private String driver;

    public final static Creator<Detailorder> CREATOR = new Creator<Detailorder>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Detailorder createFromParcel(Parcel in) {
            return new Detailorder(in);
        }

        public Detailorder[] newArray(int size) {
            return (new Detailorder[size]);
        }

    }
    ;
    private final static long serialVersionUID = -2669123340491763936L;

    protected Detailorder(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.orderno = ((String) in.readValue((String.class.getClassLoader())));
        this.shipto = ((String) in.readValue((String.class.getClassLoader())));
        this.shipname = ((String) in.readValue((String.class.getClassLoader())));
        this.location = ((String) in.readValue((String.class.getClassLoader())));
        this.recieveddate = ((String) in.readValue((String.class.getClassLoader())));
        this.poddate = ((String) in.readValue((String.class.getClassLoader())));
        this.img1 = ((String) in.readValue((String.class.getClassLoader())));
        this.img2 = ((String) in.readValue((String.class.getClassLoader())));
        this.img3 = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.verification = ((String) in.readValue((String.class.getClassLoader())));
        this.shipno = ((String) in.readValue((String.class.getClassLoader())));
        this.driver = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Detailorder() {
    }

    /**
     * 
     * @param img2
     * @param id
     * @param img1
     * @param verification
     * @param status
     * @param recieveddate
     * @param location
     * @param shipto
     * @param orderno
     * @param poddate
     * @param shipname
     * @param img3
     */
    public Detailorder(String id, String orderno, String shipto, String shipname, String location, String recieveddate, String poddate, String img1, String img2, String img3, String status, String verification, String shipno, String driver) {
        super();
        this.id = id;
        this.orderno = orderno;
        this.shipto = shipto;
        this.shipname = shipname;
        this.location = location;
        this.recieveddate = recieveddate;
        this.poddate = poddate;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.status = status;
        this.verification = verification;
        this.shipno = shipno;
        this.driver = driver;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Detailorder withId(String id) {
        this.id = id;
        return this;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public Detailorder withOrderno(String orderno) {
        this.orderno = orderno;
        return this;
    }

    public String getShipto() {
        return shipto;
    }

    public void setShipto(String shipto) {
        this.shipto = shipto;
    }

    public Detailorder withShipto(String shipto) {
        this.shipto = shipto;
        return this;
    }

    public String getShipname() {
        return shipname;
    }

    public void setShipname(String shipname) {
        this.shipname = shipname;
    }

    public Detailorder withShipname(String shipname) {
        this.shipname = shipname;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Detailorder withLocation(String location) {
        this.location = location;
        return this;
    }

    public String getRecieveddate() {
        return recieveddate;
    }

    public void setRecieveddate(String recieveddate) {
        this.recieveddate = recieveddate;
    }

    public Detailorder withRecieveddate(String recieveddate) {
        this.recieveddate = recieveddate;
        return this;
    }

    public String getPoddate() {
        return poddate;
    }

    public void setPoddate(String poddate) {
        this.poddate = poddate;
    }

    public Detailorder withPoddate(String poddate) {
        this.poddate = poddate;
        return this;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public Detailorder withImg1(String img1) {
        this.img1 = img1;
        return this;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public Detailorder withImg2(String img2) {
        this.img2 = img2;
        return this;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public Detailorder withImg3(String img3) {
        this.img3 = img3;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Detailorder withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public String getShipno() {
        return shipno;
    }

    public void setShipno(String shipno) {
        this.shipno = shipno;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public Detailorder withVerification(String verification) {
        this.verification = verification;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(orderno);
        dest.writeValue(shipto);
        dest.writeValue(shipname);
        dest.writeValue(location);
        dest.writeValue(recieveddate);
        dest.writeValue(poddate);
        dest.writeValue(img1);
        dest.writeValue(img2);
        dest.writeValue(img3);
        dest.writeValue(status);
        dest.writeValue(verification);
        dest.writeValue(shipno);
        dest.writeValue(driver);
    }

    public int describeContents() {
        return  0;
    }

}
