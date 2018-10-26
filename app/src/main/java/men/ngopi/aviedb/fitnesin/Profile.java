package men.ngopi.aviedb.fitnesin;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Profile extends Fragment implements View.OnClickListener {
    MaterialButton logoutBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_fragment, container, false);

        Spinner genderSpinner = rootView.findViewById(R.id.genderSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.gender, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        logoutBtn = rootView.findViewById(R.id.logout);
        logoutBtn.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
    }
}
