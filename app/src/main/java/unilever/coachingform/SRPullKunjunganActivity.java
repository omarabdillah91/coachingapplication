package unilever.coachingform;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dao.CoachingQuestionAnswerDAO;
import entity.CoachingQuestionAnswerEntity;
import utility.ConstantUtil;
import utility.RealmUtil;
import utility.SharedPreferenceUtil;

public class SRPullKunjunganActivity extends AppCompatActivity {
    Context context;
    Button next;
    Button report;
    TextView date;
    EditText coach, coachee, area;
    boolean bahasa = false;
    boolean english = false;
    String job, coach_email, coachee_email, text_area = "";
    String coachingSessionID = "";
    final List<CoachingQuestionAnswerEntity> coachingQAs = new ArrayList<>();
    RadioButton radio_1_a, radio_1_b,radio_2_a, radio_2_b, radio_2_c, radio_2_d;
    EditText remarks_1_a, remarks_1_b,remarks_2_a, remarks_2_b, remarks_2_c, remarks_2_d;
    boolean status_1_a, status_1_b, status_2_a, status_2_b, status_2_c, status_2_d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtra(getIntent().getExtras());
        if(english) {
            setContentView(R.layout.activity_srpull_kunjungan_bahasa);
        } else {
            setContentView(R.layout.activity_srpull_kunjungan_bahasa);
        }
        next = (Button) findViewById(R.id.next);
        report = (Button) findViewById(R.id.report_coaching);
        coach = (EditText) findViewById(R.id.coach);
        coachee = (EditText) findViewById(R.id.coachee);
        area = (EditText) findViewById(R.id.area);
        coach.setText(coach_email);
        coach.setEnabled(false);
        coachee.setText(coachee_email);
        coachee.setEnabled(false);
        area.setText(text_area);
        area.setEnabled(false);
        date = (TextView) findViewById(R.id.date);
        date.setText(SharedPreferenceUtil.getString(ConstantUtil.SP_DATE));
        report.setOnClickListener(onClick);
        next.setOnClickListener(onClick);
        radio_1_a = (RadioButton) findViewById(R.id.sr_pull_saat_1_a);
        radio_1_b = (RadioButton) findViewById(R.id.sr_pull_saat_1_b);
        radio_2_a = (RadioButton) findViewById(R.id.sr_pull_saat_2_a);
        radio_2_b = (RadioButton) findViewById(R.id.sr_pull_saat_2_b);
        radio_2_c = (RadioButton) findViewById(R.id.sr_pull_saat_2_c);
        radio_2_d = (RadioButton) findViewById(R.id.sr_pull_saat_2_d);

        remarks_1_a = (EditText) findViewById(R.id.remarks_saat_1_a);
        remarks_1_b = (EditText) findViewById(R.id.remarks_saat_1_b);
        remarks_2_a = (EditText) findViewById(R.id.remarks_saat_2_a);
        remarks_2_b = (EditText) findViewById(R.id.remarks_saat_2_b);
        remarks_2_c = (EditText) findViewById(R.id.remarks_saat_2_c);
        remarks_2_d = (EditText) findViewById(R.id.remarks_saat_2_d);
        radio_1_a.setOnClickListener(onClick);
        radio_1_b.setOnClickListener(onClick);
        radio_2_a.setOnClickListener(onClick);
        radio_2_b.setOnClickListener(onClick);
        radio_2_c.setOnClickListener(onClick);
        radio_2_d.setOnClickListener(onClick);
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.next) {
                saveQA();
            } else if (v.getId() == R.id.report_coaching) {
                Intent intent = new Intent(SRPullKunjunganActivity.this, SRPullReportActivity.class);
                intent.putExtra("coach", coach.getText().toString());
                intent.putExtra("job", job);
                intent.putExtra("coachee", coachee.getText().toString());
                intent.putExtra("bahasa",bahasa);
                intent.putExtra("english", english);
                intent.putExtra("area", area.getText().toString());
                intent.putExtra("id", coachingSessionID);
                startActivity(intent);
            } else if (v.getId() == R.id.sr_pull_saat_1_a) {
                if(status_1_a) {
                    radio_1_a.setChecked(false);
                    status_1_a = false;
                } else {
                    status_1_a = true;
                    radio_1_a.setChecked(true);
                }
            } else if (v.getId() == R.id.sr_pull_saat_1_b) {
                if(status_1_b) {
                    radio_1_b.setChecked(false);
                    status_1_b = false;
                } else {
                    status_1_b = true;
                    radio_1_b.setChecked(true);
                }
            } else if (v.getId() == R.id.sr_pull_saat_2_a) {
                if(status_2_a) {
                    radio_2_a.setChecked(false);
                    status_2_a = false;
                } else {
                    status_2_a = true;
                    radio_2_a.setChecked(true);
                }
            } else if (v.getId() == R.id.sr_pull_saat_2_b) {
                if(status_2_b) {
                    radio_2_b.setChecked(false);
                    status_2_b = false;
                } else {
                    status_2_b = true;
                    radio_2_b.setChecked(true);
                }
            } else if (v.getId() == R.id.sr_pull_saat_2_c) {
                if(status_2_c) {
                    radio_2_c.setChecked(false);
                    status_2_c = false;
                } else {
                    status_2_c = true;
                    radio_2_c.setChecked(true);
                }
            } else if (v.getId() == R.id.sr_pull_saat_2_d) {
                if(status_2_d) {
                    radio_2_d.setChecked(false);
                    status_2_d = false;
                } else {
                    status_2_d = true;
                    radio_2_d.setChecked(true);
                }
            }
        }
    };

    private void saveQA() {
        addingQA("", "sr_pull_kunjungan_1_a", status_1_a, remarks_1_a.getText().toString(), true);
        addingQA("", "sr_pull_kunjungan_1_b", status_1_b, remarks_1_b.getText().toString(), true);
        addingQA("", "sr_pull_kunjungan_2_a", status_2_a, remarks_2_a.getText().toString(), true);
        addingQA("", "sr_pull_kunjungan_2_b", status_2_b, remarks_2_b.getText().toString(), true);
        addingQA("", "sr_pull_kunjungan_2_c", status_2_c, remarks_2_c.getText().toString(), true);
        addingQA("", "sr_pull_kunjungan_2_d", status_2_d, remarks_2_d.getText().toString(), true);
        CoachingQuestionAnswerDAO.insertCoachingQA(coachingQAs, new CoachingQuestionAnswerDAO.InsertCoachingQAListener() {
            @Override
            public void onInsertQuestionAnswerCompleted(boolean isSuccess) {
                if (isSuccess) {
                    Intent intent = new Intent(SRPullKunjunganActivity.this, CoachingSummarySRPullActivity.class);
                    intent.putExtra("coach", coach.getText().toString());
                    intent.putExtra("job", job);
                    intent.putExtra("coachee", coachee.getText().toString());
                    intent.putExtra("bahasa", bahasa);
                    intent.putExtra("english", english);
                    intent.putExtra("area", area.getText().toString());
                    intent.putExtra("id", coachingSessionID);
                    startActivity(intent);
                } else {
                    Toast.makeText(SRPullKunjunganActivity.this, "Failed to save the data!!!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
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
            if(bundle.getString("id") != null) {
                coachingSessionID = bundle.getString("id");
            }
        }
    }

    private void addingQA(String column_id, String question_id, boolean status, String remarks, boolean b) {
        CoachingQuestionAnswerEntity coachingQA = new CoachingQuestionAnswerEntity();
        coachingQA.setId(RealmUtil.generateID());
        coachingQA.setCoachingSessionID(coachingSessionID);
        coachingQA.setColumnID(column_id);
        coachingQA.setQuestionID(question_id);
        coachingQA.setTextAnswer(remarks);
        coachingQA.setTickAnswer(status);
        coachingQA.setHasTickAnswer(b);
        coachingQAs.add(coachingQA);
    }
}
