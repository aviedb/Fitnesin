package men.ngopi.aviedb.fitnesin.data.source;

import android.support.annotation.NonNull;

import java.util.List;

import men.ngopi.aviedb.fitnesin.data.Instructor;

public interface InstructorsDataSource {

    interface LoadInstructorsCallback {

        void onInstructorsLoaded(List<Instructor> instructors);

        void onDataNotAvailable();

    }

    interface GetInstructorCallback {

        void onInstructorLoaded(Instructor instructor);

        void onDataNotAvailable();

    }

    interface DeleteInstructorCallback {

        void onSuccess();

        void onFailure();

    }

    void getInstructors(@NonNull LoadInstructorsCallback callback);

    void getInstructor(@NonNull String instructorId, @NonNull GetInstructorCallback callback);

    void getMe(@NonNull GetInstructorCallback callback);

    void saveMe(@NonNull Instructor instructor, @NonNull GetInstructorCallback callback);

    void deleteMe(@NonNull DeleteInstructorCallback callback);
}
