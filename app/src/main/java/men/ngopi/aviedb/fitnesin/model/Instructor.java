package men.ngopi.aviedb.fitnesin.model;

import android.graphics.drawable.Drawable;

public class Instructor {
    private String name;
    private Gender gender;
    private String city;
    private int photo;

    public Instructor(String name, Gender gender, String city) {
        this.name = name;
        this.gender = gender;
        this.city = city;
    }

    public Instructor(String name, Gender gender, String city, int photo) {
        this.name = name;
        this.gender = gender;
        this.city = city;
        this.photo = photo;
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

    public int getPhoto() {
        return photo;
    }
}
