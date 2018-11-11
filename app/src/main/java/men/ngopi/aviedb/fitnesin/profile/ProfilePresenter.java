package men.ngopi.aviedb.fitnesin.profile;

import java.util.Calendar;

import men.ngopi.aviedb.fitnesin.data.Gender;
import men.ngopi.aviedb.fitnesin.data.Member;
import men.ngopi.aviedb.fitnesin.data.source.MembersDataSource;

public class ProfilePresenter implements ProfileContract.Presenter {

    private final MembersDataSource mMembersDataSource;

    private final ProfileContract.View mProfileView;

    private Member mMember;

    public ProfilePresenter(MembersDataSource membersDataSource, ProfileContract.View mProfileView) {
        this.mMembersDataSource = membersDataSource;
        this.mProfileView = mProfileView;

        this.mProfileView.setPresenter(this);
    }

    @Override
    public void loadProfile() {
        this.mMembersDataSource.getMe(new MembersDataSource.GetMemberCallback() {
            @Override
            public void onMemberLoaded(Member member) {
                mMember = member;
                mProfileView.showProfile(member);
            }

            @Override
            public void onDataNotAvailable() {
                mProfileView.showMessage("Unable to fetch data from server");
            }
        });
    }

    @Override
    public void start() {
        this.loadProfile();
    }

    @Override
    public void saveProfile(Gender gender, Calendar birthdate, double weight, double height) {
        Member updatedMember = new Member(
                mMember.getName(),
                mMember.getPhone(),
                birthdate,
                weight,
                height,
                gender
        );

        this.mMembersDataSource.saveMe(updatedMember, new MembersDataSource.GetMemberCallback() {
            @Override
            public void onMemberLoaded(Member member) {
                mMember = member;
                mProfileView.showProfile(mMember);
                mProfileView.showMessage("Profile Saved");
            }

            @Override
            public void onDataNotAvailable() {
                mProfileView.showMessage("Unable to update member");
            }
        });

    }
}
