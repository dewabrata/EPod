
package epod.com.main.datamodel.MobileUser;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("user_mobile")
    @Expose
    private List<UserMobile> userMobile = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param userMobile
     */
    public Data(List<UserMobile> userMobile) {
        super();
        this.userMobile = userMobile;
    }

    public List<UserMobile> getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(List<UserMobile> userMobile) {
        this.userMobile = userMobile;
    }

}
