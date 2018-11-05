package men.ngopi.aviedb.fitnesin.network.model.loginMember;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("authCode")
    private String authCode;


    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}
