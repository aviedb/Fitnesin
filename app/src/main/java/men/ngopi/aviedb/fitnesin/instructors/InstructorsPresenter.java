package men.ngopi.aviedb.fitnesin.instructors;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import men.ngopi.aviedb.fitnesin.data.Instructor;
import men.ngopi.aviedb.fitnesin.data.source.InstructorsDataSource;

public class InstructorsPresenter implements InstructorsContract.Presenter {

    private final InstructorsDataSource mInstructorsDataSource;

    private final InstructorsContract.View mInstructorsView;

    public InstructorsPresenter(@NonNull InstructorsDataSource mInstructorsDataSource, @NonNull InstructorsContract.View mInstructorsView) {
        this.mInstructorsDataSource = mInstructorsDataSource;
        this.mInstructorsView = mInstructorsView;

        mInstructorsView.setPresenter(this);
    }

    @Override
    public void loadInstructors() {
        mInstructorsDataSource.getInstructors(new InstructorsDataSource.LoadInstructorsCallback() {
            @Override
            public void onInstructorsLoaded(List<Instructor> instructors) {
                mInstructorsView.showInstructors(instructors);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d("onDataNotAvailable", "error occured");
            }
        });
    }

    @Override
    public void start() {
        loadInstructors();
    }
}
