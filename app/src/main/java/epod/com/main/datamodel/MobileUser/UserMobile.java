
package epod.com.main.datamodel.MobileUser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserMobile {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("isactive")
    @Expose
    private String isactive;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UserMobile() {
    }

    /**
     * 
     * @param id
     * @param username
     * @param isactive
     * @param password
     */
    public UserMobile(String id, String username, String password, String isactive) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.isactive = isactive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

}
