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

public class SRPullReportActivity extends AppCompatActivity {
    Context context;
    Button next;
    Button kunjungan;
    TextView date;
    EditText coach, coachee, area;
    boolean bahasa = false;
    boolean english = false;
    String job, coach_email, coachee_email, text_area = "";
    String coachingSessionID = "";
    final List<CoachingQuestionAnswerEntity> coachingQAs = new ArrayList<>();
    RadioButton radio_1_1_a, radio_1_1_b, radio_1_1_c, radio_1_1_d, radio_1_1_e, radio_1_2;
    EditText remarks_1_1_a, remarks_1_1_b, remarks_1_1_c, remarks_1_1_d, remarks_1_1_e, remarks_1_2;
    boolean status_1_1_a, status_1_1_b, status_1_1_c, status_1_1_d, status_1_1_e, status_1_2;
    boolean status_2[][] = new boolean[19][3];
    RadioButton radio_2[][] = new RadioButton[19][3];
    int radio_id_2[][] = new int[19][3];
    EditText remarks_2[][] = new EditText[19][3];
    String index[] = new String[]{"","a","b","c","d"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtra(getIntent().getExtras());
        if(english) {
            setContentView(R.layout.activity_srpull_report_bahasa);
        } else {
            setContentView(R.layout.activity_srpull_report_bahasa);
        }
        next = (Button) findViewById(R.id.next);
        kunjungan = (Button) findViewById(R.id.on_coaching);
        coach = (EditText) findViewById(R.id.coach);
        coachee = (EditText) findViewById(R.id.coachee);
        area = (EditText) findViewById(R.id.area);
        coach.setText(coach_email);
        coach.setEnabled(false);
        coachee.setText(coachee_email);
        coachee.setEnabled(false);
        date = (TextView) findViewById(R.id.date);
        date.setText(SharedPreferenceUtil.getString(ConstantUtil.SP_DATE));
        kunjungan.setOnClickListener(onClick);
        next.setOnClickListener(onClick);
        radio_1_1_a = (RadioButton) findViewById(R.id.sr_pull_report_1_1_a);
        radio_1_1_b = (RadioButton) findViewById(R.id.sr_pull_report_1_1_b);
        radio_1_1_c = (RadioButton) findViewById(R.id.sr_pull_report_1_1_c);
        radio_1_1_d = (RadioButton) findViewById(R.id.sr_pull_report_1_1_d);
        radio_1_1_e = (RadioButton) findViewById(R.id.sr_pull_report_1_1_e);
        radio_1_2 = (RadioButton) findViewById(R.id.sr_pull_report_1_2);

        remarks_1_1_a = (EditText) findViewById(R.id.remarks_report_1_1_a);
        remarks_1_1_b = (EditText) findViewById(R.id.remarks_report_1_1_b);
        remarks_1_1_c = (EditText) findViewById(R.id.remarks_report_1_1_c);
        remarks_1_1_d = (EditText) findViewById(R.id.remarks_report_1_1_d);
        remarks_1_1_e = (EditText) findViewById(R.id.remarks_report_1_1_e);
        remarks_1_2 = (EditText) findViewById(R.id.remarks_report_1_2);

        radio_1_1_a.setOnClickListener(onClick);
        radio_1_1_b.setOnClickListener(onClick);
        radio_1_1_c.setOnClickListener(onClick);
        radio_1_1_d.setOnClickListener(onClick);
        radio_1_1_e.setOnClickListener(onClick);
        radio_1_2.setOnClickListener(onClick);
        for(int i = 1; i < 19;i++) {
            for (int j = 1; j < 3; j++) {
                int id = getID("sr_pull_report_2_" + i+"_"+index[j]);
                radio_2[i][j] = (RadioButton) findViewById(id);
                radio_id_2[i][j] = id;
                radio_2[i][j].setOnClickListener(onRadioClick);
                int remarks_id = getID("remarks_report_2_" + i+"_"+index[j]);
                remarks_2[i][j] = (EditText) findViewById(remarks_id);
            }
        }
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.next) {
                saveQA();
            } else if (v.getId() == R.id.on_coaching) {
                Intent intent = new Intent(SRPullReportActivity.this, SRPullKunjunganActivity.class);
                intent.putExtra("coach", coach.getText().toString());
                intent.putExtra("job", job);
                intent.putExtra("coachee", coachee.getText().toString());
                intent.putExtra("bahasa",bahasa);
                intent.putExtra("english", english);
                intent.putExtra("area", area.getText().toString());
                intent.putExtra("id", coachingSessionID);
                startActivity(intent);
            } else if (v.getId() == R.id.sr_pull_report_1_1_a) {
                if(status_1_1_a) {
                    radio_1_1_a.setChecked(false);
                    status_1_1_a = false;
                } else {
                    status_1_1_a = true;
                    radio_1_1_a.setChecked(true);
                }
            } else if (v.getId() == R.id.sr_pull_report_1_1_b) {
                if(status_1_1_b) {
                    radio_1_1_b.setChecked(false);
                    status_1_1_b = false;
                } else {
                    status_1_1_b = true;
                    radio_1_1_b.setChecked(true);
                }
            } else if (v.getId() == R.id.sr_pull_report_1_1_c) {
                if(status_1_1_c) {
                    radio_1_1_c.setChecked(false);
                    status_1_1_c = false;
                } else {
                    status_1_1_c = true;
                    radio_1_1_c.setChecked(true);
                }
            } else if (v.getId() == R.id.sr_pull_report_1_1_d) {
                if(status_1_1_d) {
                    radio_1_1_d.setChecked(false);
                    status_1_1_d = false;
                } else {
                    status_1_1_d = true;
                    radio_1_1_d.setChecked(true);
                }
            } else if (v.getId() == R.id.sr_pull_report_1_1_e) {
                if(status_1_1_e) {
                    radio_1_1_e.setChecked(false);
                    status_1_1_e = false;
                } else {
                    status_1_1_e = true;
                    radio_1_1_e.setChecked(true);
                }
            } else if (v.getId() == R.id.sr_pull_report_1_2) {
                if(status_1_2) {
                    radio_1_2.setChecked(false);
                    status_1_2 = false;
                } else {
                    status_1_2 = true;
                    radio_1_2.setChecked(true);
                }
            }
        }
    };

    View.OnClickListener onRadioClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for(int i = 1; i < 19;i++) {
                for (int j = 1; j < 3; j++) {
                    if(v.getId() == radio_id_2[i][j]) {
                        if(status_2[i][j]) {
                            radio_2[i][j].setChecked(false);
                            status_2[i][j] = false;
                        } else {
                            status_2[i][j] = true;
                            radio_2[i][j].setChecked(true);
                        }
                        break;
                    }
                }
            }
        }
    };

    private void saveQA() {
        addingQA("","sr_pull_report_1_1_a",status_1_1_a,remarks_1_1_a.getText().toString(),true);
        addingQA("","sr_pull_report_1_1_b",status_1_1_b,remarks_1_1_b.getText().toString(),true);
        addingQA("","sr_pull_report_1_1_c",status_1_1_c,remarks_1_1_c.getText().toString(),true);
        addingQA("","sr_pull_report_1_1_d",status_1_1_d,remarks_1_1_d.getText().toString(),true);
        addingQA("","sr_pull_report_1_1_e",status_1_1_e,remarks_1_1_e.getText().toString(),true);
        addingQA("","sr_pull_report_1_2",status_1_2,remarks_1_2.getText().toString(),true);
        for(int i = 1; i < status_2.length;i++) {
            for (int j = 1;j < status_2[0].length;j++) {
                addingQA("","sr_pull_report_2_"+i+"_"+index[j], status_2[i][j], remarks_2[i][j].getText().toString(), true);
            }
        }
        CoachingQuestionAnswerDAO.insertCoachingQA(coachingQAs, new CoachingQuestionAnswerDAO.InsertCoachingQAListener() {
            @Override
            public void onInsertQuestionAnswerCompleted(boolean isSuccess) {
                if (isSuccess) {
                    Intent intent = new Intent(SRPullReportActivity.this, SRPullKunjunganActivity.class);
                    intent.putExtra("coach", coach.getText().toString());
                    intent.putExtra("job", job);
                    intent.putExtra("coachee", coachee.getText().toString());
                    intent.putExtra("bahasa", bahasa);
                    intent.putExtra("english", english);
                    intent.putExtra("area", area.getText().toString());
                    intent.putExtra("id", coachingSessionID);
                    startActivity(intent);
                } else {
                    Toast.makeText(SRPullReportActivity.this, "Failed to save the data!!!",
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

    private int getID(String s) {
        return getResources().getIdentifier(s, "id", this.getPackageName());
    }
}
