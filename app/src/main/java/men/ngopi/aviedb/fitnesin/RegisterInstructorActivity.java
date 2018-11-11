package men.ngopi.aviedb.fitnesin;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import men.ngopi.aviedb.fitnesin.data.Gender;

public class RegisterInstructorActivity extends Activity {

    private Spinner mGenderSpinner;
    private MaterialButton mSubmitRegisterButton;
    private TextInputEditText mName;
    private TextInputEditText mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_instructor);

        // Setup UI
        mGenderSpinner = findViewById(R.id.genderSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mGenderSpinner.setAdapter(adapter);

        mSubmitRegisterButton = findViewById(R.id.submit_register_button);
        mSubmitRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishThisActivity();
            }
        });
        mName = findViewById(R.id.ti_name);
        mLocation = findViewById(R.id.ti_location);
    }

    private boolean checkInputValues() {
        boolean isValid = true;
        String name = mName.getText().toString();
        String location = mLocation.getText().toString();

        if (name.length() == 0) {
            mName.setError("Name cannot be empty");
            isValid = false;
        }

        if (location.length() == 0) {
            mLocation.setError("Location cannot be empty");
            isValid = false;
        }

        if (!isValid)
            return false;

        return isValid;
    }

    private void finishThisActivity() {
        // check for empty / invalid input
        if (!checkInputValues())
            return;

        Gender selectedGender = mGenderSpinner.getSelectedItemPosition() == 0 ? Gender.MALE : Gender.FEMALE;

        Intent data = new Intent();
        data.putExtra("akAuthCode", this.getIntent().getStringExtra("akAuthCode"));
        data.putExtra("name", mName.getText().toString());
        data.putExtra("city", mLocation.getText().toString());
        data.putExtra("gender", selectedGender.toString().toLowerCase());

        setResult(Activity.RESULT_OK, data);
        finish();
    }

}
