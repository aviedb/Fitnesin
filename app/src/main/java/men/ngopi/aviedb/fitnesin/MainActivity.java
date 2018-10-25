package men.ngopi.aviedb.fitnesin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                fragment = new FindInstructor();
                break;

            case R.id.navigation_profile:
                fragment = new Profile();
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

}
