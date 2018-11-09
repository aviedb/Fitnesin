package men.ngopi.aviedb.fitnesin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.gson.internal.bind.util.ISO8601Utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;

import men.ngopi.aviedb.fitnesin.data.source.InstructorsDataSource;
import men.ngopi.aviedb.fitnesin.data.source.MembersDataSource;
import men.ngopi.aviedb.fitnesin.data.source.local.InstructorsLocalDataSource;
import men.ngopi.aviedb.fitnesin.data.source.remote.MembersRemoteDataSource;
import men.ngopi.aviedb.fitnesin.instructors.InstructorsFragment;
import men.ngopi.aviedb.fitnesin.instructors.InstructorsPresenter;
import men.ngopi.aviedb.fitnesin.profile.ProfileFragment;
import men.ngopi.aviedb.fitnesin.profile.ProfilePresenter;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static final String SHARED_PREFERENCE = "pref";
    public static final String PREF_TOKEN_KEY = "token";
    public static final String PREF_USERTOKEN_KEY = "token_for_member";
    public static final String PREF_TOKEN_EXPIRY_KEY = "token_expiry";

    Fragment fragment;

    private InstructorsFragment mInstructorsView;
    private InstructorsPresenter mInstructorsPresenter;
    private InstructorsDataSource mInstructorsDataSource;
    private MembersDataSource mMembersDataSource;

    private ProfileFragment mProfileView;
    private ProfilePresenter mProfilePresenter;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Shared Preferences
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE);

        // Check if user already login
        String loginToken = sharedPreferences.getString(PREF_TOKEN_KEY, null);
        String tokenExpiry = sharedPreferences.getString(PREF_TOKEN_EXPIRY_KEY, null);
        if (loginToken == null || tokenExpiry == null) {
            showLogin();
            return;
        }

        // Check for token expiry
        try {
            Date date = ISO8601Utils.parse(tokenExpiry, new ParsePosition(0));
            Date currentDate = new Date();
            if (date.before(currentDate)) {
                // Clear credentials
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(PREF_TOKEN_KEY);
                editor.remove(PREF_TOKEN_EXPIRY_KEY);
                editor.remove(PREF_USERTOKEN_KEY);
                editor.apply();
                showLogin();
                return;
            }
        } catch (ParseException ex) {
            showLogin();
            return;
        }

        if (!sharedPreferences.getBoolean(PREF_USERTOKEN_KEY, false)) {
            // token is for instructor
            // TODO: finish this activity and start InstructorActivity

        }


        if (savedInstanceState != null) {
            Log.d("onRestoreInstanceState", "Restoring");

            fragment = getSupportFragmentManager().getFragment(savedInstanceState, "myFragment");
        } else {
            //loading the default fragment
            fragment = new Fitnesin();
        }

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        // set up instructors data source
        mInstructorsDataSource = InstructorsLocalDataSource.getInstance();
        mMembersDataSource = MembersRemoteDataSource.getInstance();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d("onSaveInstanceState", "Saving");
        getSupportFragmentManager().putFragment(outState, "myFragment", fragment);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadFragment(fragment);
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadFragment(fragment);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_fitnesin:
                fragment = new Fitnesin();
                break;

            case R.id.navigation_find_instructor:
                if (mInstructorsView == null) {
                    mInstructorsView = new InstructorsFragment();
                    mInstructorsPresenter = new InstructorsPresenter(mInstructorsDataSource, mInstructorsView);
                }
                fragment = mInstructorsView;
                break;

            case R.id.navigation_profile:
                if (mProfileView == null) {
                    mProfileView = new ProfileFragment();
                    mProfilePresenter = new ProfilePresenter(mMembersDataSource, mProfileView);

                }

                fragment = mProfileView;
                break;
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void showLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
