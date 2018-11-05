package men.ngopi.aviedb.fitnesin.data.source.remote;

import android.support.annotation.NonNull;

import men.ngopi.aviedb.fitnesin.data.source.MembersDataSource;
import men.ngopi.aviedb.fitnesin.network.FitnesinService;
import men.ngopi.aviedb.fitnesin.network.model.fetchMember.FetchMemberResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MembersRemoteDataSource implements MembersDataSource {

    private static volatile MembersRemoteDataSource INSTANCE;

    private final FitnesinService.IFitnesinService service;

    private MembersRemoteDataSource() {
        service = FitnesinService.getInstance().getService();
    }
    public static MembersRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MembersRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getMe(@NonNull String token, @NonNull final GetMemberCallback callback) {

        // TODO: add cache

        service.fetchMember("bearer " + token).enqueue(new Callback<FetchMemberResponse>() {
            @Override
            public void onResponse(Call<FetchMemberResponse> call, Response<FetchMemberResponse> response) {
                if(response.isSuccessful()) {
                    callback.onMemberLoaded(response.body().getData());
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<FetchMemberResponse> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }
}
