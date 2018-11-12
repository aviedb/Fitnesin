package men.ngopi.aviedb.fitnesin.registerMember;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.google.gson.internal.bind.util.ISO8601Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import men.ngopi.aviedb.fitnesin.R;
import men.ngopi.aviedb.fitnesin.data.Gender;

public class RegisterMemberActivity extends AppCompatActivity {

    private Spinner mGenderSpinner;
    private MaterialButton mSubmitRegisterButton;
    private TextInputEditText mName;
    private TextInputEditText mBirthdate;
    private TextInputEditText mWeight;
    private TextInputEditText mHeight;
    private DatePickerDialog dpd;

    private Calendar selectedDate = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_member);

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
        mBirthdate = findViewById(R.id.ti_birthdate);
        mWeight = findViewById(R.id.ti_weight);
        mHeight = findViewById(R.id.ti_height);

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR); // Initial year selection
        int month = now.get(Calendar.MONTH); // Initial month selection
        int day = now.get(Calendar.DAY_OF_MONTH); // Inital day selection

        dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                selectedDate.set(mYear, mMonth, mDay);

                SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
                mBirthdate.setText(formatter.format(selectedDate.getTime()));

            }
        },day,month,year);

        dpd.getDatePicker().setMaxDate(System.currentTimeMillis() + 60*60*1000);
        dpd.updateDate(year, month, day);

        mBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dpd.show();
            }
        });
    }

    private boolean checkInputValues() {
        boolean isValid = true;
        String name = mName.getText().toString();
        String birthdate = mBirthdate.getText().toString();
        String weight = mWeight.getText().toString();
        String height = mHeight.getText().toString();

        if (name.length() == 0) {
            mName.setError("Name cannot be empty");
            isValid = false;
        }
        if (birthdate.length() == 0) {
            mBirthdate.setError("Birthdate cannot be empty");
            isValid = false;
        }
        if (weight.length() == 0) {
            mWeight.setError("Weight cannot be empty");
            isValid = false;
        }
        if (height.length() == 0) {
            mHeight.setError("Height cannot be empty");
            isValid = false;
        }

        if (!isValid)
            return false;

        double weightVal = Double.valueOf(weight);
        double heightVal = Double.valueOf(height);
        if (weightVal < 1 || weightVal > 1000) {
            mWeight.setError("Weight cannot be lower than 1 or larger than 1000");
            isValid = false;
        }
        if (heightVal < 1 || heightVal > 1000) {
            mHeight.setError("Height cannot be lower than 1 or larger than 1000");
            isValid = false;
        }

        return isValid;

    }

    private void finishThisActivity() {
        // TODO: check for empty / invalid input

        if (!checkInputValues())
            return;


        Gender selectedGender = mGenderSpinner.getSelectedItemPosition() == 0 ? Gender.MALE : Gender.FEMALE;
        double weightVal = Double.valueOf(mWeight.getText().toString());
        double heightVal = Double.valueOf(mHeight.getText().toString());
//        double weightVal = 0;
//        double heightVal = 0;

        Intent data = new Intent();
        data.putExtra("akAuthCode", this.getIntent().getStringExtra("akAuthCode"));
        data.putExtra("name", mName.getText().toString());
        data.putExtra("birthdate", ISO8601Utils.format(selectedDate.getTime()));
        data.putExtra("weight", weightVal);
        data.putExtra("height", heightVal);
        data.putExtra("gender", selectedGender.toString().toLowerCase());

        setResult(Activity.RESULT_OK, data);
        finish();
    }

}
