package men.ngopi.aviedb.fitnesin.profile;

import men.ngopi.aviedb.fitnesin.BasePresenter;
import men.ngopi.aviedb.fitnesin.BaseView;
import men.ngopi.aviedb.fitnesin.data.Member;

public interface ProfileContract {

    interface View extends BaseView<Presenter> {

        void showProfile(Member member);

    }

    interface Presenter extends BasePresenter {

        void loadProfile();

    }

}
