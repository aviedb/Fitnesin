package men.ngopi.aviedb.fitnesin.network.model.loginMember;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("data")
    private LoginData data;

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }

    public class LoginData {
        private String token;
        private String expiry;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getExpiry() {
            return expiry;
        }

        public void setExpiry(String expiry) {
            this.expiry = expiry;
        }

    }
}
