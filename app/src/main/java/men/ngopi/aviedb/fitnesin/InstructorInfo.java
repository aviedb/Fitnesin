package men.ngopi.aviedb.fitnesin;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public class InstructorInfo extends Activity implements View.OnClickListener {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_info);

        tv = findViewById(R.id.back);
        tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(InstructorInfo.this, MainActivity.class);
        startActivity(i);
    }
}
