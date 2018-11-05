package men.ngopi.aviedb.fitnesin.network;

import men.ngopi.aviedb.fitnesin.network.model.loginMember.LoginRequest;
import men.ngopi.aviedb.fitnesin.network.model.loginMember.LoginResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class FitnesinService {
    private static final String SERVER_URL = "https://fitnesin.ngopi.men/api/";
    private static FitnesinService INSTANCE;

    private final Retrofit retrofit;
    private final IFitnesinService service;


    private FitnesinService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(FitnesinService.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(IFitnesinService.class);
    }

    public static FitnesinService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FitnesinService();
        }
        return INSTANCE;
    }

    public IFitnesinService getService() {
        return service;
    }

    public interface IFitnesinService {

        @POST("login/member")
        Call<LoginResponse> loginMember(@Body LoginRequest request);

    }
}
