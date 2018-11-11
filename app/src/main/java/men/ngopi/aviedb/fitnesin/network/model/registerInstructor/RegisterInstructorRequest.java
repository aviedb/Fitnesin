package men.ngopi.aviedb.fitnesin.network.model.registerInstructor;

import com.google.gson.annotations.SerializedName;

public class RegisterInstructorRequest {
    @SerializedName("authCode")
    private String authCode;
    private String name;
    private String city;
    private String gender;
    private String birthdate;

    public RegisterInstructorRequest(String authCode, String name, String city, String gender, String birthdate) {
        this.authCode = authCode;
        this.name = name;
        this.city = city;
        this.gender = gender;
        this.birthdate = birthdate;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
}
