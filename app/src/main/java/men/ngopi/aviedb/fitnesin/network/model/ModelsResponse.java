package men.ngopi.aviedb.fitnesin.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelsResponse<T> {
    @SerializedName("data")
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
