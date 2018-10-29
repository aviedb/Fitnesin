package men.ngopi.aviedb.fitnesin.data;

import java.util.Calendar;

public class Member {
    private String name;
    private String phone;
    private Calendar birthdate;
    private double weight;
    private double height;
    private Gender gender;

    public Member(String name, String phone, Calendar birthdate, double weight, double height, Gender gender) {
        this.name = name;
        this.phone = phone;
        this.birthdate = birthdate;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public Calendar getBirthdate() {
        return birthdate;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public Gender getGender() {
        return gender;
    }
}
