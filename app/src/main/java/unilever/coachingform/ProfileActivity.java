package unilever.coachingform;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner coach_job, coachee_job, first_job,second_job, cd_job;
    EditText coach_name, coachee_name, first_name,second_name, cd_name;
    EditText coach_email, coachee_email, first_email,second_email, cd_email;
    Button next;
    int login_job;
    ArrayAdapter<CharSequence> job_adapter;
    String coach_position, coachee_position, first_position, second_position, cd_position = "";
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.next) {
                Intent intent = new Intent(ProfileActivity.this, CoachingOption.class);
                intent.putExtra("coach", coach_email.getText().toString());
                intent.putExtra("coachee", coachee_email.getText().toString());
                intent.putExtra("job", login_job);
                startActivity(intent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        coach_job = (Spinner) findViewById(R.id.coach_position);
        coachee_job = (Spinner) findViewById(R.id.coachee_position);
        first_job = (Spinner) findViewById(R.id.firstlinemanager_position);
        second_job = (Spinner) findViewById(R.id.secondlinemanager_position);
        cd_job = (Spinner) findViewById(R.id.cdteam_position);
        coach_name = (EditText) findViewById(R.id.coach_name);
        coachee_name = (EditText) findViewById(R.id.coachee_name);
        first_name = (EditText) findViewById(R.id.firstlm_name);
        second_name = (EditText) findViewById(R.id.secondlm_name);
        cd_name = (EditText) findViewById(R.id.cdteam_name);
        coach_email = (EditText) findViewById(R.id.coach_email);
        coachee_email = (EditText) findViewById(R.id.coachee_email);
        first_email = (EditText) findViewById(R.id.firstlinemanager_email);
        second_email = (EditText) findViewById(R.id.secondlinemanager_email);
        cd_email = (EditText) findViewById(R.id.cdteam_email);
        next = (Button) findViewById(R.id.next);
        job_adapter = ArrayAdapter.createFromResource(this, R.array.job_title, android.R.layout.simple_spinner_item);
        coach_job.setAdapter(job_adapter);
        coachee_job.setAdapter(job_adapter);
        first_job.setAdapter(job_adapter);
        second_job.setAdapter(job_adapter);
        cd_job.setAdapter(job_adapter);
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            if(bundle.getString("email") != null) {
                coach_email.setText(bundle.getString("email"));
            }
            if(bundle.getString("job") != null) {
                login_job = bundle.getInt("job");
                coach_job.setSelection(bundle.getInt("job"));
            }
        }
        coach_job.setOnItemSelectedListener(this);
        coachee_job.setOnItemSelectedListener(this);
        first_job.setOnItemSelectedListener(this);
        second_job.setOnItemSelectedListener(this);
        cd_job.setOnItemSelectedListener(this);
        next.setOnClickListener(onClick);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(view.getId() == R.id.coach_position) {
            coach_position = parent.getSelectedItem().toString();
        } else if (view.getId() == R.id.coachee_position) {
            coachee_position = parent.getSelectedItem().toString();
        } else if (view.getId() == R.id.firstlinemanager_position) {
            first_position = parent.getSelectedItem().toString();
        } else if (view.getId() == R.id.secondlinemanager_position) {
            second_position = parent.getSelectedItem().toString();
        } else if (view.getId() == R.id.cdteam_position) {
            cd_position = parent.getSelectedItem().toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
