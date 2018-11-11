package men.ngopi.aviedb.fitnesin.data.source.local;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import men.ngopi.aviedb.fitnesin.R;
import men.ngopi.aviedb.fitnesin.data.Gender;
import men.ngopi.aviedb.fitnesin.data.Instructor;
import men.ngopi.aviedb.fitnesin.data.source.InstructorsDataSource;

public class InstructorsLocalDataSource implements InstructorsDataSource {

    private static volatile InstructorsLocalDataSource INSTANCE;

    private ArrayList<Instructor> instructors = new ArrayList<>();
    private InstructorsLocalDataSource() {
        instructors.add(new Instructor("Elon Musk", Gender.MALE, "Malang","+62 822 9220 9034",  R.drawable.elon_musk));
        instructors.add(new Instructor("Tony Stark", Gender.MALE, "Malang", "+62 822 1774 1234", R.drawable.tony_stark));
        instructors.add(new Instructor("Via Vallen", Gender.FEMALE, "Malang", "+62 822 8732 6666", R.drawable.via_vallen));
    }

    public static InstructorsLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InstructorsLocalDataSource();
        }

        return INSTANCE;
    }

    @Override
    public void getInstructors(@NonNull LoadInstructorsCallback callback) {
        callback.onInstructorsLoaded(this.instructors);
    }

    @Override
    public void getInstructor(@NonNull String instructorId, @NonNull GetInstructorCallback callback) {
        callback.onInstructorLoaded(instructors.get(0));
    }

    @Override
    public void getMe(@NonNull GetInstructorCallback callback) {
        callback.onInstructorLoaded(instructors.get(0));
    }

    @Override
    public void saveMe(@NonNull final Instructor instructor, @NonNull GetInstructorCallback callback) {
        instructors.add(instructor);
        callback.onInstructorLoaded(instructor);
    }
}
