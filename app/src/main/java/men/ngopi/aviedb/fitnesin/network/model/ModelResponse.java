package men.ngopi.aviedb.fitnesin.network.model;

import com.google.gson.annotations.SerializedName;

public class ModelResponse<T> {
    @SerializedName("data")
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
