package men.ngopi.aviedb.fitnesin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import men.ngopi.aviedb.fitnesin.data.Gender;
import men.ngopi.aviedb.fitnesin.data.Instructor;
import men.ngopi.aviedb.fitnesin.data.source.InstructorsDataSource;
import men.ngopi.aviedb.fitnesin.data.source.remote.InstructorsRemoteDataSource;

public class InstructorMainActivity extends AppCompatActivity implements View.OnClickListener {
    private static int APP_EDIT_INSTRUCTOR = 300;

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
        getData();

        // Add action menu on appBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // TODO: implement MVP pattern
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_account) {
//            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
//            builder1.setTitle("Delete Account");
//            builder1.setMessage("Are you sure you want to delete your account?");
//            builder1.setCancelable(true);

            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View promptView = layoutInflater.inflate(R.layout.prompt, null);

            final AlertDialog alertD = new AlertDialog.Builder(this).create();

            MaterialButton yesBtn = (MaterialButton) promptView.findViewById(R.id.yes_button);

            MaterialButton noBtn = (MaterialButton) promptView.findViewById(R.id.no_button);

            yesBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    deleteInstructor();
                    alertD.dismiss();

                }
            });

            noBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    alertD.cancel();

                }
            });

            alertD.setView(promptView);

            alertD.show();

//            builder1.setPositiveButton(
//                    "Yes",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            // TODO: Delete account
//                            Toast.makeText(InstructorMainActivity.this, "Account Deleted [NOT REALLY]", Toast.LENGTH_LONG).show();
//                        }
//                    });
//
//            builder1.setNegativeButton(
//                    "No",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.cancel();
//                        }
//                    });

//            AlertDialog alert11 = builder1.create();
//            alert11.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
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
            startActivityForResult(i, APP_EDIT_INSTRUCTOR);
        } else if (v.equals(logoutBtn)) {
            logout();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == APP_EDIT_INSTRUCTOR && resultCode == Activity.RESULT_OK) {
            String name = data.getStringExtra("name");
            String city = data.getStringExtra("city");
            String gender = data.getStringExtra("gender");
            if (name != null)
                instructor.setName(name);
            if (city != null)
                instructor.setCity(city);

            if (gender != null)
                instructor.setGender(Gender.valueOf(gender.toUpperCase()));

            final Context ctx = this;

            // TODO: add loading indicator (1)
            instructorsDataSource.saveMe(instructor, new InstructorsDataSource.GetInstructorCallback() {
                @Override
                public void onInstructorLoaded(Instructor instructor) {
                    Toast.makeText(ctx, "Data successfully saved", Toast.LENGTH_LONG).show();
                    getData();
                }

                @Override
                public void onDataNotAvailable() {
                    Toast.makeText(ctx, "Unable to save data", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void getData() {
        // TODO : add loading indicator (2)
        final Context ctx = this;
        instructorsDataSource.getMe(new InstructorsDataSource.GetInstructorCallback() {
            @Override
            public void onInstructorLoaded(Instructor instructor) {
                showInstructor(instructor);
            }

            @Override
            public void onDataNotAvailable() {
                Toast.makeText(ctx, "Unable to fetch data, please try again", Toast.LENGTH_LONG).show();
                Log.d(InstructorMainActivity.class.getSimpleName(), "failed to fetch data");
            }
        });
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

    private void deleteInstructor() {
        instructorsDataSource.deleteMe(new InstructorsDataSource.DeleteInstructorCallback() {
            @Override
            public void onSuccess() {
                logout();
                Toast.makeText(InstructorMainActivity.this, "Account deleted", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure() {
                Toast.makeText(InstructorMainActivity.this, "Unable to delete Instructor", Toast.LENGTH_LONG).show();
            }
        });
    }
}
