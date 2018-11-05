package men.ngopi.aviedb.fitnesin.network.model.fetchMember;

import com.google.gson.annotations.SerializedName;

import men.ngopi.aviedb.fitnesin.data.Member;

public class FetchMemberResponse {
    @SerializedName("data")
    private Member data;

    public Member getData() {
        return data;
    }

    public void setData(Member data) {
        this.data = data;
    }
}
