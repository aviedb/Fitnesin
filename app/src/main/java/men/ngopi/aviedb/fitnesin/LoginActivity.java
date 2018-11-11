package men.ngopi.aviedb.fitnesin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import men.ngopi.aviedb.fitnesin.data.Member;

import men.ngopi.aviedb.fitnesin.network.FitnesinService;
import men.ngopi.aviedb.fitnesin.network.model.fetchMember.FetchMemberResponse;
import men.ngopi.aviedb.fitnesin.network.model.loginMember.LoginRequest;
import men.ngopi.aviedb.fitnesin.network.model.loginMember.LoginResponse;
import men.ngopi.aviedb.fitnesin.network.model.registerMember.RegisterMemberRequest;
import men.ngopi.aviedb.fitnesin.registerMember.RegisterMemberActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set context
        context = this;

        MaterialButton mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLoginAsMember();
            }
        });

        MaterialButton mRegisterAsMemberButton = findViewById(R.id.register_member_button);
        mRegisterAsMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegisterAsMember();
            }
        });

        MaterialButton mRegisterAsInstructorButton = findViewById(R.id.register_instructor_button);
        mRegisterAsInstructorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegisterAsInstructor();
            }
        });

        sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFERENCE, MODE_PRIVATE);
        MaterialButton mSignInInstructor = findViewById(R.id.sign_in_instructor);
        mSignInInstructor.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLoginAsInstructor();
            }
        });

    }

    private void attemptLoginAsMember() {
        verifyAccountKitPhone(AK_LOGIN_AS_MEMBER);
    }

    private void attemptLoginAsInstructor() {
        verifyAccountKitPhone(AK_LOGIN_AS_INSTRUCTOR);
//        final Intent i = new Intent(this, InstructorMainActivity.class);
//        startActivity(i);
    }

    private void onVerifyPhoneForLoginAsMember(String authCode) {
        LoginRequest req = new LoginRequest();
        req.setAuthCode(authCode);

        // TODO: Show loading (3)
        fitnesinService.loginMember(req).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

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
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("authMember", "failed");
            }
        });
    }

    private void attemptRegisterAsMember() {
        verifyAccountKitPhone(AK_REGISTER_AS_MEMBER);
//        onVerifyPhoneForRegisterAsMember("");
    }

    private void attemptRegisterAsInstructor() {
        // TODO: Verify phone number first

        Intent i = new Intent(this, RegisterInstructorActivity.class);
        startActivity(i);
    }

    private void onVerifyPhoneForRegisterAsMember(String authCode) {

        Intent intent = new Intent(this, RegisterMemberActivity.class);
        intent.putExtra("akAuthCode", authCode);
        startActivityForResult(intent, APP_REGISTER_AS_MEMBER);

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
                data.getDoubleExtra("height", 1),
                data.getDoubleExtra("weight", 1),
                data.getStringExtra("gender")
        );
        // TODO: Show loading (2)
        fitnesinService.registerMember(req).enqueue(new Callback<FetchMemberResponse>() {
            @Override
            public void onResponse(Call<FetchMemberResponse> call, Response<FetchMemberResponse> response) {
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
            public void onFailure(Call<FetchMemberResponse> call, Throwable t) {
                showToast("Registration Failed");
            }
        });
    }

    private void onVerifyPhoneForLoginAsInstructor(String authCode) {
        LoginRequest req = new LoginRequest();
        req.setAuthCode(authCode);

        // TODO: Show loading (1)
        fitnesinService.loginInstructor(req).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
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
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
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

}

