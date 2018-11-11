package men.ngopi.aviedb.fitnesin.data;

import android.graphics.drawable.Drawable;

import java.util.Calendar;

public class Instructor {
    private String name;
    private Gender gender;
    private String city;
    private String phone;
    private Calendar birthdate;
    private int photo;

    public Instructor(String name, Gender gender, String city) {
        this.name = name;
        this.gender = gender;
        this.city = city;
    }

    public Instructor(String name, Gender gender, String city, String phone, int photo, int birthYear, int birthMonth, int birthDay) {
        this.name = name;
        this.gender = gender;
        this.city = city;
        this.photo = photo;
        this.phone = phone;

        Calendar birthdate = Calendar.getInstance();
        birthdate.set(birthYear, birthMonth, birthDay);
        this.birthdate = birthdate;
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

    public Calendar getBirthdate() {
        return birthdate;
    }

    public int getPhoto() {
        return photo;
    }
}
