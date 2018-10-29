package men.ngopi.aviedb.fitnesin.profile;

import java.util.Calendar;

import men.ngopi.aviedb.fitnesin.data.Gender;
import men.ngopi.aviedb.fitnesin.data.Member;

public class ProfilePresenter implements ProfileContract.Presenter {

    private final ProfileContract.View mProfileView;

    public ProfilePresenter(ProfileContract.View mProfileView) {
        this.mProfileView = mProfileView;

        this.mProfileView.setPresenter(this);
    }

    @Override
    public void loadProfile() {

        Calendar birthdate = Calendar.getInstance();

        // NOTE: Month start from 0 (January)
        birthdate.set(2000, 7, 6);
        this.mProfileView.showProfile(new Member("Muhammad Avied Bachmid", "+62 822 9221 2073", birthdate, 60, 160, Gender.MALE));
    }

    @Override
    public void start() {
        this.loadProfile();
    }
}
