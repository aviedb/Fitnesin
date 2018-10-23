package men.ngopi.aviedb.fitnesin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FindInstructor extends Fragment implements View.OnClickListener {
    CardView cv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.find_instructor_fragment, container, false);

        cv = (CardView) rootView.findViewById(R.id.instructor1);
        cv.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getContext(), InstructorInfo.class);
        startActivity(i);
    }
}
