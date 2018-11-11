package men.ngopi.aviedb.fitnesin.profile;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import men.ngopi.aviedb.fitnesin.LoginActivity;
import men.ngopi.aviedb.fitnesin.MainActivity;
import men.ngopi.aviedb.fitnesin.R;
import men.ngopi.aviedb.fitnesin.data.Gender;
import men.ngopi.aviedb.fitnesin.data.Member;

public class ProfileFragment extends Fragment implements View.OnClickListener, ProfileContract.View {
    private ProfileContract.Presenter mPresenter;

    private TextView mMemberName;
    private TextView mMemberPhone;
    private Spinner mGenderSpinner;
    private TextInputEditText mMemberBirthdate;
    private TextInputEditText mMemberWeight;
    private TextInputEditText mMemberHeight;
    private MaterialButton logoutBtn;
    private MaterialButton saveBtn;
    private DatePickerDialog dpd;

    private SharedPreferences sharedPreferences;
    private Calendar selectedBirthdate = Calendar.getInstance();
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.profile_fragment, container, false);

        mMemberName = rootView.findViewById(R.id.tv_member_name);
        mMemberPhone = rootView.findViewById(R.id.tv_member_phone);
        mGenderSpinner = rootView.findViewById(R.id.genderSpinner);
        mMemberBirthdate = rootView.findViewById(R.id.ti_birthdate);
        mMemberWeight = rootView.findViewById(R.id.ti_weight);
        mMemberHeight = rootView.findViewById(R.id.ti_height);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.gender, R.layout.spinner_item);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mGenderSpinner.setAdapter(adapter);

        logoutBtn = rootView.findViewById(R.id.logout);
        logoutBtn.setOnClickListener(this);

        saveBtn = rootView.findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gender gender = mGenderSpinner.getSelectedItemPosition() == 0 ? Gender.MALE : Gender.FEMALE;

                boolean isValid = true;
                // check for invalid value
//                String name = mMemberName.getText().toString();
                String weight = mMemberWeight.getText().toString();
                String height = mMemberHeight.getText().toString();

                if (weight.length() == 0) {
                    mMemberWeight.setError("Weight can't be empty");
                    isValid = false;
                }

                if (height.length() == 0) {
                    mMemberHeight.setError("Height can't be empty");
                    isValid = false;
                }

                if (!isValid)
                    return;

                double weightVal = 0;
                double heightVal = 0;

                try {
                    weightVal = Double.valueOf(weight);
                    heightVal = Double.valueOf(height);
                } catch (NumberFormatException ie) {
                    Log.e(ProfileFragment.class.getSimpleName(), "Error when casting weight or height");
                    Log.e(ProfileFragment.class.getSimpleName(), ie.getMessage());
                }

                mPresenter.saveProfile(
                        gender,
                        selectedBirthdate,
                        weightVal,
                        heightVal
                );
            }
        });

        sharedPreferences = rootView.getContext().getSharedPreferences(MainActivity.SHARED_PREFERENCE, Context.MODE_PRIVATE);

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR); // Initial year selection
        int month = now.get(Calendar.MONTH); // Initial month selection
        int day = now.get(Calendar.DAY_OF_MONTH); // Inital day selection

        dpd = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                // move newBirthdate to selectedBirthdate as its value will be shared
                selectedBirthdate.set(mYear, mMonth, mDay);

                SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
                mMemberBirthdate.setText(formatter.format(selectedBirthdate.getTime()));

            }
        }, day, month, year);

        dpd.getDatePicker().setMaxDate(System.currentTimeMillis() + 60 * 60 * 1000);
        dpd.updateDate(year, month, day);

        mMemberBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dpd.show();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onClick(View v) {

        // Clear credentials
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(MainActivity.PREF_TOKEN_KEY);
        editor.remove(MainActivity.PREF_USERTOKEN_KEY);
        editor.apply();

        // Show LoginActivity
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    @Override
    public void showProfile(Member member) {
        mMemberName.setText(member.getName());
        mMemberPhone.setText(member.getPhone());
        mMemberWeight.setText(String.valueOf(member.getWeight()));
        mMemberHeight.setText(String.valueOf(member.getHeight()));

        if (member.getGender() == Gender.MALE) {
            mGenderSpinner.setSelection(0);
        } else {
            mGenderSpinner.setSelection(1);
        }

        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
        mMemberBirthdate.setText(formatter.format(member.getBirthdate().getTime()));
        dpd.updateDate(member.getBirthdate().get(Calendar.YEAR), member.getBirthdate()
                .get(Calendar.MONTH), member.getBirthdate().get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(this.rootView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
