package unilever.coachingform;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.DSRSaatQuestionAdapter;
import adapter.QuestionAdapter;
import dao.CoachingQuestionAnswerDAO;
import entity.CoachingQuestionAnswerEntity;
import utility.RealmUtil;

public class DSRSaatActivity extends AppCompatActivity {
    Context context;
    Button next;
    Button sebelum, setelah;
    EditText coach, coachee, area, distributor, text_satu, text_dua;
    boolean bahasa = false;
    boolean english = false;
    String job, coach_email, coachee_email, text_area, text_distributor = "";
    String coachingSessionID = "";
    boolean status[][] = new boolean[11][11];
    RadioButton radio[][] = new RadioButton[11][11];
    int radio_id[][] = new int[11][11];
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
                            Intent intent = new Intent(DSRSaatActivity.this, DSRSetelahActivity.class);
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
                            Toast.makeText(DSRSaatActivity.this, "Failed to save the data!!!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else if (v.getId() == R.id.before_coaching) {
                Intent intent = new Intent(DSRSaatActivity.this, DSRSebelumActivity.class);
                intent.putExtra("coach", coach.getText().toString());
                intent.putExtra("job", job);
                intent.putExtra("coachee", coachee.getText().toString());
                intent.putExtra("bahasa",bahasa);
                intent.putExtra("english", english);
                intent.putExtra("area", area.getText().toString());
                intent.putExtra("distributor", distributor.getText().toString());
                intent.putExtra("id", coachingSessionID);
                startActivity(intent);
            } else if (v.getId() == R.id.after_coaching) {
                Intent intent = new Intent(DSRSaatActivity.this, DSRSetelahActivity.class);
                intent.putExtra("coach", coach.getText().toString());
                intent.putExtra("job", job);
                intent.putExtra("coachee", coachee.getText().toString());
                intent.putExtra("bahasa",bahasa);
                intent.putExtra("english", english);
                intent.putExtra("area", area.getText().toString());
                intent.putExtra("distributor", distributor.getText().toString());
                intent.putExtra("id", coachingSessionID);
                startActivity(intent);
            }
        }

    };

    private void saveQA() {
        for(int i = 1; i < status.length;i++) {
            for (int j = 1;j < status[0].length;j++) {
                addingQA("customer_"+j, "dsr_saat_"+i, status[i][j], "", true);
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

    View.OnClickListener onRadioClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for(int i = 1; i < 11;i++) {
                for (int j = 1; j < 11; j++) {
                    if(v.getId() == radio_id[i][j]) {
                        Log.d("indeks",i+" "+j);
                        if(status[i][j]) {
                            radio[i][j].setChecked(false);
                            status[i][j] = false;
                        } else {
                            status[i][j] = true;
                            radio[i][j].setChecked(true);
                        }
                        break;
                    }
                }
            }
        }

    };

    private int getID(String s) {
        return getResources().getIdentifier(s, "id", this.getPackageName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtra(getIntent().getExtras());
        if(english) {
            setContentView(R.layout.activity_dsr_saat_english);
        } else {
            setContentView(R.layout.activity_dsr_saat_bahasa);
        }
        next = (Button) findViewById(R.id.next);
        sebelum = (Button) findViewById(R.id.before_coaching);
        setelah = (Button) findViewById(R.id.after_coaching);
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
        for(int i = 1; i < 11;i++) {
            for (int j = 1; j < 11; j++) {
                int id = getID("dsr_saat_" + i + "_" + j);
                radio[i][j] = (RadioButton) findViewById(id);
                radio_id[i][j] = id;
                radio[i][j].setOnClickListener(onRadioClick);
            }
        }
        next.setOnClickListener(onClick);
        sebelum.setOnClickListener(onClick);
        setelah.setOnClickListener(onClick);
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
