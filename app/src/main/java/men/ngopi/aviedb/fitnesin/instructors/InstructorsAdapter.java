package men.ngopi.aviedb.fitnesin.instructors;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import men.ngopi.aviedb.fitnesin.InstructorInfo;
import men.ngopi.aviedb.fitnesin.R; // Needed to import R. resource
import men.ngopi.aviedb.fitnesin.data.Instructor;

public class InstructorsAdapter extends RecyclerView.Adapter<InstructorsAdapter.InstructorViewHolder> {

    private ArrayList<Instructor> instructors;

    public InstructorsAdapter() {
        this.instructors = new ArrayList<>();
    }

    public InstructorsAdapter(ArrayList<Instructor> instructors) {
        this.instructors = instructors;
    }

    public void addInstructors(ArrayList<Instructor> instructors) {
        this.instructors.addAll(instructors);
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
        instructorViewHolder.mGenderTextView.setText(instructor.getGender().toString().equals("MALE")? "Male":"Female");
        instructorViewHolder.mCityTextView.setText(instructor.getCity());
        instructorViewHolder.mProfilePicture.setImageResource(instructor.getPhoto());
        instructorViewHolder.ppInt = instructor.getPhoto();
        instructorViewHolder.mBirthdate = instructor.getBirthdate();
        instructorViewHolder.phoneString = instructor.getPhone();

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
        public CircleImageView mProfilePicture;
        public String phoneString;
        public Calendar mBirthdate;
        public int ppInt;

        public InstructorViewHolder(@NonNull View itemView) {
            super(itemView);

            this.context = itemView.getContext();

            mNameTextView = itemView.findViewById(R.id.tv_instructor_name);
            mGenderTextView = itemView.findViewById(R.id.tv_instructor_gender);
            mCityTextView = itemView.findViewById(R.id.tv_instructor_city);
            mProfilePicture = itemView.findViewById(R.id.profile_picture);

            // set click listener to current item card
            itemView.findViewById(R.id.cv_instructor).setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("onClick", "Clicking");

            Intent i = new Intent(this.context, InstructorInfo.class);
            i.putExtra("INSTRUCTOR_NAME", mNameTextView.getText());
            i.putExtra("GENDER", mGenderTextView.getText());
            i.putExtra("CITY", mCityTextView.getText());
            i.putExtra("PROFILE_PICTURE", ppInt);
            i.putExtra("PHONE_NUMBER", phoneString);
            i.putExtra("BIRTHDATE", mBirthdate);

            this.context.startActivity(i);
        }
    }
}
