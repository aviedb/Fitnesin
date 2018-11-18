package men.ngopi.aviedb.fitnesin.member.listInstructors;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import men.ngopi.aviedb.fitnesin.R;

public class InstructorInfoActivity extends Activity implements View.OnClickListener {
    TextView tv, instructorName, location, phoneNumber, gender, birthdate;
    SimpleDateFormat formatter;
    CircleImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_info);

        instructorName = findViewById(R.id.instructor_name);
        location = findViewById(R.id.location);
        phoneNumber = findViewById(R.id.phone_number);
        gender = findViewById(R.id.ti_gender);
        birthdate = findViewById(R.id.ti_birthdate);
        profilePicture = findViewById(R.id.profile_picture);

        formatter = new SimpleDateFormat("MMM dd, yyyy");

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            instructorName.setText(extras.getString("INSTRUCTOR_NAME"));
            location.setText(extras.getString("CITY"));
            phoneNumber.setText(extras.getString("PHONE_NUMBER"));
            gender.setText(extras.getString("GENDER"));
            profilePicture.setImageResource(extras.getInt("PROFILE_PICTURE", 0));

            Calendar calBirthdate = (Calendar) extras.get("BIRTHDATE");
            assert calBirthdate != null;
            birthdate.setText(formatter.format(calBirthdate.getTime()));

            phoneNumber.setOnClickListener(this);
        }

        tv = findViewById(R.id.back);
        tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == tv) {
            finish();
        }

        if (v == phoneNumber) {
            Intent telp = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber.getText()));
            startActivity(telp);
        }
    }
}
