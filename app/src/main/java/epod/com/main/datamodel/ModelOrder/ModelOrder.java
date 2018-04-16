
package epod.com.main.datamodel.ModelOrder;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelOrder implements Serializable, Parcelable
{

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("total")
    @Expose
    private int total;
    public final static Creator<ModelOrder> CREATOR = new Creator<ModelOrder>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelOrder createFromParcel(Parcel in) {
            return new ModelOrder(in);
        }

        public ModelOrder[] newArray(int size) {
            return (new ModelOrder[size]);
        }

    }
    ;
    private final static long serialVersionUID = -3057936808870566574L;

    protected ModelOrder(Parcel in) {
        this.status = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
        this.total = ((int) in.readValue((int.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public ModelOrder() {
    }

    /**
     * 
     * @param total
     * @param message
     * @param status
     * @param data
     */
    public ModelOrder(boolean status, String message, Data data, int total) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
        this.total = total;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ModelOrder withStatus(boolean status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ModelOrder withMessage(String message) {
        this.message = message;
        return this;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public ModelOrder withData(Data data) {
        this.data = data;
        return this;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ModelOrder withTotal(int total) {
        this.total = total;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(message);
        dest.writeValue(data);
        dest.writeValue(total);
    }

    public int describeContents() {
        return  0;
    }

}
