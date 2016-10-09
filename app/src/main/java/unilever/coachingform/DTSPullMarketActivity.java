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

public class DTSPullMarketActivity extends AppCompatActivity {
    Context context;
    Button next;
    Button sebelum,report, infra;
    TextView date;
    EditText coach, coachee, area, distributor;
    boolean bahasa = false;
    boolean english = false;
    String job, coach_email, coachee_email, text_area, text_distributor = "";
    String coachingSessionID = "";
    final List<CoachingQuestionAnswerEntity> coachingQAs = new ArrayList<>();
    RadioButton radio_1, radio_2, radio_3;
    EditText remarks_1, remarks_2, remarks_3;
    boolean status_1, status_2, status_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtra(getIntent().getExtras());
        if(english) {
            setContentView(R.layout.activity_dtspull_market_english);
        } else {
            setContentView(R.layout.activity_dtspull_market_bahasa);
        }
        next = (Button) findViewById(R.id.next);
        sebelum = (Button) findViewById(R.id.before_coaching);
        report = (Button) findViewById(R.id.on_report_coaching);
        infra = (Button) findViewById(R.id.on_infra_coaching);
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
        radio_1 = (RadioButton) findViewById(R.id.dts_pasar_1);
        radio_2 = (RadioButton) findViewById(R.id.dts_pasar_2);
        radio_3 = (RadioButton) findViewById(R.id.dts_pasar_3);
        remarks_1 = (EditText) findViewById(R.id.remarks_pasar_1);
        remarks_2 = (EditText) findViewById(R.id.remarks_pasar_2);
        remarks_3 = (EditText) findViewById(R.id.remarks_pasar_3);
        next.setOnClickListener(onClick);
        sebelum.setOnClickListener(onClick);
        report.setOnClickListener(onClick);
        infra.setOnClickListener(onClick);
        radio_1.setOnClickListener(onClick);
        radio_2.setOnClickListener(onClick);
        radio_3.setOnClickListener(onClick);
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
        addingQA("","dts_pull_market_1",status_1,remarks_1.getText().toString(),true);
        addingQA("","dts_pull_market_2",status_2,remarks_2.getText().toString(),true);
        addingQA("","dts_pull_market_3",status_3,remarks_3.getText().toString(),true);
        CoachingQuestionAnswerDAO.insertCoachingQA(coachingQAs, new CoachingQuestionAnswerDAO.InsertCoachingQAListener() {
            @Override
            public void onInsertQuestionAnswerCompleted(boolean isSuccess) {
                if (isSuccess) {
                    Intent intent = new Intent(DTSPullMarketActivity.this, CoachingSummaryDTSPullActivity.class);
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
                    Toast.makeText(DTSPullMarketActivity.this, "Failed to save the data!!!",
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
                Intent intent = new Intent(DTSPullMarketActivity.this, DTSPullSebelumActivity.class);
                intent.putExtra("coach", coach.getText().toString());
                intent.putExtra("job", job);
                intent.putExtra("coachee", coachee.getText().toString());
                intent.putExtra("bahasa",bahasa);
                intent.putExtra("english", english);
                intent.putExtra("area", area.getText().toString());
                intent.putExtra("distributor", distributor.getText().toString());
                intent.putExtra("id", coachingSessionID);
                startActivity(intent);
            } else if (v.getId() == R.id.on_report_coaching) {
                Intent intent = new Intent(DTSPullMarketActivity.this, DTSPullReportActivity.class);
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
                Intent intent = new Intent(DTSPullMarketActivity.this, DTSPullInfraActivity.class);
                intent.putExtra("coach", coach.getText().toString());
                intent.putExtra("job", job);
                intent.putExtra("coachee", coachee.getText().toString());
                intent.putExtra("bahasa",bahasa);
                intent.putExtra("english", english);
                intent.putExtra("area", area.getText().toString());
                intent.putExtra("distributor", distributor.getText().toString());
                intent.putExtra("id", coachingSessionID);
                startActivity(intent);
            }else if (v.getId() == R.id.dts_pasar_1) {
                if(status_1) {
                    radio_1.setChecked(false);
                    status_1 = false;
                } else {
                    status_1 = true;
                    radio_1.setChecked(true);
                }
            } else if (v.getId() == R.id.dts_pasar_2) {
                if(status_2) {
                    radio_2.setChecked(false);
                    status_2 = false;
                } else {
                    status_2 = true;
                    radio_2.setChecked(true);
                }
            } else if (v.getId() == R.id.dts_pasar_3) {
                if(status_3) {
                    radio_3.setChecked(false);
                    status_3 = false;
                } else {
                    status_3 = true;
                    radio_3.setChecked(true);
                }
            }
        }
    };
}
