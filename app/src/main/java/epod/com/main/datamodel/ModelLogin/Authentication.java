package epod.com.main.datamodel.ModelLogin;

/**
 * Created by user on 1/10/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Authentication {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private AuthenticationData data;
    @SerializedName("token")
    @Expose
    private String token;

    /**
     * No args constructor for use in serialization
     *
     */
    public Authentication() {
    }

    /**
     *
     * @param message
     * @param token
     * @param status
     * @param data
     */
    public Authentication(Boolean status, String message, AuthenticationData data, String token) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
        this.token = token;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Authentication withStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Authentication withMessage(String message) {
        this.message = message;
        return this;
    }

    public AuthenticationData getData() {
        return data;
    }

    public void setData(AuthenticationData data) {
        this.data = data;
    }

    public Authentication withData(AuthenticationData data) {
        this.data = data;
        return this;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Authentication withToken(String token) {
        this.token = token;
        return this;
    }

}
