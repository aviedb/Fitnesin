package men.ngopi.aviedb.fitnesin.member.listInstructors;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import men.ngopi.aviedb.fitnesin.data.Instructor;
import men.ngopi.aviedb.fitnesin.R; // Needed to import R. resource
import men.ngopi.aviedb.fitnesin.data.source.InstructorsDataSource;
import men.ngopi.aviedb.fitnesin.data.source.remote.InstructorsRemoteDataSource;

public class InstructorsFragment extends Fragment {
    private InstructorsDataSource mInstructorsDataSource;

    private RecyclerView mInstructorsRecylerView;
    private RecyclerView.Adapter mInstructorsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private TextInputEditText search;
    private String searchQuery = "";

    private ArrayList<Instructor> instructors = new ArrayList<>();

    public InstructorsFragment() { }

    @SuppressLint("ClickableViewAccessibility")
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

        // Search Button event listener
        search = rootView.findViewById(R.id.search);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    search();
                }
                return false;
            }
        });
        search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (search.getRight() - search.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        search();

                        return true;
                    }
                }
                return false;
            }
        });

        return rootView;
    }

    public void search() {
        searchQuery = search.getText().toString();
        loadInstructors();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadInstructors();
    }

    public void showInstructors(List<Instructor> instructors) {
        this.instructors.clear();

//        this.instructors.addAll(instructors);
        if (searchQuery.length() > 0) {
            List<Instructor> filteredInstructors = new ArrayList<>();
            for (Instructor temp : instructors) {
                if (temp.getName().toLowerCase().contains(searchQuery.toLowerCase()))
                    filteredInstructors.add(temp);
            }
            this.instructors.addAll(filteredInstructors);
        } else {
            this.instructors.addAll(instructors);
        }

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
