package men.ngopi.aviedb.fitnesin.instructors;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import men.ngopi.aviedb.fitnesin.InstructorInfo;
import men.ngopi.aviedb.fitnesin.data.Instructor;
import men.ngopi.aviedb.fitnesin.R; // Needed to import R. resource

public class InstructorsFragment extends Fragment implements View.OnClickListener, InstructorsContract.View {
    private InstructorsContract.Presenter mPresenter;

    private RecyclerView mInstructorsRecylerView;
    private RecyclerView.Adapter mInstructorsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Instructor> instructors = new ArrayList<>();

    public InstructorsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.find_instructor_fragment, container, false);


        mInstructorsRecylerView = rootView.findViewById(R.id.rcv_instructors);
        mInstructorsRecylerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mInstructorsRecylerView.setLayoutManager(mLayoutManager);

        // init adapter
        this.mInstructorsAdapter = new InstructorsAdapter(instructors);
        mInstructorsRecylerView.setAdapter(this.mInstructorsAdapter);


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getContext(), InstructorInfo.class);
        startActivity(i);
    }

    @Override
    public void showInstructors(List<Instructor> instructors) {
        Log.d("showInstructors", "inserting instructors -> " + instructors.size());
        this.instructors.clear();
        this.instructors.addAll(instructors);
        this.mInstructorsAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(InstructorsContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
