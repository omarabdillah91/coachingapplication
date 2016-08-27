package unilever.coachingform;

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

public class DSRSetelahActivity extends AppCompatActivity {
    Button next;
    Button sebelum, saat;
    EditText coach, coachee, area, distributor, text_satu, text_dua;
    TextView date;
    boolean bahasa = false;
    boolean english = false;
    String job, coach_email, coachee_email, text_area, text_distributor = "";
    String coachingSessionID = "";
    RadioButton satu, dua;
    boolean status_1, status_2 = false;
    final List<CoachingQuestionAnswerEntity> coachingQAs = new ArrayList<>();
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.next) {
                saveQA();
                CoachingQuestionAnswerDAO.insertCoachingQA(coachingQAs, new CoachingQuestionAnswerDAO.InsertCoachingQAListener() {
                    @Override
                    public void onInsertQuestionAnswerCompleted(boolean isSuccess) {
                        if (isSuccess) {
                            Intent intent = new Intent(DSRSetelahActivity.this, CoachingSummaryDSRActivity.class);
                            intent.putExtra("coach", coach_email);
                            intent.putExtra("job", job);
                            intent.putExtra("coachee", coachee_email);
                            intent.putExtra("bahasa", bahasa);
                            intent.putExtra("english", english);
                            intent.putExtra("area", area.getText().toString());
                            intent.putExtra("distributor", distributor.getText().toString());
                            intent.putExtra("id", coachingSessionID);
                            startActivity(intent);
                        } else {
                            Toast.makeText(DSRSetelahActivity.this, "Failed to save the data!!!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else if (v.getId() == R.id.on_coaching) {
                Intent intent = new Intent(DSRSetelahActivity.this, DSRSaatActivity.class);
                intent.putExtra("coach", coach_email);
                intent.putExtra("job", job);
                intent.putExtra("coachee", coachee_email);
                intent.putExtra("bahasa",bahasa);
                intent.putExtra("english", english);
                intent.putExtra("area", area.getText().toString());
                intent.putExtra("distributor", distributor.getText().toString());
                intent.putExtra("id", coachingSessionID);
                startActivity(intent);
            } else if (v.getId() == R.id.before_coaching) {
                Intent intent = new Intent(DSRSetelahActivity.this, DSRSebelumActivity.class);
                intent.putExtra("coach", coach_email);
                intent.putExtra("job", job);
                intent.putExtra("coachee", coachee_email);
                intent.putExtra("bahasa",bahasa);
                intent.putExtra("english", english);
                intent.putExtra("area", area.getText());
                intent.putExtra("distributor", distributor.getText());
                intent.putExtra("id", coachingSessionID);
                startActivity(intent);
            } else if (v.getId() == R.id.dsr_setelah_1) {
                if(status_1) {
                    satu.setChecked(false);
                    status_1 = false;
                } else {
                    status_1 = true;
                    satu.setChecked(true);
                }
            } else if (v.getId() == R.id.dsr_setelah_2) {
                if(status_2) {
                    dua.setChecked(false);
                    status_2 = false;
                } else {
                    status_2 = true;
                    dua.setChecked(true);
                }
            }
        }
    };

    private void saveQA() {
        addingQA("","dsr_setelah_1",status_1,text_satu.getText().toString(),true);
        addingQA("","dsr_setelah_2",status_2,text_dua.getText().toString(),true);
    }

    private void addingQA(String s, String dsr_sebelum_1, boolean status_1, String remarks, boolean b) {
        CoachingQuestionAnswerEntity coachingQA = new CoachingQuestionAnswerEntity();
        coachingQA.setId(RealmUtil.generateID());
        coachingQA.setCoachingSessionID(coachingSessionID);
        coachingQA.setColumnID(s);
        coachingQA.setQuestionID(dsr_sebelum_1);
        coachingQA.setTextAnswer(remarks);
        coachingQA.setTickAnswer(status_1);
        coachingQA.setHasTickAnswer(b);
        coachingQAs.add(coachingQA);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtra(getIntent().getExtras());
        if(english) {
            setContentView(R.layout.activity_dsr_setelah_english);
        } else {
            setContentView(R.layout.activity_dsr_setelah_bahasa);
        }
        next = (Button) findViewById(R.id.next);
        sebelum = (Button) findViewById(R.id.before_coaching);
        saat = (Button) findViewById(R.id.on_coaching);
        coach = (EditText) findViewById(R.id.coach);
        coachee = (EditText) findViewById(R.id.coachee);
        area = (EditText) findViewById(R.id.area);
        distributor = (EditText) findViewById(R.id.distributor);
        satu = (RadioButton) findViewById(R.id.dsr_setelah_1);
        dua = (RadioButton) findViewById(R.id.dsr_setelah_2);
        text_satu = (EditText) findViewById(R.id.remarks_dsr_setelah_1);
        text_dua = (EditText) findViewById(R.id.remarks_dsr_setelah_2);
        date = (TextView) findViewById(R.id.date);
        date.setText(SharedPreferenceUtil.getString(ConstantUtil.SP_DATE));
        coach.setText(coach_email);
        coach.setEnabled(false);
        coachee.setText(coachee_email);
        coachee.setEnabled(false);
        area.setText(text_area);
        area.setEnabled(false);
        distributor.setText(text_distributor);
        distributor.setEnabled(false);
        next.setOnClickListener(onClick);
        sebelum.setOnClickListener(onClick);
        saat.setOnClickListener(onClick);
        satu.setOnClickListener(onClick);
        dua.setOnClickListener(onClick);
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
}
