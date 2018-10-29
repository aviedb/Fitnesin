package men.ngopi.aviedb.fitnesin.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import men.ngopi.aviedb.fitnesin.LoginActivity;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_fragment, container, false);

        mMemberName = rootView.findViewById(R.id.tv_member_name);
        mMemberPhone = rootView.findViewById(R.id.tv_member_phone);
        mGenderSpinner = rootView.findViewById(R.id.genderSpinner);
        mMemberBirthdate = rootView.findViewById(R.id.ti_birthdate);
        mMemberWeight = rootView.findViewById(R.id.ti_weight);
        mMemberHeight = rootView.findViewById(R.id.ti_height);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.gender, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGenderSpinner.setAdapter(adapter);

        logoutBtn = rootView.findViewById(R.id.logout);
        logoutBtn.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
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

    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
