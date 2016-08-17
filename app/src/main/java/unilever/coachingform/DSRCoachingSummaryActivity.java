package unilever.coachingform;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DSRCoachingSummaryActivity extends AppCompatActivity {
    Button next, back;
    EditText coach, coachee, area, distributor, summary_1, summary_2, summary_3;
    boolean bahasa = false;
    boolean english = false;
    String job, coach_email, coachee_email, text_area, text_distributor = "";
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.next) {
                Intent intent = new Intent(DSRCoachingSummaryActivity.this, ProfileActivity.class);
                intent.putExtra("coach", coach.getText().toString());
                intent.putExtra("job", job);
                intent.putExtra("coachee", coachee.getText().toString());
                intent.putExtra("bahasa",bahasa);
                intent.putExtra("english", english);
                intent.putExtra("area", area.getText());
                intent.putExtra("distributor", distributor.getText());
                startActivity(intent);
            } else if (v.getId() == R.id.back) {
                Intent intent = new Intent(DSRCoachingSummaryActivity.this, DSRSetelahActivity.class);
                intent.putExtra("coach", coach.getText().toString());
                intent.putExtra("job", job);
                intent.putExtra("coachee", coachee.getText().toString());
                intent.putExtra("bahasa",bahasa);
                intent.putExtra("english", english);
                intent.putExtra("area", area.getText());
                intent.putExtra("distributor", distributor.getText());
                startActivity(intent);
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtra(getIntent().getExtras());
        if(english) {
            setContentView(R.layout.activity_dsr_coaching_summary_english);
        } else {
            setContentView(R.layout.activity_dsr_coaching_summary_bahasa);
        }
        next = (Button) findViewById(R.id.next);
        back = (Button) findViewById(R.id.back);
        coach = (EditText) findViewById(R.id.coach);
        coachee = (EditText) findViewById(R.id.coachee);
        area = (EditText) findViewById(R.id.area);
        distributor = (EditText) findViewById(R.id.distributor);
        coach.setText(coach_email);
        coach.setEnabled(false);
        coachee.setText(coachee_email);
        coachee.setEnabled(false);
        area.setText(text_area);
        area.setEnabled(false);
        distributor.setText(text_distributor);
        distributor.setEnabled(false);
        next.setOnClickListener(onClick);
        back.setOnClickListener(onClick);
    }

    private void getExtra(Bundle bundle) {
        if (bundle != null) {
            if(bundle.getBoolean("english")) {
                english = bundle.getBoolean("english");
            }else {
                bahasa = bundle.getBoolean("bahasa");
            }
            if(bundle.getString("coach") != null) {
                coach_email = bundle.getString("coach");
            }
            if(bundle.getString("coachee") != null) {
                coachee_email = bundle.getString("coachee");
            }
            if(bundle.getString("job") != null) {
                job = bundle.getString("job");
            }
            if(bundle.getString("area") != null) {
                text_area = bundle.getString("area");
            }
            if(bundle.getString("distributor") != null) {
                text_distributor = bundle.getString("distributor");
            }
        }
    }

}
