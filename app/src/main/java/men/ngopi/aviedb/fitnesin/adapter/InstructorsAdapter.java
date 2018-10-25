package men.ngopi.aviedb.fitnesin.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import men.ngopi.aviedb.fitnesin.InstructorInfo;
import men.ngopi.aviedb.fitnesin.R; // Needed to import R. resource
import men.ngopi.aviedb.fitnesin.model.Instructor;

public class InstructorsAdapter extends RecyclerView.Adapter<InstructorsAdapter.InstructorViewHolder> {

    private ArrayList<Instructor> instructors;

    public InstructorsAdapter(ArrayList<Instructor> instructors) {
        this.instructors = instructors;
    }

    @NonNull
    @Override
    public InstructorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // card view
        View instructorItem = inflater.inflate(R.layout.instructor_item_card, parent, false);
        InstructorViewHolder viewHolder = new InstructorViewHolder(instructorItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InstructorViewHolder instructorViewHolder, int i) {
        Instructor instructor = instructors.get(i);

        instructorViewHolder.mNameTextView.setText(instructor.getName());
        instructorViewHolder.mGenderTextView.setText(instructor.getGender().toString());
        instructorViewHolder.mCityTextView.setText(instructor.getCity());

    }

    @Override
    public int getItemCount() {
        return this.instructors.size();
    }

    public static class InstructorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final Context context;

        public TextView mNameTextView;
        public TextView mGenderTextView;
        public TextView mCityTextView;

        public InstructorViewHolder(@NonNull View itemView) {
            super(itemView);

            this.context = itemView.getContext();

            mNameTextView = itemView.findViewById(R.id.tv_instructor_name);
            mGenderTextView = itemView.findViewById(R.id.tv_instructor_gender);
            mCityTextView = itemView.findViewById(R.id.tv_instructor_city);

            // set click listener to current item card
            itemView.findViewById(R.id.cv_instructor).setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("onClick", "Clicking");
            Intent i = new Intent(this.context, InstructorInfo.class);
            this.context.startActivity(i);
        }
    }
}
