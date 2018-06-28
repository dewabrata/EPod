
package epod.com.main.datamodel.NewOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import epod.com.main.application.AppController;

@Table(database = AppController.class)
public class Dataorder extends BaseModel {

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


    @SerializedName("pod_date")
    @Expose
    private String podDate;
    @Column

    @SerializedName("status")
    @Expose
    private String status;
    @Column

    @SerializedName("lat")
    @Expose
    private String lat;
    @Column

    @SerializedName("lon")
    @Expose
    private String lon;

    @Column
    @SerializedName("poddate")
    @Expose
    private String poddate;
    @Column

    @SerializedName("recievedate")
    @Expose
    private String recievedate;
    @Column

    @SerializedName("verification")
    @Expose
    private String verification;


    /**
     * No args constructor for use in serialization
     * 
     */
    public Dataorder() {
    }

    /**
     * 
     * @param lon
     * @param status
     * @param receiveDt
     * @param origin
     * @param shipName
     * @param recievedate
     * @param id
     * @param shipmentNo
     * @param podDate
     * @param orderNo
     * @param verification
     * @param shipTo
     * @param poddate
     * @param driver
     * @param dispatchDt
     * @param lat
     * @param vehicleNo
     * @param transCode
     * @param transName
     */
    public Dataorder(String id, String shipmentNo, String origin, String transCode, String transName, String vehicleNo, String driver, String shipTo, String shipName, String orderNo, String dispatchDt, String receiveDt, String podDate, String status, String lat, String lon, String poddate, String recievedate, String verification) {
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
        this.status = status;
        this.lat = lat;
        this.lon = lon;
        this.poddate = poddate;
        this.recievedate = recievedate;
        this.verification = verification;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShipmentNo() {
        return shipmentNo;
    }

    public void setShipmentNo(String shipmentNo) {
        this.shipmentNo = shipmentNo;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getShipTo() {
        return shipTo;
    }

    public void setShipTo(String shipTo) {
        this.shipTo = shipTo;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getDispatchDt() {
        return dispatchDt;
    }

    public void setDispatchDt(String dispatchDt) {
        this.dispatchDt = dispatchDt;
    }

    public String getReceiveDt() {
        return receiveDt;
    }

    public void setReceiveDt(String receiveDt) {
        this.receiveDt = receiveDt;
    }

    public String getPodDate() {
        return podDate;
    }

    public void setPodDate(String podDate) {
        this.podDate = podDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getPoddate() {
        return poddate;
    }

    public void setPoddate(String poddate) {
        this.poddate = poddate;
    }

    public String getRecievedate() {
        return recievedate;
    }

    public void setRecievedate(String recievedate) {
        this.recievedate = recievedate;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

}
