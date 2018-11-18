package men.ngopi.aviedb.fitnesin.instructor;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.TextInputEditText;
import android.support.design.button.MaterialButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import men.ngopi.aviedb.fitnesin.R;
import men.ngopi.aviedb.fitnesin.data.Gender;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class InstructorEditInfo extends Activity implements View.OnClickListener {
    private TextView tv;
    private MaterialButton mSaveButton;
    private TextInputEditText mName;
    private TextInputEditText mPhone;
    private TextInputEditText mCity;
    private Spinner mGenderSpinner;
    private TextInputEditText mBirthdate;
    private DatePickerDialog dpd;
    SimpleDateFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_edit_info);

        mGenderSpinner = findViewById(R.id.genderSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGenderSpinner.setAdapter(adapter);

        tv = findViewById(R.id.back);
        tv.setOnClickListener(this);

        mSaveButton = findViewById(R.id.saveBtn);
        mSaveButton.setOnClickListener(this);

        mName = findViewById(R.id.ti_name);
        mPhone = findViewById(R.id.ti_phone);
        mCity = findViewById(R.id.ti_city);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");
        String city = intent.getStringExtra("city");
        String gender = intent.getStringExtra("gender");
        if (name != null)
            mName.setText(name);

        if (phone != null)
            mPhone.setText(phone);

        if (city != null)
            mCity.setText(city);

        if (gender.equalsIgnoreCase("male"))
            mGenderSpinner.setSelection(0);
        else
            mGenderSpinner.setSelection(1);

        mBirthdate = findViewById(R.id.ti_birthdate);
        formatter = new SimpleDateFormat("MMM dd, yyyy");

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR); // Initial year selection
        int month = now.get(Calendar.MONTH); // Initial month selection
        int day = now.get(Calendar.DAY_OF_MONTH); // Inital day selection

        dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                Calendar newBirthdate = Calendar.getInstance();
                newBirthdate.set(mYear, mMonth, mDay);

                mBirthdate.setText(formatter.format(newBirthdate.getTime()));

            }
        }, day, month, year);

        // Initial Birthdate
        now.set(1971, 5, 28);
        mBirthdate.setText(formatter.format(now.getTime()));

        dpd.updateDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        dpd.getDatePicker().setMaxDate(System.currentTimeMillis() + 60 * 60 * 1000);

        mBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dpd.show();
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v.equals(tv)) {
            setResult(Activity.RESULT_CANCELED);
        } else if (v.equals(mSaveButton)) {
            // TODO: check for invalid data
            Intent data = new Intent();
            data.putExtra("name", mName.getText().toString());
            data.putExtra("city", mCity.getText().toString());
            data.putExtra("gender", mGenderSpinner.getSelectedItemPosition() == 0 ? Gender.MALE.toString() : Gender.FEMALE.toString());
            setResult(Activity.RESULT_OK, data);
        }
        finish();
    }

}
