package men.ngopi.aviedb.fitnesin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import men.ngopi.aviedb.fitnesin.data.Gender;
import men.ngopi.aviedb.fitnesin.data.Instructor;
import men.ngopi.aviedb.fitnesin.data.source.InstructorsDataSource;
import men.ngopi.aviedb.fitnesin.data.source.remote.InstructorsRemoteDataSource;

public class InstructorMainActivity extends Activity implements View.OnClickListener {
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
        instructorsDataSource.getMe(new InstructorsDataSource.GetInstructorCallback() {
            @Override
            public void onInstructorLoaded(Instructor instructor) {
                showInstructor(instructor);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(InstructorMainActivity.class.getSimpleName(), "failed to fetch data");
            }
        });
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
            startActivity(i);
        } else if (v.equals(logoutBtn)) {
            logout();
        }
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
