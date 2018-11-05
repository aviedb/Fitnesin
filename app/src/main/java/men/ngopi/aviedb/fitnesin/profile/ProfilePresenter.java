package men.ngopi.aviedb.fitnesin.profile;

import java.util.Calendar;

import men.ngopi.aviedb.fitnesin.data.Gender;
import men.ngopi.aviedb.fitnesin.data.Member;
import men.ngopi.aviedb.fitnesin.data.source.MembersDataSource;

public class ProfilePresenter implements ProfileContract.Presenter {

    private final MembersDataSource mMembersDataSource;

    private final ProfileContract.View mProfileView;

    public ProfilePresenter(MembersDataSource membersDataSource, ProfileContract.View mProfileView) {
        this.mMembersDataSource = membersDataSource;
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

    // from remote
    @Override
    public void loadProfile(String token) {

        this.mMembersDataSource.getMe(token, new MembersDataSource.GetMemberCallback() {
            @Override
            public void onMemberLoaded(Member member) {
                mProfileView.showProfile(member);
            }

            @Override
            public void onDataNotAvailable() {
                loadProfile();
            }
        });

    }
}
