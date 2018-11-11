package men.ngopi.aviedb.fitnesin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class InstructorEditInfo extends Activity implements View.OnClickListener {
    private TextView tv;
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
        },day,month,year);

        // Initial Birthdate
        now.set(1971, 5, 28);
        mBirthdate.setText(formatter.format(now.getTime()));

        dpd.updateDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        dpd.getDatePicker().setMaxDate(System.currentTimeMillis() + 60*60*1000);

        mBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dpd.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        finish();
    }

}
