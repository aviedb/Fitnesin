package men.ngopi.aviedb.fitnesin.data.source.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import men.ngopi.aviedb.fitnesin.data.Instructor;
import men.ngopi.aviedb.fitnesin.data.source.InstructorsDataSource;
import men.ngopi.aviedb.fitnesin.network.FitnesinService;
import men.ngopi.aviedb.fitnesin.network.model.ModelResponse;
import men.ngopi.aviedb.fitnesin.network.model.ModelsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InstructorsRemoteDataSource implements InstructorsDataSource {

    private static volatile InstructorsRemoteDataSource INSTANCE;

    private final FitnesinService.IFitnesinService service;
    private String mToken;

    private InstructorsRemoteDataSource() {
        service = FitnesinService.getInstance().getService();
    }

    public void setToken(String token) {
        this.mToken = "bearer " + token;
    }

    public static InstructorsRemoteDataSource getInstance(String token) {
        if (INSTANCE == null) {
            INSTANCE = new InstructorsRemoteDataSource();
        }
        INSTANCE.setToken(token);
        return INSTANCE;
    }

    @Override
    public void getInstructors(@NonNull final LoadInstructorsCallback callback) {
        service.fetchInstructors(mToken).enqueue(new Callback<ModelsResponse<Instructor>>() {
            @Override
            public void onResponse(Call<ModelsResponse<Instructor>> call, Response<ModelsResponse<Instructor>> response) {
                if (response.isSuccessful())
                    callback.onInstructorsLoaded(response.body().getData());
                else
                    callback.onDataNotAvailable();
            }

            @Override
            public void onFailure(Call<ModelsResponse<Instructor>> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getInstructor(@NonNull String instructorId, @NonNull GetInstructorCallback callback) {
        callback.onDataNotAvailable();
    }

    @Override
    public void getMe(@NonNull final GetInstructorCallback callback) {
        service.fetchInstructorAsMe(mToken).enqueue(new Callback<ModelResponse<Instructor>>() {
            @Override
            public void onResponse(Call<ModelResponse<Instructor>> call, Response<ModelResponse<Instructor>> response) {
                if (response.isSuccessful())
                    callback.onInstructorLoaded(response.body().getData());
                else {
                    Log.d(InstructorsRemoteDataSource.class.getSimpleName(), "Error Body: " + response.errorBody().toString());
                    Log.d(InstructorsRemoteDataSource.class.getSimpleName(), "Code" + response.code());
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<ModelResponse<Instructor>> call, Throwable t) {
                Log.d(InstructorsRemoteDataSource.class.getSimpleName(), "Failure: " + t.getMessage());
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void saveMe(@NonNull Instructor instructor, @NonNull final GetInstructorCallback callback) {
        service.updateMeInstructor(mToken, instructor).enqueue(new Callback<ModelResponse<Instructor>>() {
            @Override
            public void onResponse(Call<ModelResponse<Instructor>> call, Response<ModelResponse<Instructor>> response) {
                if(response.isSuccessful() && response.body() != null && response.body().getData() != null)
                    callback.onInstructorLoaded(response.body().getData());
                else
                    callback.onDataNotAvailable();
            }

            @Override
            public void onFailure(Call<ModelResponse<Instructor>> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });

    }

    @Override
    public void deleteMe(@NonNull final DeleteInstructorCallback callback) {
        service.deleteMeInstructor(mToken).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful())
                    callback.onSuccess();
                else
                    callback.onFailure();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailure();
            }
        });
    }
}
