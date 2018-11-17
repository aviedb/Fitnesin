package men.ngopi.aviedb.fitnesin.network.model;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
