package men.ngopi.aviedb.fitnesin.profile;

import java.util.Calendar;

import men.ngopi.aviedb.fitnesin.BasePresenter;
import men.ngopi.aviedb.fitnesin.BaseView;
import men.ngopi.aviedb.fitnesin.data.Gender;
import men.ngopi.aviedb.fitnesin.data.Member;

public interface ProfileContract {

    interface View extends BaseView<Presenter> {

        void showProfile(Member member);

        void showMessage(String message);

    }

    interface Presenter extends BasePresenter {

        void loadProfile();

        void loadProfile(String token);

        void saveProfile(Gender gender, Calendar birthdate, double height, double weight);

    }

}
