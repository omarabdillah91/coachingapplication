package unilever.coachingform;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import dao.CoachingSessionDAO;

public class ProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner coach_job, coachee_job, first_job,second_job, cd_job;
    EditText coach_name, coachee_name, first_name,second_name, cd_name;
    EditText coach_email, coachee_email, first_email,second_email, cd_email;
    Button next;
    String job = "";
    int job_id = -1;
    ArrayAdapter<CharSequence> job_adapter;
    String coach_position, coachee_position, first_position, second_position, cd_position = "";
    String coachingSessionID = "";
    final String TAG = "Profile";
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.next) {
                CoachingSessionDAO.insertNewCoaching(coachee_name.getText().toString(), coachee_email.getText().toString(), coachee_position,
                        first_name.getText().toString(), first_email.getText().toString(), first_position,
                        second_name.getText().toString(), second_email.getText().toString(), second_position,
                        cd_name.getText().toString(), cd_email.getText().toString(), cd_position,
                        new CoachingSessionDAO.InsertCoachingListener() {
                            @Override
                            public void onInsertCoachingCompleted(String id) {
                                coachingSessionID = id;
                                Intent intent = new Intent(ProfileActivity.this, CoachingOption.class);
                                Bundle profile = getBundle();
                                intent.putExtra("coach", coach_email.getText().toString());
                                intent.putExtra("coachee", coachee_email.getText().toString());
                                intent.putExtra("job", job);
                                intent.putExtra("id", coachingSessionID);
                                intent.putExtra("profile", profile);
                                startActivity(intent);
                                Log.d(TAG, " " + id);
                            }
                        });
            }
        }
    };

    private Bundle getBundle() {
        Bundle outState = new Bundle();
        outState.putString("coach_name", coach_name.getText().toString());
        outState.putString("coachee_name", coachee_name.getText().toString());
        outState.putString("first_name", first_name.getText().toString());
        outState.putString("second_name", second_name.getText().toString());
        outState.putString("cd_name", cd_name.getText().toString());
        outState.putString("coach_email", coach_email.getText().toString());
        outState.putString("coachee_email", coachee_email.getText().toString());
        outState.putString("first_email", first_email.getText().toString());
        outState.putString("second_email", second_email.getText().toString());
        outState.putString("cd_email", cd_email.getText().toString());
        outState.putString("coach_name", coach_name.getText().toString());
        outState.putString("coach_position", coach_position);
        outState.putString("coachee_position", coachee_position);
        outState.putString("first_position", first_position);
        outState.putString("second_position", second_position);
        outState.putString("cd_position", cd_position);
        return outState;
    }

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
            if(bundle.getInt("job") != -1) {
                job_id = bundle.getInt("job");
                //coach_job.setS;
            }
            if(bundle.getBundle("profile")!= null) {
                setField(bundle.getBundle("profile"));
            }
        }
        coach_job.setSelection(job_id);
        coach_position = coach_job.getSelectedItem().toString();
        coach_job.setOnItemSelectedListener(this);
        coachee_job.setOnItemSelectedListener(this);
        first_job.setOnItemSelectedListener(this);
        second_job.setOnItemSelectedListener(this);
        cd_job.setOnItemSelectedListener(this);
        next.setOnClickListener(onClick);
    }

    private void setField(Bundle savedState) {
        coach_name.setText(savedState.getString("coach_name"));
        coachee_name.setText(savedState.getString("coachee_name"));
        first_name.setText(savedState.getString("first_name"));
        second_name.setText(savedState.getString("second_name"));
        cd_name.setText(savedState.getString("cd_name"));
        coach_email.setText(savedState.getString("coach_email"));
        coachee_email.setText(savedState.getString("coachee_email"));
        first_email.setText(savedState.getString("first_email"));
        second_email.setText(savedState.getString("second_email"));
        cd_email.setText(savedState.getString("cd_email"));
        coach_job.setSelection(job_adapter.getPosition(savedState.getString("coach_position")));
        coachee_job.setSelection(job_adapter.getPosition(savedState.getString("coachee_position")));
        first_job.setSelection(job_adapter.getPosition(savedState.getString("first_position")));
        second_job.setSelection(job_adapter.getPosition(savedState.getString("second_position")));
        cd_job.setSelection(job_adapter.getPosition(savedState.getString("cd_position")));
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

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("coach_name", coach_name.getText().toString());
        outState.putString("coachee_name", coachee_name.getText().toString());
        outState.putString("first_name", first_name.getText().toString());
        outState.putString("second_name", second_name.getText().toString());
        outState.putString("cdname", cd_name.getText().toString());
        outState.putString("coach_email", coach_email.getText().toString());
        outState.putString("coachee_email", coachee_email.getText().toString());
        outState.putString("first_email", first_email.getText().toString());
        outState.putString("second_email", second_email.getText().toString());
        outState.putString("cd_email", cd_email.getText().toString());
        outState.putString("coach_name", coach_name.getText().toString());
        outState.putString("coach_position", coach_position);
        outState.putString("coachee_position", coachee_position);
        outState.putString("first_position", first_position);
        outState.putString("second_position", second_position);
        outState.putString("cd_position", cd_position);
    }

    protected void onRestoreInstanceState(Bundle savedState) {
        coach_name.setText(savedState.getString("coach_name"));
        coachee_name.setText(savedState.getString("coachee_name"));
        first_name.setText(savedState.getString("first_name"));
        second_name.setText(savedState.getString("second_name"));
        cd_name.setText(savedState.getString("cd_name"));
        coach_email.setText(savedState.getString("coach_email"));
        coachee_email.setText(savedState.getString("coachee_email"));
        first_email.setText(savedState.getString("first_email"));
        second_email.setText(savedState.getString("second_email"));
        cd_email.setText(savedState.getString("cd_email"));
        coach_job.setSelection(job_adapter.getPosition(savedState.getString("coach_position")));
        coachee_job.setSelection(job_adapter.getPosition(savedState.getString("coachee_position")));
        first_job.setSelection(job_adapter.getPosition(savedState.getString("first_position")));
        second_job.setSelection(job_adapter.getPosition(savedState.getString("second_position")));
        cd_job.setSelection(job_adapter.getPosition(savedState.getString("cd_position")));
    }
}
