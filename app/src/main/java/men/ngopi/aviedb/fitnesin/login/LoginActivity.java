package men.ngopi.aviedb.fitnesin.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import men.ngopi.aviedb.fitnesin.instructor.InstructorMainActivity;
import men.ngopi.aviedb.fitnesin.MainActivity;
import men.ngopi.aviedb.fitnesin.R;
import men.ngopi.aviedb.fitnesin.data.Instructor;
import men.ngopi.aviedb.fitnesin.data.Member;

import men.ngopi.aviedb.fitnesin.network.FitnesinService;
import men.ngopi.aviedb.fitnesin.network.model.ErrorResponse;
import men.ngopi.aviedb.fitnesin.network.model.ModelResponse;
import men.ngopi.aviedb.fitnesin.network.model.loginMember.LoginRequest;
import men.ngopi.aviedb.fitnesin.network.model.loginMember.LoginResponse;
import men.ngopi.aviedb.fitnesin.network.model.registerInstructor.RegisterInstructorRequest;
import men.ngopi.aviedb.fitnesin.network.model.registerMember.RegisterMemberRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    private static final int AK_LOGIN_AS_MEMBER = 99;
    private static final int AK_REGISTER_AS_MEMBER = 100;
    private static final int AK_LOGIN_AS_INSTRUCTOR = 101;
    private static final int AK_REGISTER_AS_INSTRUCTOR = 102;
    private static final int APP_REGISTER_AS_MEMBER = 200;
    private static final int APP_REGISTER_AS_INSTRUCTOR = 201;

    private final FitnesinService.IFitnesinService fitnesinService = FitnesinService.getInstance().getService();

    private SharedPreferences sharedPreferences;
    private Context context;

    private ProgressBar mLoadingProgressBar;
    private MaterialButton mMemberLoginButton;
    private MaterialButton mInstructorLoginButton;
    private MaterialButton mMemberRegisterButton;
    private MaterialButton mInstructorRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set context
        context = this;

        mLoadingProgressBar = findViewById(R.id.login_progress);

        mMemberLoginButton = findViewById(R.id.email_sign_in_button);
        mMemberLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLoginAsMember();
            }
        });

        mInstructorLoginButton = findViewById(R.id.sign_in_instructor);
        mInstructorLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLoginAsInstructor();
            }
        });

        mMemberRegisterButton= findViewById(R.id.register_member_button);
        mMemberRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegisterAsMember();
            }
        });

        mInstructorRegisterButton= findViewById(R.id.register_instructor_button);
        mInstructorRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegisterAsInstructor();
            }
        });

        sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFERENCE, MODE_PRIVATE);
    }

    private void attemptLoginAsMember() {
        verifyAccountKitPhone(AK_LOGIN_AS_MEMBER);
    }

    private void attemptLoginAsInstructor() {
        verifyAccountKitPhone(AK_LOGIN_AS_INSTRUCTOR);
    }

    private void onVerifyPhoneForLoginAsMember(String authCode) {
        LoginRequest req = new LoginRequest();
        req.setAuthCode(authCode);

        // Show loading (3)
        setLoading(true);
        fitnesinService.loginMember(req).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                setLoading(false);
                Log.d("authMember", "response: " + response.toString());
                Log.d("authMember", "isSuccessful: " + response.isSuccessful());
                Log.d("authMember", "code: " + response.code());
                if (response.body() != null && response.body().getData() != null) {
                    LoginResponse.LoginData loginData = response.body().getData();
                    Log.d("authMember", "token: " + loginData.getToken());

                    setToken(loginData.getToken(), loginData.getExpiry(), true);

                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                    finish();
                }

                if (response.code() == 404) {
                    showToast("Member is not registered");
                    return;
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                setLoading(false);
                Log.e("authMember", "failed");
            }
        });
    }

    private void attemptRegisterAsMember() {
        verifyAccountKitPhone(AK_REGISTER_AS_MEMBER);
    }

    private void attemptRegisterAsInstructor() {
        // Verify phone number first
        verifyAccountKitPhone(AK_REGISTER_AS_INSTRUCTOR);
    }

    private void onVerifyPhoneForRegisterAsMember(String authCode) {

        Intent intent = new Intent(this, RegisterMemberActivity.class);
        intent.putExtra("akAuthCode", authCode);
        startActivityForResult(intent, APP_REGISTER_AS_MEMBER);

    }

    private void onVerifyPhoneForRegisterAsInstructor(String authCode) {
        Intent intent = new Intent(this, RegisterInstructorActivity.class);
        intent.putExtra("akAuthCode", authCode);
        startActivityForResult(intent, APP_REGISTER_AS_INSTRUCTOR);
    }

    private void onMemberRegisterActivityResult(final Intent data) {
        Log.d("onMemberRegisterResult", "AkAuthCode: " + data.getStringExtra("akAuthCode"));
        Log.d("onMemberRegisterResult", "Name: " + data.getStringExtra("name"));
        Log.d("onMemberRegisterResult", "Birthdate: " + data.getStringExtra("birthdate"));
        Log.d("onMemberRegisterResult", "Height: " + data.getDoubleExtra("height", -1));
        Log.d("onMemberRegisterResult", "Weight: " + data.getDoubleExtra("weight", -1));
        Log.d("onMemberRegisterResult", "Gender: " + data.getStringExtra("gender"));

        RegisterMemberRequest req = new RegisterMemberRequest(
                data.getStringExtra("akAuthCode"),
                data.getStringExtra("name"),
                data.getStringExtra("birthdate"),
                data.getDoubleExtra("weight", 1),
                data.getDoubleExtra("height", 1),
                data.getStringExtra("gender")
        );
        // Show loading
        setLoading(true);
        fitnesinService.registerMember(req).enqueue(new Callback<ModelResponse<Member>>() {
            @Override
            public void onResponse(Call<ModelResponse<Member>> call, Response<ModelResponse<Member>> response) {
                setLoading(false);
                if (!response.isSuccessful()) {
                    showToast("Not successfull");
                    return;
                }
                if (response.body() != null && response.body().getData() != null) {
                    Member member = response.body().getData();
                    Log.d("onRegisterMemberService", "Name: " + member.getName());
                    Log.d("onRegisterMemberService", "Birthdate: " + member.getBirthdate().toString());
                    Log.d("onRegisterMemberService", "Height: " + member.getHeight());
                    Log.d("onRegisterMemberService", "Weight: " + member.getWeight());
                    Log.d("onRegisterMemberService", "Gender: " + member.getGender().toString());
                    showToast("Registration success");
                }
            }

            @Override
            public void onFailure(Call<ModelResponse<Member>> call, Throwable t) {
                setLoading(false);
                showToast("Registration Failed");
            }
        });
    }

    private void onInstructorRegisterActivityResult(final Intent data) {
        Log.d("InstructorRegister", "AkAuthCode: " + data.getStringExtra("akAuthCode"));
        Log.d("InstructorRegister", "Name: " + data.getStringExtra("name"));
        Log.d("InstructorRegister", "City: " + data.getStringExtra("city"));
        Log.d("InstructorRegister", "Gender: " + data.getStringExtra("gender"));

        RegisterInstructorRequest req = new RegisterInstructorRequest(
                data.getStringExtra("akAuthCode"),
                data.getStringExtra("name"),
                data.getStringExtra("city"),
                data.getStringExtra("gender"),
                data.getStringExtra("birthdate")
        );

        // Show loading
        setLoading(true);
        fitnesinService.registerInstructor(req).enqueue(new Callback<ModelResponse<Instructor>>() {
            @Override
            public void onResponse(Call<ModelResponse<Instructor>> call, Response<ModelResponse<Instructor>> response) {
                setLoading(false);
                if (!response.isSuccessful()) {
                    showToast("Instructor Registration Not successfull");
                    return;
                }
                if (response.body() != null && response.body().getData() != null) {
                    Instructor instructor = response.body().getData();
                    Log.d("onRegisterMemberService", "Name: " + instructor.getName());
                    Log.d("onRegisterMemberService", "City: " + instructor.getCity());
                    Log.d("onRegisterMemberService", "Phone: " + instructor.getPhone());
                    Log.d("onRegisterMemberService", "Gender: " + instructor.getGender().toString());
                    showToast("Instructor Registration success");
                }
            }

            @Override
            public void onFailure(Call<ModelResponse<Instructor>> call, Throwable t) {
                setLoading(false);
                showToast("Instructor Registration Failed");
            }
        });

    }

    private void onVerifyPhoneForLoginAsInstructor(String authCode) {
        LoginRequest req = new LoginRequest();
        req.setAuthCode(authCode);

        // Show loading
        setLoading(true);
        fitnesinService.loginInstructor(req).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                setLoading(false);
                Log.d("authInstructor", "response: " + response.toString());
                Log.d("authInstructor", "isSuccessful: " + response.isSuccessful());
                Log.d("authInstructor", "code: " + response.code());
                if (response.body() != null && response.body().getData() != null) {
                    LoginResponse.LoginData loginData = response.body().getData();
                    Log.d("authInstructor", "token: " + loginData.getToken());

                    setToken(loginData.getToken(), loginData.getExpiry(), false);


                    Intent intent = new Intent(context, InstructorMainActivity.class);
                    context.startActivity(intent);
                    finish();
                }

                if (response.code() == 404) {
                    showToast("Instructor is not registered");
                    return;
                }

                if (response.code() > 400) {
                    Gson gson = new GsonBuilder().create();
                    ErrorResponse errorResponse = new ErrorResponse();
                    try {
                        errorResponse = gson.fromJson(response.errorBody().string(),ErrorResponse.class);
                        showToast(errorResponse.getMessage());
                    }catch (IOException e) {
                        Log.e("authInstructor", "I/O Error");
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                setLoading(false);
                Log.e("authInstructor", "failed");
            }
        });
    }

    private void verifyAccountKitPhone(int intentRequestCode) {
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.CODE
                );

        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build()
        );
        startActivityForResult(intent, intentRequestCode);
    }

    private void showToast(String toastMessage) {
        Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult", "requestCode: " + requestCode);
        Log.d("onActivityResult", "resultCode: " + resultCode);

        switch (requestCode) {
            case APP_REGISTER_AS_MEMBER: {
                if (resultCode == Activity.RESULT_OK)
                    onMemberRegisterActivityResult(data);
                break;
            }
            case APP_REGISTER_AS_INSTRUCTOR: {
                if (resultCode == Activity.RESULT_OK)
                    onInstructorRegisterActivityResult(data);
                break;
            }
            case AK_LOGIN_AS_MEMBER:
            case AK_REGISTER_AS_MEMBER:
            case AK_LOGIN_AS_INSTRUCTOR:
            case AK_REGISTER_AS_INSTRUCTOR: {
                // confirm that this response matches your request
                AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
                String toastMessage = null;
                if (loginResult.getError() != null) {
                    toastMessage = loginResult.getError().getErrorType().getMessage();
                    showErrorActivity(loginResult.getError());
                } else if (loginResult.wasCancelled()) {
                    toastMessage = "Phone Verification Cancelled";
                } else if (loginResult.getAuthorizationCode() != null) {
                    String authCode = loginResult.getAuthorizationCode();
                    if (requestCode == AK_LOGIN_AS_MEMBER)
                        onVerifyPhoneForLoginAsMember(authCode);
                    else if (requestCode == AK_REGISTER_AS_MEMBER)
                        onVerifyPhoneForRegisterAsMember(authCode);
                    else if (requestCode == AK_LOGIN_AS_INSTRUCTOR)
                        onVerifyPhoneForLoginAsInstructor(authCode);
                    else if (requestCode == AK_REGISTER_AS_INSTRUCTOR)
                        onVerifyPhoneForRegisterAsInstructor(authCode);
                } else {
                    toastMessage = "Phone Verification Failed";
                }

                // Show toast
                if (toastMessage != null) {
                    showToast(toastMessage);
                }
            }
        }

    }

    private void showErrorActivity(AccountKitError error) {
        Log.d("accountKitError", error.toString());
    }

    private void setToken(String token, String expiry, boolean forMember) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MainActivity.PREF_TOKEN_KEY, token);
        editor.putString(MainActivity.PREF_TOKEN_EXPIRY_KEY, expiry);
        editor.putBoolean(MainActivity.PREF_USERTOKEN_KEY, forMember);
        editor.apply();
    }

    private void setLoading(boolean loading) {
        mLoadingProgressBar.setVisibility(loading ? View.VISIBLE : View.INVISIBLE);
        mMemberLoginButton.setEnabled(!loading);
        mInstructorLoginButton.setEnabled(!loading);
        mMemberRegisterButton.setEnabled(!loading);
        mInstructorRegisterButton.setEnabled(!loading);

    }
}

