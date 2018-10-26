package men.ngopi.aviedb.fitnesin.data;

import android.graphics.drawable.Drawable;

public class Instructor {
    private String name;
    private Gender gender;
    private String city;
    private String phone;
    private int photo;

    public Instructor(String name, Gender gender, String city) {
        this.name = name;
        this.gender = gender;
        this.city = city;
    }

    public Instructor(String name, Gender gender, String city, String phone, int photo) {
        this.name = name;
        this.gender = gender;
        this.city = city;
        this.photo = photo;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public String getCity() {
        return city;
    }

    public String getPhone() {
        return phone;
    }

    public int getPhoto() {
        return photo;
    }
}
