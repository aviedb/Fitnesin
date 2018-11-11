package men.ngopi.aviedb.fitnesin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import men.ngopi.aviedb.fitnesin.data.Gender;
import men.ngopi.aviedb.fitnesin.data.Instructor;
import men.ngopi.aviedb.fitnesin.data.source.InstructorsDataSource;
import men.ngopi.aviedb.fitnesin.data.source.remote.InstructorsRemoteDataSource;

public class InstructorMainActivity extends Activity implements View.OnClickListener {
    private static int APP_EDIT_INSTRUCTOR = 300;

    private TextView mName;
    private TextView mPhone;
    private TextView mCity;
    private TextView mGender;
    private MaterialButton editInfoBtn;
    private MaterialButton logoutBtn;

    private SharedPreferences sharedPreferences;
    private InstructorsDataSource instructorsDataSource;
    private Instructor instructor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_main);

        mName = findViewById(R.id.tv_instructor_name);
        mPhone = findViewById(R.id.tv_instructor_phone);
        mCity = findViewById(R.id.tv_instructor_city);
        mGender = findViewById(R.id.tv_instructor_gender);

        editInfoBtn = findViewById(R.id.edit_info);
        editInfoBtn.setOnClickListener(this);

        logoutBtn = findViewById(R.id.logout);
        logoutBtn.setOnClickListener(this);

        sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFERENCE, MODE_PRIVATE);
        String token = sharedPreferences.getString(MainActivity.PREF_TOKEN_KEY, null);
        if (token == null) {
            logout();
            return;
        }
        instructorsDataSource = InstructorsRemoteDataSource.getInstance(token);
        getData();

        // TODO: add delete instructor button
        // TODO: implement MVP pattern
    }

    @Override
    public void onClick(View v) {
        if (v.equals(editInfoBtn)) {
            Intent i = new Intent(this, InstructorEditInfo.class);
            if (instructor != null) {
                i.putExtra("name", instructor.getName());
                i.putExtra("phone", instructor.getPhone());
                i.putExtra("gender", instructor.getGender().toString());
                i.putExtra("city", instructor.getCity());
            }
            startActivityForResult(i, APP_EDIT_INSTRUCTOR);
        } else if (v.equals(logoutBtn)) {
            logout();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == APP_EDIT_INSTRUCTOR && resultCode == Activity.RESULT_OK) {
            String name = data.getStringExtra("name");
            String city = data.getStringExtra("city");
            String gender = data.getStringExtra("gender");
            if (name != null)
                instructor.setName(name);
            if (city != null)
                instructor.setCity(city);

            if (gender != null)
                instructor.setGender(Gender.valueOf(gender.toUpperCase()));

            final Context ctx = this;

            // TODO: add loading indicator (1)
            instructorsDataSource.saveMe(instructor, new InstructorsDataSource.GetInstructorCallback() {
                @Override
                public void onInstructorLoaded(Instructor instructor) {
                    Toast.makeText(ctx, "Data successfully saved", Toast.LENGTH_LONG).show();
                    getData();
                }

                @Override
                public void onDataNotAvailable() {
                    Toast.makeText(ctx, "Unable to save data", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void getData() {
        // TODO : add loading indicator (2)
        final Context ctx = this;
        instructorsDataSource.getMe(new InstructorsDataSource.GetInstructorCallback() {
            @Override
            public void onInstructorLoaded(Instructor instructor) {
                showInstructor(instructor);
            }

            @Override
            public void onDataNotAvailable() {
                Toast.makeText(ctx, "Unable to fetch data, please try again", Toast.LENGTH_LONG).show();
                Log.d(InstructorMainActivity.class.getSimpleName(), "failed to fetch data");
            }
        });
    }

    private void showInstructor(Instructor instructor) {
        this.instructor = instructor;

        mName.setText(instructor.getName());
        mPhone.setText(instructor.getPhone());
        mCity.setText(instructor.getCity());
        String gender = instructor.getGender() == Gender.MALE ? "Male" : "Female";
        mGender.setText(gender);
    }

    private void logout() {
        // Clear credentials
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(MainActivity.PREF_TOKEN_KEY);
        editor.remove(MainActivity.PREF_TOKEN_EXPIRY_KEY);
        editor.remove(MainActivity.PREF_USERTOKEN_KEY);
        editor.apply();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }
}
