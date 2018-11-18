package men.ngopi.aviedb.fitnesin.member.listInstructors;

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

import men.ngopi.aviedb.fitnesin.data.Instructor;
import men.ngopi.aviedb.fitnesin.R; // Needed to import R. resource
import men.ngopi.aviedb.fitnesin.data.source.InstructorsDataSource;
import men.ngopi.aviedb.fitnesin.data.source.remote.InstructorsRemoteDataSource;

public class InstructorsFragment extends Fragment implements View.OnClickListener {
    private InstructorsDataSource mInstructorsDataSource;

    private RecyclerView mInstructorsRecylerView;
    private RecyclerView.Adapter mInstructorsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Instructor> instructors = new ArrayList<>();

    public InstructorsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.find_instructor_fragment, container, false);

        // init instructors data source
        // no need to use token as getInstructors need no token
        mInstructorsDataSource = InstructorsRemoteDataSource.getInstance("");

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
        loadInstructors();
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getContext(), InstructorInfoActivity.class);
        startActivity(i);
    }

    public void showInstructors(List<Instructor> instructors) {
        this.instructors.clear();
        this.instructors.addAll(instructors);
        this.mInstructorsAdapter.notifyDataSetChanged();
    }

    private void loadInstructors() {
        mInstructorsDataSource.getInstructors(new InstructorsDataSource.LoadInstructorsCallback() {
            @Override
            public void onInstructorsLoaded(List<Instructor> instructors) {
                showInstructors(instructors);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d("onDataNotAvailable", "error occured");
            }
        });
    }
}
