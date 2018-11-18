package men.ngopi.aviedb.fitnesin.member;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import men.ngopi.aviedb.fitnesin.R;

public class FitnesinFragment extends Fragment implements View.OnClickListener{
    private View rootView;
    FloatingActionButton ex1MinButton, ex1PlusButton, ex2MinButton, ex2PlusButton;
    TextView ex1Value, ex2Value;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fitnesin_fragment, null);

        ex1MinButton = rootView.findViewById(R.id.ex1_min_button);
        ex1PlusButton = rootView.findViewById(R.id.ex1_plus_button);
        ex2MinButton = rootView.findViewById(R.id.ex2_min_button);
        ex2PlusButton = rootView.findViewById(R.id.ex2_plus_button);
        ex1Value = rootView.findViewById(R.id.ex1_value);
        ex2Value = rootView.findViewById(R.id.ex2_value);

        ex1MinButton.setOnClickListener(this);
        ex1PlusButton.setOnClickListener(this);
        ex2MinButton.setOnClickListener(this);
        ex2PlusButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.equals(ex1MinButton)) {
            Toast.makeText(getContext(), ex1Value.getText().toString(), Toast.LENGTH_LONG).show();
        } else if (v.equals(ex1PlusButton)) {
            Toast.makeText(getContext(), ex1Value.getText().toString(), Toast.LENGTH_LONG).show();
        } else if (v.equals(ex2MinButton)) {
            Toast.makeText(getContext(), ex2Value.getText().toString(), Toast.LENGTH_LONG).show();
        } else if (v.equals(ex2PlusButton)) {
            Toast.makeText(getContext(), ex2Value.getText().toString(), Toast.LENGTH_LONG).show();
        }
    }
}
