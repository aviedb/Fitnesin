package men.ngopi.aviedb.fitnesin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class InstructorInfo extends Activity implements View.OnClickListener {
    TextView tv, instructorName, location, phoneNumber;
    CircleImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_info);

        instructorName = findViewById(R.id.instructor_name);
        location = findViewById(R.id.location);
        phoneNumber = findViewById(R.id.phone_number);
        profilePicture = findViewById(R.id.profile_picture);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            instructorName.setText(extras.getString("INSTRUCTOR_NAME"));
            location.setText(extras.getString("CITY"));
            phoneNumber.setText(extras.getString("PHONE_NUMBER"));
            profilePicture.setImageResource(extras.getInt("PROFILE_PICTURE", 0));

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
