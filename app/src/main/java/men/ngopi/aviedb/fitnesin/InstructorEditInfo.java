package men.ngopi.aviedb.fitnesin;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public class InstructorEditInfo extends Activity implements View.OnClickListener {
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_edit_info);

        tv = findViewById(R.id.back);
        tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

}
