package men.ngopi.aviedb.fitnesin.member;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import men.ngopi.aviedb.fitnesin.MainActivity;
import men.ngopi.aviedb.fitnesin.R;
import men.ngopi.aviedb.fitnesin.data.Member;
import men.ngopi.aviedb.fitnesin.data.source.MembersDataSource;
import men.ngopi.aviedb.fitnesin.data.source.remote.MembersRemoteDataSource;

public class FitnesinFragment extends Fragment implements View.OnClickListener{
    private View rootView;
    private FloatingActionButton ex1MinButton, ex1PlusButton, ex2MinButton, ex2PlusButton;
    private TextView ex1Value, ex2Value, sport1Name, sport1Counter, sport1Value, sport2Name, sport2Counter, sport2Value;
    private TextView sport1Update, sport2Update;

    private String sport1NameString = "";
    private String sport2NameString = "";

    private int sport1MaxVal = 100;
    private int sport2MaxVal = 10;
    private int sport1CounterVal = 0;
    private int sport2CounterVal = 0;

    private ProgressBar progressBar1;
    private ProgressBar progressBar2;

    private MembersDataSource mMembersDataSource;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPrefEditor;
    private Member mMember;

    private final Sport[] sports = new Sport[]{
            new Sport("Swimming", "hours", "week", 5),
            new Sport("Biking", "meters", "day", 1000),
            new Sport("Jogging", "meters", "day", 500),
            new Sport("Push Up", "x", "day", 25),
            new Sport("Jump Rope", "x", "day", 100),
            new Sport("Running", "meters", "day", 1500)
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fitnesin_fragment, null);

        progressBar1 = rootView.findViewById(R.id.progressBar1);
        progressBar2 = rootView.findViewById((R.id.progressBar2));

        ex1MinButton = rootView.findViewById(R.id.ex1_min_button);
        ex1PlusButton = rootView.findViewById(R.id.ex1_plus_button);
        ex2MinButton = rootView.findViewById(R.id.ex2_min_button);
        ex2PlusButton = rootView.findViewById(R.id.ex2_plus_button);
        ex1Value = rootView.findViewById(R.id.ex1_value);
        ex2Value = rootView.findViewById(R.id.ex2_value);
        sport1Name = rootView.findViewById(R.id.tv_sport_1_name);
        sport1Counter = rootView.findViewById(R.id.tv_sport_1_counter);
        sport1Value = rootView.findViewById(R.id.tv_sport_1_val);
        sport2Name = rootView.findViewById(R.id.tv_sport_2_name);
        sport2Counter = rootView.findViewById(R.id.tv_sport_2_counter);
        sport2Value = rootView.findViewById(R.id.tv_sport_2_val);
        sport1Update= rootView.findViewById(R.id.tv_sport_1_update);
        sport2Update = rootView.findViewById(R.id.tv_sport_2_update);

        ex1MinButton.setOnClickListener(this);
        ex1PlusButton.setOnClickListener(this);
        ex2MinButton.setOnClickListener(this);
        ex2PlusButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences(
                MainActivity.SHARED_PREFERENCE,
                Context.MODE_PRIVATE
        );
        sharedPrefEditor = sharedPreferences.edit();
        mMembersDataSource = MembersRemoteDataSource.getInstance(
                sharedPreferences.getString(MainActivity.PREF_TOKEN_KEY, "")
        );

        sport1CounterVal = sharedPreferences.getInt(MainActivity.PREF_COUNTER_1, 0);
        sport2CounterVal = sharedPreferences.getInt(MainActivity.PREF_COUNTER_2, 0);

        updateCounterValue();
        loadMember();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(ex1MinButton)) {
            if (sport1CounterVal > 0) {
//                sport1CounterVal--;
                if (sport1NameString.equals("Running") || sport1NameString.equals("Biking"))
                    sport1CounterVal -= 50;
                else if (sport1NameString.equals("Jogging") || sport1NameString.equals("Jump Rope"))
                    sport1CounterVal -= 10;
                else
                    sport1CounterVal--;
            }
        } else if (v.equals(ex1PlusButton)) {
            if (sport1CounterVal < sport1MaxVal) {
//                sport1CounterVal++;
                if (sport1NameString.equals("Running") || sport1NameString.equals("Biking"))
                    sport1CounterVal += 50;
                else if (sport1NameString.equals("Jogging") || sport1NameString.equals("Jump Rope"))
                    sport1CounterVal += 10;
                else
                    sport1CounterVal++;
            }
        } else if (v.equals(ex2MinButton)) {
            if (sport2CounterVal > 0) {
//                sport2CounterVal--;
                if (sport2NameString.equals("Running") || sport2NameString.equals("Biking"))
                    sport2CounterVal -= 50;
                else if (sport2NameString.equals("Jogging") || sport2NameString.equals("Jump Rope"))
                    sport2CounterVal -= 10;
                else
                    sport2CounterVal--;
            }
        } else if (v.equals(ex2PlusButton)) {
            if (sport2CounterVal < sport2MaxVal) {
//                sport2CounterVal++;
                if (sport2NameString.equals("Running") || sport2NameString.equals("Biking"))
                    sport2CounterVal += 50;
                else if (sport2NameString.equals("Jogging") || sport2NameString.equals("Jump Rope"))
                    sport2CounterVal += 10;
                else
                    sport2CounterVal++;
            }
        }
        updateCounterValue();
    }

    private void updateCounterValue() {
        sport1Counter.setText(String.valueOf(sport1CounterVal));
        ex1Value.setText(String.valueOf(sport1CounterVal));
        sport1Counter.setText(String.valueOf(sport1CounterVal));
        ex1Value.setText(String.valueOf(sport1CounterVal));
        sport2Counter.setText(String.valueOf(sport2CounterVal));
        ex2Value.setText(String.valueOf(sport2CounterVal));

        progressBar1.setProgress(sport1CounterVal);
        progressBar2.setProgress(sport2CounterVal);

        if (sharedPrefEditor == null)
            sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(MainActivity.PREF_COUNTER_1, sport1CounterVal);
        sharedPrefEditor.putInt(MainActivity.PREF_COUNTER_2, sport2CounterVal);
        sharedPrefEditor.apply();
    }

    private void loadMember() {
        mMembersDataSource.getMe(new MembersDataSource.GetMemberCallback() {
            @Override
            public void onMemberLoaded(Member member) {
                mMember = member;
                showSuggestion();
            }

            @Override
            public void onDataNotAvailable() {
                showMessage("Unable to fetch Member");
            }
        });
    }

    private void showSuggestion() {
        // calculate IMT
        double imt = mMember.getWeight() / Math.pow(mMember.getHeight() / 100d, 2);
        Log.d("showSuggestion", "IMT: " + imt);


        if (imt < 18.5d) {
            showSportSuggestion(sports[0], sports[1]);
        } else if (imt < 25d) {
            showSportSuggestion(sports[2], sports[3]);
        } else if (imt < 30d) {
            showSportSuggestion(sports[0], sports[2]);
        } else if (imt < 40d) {
            showSportSuggestion(sports[4], sports[5]);
        } else {
            showSportSuggestion(sports[0], sports[4]);
        }
    }

    private void showSportSuggestion(Sport sport1, Sport sport2) {
        String sport1NameFormatted = String.format("%s (%s/%s)", sport1.getName(), sport1.getUnit(), sport1.getTimeUnit());
        String sport2NameFormatted = String.format("%s (%s/%s)", sport2.getName(), sport2.getUnit(), sport2.getTimeUnit());
        sport1Name.setText(sport1NameFormatted);
        sport1Value.setText(String.valueOf(sport1.getValue()));
        sport2Name.setText(sport2NameFormatted);
        sport2Value.setText(String.valueOf(sport2.getValue()));
        sport1Update.setText(sport1NameFormatted);
        sport2Update.setText(sport2NameFormatted);

        sport1NameString = sport1.getName();
        sport2NameString = sport2.getName();

        sport1MaxVal = sport1.getValue();
        sport2MaxVal = sport2.getValue();

        progressBar1.setMax(sport1MaxVal);
        progressBar2.setMax(sport2MaxVal);
    }

    private void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    private class Sport {
        private String name;
        private String unit;
        private String timeUnit;
        private int value;

        public Sport(String name, String unit, String timeUnit, int value) {
            this.name = name;
            this.unit = unit;
            this.timeUnit = timeUnit;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getUnit() {
            return unit;
        }

        public String getTimeUnit() {
            return timeUnit;
        }

        public int getValue() {
            return value;
        }
    }
}
