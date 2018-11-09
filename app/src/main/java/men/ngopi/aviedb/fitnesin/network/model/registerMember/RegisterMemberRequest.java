package men.ngopi.aviedb.fitnesin.network.model.registerMember;

import com.google.gson.annotations.SerializedName;

public class RegisterMemberRequest {
    @SerializedName("authCode")
    private String authCode;
    private String name;
    private String birthdate;
    private double weight;
    private double height;
    private String gender;

    public RegisterMemberRequest(String authCode, String name, String birthdate, double weight, double height, String gender) {
        this.authCode = authCode;
        this.name = name;
        this.birthdate = birthdate;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
