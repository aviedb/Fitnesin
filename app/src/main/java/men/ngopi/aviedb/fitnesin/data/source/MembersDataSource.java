package men.ngopi.aviedb.fitnesin.data.source;

import android.support.annotation.NonNull;

import java.util.List;

import men.ngopi.aviedb.fitnesin.data.Member;

public interface MembersDataSource {


    interface GetMemberCallback {

        void onMemberLoaded(Member member);

        void onDataNotAvailable();

    }

    void getMe(@NonNull GetMemberCallback callback);

    void saveMe(@NonNull Member member, @NonNull GetMemberCallback callback);

}
