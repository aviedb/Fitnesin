package men.ngopi.aviedb.fitnesin.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import men.ngopi.aviedb.fitnesin.data.Instructor;
import men.ngopi.aviedb.fitnesin.data.InstructorSerializer;
import men.ngopi.aviedb.fitnesin.data.Member;
import men.ngopi.aviedb.fitnesin.data.MemberSerializer;
import men.ngopi.aviedb.fitnesin.network.model.ModelResponse;
import men.ngopi.aviedb.fitnesin.network.model.ModelsResponse;
import men.ngopi.aviedb.fitnesin.network.model.fetchMember.FetchMemberResponse;
import men.ngopi.aviedb.fitnesin.network.model.loginMember.LoginRequest;
import men.ngopi.aviedb.fitnesin.network.model.loginMember.LoginResponse;
import men.ngopi.aviedb.fitnesin.network.model.registerMember.RegisterMemberRequest;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public class FitnesinService {
    private static final String SERVER_URL = "https://fitnesin.ngopi.men/api/";
    private static FitnesinService INSTANCE;

    private final Retrofit retrofit;
    private final IFitnesinService service;


    private FitnesinService() {

        // register custom serializer
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder
                .registerTypeAdapter(Member.class, new MemberSerializer())
                .registerTypeAdapter(Instructor.class, new InstructorSerializer())
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(FitnesinService.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
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

        @GET("members/me")
        Call<FetchMemberResponse> fetchMember(@Header("Authorization") String token);

        @POST("register/member")
        Call<FetchMemberResponse> registerMember(@Body RegisterMemberRequest request);

        @POST("login/instructor")
        Call<LoginResponse> loginInstructor(@Body LoginRequest request);

        @GET("instructors/me")
        Call<ModelResponse<Instructor>> fetchInstructorAsMe(@Header("Authorization") String token);

        @GET("instructors")
        Call<ModelsResponse<Instructor>> fetchInstructors(@Header("Authorization") String token);

    }
}
