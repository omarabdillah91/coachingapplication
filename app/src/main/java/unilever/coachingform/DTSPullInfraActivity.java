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

public class DTSPullInfraActivity extends AppCompatActivity {
    Context context;
    Button next;
    Button sebelum,report, market;
    TextView date;
    EditText coach, coachee, area, distributor;
    boolean bahasa = false;
    boolean english = false;
    String job, coach_email, coachee_email, text_area, text_distributor = "";
    String coachingSessionID = "";
    final List<CoachingQuestionAnswerEntity> coachingQAs = new ArrayList<>();
    RadioButton radio_1_a, radio_1_b, radio_1_c, radio_1_d, radio_1_e, radio_1_f, radio_1_g
            , radio_2_a, radio_2_b, radio_2_c, radio_2_d, radio_2_e, radio_3;
    EditText remarks_1_a, remarks_1_b, remarks_1_c, remarks_1_d, remarks_1_e, remarks_1_f, remarks_1_g
            , remarks_2_a, remarks_2_b, remarks_2_c, remarks_2_d, remarks_2_e, remarks_3;
    boolean status_1_a, status_1_b, status_1_c, status_1_d, status_1_e, status_1_f, status_1_g
            , status_2_a, status_2_b, status_2_c, status_2_d, status_2_e, status_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtra(getIntent().getExtras());
        if(english) {
            setContentView(R.layout.activity_dtspull_infra_english);
        } else {
            setContentView(R.layout.activity_dtspull_infra_bahasa);
        }
        next = (Button) findViewById(R.id.next);
        sebelum = (Button) findViewById(R.id.before_coaching);
        report = (Button) findViewById(R.id.on_report_coaching);
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
        radio_1_a = (RadioButton) findViewById(R.id.dts_infra_1_a);
        radio_1_b = (RadioButton) findViewById(R.id.dts_infra_1_b);
        radio_1_c = (RadioButton) findViewById(R.id.dts_infra_1_c);
        radio_1_d = (RadioButton) findViewById(R.id.dts_infra_1_d);
        radio_1_e = (RadioButton) findViewById(R.id.dts_infra_1_e);
        radio_1_f = (RadioButton) findViewById(R.id.dts_infra_1_f);
        radio_1_g = (RadioButton) findViewById(R.id.dts_infra_1_g);
        radio_2_a = (RadioButton) findViewById(R.id.dts_infra_2_a);
        radio_2_b = (RadioButton) findViewById(R.id.dts_infra_2_b);
        radio_2_c = (RadioButton) findViewById(R.id.dts_infra_2_c);
        radio_2_d = (RadioButton) findViewById(R.id.dts_infra_2_d);
        radio_2_e = (RadioButton) findViewById(R.id.dts_infra_2_e);
        radio_3 = (RadioButton) findViewById(R.id.dts_infra_3);
        remarks_1_a = (EditText) findViewById(R.id.remarks_infra_1_a);
        remarks_1_b = (EditText) findViewById(R.id.remarks_infra_1_b);
        remarks_1_c = (EditText) findViewById(R.id.remarks_infra_1_c);
        remarks_1_d = (EditText) findViewById(R.id.remarks_infra_1_d);
        remarks_1_e = (EditText) findViewById(R.id.remarks_infra_1_e);
        remarks_1_f = (EditText) findViewById(R.id.remarks_infra_1_f);
        remarks_1_g = (EditText) findViewById(R.id.remarks_infra_1_g);
        remarks_2_a = (EditText) findViewById(R.id.remarks_infra_2_a);
        remarks_2_b = (EditText) findViewById(R.id.remarks_infra_2_b);
        remarks_2_c = (EditText) findViewById(R.id.remarks_infra_2_c);
        remarks_2_d = (EditText) findViewById(R.id.remarks_infra_2_d);
        remarks_2_e = (EditText) findViewById(R.id.remarks_infra_2_e);
        remarks_3 = (EditText) findViewById(R.id.remarks_infra_3);
        next.setOnClickListener(onClick);
        sebelum.setOnClickListener(onClick);
        report.setOnClickListener(onClick);
        market.setOnClickListener(onClick);
        radio_1_a.setOnClickListener(onClick);
        radio_1_b.setOnClickListener(onClick);
        radio_1_c.setOnClickListener(onClick);
        radio_1_d.setOnClickListener(onClick);
        radio_1_e.setOnClickListener(onClick);
        radio_1_f.setOnClickListener(onClick);
        radio_1_g.setOnClickListener(onClick);
        radio_2_a.setOnClickListener(onClick);
        radio_2_b.setOnClickListener(onClick);
        radio_2_c.setOnClickListener(onClick);
        radio_2_d.setOnClickListener(onClick);
        radio_2_e.setOnClickListener(onClick);
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
        addingQA("","dts_pull_infra_1_a",status_1_a,remarks_1_a.getText().toString(),true);
        addingQA("","dts_pull_infra_1_b",status_1_b,remarks_1_b.getText().toString(),true);
        addingQA("","dts_pull_infra_1_c",status_1_c,remarks_1_c.getText().toString(),true);
        addingQA("","dts_pull_infra_1_d",status_1_d,remarks_1_d.getText().toString(),true);
        addingQA("","dts_pull_infra_1_e",status_1_e,remarks_1_e.getText().toString(),true);
        addingQA("","dts_pull_infra_1_f",status_1_f,remarks_1_f.getText().toString(),true);
        addingQA("","dts_pull_infra_1_g",status_1_g,remarks_1_g.getText().toString(),true);
        addingQA("","dts_pull_infra_2_a",status_2_a,remarks_2_a.getText().toString(),true);
        addingQA("","dts_pull_infra_2_b",status_2_b,remarks_2_b.getText().toString(),true);
        addingQA("","dts_pull_infra_2_c",status_2_c,remarks_2_c.getText().toString(),true);
        addingQA("","dts_pull_infra_2_d",status_2_d,remarks_2_d.getText().toString(),true);
        addingQA("","dts_pull_infra_2_e",status_2_e,remarks_2_e.getText().toString(),true);
        addingQA("","dts_pull_infra_3",status_3,remarks_3.getText().toString(),true);
        CoachingQuestionAnswerDAO.insertCoachingQA(coachingQAs, new CoachingQuestionAnswerDAO.InsertCoachingQAListener() {
            @Override
            public void onInsertQuestionAnswerCompleted(boolean isSuccess) {
                if (isSuccess) {
                    Intent intent = new Intent(DTSPullInfraActivity.this, DTSPullMarketActivity.class);
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
                    Toast.makeText(DTSPullInfraActivity.this, "Failed to save the data!!!",
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
                Intent intent = new Intent(DTSPullInfraActivity.this, DTSPullSebelumActivity.class);
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
                Intent intent = new Intent(DTSPullInfraActivity.this, DTSPullReportActivity.class);
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
                Intent intent = new Intent(DTSPullInfraActivity.this, DTSPullMarketActivity.class);
                intent.putExtra("coach", coach.getText().toString());
                intent.putExtra("job", job);
                intent.putExtra("coachee", coachee.getText().toString());
                intent.putExtra("bahasa",bahasa);
                intent.putExtra("english", english);
                intent.putExtra("area", area.getText().toString());
                intent.putExtra("distributor", distributor.getText().toString());
                intent.putExtra("id", coachingSessionID);
                startActivity(intent);
            } else if (v.getId() == R.id.dts_infra_1_a) {
                if(status_1_a) {
                    radio_1_a.setChecked(false);
                    status_1_a = false;
                } else {
                    status_1_a = true;
                    radio_1_a.setChecked(true);
                }
            } else if (v.getId() == R.id.dts_infra_1_b) {
                if(status_1_b) {
                    radio_1_b.setChecked(false);
                    status_1_b = false;
                } else {
                    status_1_b = true;
                    radio_1_b.setChecked(true);
                }
            } else if (v.getId() == R.id.dts_infra_1_c) {
                if(status_1_c) {
                    radio_1_c.setChecked(false);
                    status_1_c = false;
                } else {
                    status_1_c = true;
                    radio_1_c.setChecked(true);
                }
            } else if (v.getId() == R.id.dts_infra_1_d) {
                if(status_1_d) {
                    radio_1_d.setChecked(false);
                    status_1_d = false;
                } else {
                    status_1_d = true;
                    radio_1_d.setChecked(true);
                }
            } else if (v.getId() == R.id.dts_infra_1_e) {
                if(status_1_e) {
                    radio_1_e.setChecked(false);
                    status_1_e = false;
                } else {
                    status_1_e = true;
                    radio_1_e.setChecked(true);
                }
            } else if (v.getId() == R.id.dts_infra_1_f) {
                if(status_1_f) {
                    radio_1_f.setChecked(false);
                    status_1_f = false;
                } else {
                    status_1_f = true;
                    radio_1_f.setChecked(true);
                }
            } else if (v.getId() == R.id.dts_infra_1_g) {
                if(status_1_g) {
                    radio_1_g.setChecked(false);
                    status_1_g = false;
                } else {
                    status_1_g = true;
                    radio_1_g.setChecked(true);
                }
            } else if (v.getId() == R.id.dts_infra_2_a) {
                if(status_2_a) {
                    radio_2_a.setChecked(false);
                    status_2_a = false;
                } else {
                    status_2_a = true;
                    radio_2_a.setChecked(true);
                }
            } else if (v.getId() == R.id.dts_infra_2_b) {
                if(status_2_b) {
                    radio_2_b.setChecked(false);
                    status_2_b = false;
                } else {
                    status_2_b = true;
                    radio_2_b.setChecked(true);
                }
            } else if (v.getId() == R.id.dts_infra_2_c) {
                if(status_2_c) {
                    radio_2_c.setChecked(false);
                    status_2_c = false;
                } else {
                    status_2_c = true;
                    radio_2_c.setChecked(true);
                }
            } else if (v.getId() == R.id.dts_infra_2_d) {
                if(status_2_d) {
                    radio_2_d.setChecked(false);
                    status_2_d = false;
                } else {
                    status_2_d = true;
                    radio_2_d.setChecked(true);
                }
            } else if (v.getId() == R.id.dts_infra_2_e) {
                if(status_2_e) {
                    radio_2_e.setChecked(false);
                    status_2_e = false;
                } else {
                    status_2_e = true;
                    radio_2_e.setChecked(true);
                }
            } else if (v.getId() == R.id.dts_infra_3) {
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
