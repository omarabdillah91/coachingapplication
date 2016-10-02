package unilever.coachingform;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class ASMPullHabbitActivity extends AppCompatActivity {
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
    RadioButton radio_1, radio_2;
    EditText remarks_1, remarks_2;
    boolean status_1, status_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtra(getIntent().getExtras());
        if(english) {
            setContentView(R.layout.activity_asmpull_habbit_bahasa);
        } else {
            setContentView(R.layout.activity_asmpull_habbit_bahasa);
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
        radio_1 = (RadioButton) findViewById(R.id.asm_pull_habit_1);
        radio_2 = (RadioButton) findViewById(R.id.asm_pull_habit_2);
        remarks_1 = (EditText) findViewById(R.id.remarks_habit_1);
        remarks_2 = (EditText) findViewById(R.id.remarks_habit_2);
        report.setOnClickListener(onClick);
        next.setOnClickListener(onClick);
        radio_1.setOnClickListener(onClick);
        radio_2.setOnClickListener(onClick);
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.next) {
                saveQA();
            } else if (v.getId() == R.id.on_report_coaching) {
                Intent intent = new Intent(ASMPullHabbitActivity.this, ASMPullReportActivity.class);
                intent.putExtra("coach", coach.getText().toString());
                intent.putExtra("job", job);
                intent.putExtra("coachee", coachee.getText().toString());
                intent.putExtra("bahasa",bahasa);
                intent.putExtra("english", english);
                intent.putExtra("area", area.getText().toString());
                intent.putExtra("id", coachingSessionID);
                startActivity(intent);
            } else if (v.getId() == R.id.asm_pull_habit_1) {
                if(status_1) {
                    radio_1.setChecked(false);
                    status_1 = false;
                } else {
                    status_1 = true;
                    radio_1.setChecked(true);
                }
            } else if (v.getId() == R.id.asm_pull_habit_2) {
                if(status_2) {
                    radio_2.setChecked(false);
                    status_2 = false;
                } else {
                    status_2 = true;
                    radio_2.setChecked(true);
                }
            }
        }
    };

    private void saveQA() {
        addingQA("", "asm_pull_habbit_1", status_1, remarks_1.getText().toString(), true);
        addingQA("", "asm_pull_habbit_2", status_2, remarks_2.getText().toString(), true);
        CoachingQuestionAnswerDAO.insertCoachingQA(coachingQAs, new CoachingQuestionAnswerDAO.InsertCoachingQAListener() {
            @Override
            public void onInsertQuestionAnswerCompleted(boolean isSuccess) {
                if (isSuccess) {
                    Intent intent = new Intent(ASMPullHabbitActivity.this, CoachingSummaryASMPullActivity.class);
                    intent.putExtra("coach", coach.getText().toString());
                    intent.putExtra("job", job);
                    intent.putExtra("coachee", coachee.getText().toString());
                    intent.putExtra("bahasa", bahasa);
                    intent.putExtra("english", english);
                    intent.putExtra("area", area.getText().toString());
                    intent.putExtra("id", coachingSessionID);
                    startActivity(intent);
                } else {
                    Toast.makeText(ASMPullHabbitActivity.this, "Failed to save the data!!!",
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
