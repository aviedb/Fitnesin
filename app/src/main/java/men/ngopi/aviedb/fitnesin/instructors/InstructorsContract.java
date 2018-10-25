package men.ngopi.aviedb.fitnesin.instructors;

import java.util.List;

import men.ngopi.aviedb.fitnesin.BasePresenter;
import men.ngopi.aviedb.fitnesin.BaseView;
import men.ngopi.aviedb.fitnesin.data.Instructor;

public interface InstructorsContract {

    interface View extends BaseView<Presenter> {

        void showInstructors(List<Instructor> instructors);

    }

    interface Presenter extends BasePresenter {

        void loadInstructors();

    }
}
