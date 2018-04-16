
package epod.com.main.datamodel.ModelOrder;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
{

    @SerializedName("dataorder")
    @Expose
    private List<Dataorder> dataorder = null;
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
    private final static long serialVersionUID = -6155919881368981873L;

    protected Data(Parcel in) {
        in.readList(this.dataorder, (epod.com.main.datamodel.ModelOrder.Dataorder.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param dataorder
     */
    public Data(List<Dataorder> dataorder) {
        super();
        this.dataorder = dataorder;
    }

    public List<Dataorder> getDataorder() {
        return dataorder;
    }

    public void setDataorder(List<Dataorder> dataorder) {
        this.dataorder = dataorder;
    }

    public Data withDataorder(List<Dataorder> dataorder) {
        this.dataorder = dataorder;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(dataorder);
    }

    public int describeContents() {
        return  0;
    }

}
