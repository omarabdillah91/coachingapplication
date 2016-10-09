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

public class DTSPullReportActivity extends AppCompatActivity {
    Context context;
    Button next;
    Button sebelum,infra, market;
    TextView date;
    EditText coach, coachee, area, distributor;
    boolean bahasa = false;
    boolean english = false;
    String job, coach_email, coachee_email, text_area, text_distributor = "";
    String coachingSessionID = "";
    final List<CoachingQuestionAnswerEntity> coachingQAs = new ArrayList<>();
    RadioButton radio_1_a, radio_1_b, radio_2_a, radio_2_b, radio_3_a, radio_3_b, radio_3_c;
    EditText remarks_1_a, remarks_1_b, remarks_2_a, remarks_2_b, remarks_3_a, remarks_3_b, remarks_3_c;
    boolean status_1_a, status_1_b, status_2_a, status_2_b, status_3_a, status_3_b, status_3_c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtra(getIntent().getExtras());
        if(english) {
            setContentView(R.layout.activity_dtspull_report_english);
        } else {
            setContentView(R.layout.activity_dtspull_report_bahasa);
        }
        next = (Button) findViewById(R.id.next);
        sebelum = (Button) findViewById(R.id.before_coaching);
        infra = (Button) findViewById(R.id.on_infra_coaching);
        market = (Button) findViewById(R.id.market_coaching);
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
        date = (TextView) findViewById(R.id.date);
        date.setText(SharedPreferenceUtil.getString(ConstantUtil.SP_DATE));
        radio_1_a = (RadioButton) findViewById(R.id.dts_report_1_a);
        radio_1_b = (RadioButton) findViewById(R.id.dts_report_1_b);
        radio_2_a = (RadioButton) findViewById(R.id.dts_report_2_a);
        radio_2_b = (RadioButton) findViewById(R.id.dts_report_2_b);
        radio_3_a = (RadioButton) findViewById(R.id.dts_report_3_a);
        radio_3_b = (RadioButton) findViewById(R.id.dts_report_3_b);
        radio_3_c = (RadioButton) findViewById(R.id.dts_report_3_c);
        remarks_1_a = (EditText) findViewById(R.id.remarks_report_1_a);
        remarks_1_b = (EditText) findViewById(R.id.remarks_report_1_b);
        remarks_2_a = (EditText) findViewById(R.id.remarks_report_2_a);
        remarks_2_b = (EditText) findViewById(R.id.remarks_report_2_b);
        remarks_3_a = (EditText) findViewById(R.id.remarks_report_3_a);
        remarks_3_b = (EditText) findViewById(R.id.remarks_report_3_b);
        remarks_3_c = (EditText) findViewById(R.id.remarks_report_3_c);
        sebelum.setOnClickListener(onClick);
        infra.setOnClickListener(onClick);
        market.setOnClickListener(onClick);
        next.setOnClickListener(onClick);
        radio_1_a.setOnClickListener(onClick);
        radio_1_b.setOnClickListener(onClick);
        radio_2_a.setOnClickListener(onClick);
        radio_2_b.setOnClickListener(onClick);
        radio_3_a.setOnClickListener(onClick);
        radio_3_b.setOnClickListener(onClick);
        radio_3_c.setOnClickListener(onClick);
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
            if(bundle.getString("id") != null) {
                coachingSessionID = bundle.getString("id");
            }
        }
    }

    private void saveQA() {
        addingQA("","dts_pull_report_1_a",status_1_a,remarks_1_a.getText().toString(),true);
        addingQA("","dts_pull_report_1_b",status_1_b,remarks_1_b.getText().toString(),true);
        addingQA("","dts_pull_report_2_a",status_2_a,remarks_2_a.getText().toString(),true);
        addingQA("","dts_pull_report_2_b",status_2_b,remarks_2_b.getText().toString(),true);
        addingQA("","dts_pull_report_3_a",status_3_a,remarks_3_a.getText().toString(),true);
        addingQA("","dts_pull_report_3_b",status_3_b,remarks_3_b.getText().toString(),true);
        addingQA("","dts_pull_report_3_c",status_3_c,remarks_3_c.getText().toString(),true);
        CoachingQuestionAnswerDAO.insertCoachingQA(coachingQAs, new CoachingQuestionAnswerDAO.InsertCoachingQAListener() {
            @Override
            public void onInsertQuestionAnswerCompleted(boolean isSuccess) {
                if (isSuccess) {
                    Intent intent = new Intent(DTSPullReportActivity.this, DTSPullInfraActivity.class);
                    intent.putExtra("coach", coach.getText().toString());
                    intent.putExtra("job", job);
                    intent.putExtra("coachee", coachee.getText().toString());
                    intent.putExtra("bahasa", bahasa);
                    intent.putExtra("english", english);
                    intent.putExtra("area", area.getText().toString());
                    intent.putExtra("distributor", distributor.getText().toString());
                    intent.putExtra("id", coachingSessionID);
                    startActivity(intent);
                } else {
                    Toast.makeText(DTSPullReportActivity.this, "Failed to save the data!!!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addingQA(String s, String question_id, boolean status_1, String remarks, boolean b) {
        CoachingQuestionAnswerEntity coachingQA = new CoachingQuestionAnswerEntity();
        coachingQA.setId(RealmUtil.generateID());
        coachingQA.setCoachingSessionID(coachingSessionID);
        coachingQA.setColumnID(s);
        coachingQA.setQuestionID(question_id);
        coachingQA.setTextAnswer(remarks);
        coachingQA.setTickAnswer(status_1);
        coachingQA.setHasTickAnswer(b);
        coachingQAs.add(coachingQA);
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.next) {
                saveQA();
            } else if (v.getId() == R.id.before_coaching) {
                Intent intent = new Intent(DTSPullReportActivity.this, DTSPullSebelumActivity.class);
                intent.putExtra("coach", coach.getText().toString());
                intent.putExtra("job", job);
                intent.putExtra("coachee", coachee.getText().toString());
                intent.putExtra("bahasa",bahasa);
                intent.putExtra("english", english);
                intent.putExtra("area", area.getText().toString());
                intent.putExtra("distributor", distributor.getText().toString());
                intent.putExtra("id", coachingSessionID);
                startActivity(intent);
            } else if (v.getId() == R.id.on_infra_coaching) {
                Intent intent = new Intent(DTSPullReportActivity.this, DTSPullInfraActivity.class);
                intent.putExtra("coach", coach.getText().toString());
                intent.putExtra("job", job);
                intent.putExtra("coachee", coachee.getText().toString());
                intent.putExtra("bahasa",bahasa);
                intent.putExtra("english", english);
                intent.putExtra("area", area.getText().toString());
                intent.putExtra("distributor", distributor.getText().toString());
                intent.putExtra("id", coachingSessionID);
                startActivity(intent);
            } else if (v.getId() == R.id.market_coaching) {
                Intent intent = new Intent(DTSPullReportActivity.this, DTSPullMarketActivity.class);
                intent.putExtra("coach", coach.getText().toString());
                intent.putExtra("job", job);
                intent.putExtra("coachee", coachee.getText().toString());
                intent.putExtra("bahasa",bahasa);
                intent.putExtra("english", english);
                intent.putExtra("area", area.getText().toString());
                intent.putExtra("distributor", distributor.getText().toString());
                intent.putExtra("id", coachingSessionID);
                startActivity(intent);
            } else if (v.getId() == R.id.dts_report_1_a) {
                if(status_1_a) {
                    radio_1_a.setChecked(false);
                    status_1_a = false;
                } else {
                    status_1_a = true;
                    radio_1_a.setChecked(true);
                }
            } else if (v.getId() == R.id.dts_report_1_b) {
                if(status_1_b) {
                    radio_1_b.setChecked(false);
                    status_1_b = false;
                } else {
                    status_1_b = true;
                    radio_1_b.setChecked(true);
                }
            } else if (v.getId() == R.id.dts_report_2_a) {
                if(status_2_a) {
                    radio_2_a.setChecked(false);
                    status_2_a = false;
                } else {
                    status_2_a = true;
                    radio_2_a.setChecked(true);
                }
            } else if (v.getId() == R.id.dts_report_2_b) {
                if(status_2_b) {
                    radio_2_b.setChecked(false);
                    status_2_b = false;
                } else {
                    status_2_b = true;
                    radio_2_b.setChecked(true);
                }
            } else if (v.getId() == R.id.dts_report_3_a) {
                if(status_3_a) {
                    radio_3_a.setChecked(false);
                    status_3_a = false;
                } else {
                    status_3_a = true;
                    radio_3_a.setChecked(true);
                }
            } else if (v.getId() == R.id.dts_report_3_b) {
                if(status_3_b) {
                    radio_3_b.setChecked(false);
                    status_3_b = false;
                } else {
                    status_3_b = true;
                    radio_3_b.setChecked(true);
                }
            } else if (v.getId() == R.id.dts_report_3_c) {
                if(status_3_c) {
                    radio_3_c.setChecked(false);
                    status_3_c = false;
                } else {
                    status_3_c = true;
                    radio_3_c.setChecked(true);
                }
            }
        }
    };
}
