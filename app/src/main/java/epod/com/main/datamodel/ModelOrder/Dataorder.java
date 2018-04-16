
package epod.com.main.datamodel.ModelOrder;

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
public class Dataorder extends BaseModel implements Serializable, Parcelable
{
    @Column
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private String id;

    @Column
    @SerializedName("shipment_no")
    @Expose
    private String shipmentNo;

    @Column
    @SerializedName("origin")
    @Expose
    private String origin;

    @Column
    @SerializedName("trans_code")
    @Expose
    private String transCode;

    @Column
    @SerializedName("trans_name")
    @Expose
    private String transName;

    @Column
    @SerializedName("vehicle_no")
    @Expose
    private String vehicleNo;

    @Column
    @SerializedName("driver")
    @Expose
    private String driver;

    @Column
    @SerializedName("ship_to")
    @Expose
    private String shipTo;

    @Column
    @SerializedName("ship_name")
    @Expose
    private String shipName;

    @Column
    @SerializedName("order_no")
    @Expose
    private String orderNo;

    @Column
    @SerializedName("dispatch_dt")
    @Expose
    private String dispatchDt;

    @Column
    @SerializedName("receive_dt")
    @Expose
    private String receiveDt;

    @Column
    @SerializedName("pod_date")
    @Expose
    private String podDate;

    public final static Creator<Dataorder> CREATOR = new Creator<Dataorder>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Dataorder createFromParcel(Parcel in) {
            return new Dataorder(in);
        }

        public Dataorder[] newArray(int size) {
            return (new Dataorder[size]);
        }

    }
    ;
    private final static long serialVersionUID = -5327550136728372752L;

    protected Dataorder(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.shipmentNo = ((String) in.readValue((String.class.getClassLoader())));
        this.origin = ((String) in.readValue((String.class.getClassLoader())));
        this.transCode = ((String) in.readValue((String.class.getClassLoader())));
        this.transName = ((String) in.readValue((String.class.getClassLoader())));
        this.vehicleNo = ((String) in.readValue((String.class.getClassLoader())));
        this.driver = ((String) in.readValue((String.class.getClassLoader())));
        this.shipTo = ((String) in.readValue((String.class.getClassLoader())));
        this.shipName = ((String) in.readValue((String.class.getClassLoader())));
        this.orderNo = ((String) in.readValue((String.class.getClassLoader())));
        this.dispatchDt = ((String) in.readValue((String.class.getClassLoader())));
        this.receiveDt = ((String) in.readValue((String.class.getClassLoader())));
        this.podDate = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Dataorder() {
    }

    /**
     * 
     * @param shipmentNo
     * @param id
     * @param podDate
     * @param orderNo
     * @param receiveDt
     * @param origin
     * @param shipName
     * @param shipTo
     * @param driver
     * @param dispatchDt
     * @param vehicleNo
     * @param transName
     * @param transCode
     */
    public Dataorder(String id, String shipmentNo, String origin, String transCode, String transName, String vehicleNo, String driver, String shipTo, String shipName, String orderNo, String dispatchDt, String receiveDt, String podDate) {
        super();
        this.id = id;
        this.shipmentNo = shipmentNo;
        this.origin = origin;
        this.transCode = transCode;
        this.transName = transName;
        this.vehicleNo = vehicleNo;
        this.driver = driver;
        this.shipTo = shipTo;
        this.shipName = shipName;
        this.orderNo = orderNo;
        this.dispatchDt = dispatchDt;
        this.receiveDt = receiveDt;
        this.podDate = podDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Dataorder withId(String id) {
        this.id = id;
        return this;
    }

    public String getShipmentNo() {
        return shipmentNo;
    }

    public void setShipmentNo(String shipmentNo) {
        this.shipmentNo = shipmentNo;
    }

    public Dataorder withShipmentNo(String shipmentNo) {
        this.shipmentNo = shipmentNo;
        return this;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Dataorder withOrigin(String origin) {
        this.origin = origin;
        return this;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public Dataorder withTransCode(String transCode) {
        this.transCode = transCode;
        return this;
    }

    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

    public Dataorder withTransName(String transName) {
        this.transName = transName;
        return this;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public Dataorder withVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
        return this;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public Dataorder withDriver(String driver) {
        this.driver = driver;
        return this;
    }

    public String getShipTo() {
        return shipTo;
    }

    public void setShipTo(String shipTo) {
        this.shipTo = shipTo;
    }

    public Dataorder withShipTo(String shipTo) {
        this.shipTo = shipTo;
        return this;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public Dataorder withShipName(String shipName) {
        this.shipName = shipName;
        return this;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Dataorder withOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public String getDispatchDt() {
        return dispatchDt;
    }

    public void setDispatchDt(String dispatchDt) {
        this.dispatchDt = dispatchDt;
    }

    public Dataorder withDispatchDt(String dispatchDt) {
        this.dispatchDt = dispatchDt;
        return this;
    }

    public String getReceiveDt() {
        return receiveDt;
    }

    public void setReceiveDt(String receiveDt) {
        this.receiveDt = receiveDt;
    }

    public Dataorder withReceiveDt(String receiveDt) {
        this.receiveDt = receiveDt;
        return this;
    }

    public String getPodDate() {
        return podDate;
    }

    public void setPodDate(String podDate) {
        this.podDate = podDate;
    }

    public Dataorder withPodDate(String podDate) {
        this.podDate = podDate;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(shipmentNo);
        dest.writeValue(origin);
        dest.writeValue(transCode);
        dest.writeValue(transName);
        dest.writeValue(vehicleNo);
        dest.writeValue(driver);
        dest.writeValue(shipTo);
        dest.writeValue(shipName);
        dest.writeValue(orderNo);
        dest.writeValue(dispatchDt);
        dest.writeValue(receiveDt);
        dest.writeValue(podDate);
    }

    public int describeContents() {
        return  0;
    }

}
