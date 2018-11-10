package men.ngopi.aviedb.fitnesin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class InstructorEditInfo extends Activity implements View.OnClickListener {
    private TextView tv;
    private TextInputEditText mName;
    private TextInputEditText mPhone;
    private TextInputEditText mCity;
    private Spinner mGenderSpinner;

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
    }

    @Override
    public void onClick(View v) {
        finish();
    }

}
