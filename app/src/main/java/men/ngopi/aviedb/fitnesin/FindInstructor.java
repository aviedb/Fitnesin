package men.ngopi.aviedb.fitnesin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import men.ngopi.aviedb.fitnesin.adapter.InstructorsAdapter;
import men.ngopi.aviedb.fitnesin.model.Gender;
import men.ngopi.aviedb.fitnesin.model.Instructor;

public class FindInstructor extends Fragment implements View.OnClickListener {
    CardView cv;


    private RecyclerView mInstructorsRecylerView;
    private RecyclerView.Adapter mInstructorsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.find_instructor_fragment, container, false);

//        cv = (CardView) rootView.findViewById(R.id.instructor1);
//        cv.setOnClickListener(this);

        mInstructorsRecylerView = rootView.findViewById(R.id.rcv_instructors);
        mInstructorsRecylerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mInstructorsRecylerView.setLayoutManager(mLayoutManager);

        // init adapter

        ArrayList<Instructor> instructors = new ArrayList<>();
        instructors.add(new Instructor("Elon Musk", Gender.MALE, "Malang"));
        instructors.add(new Instructor("Tony Stark", Gender.MALE, "Malang"));
        instructors.add(new Instructor("Via Vallen", Gender.FEMALE, "Malang"));
        mInstructorsAdapter = new InstructorsAdapter(instructors);
        mInstructorsRecylerView.setAdapter(mInstructorsAdapter);


        return rootView;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getContext(), InstructorInfo.class);
        startActivity(i);
    }
}
