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

    void getInstructors(@NonNull LoadInstructorsCallback callback);

    void getInstructor(@NonNull String instructorId, @NonNull GetInstructorCallback callback);

}
