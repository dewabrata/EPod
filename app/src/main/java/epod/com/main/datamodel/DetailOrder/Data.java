
package epod.com.main.datamodel.DetailOrder;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
{

    @SerializedName("detailorder")
    @Expose
    private List<Detailorder> detailorder = null;
    public final static Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
    ;
    private final static long serialVersionUID = 2834048566978850054L;

    protected Data(Parcel in) {
        in.readList(this.detailorder, (epod.com.main.datamodel.DetailOrder.Detailorder.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param detailorder
     */
    public Data(List<Detailorder> detailorder) {
        super();
        this.detailorder = detailorder;
    }

    public List<Detailorder> getDetailorder() {
        return detailorder;
    }

    public void setDetailorder(List<Detailorder> detailorder) {
        this.detailorder = detailorder;
    }

    public Data withDetailorder(List<Detailorder> detailorder) {
        this.detailorder = detailorder;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(detailorder);
    }

    public int describeContents() {
        return  0;
    }

}
