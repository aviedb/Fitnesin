package men.ngopi.aviedb.fitnesin;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.button.MaterialButton;
import android.view.View;

public class InstructorMainActivity extends Activity implements View.OnClickListener {
    private MaterialButton editInfoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_main);

        editInfoBtn = findViewById(R.id.edit_info);
        editInfoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, InstructorEditInfo.class);
        startActivity(i);
    }
}
