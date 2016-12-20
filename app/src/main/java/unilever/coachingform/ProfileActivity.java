package unilever.coachingform;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import dao.CoachingSessionDAO;
import utility.ConstantUtil;
import utility.SharedPreferenceUtil;

public class ProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner coach_job, coachee_job, first_job, cd_job;
    EditText coach_email, coachee_email, first_email, cd_email;
    Button next;
    String job = "";
    int job_id = -1;
    ArrayAdapter<CharSequence> job_adapter, cdteam_adapter;
    String coach_position, coachee_position, first_position, cd_position = "";
    String coachingSessionID = "";
    final String TAG = "Profile";
    private FirebaseAuth.AuthStateListener mAuthListener;
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.next) {
                if(validate()) {
                    Date date = new Date(System.currentTimeMillis());
                    DateFormat format = new SimpleDateFormat("yyyy MMM dd");
                    format.setTimeZone(TimeZone.getTimeZone("Indonesia"));
                    String date_formatted = format.format(date);
                    SharedPreferenceUtil.putString(ConstantUtil.SP_DATE,date_formatted);
                    SharedPreferenceUtil.putString(ConstantUtil.SP_COACH_NAME, coach_email.getText().toString());
                    CoachingSessionDAO.insertNewCoaching(coachee_email.getText().toString(), coachee_email.getText().toString(), coachee_position,
                            first_email.getText().toString(), first_email.getText().toString(), first_position,
                            cd_email.getText().toString(), cd_email.getText().toString(), cd_position,
                            new CoachingSessionDAO.InsertCoachingListener() {
                                @Override
                                public void onInsertCoachingCompleted(String id) {
                                    coachingSessionID = id;
                                    SharedPreferenceUtil.putString(ConstantUtil.SP_COACH_EMAIL, coach_email.getText().toString());
                                    SharedPreferenceUtil.putString(ConstantUtil.SP_COACH_ID, id);
                                    SharedPreferenceUtil.putString(ConstantUtil.SP_COACH_POSITION, job);
                                    SharedPreferenceUtil.putString(ConstantUtil.SP_POSITION_ID, job_id+"");
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
                } else {
                    Toast.makeText(ProfileActivity.this, "Email of every roles should not be empty!!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private boolean validate() {
        if(!coach_email.getText().toString().equalsIgnoreCase("") && !coachee_email.getText().toString().equalsIgnoreCase("")  &&
                !cd_email.getText().toString().equalsIgnoreCase("") ) {
            return true;
        }
        return false;
    }

    private Bundle getBundle() {
        Bundle outState = new Bundle();
        outState.putString("coach_email", coach_email.getText().toString());
        outState.putString("coachee_email", coachee_email.getText().toString());
        outState.putString("first_email", first_email.getText().toString());
        outState.putString("cd_email", cd_email.getText().toString());
        outState.putString("coach_position", coach_position);
        outState.putString("coachee_position", coachee_position);
        outState.putString("first_position", first_position);
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
        cd_job = (Spinner) findViewById(R.id.cdteam_position);
        coach_email = (EditText) findViewById(R.id.coach_email);
        coachee_email = (EditText) findViewById(R.id.coachee_email);
        first_email = (EditText) findViewById(R.id.firstlinemanager_email);
        cd_email = (EditText) findViewById(R.id.cdteam_email);
        next = (Button) findViewById(R.id.next);
        job_adapter = ArrayAdapter.createFromResource(this, R.array.job_title, android.R.layout.simple_spinner_item);
        cdteam_adapter = ArrayAdapter.createFromResource(this, R.array.cdteam_title, android.R.layout.simple_spinner_item);
        coach_job.setAdapter(job_adapter);
        coachee_job.setAdapter(job_adapter);
        first_job.setAdapter(job_adapter);
        cd_job.setAdapter(cdteam_adapter);
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            if(bundle.getString("email") != null) {
                coach_email.setText(bundle.getString("email"));
            }
            if(bundle.getInt("job") != -1) {
                job_id = bundle.getInt("job");
            }
            if(bundle.getBundle("profile")!= null) {
                setField(bundle.getBundle("profile"));
            }
        }
        if (!SharedPreferenceUtil.getString(ConstantUtil.SP_COACH_EMAIL).equalsIgnoreCase("")) {
            coach_email.setText(SharedPreferenceUtil.getString(ConstantUtil.SP_COACH_EMAIL));
            if(job_id != -1) {
                job_id = Integer.parseInt(SharedPreferenceUtil.getString(ConstantUtil.SP_POSITION_ID));
            }
        }
        coach_job.setSelection(job_id);
        coach_position = coach_job.getSelectedItem().toString();
        coach_job.setOnItemSelectedListener(this);
        coachee_job.setOnItemSelectedListener(this);
        first_job.setOnItemSelectedListener(this);
        cd_job.setOnItemSelectedListener(this);
        next.setOnClickListener(onClick);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, ConstantUtil.PERM_WRITE_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == ConstantUtil.PERM_WRITE_STORAGE){
            if(grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED ){
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, ConstantUtil.PERM_WRITE_STORAGE);
            }
        }
    }

    /**
    private void setAutomatic() {
        coach_email.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                coach_name.setText(coach_email.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        coachee_email.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                coachee_name.setText(coachee_email.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        first_email.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                first_name.setText(first_email.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        second_email.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                second_name.setText(second_email.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        cd_email.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cd_name.setText(cd_email.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }*/

    private void setField(Bundle savedState) {
//        coach_name.setText(savedState.getString("coach_name"));
//        coachee_name.setText(savedState.getString("coachee_name"));
//        first_name.setText(savedState.getString("first_name"));
//        second_name.setText(savedState.getString("second_name"));
//        cd_name.setText(savedState.getString("cd_name"));
        coach_email.setText(savedState.getString("coach_email"));
        coachee_email.setText(savedState.getString("coachee_email"));
        first_email.setText(savedState.getString("first_email"));
        cd_email.setText(savedState.getString("cd_email"));
        coach_job.setSelection(job_adapter.getPosition(savedState.getString("coach_position")));
        coachee_job.setSelection(job_adapter.getPosition(savedState.getString("coachee_position")));
        first_job.setSelection(job_adapter.getPosition(savedState.getString("first_position")));
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
        } else if (view.getId() == R.id.cdteam_position) {
            cd_position = parent.getSelectedItem().toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putString("coach_name", coach_name.getText().toString());
//        outState.putString("coachee_name", coachee_name.getText().toString());
//        outState.putString("first_name", first_name.getText().toString());
//        outState.putString("second_name", second_name.getText().toString());
//        outState.putString("cdname", cd_name.getText().toString());
        outState.putString("coach_email", coach_email.getText().toString());
        outState.putString("coachee_email", coachee_email.getText().toString());
        outState.putString("first_email", first_email.getText().toString());
        outState.putString("cd_email", cd_email.getText().toString());
        outState.putString("coach_position", coach_position);
        outState.putString("coachee_position", coachee_position);
        outState.putString("first_position", first_position);
        outState.putString("cd_position", cd_position);
    }

    protected void onRestoreInstanceState(Bundle savedState) {
//        coach_name.setText(savedState.getString("coach_name"));
//        coachee_name.setText(savedState.getString("coachee_name"));
//        first_name.setText(savedState.getString("first_name"));
//        second_name.setText(savedState.getString("second_name"));
//        cd_name.setText(savedState.getString("cd_name"));
        coach_email.setText(savedState.getString("coach_email"));
        coachee_email.setText(savedState.getString("coachee_email"));
        first_email.setText(savedState.getString("first_email"));
        cd_email.setText(savedState.getString("cd_email"));
        coach_job.setSelection(job_adapter.getPosition(savedState.getString("coach_position")));
        coachee_job.setSelection(job_adapter.getPosition(savedState.getString("coachee_position")));
        first_job.setSelection(job_adapter.getPosition(savedState.getString("first_position")));
        cd_job.setSelection(job_adapter.getPosition(savedState.getString("cd_position")));
    }
}
