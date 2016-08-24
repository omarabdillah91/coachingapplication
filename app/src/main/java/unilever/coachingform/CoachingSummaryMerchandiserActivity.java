package unilever.coachingform;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dao.CoachingQuestionAnswerDAO;
import entity.CoachingQuestionAnswerEntity;
import utility.RealmUtil;

public class CoachingSummaryMerchandiserActivity extends AppCompatActivity {
    Button next;
    EditText coach, coachee, store, summary_1, summary_2, summary_3;
    boolean bahasa = false;
    boolean english = false;
    String job, coach_email, coachee_email, text_store = "";
    String coachingSessionID = "";
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
                            Intent intent = new Intent(CoachingSummaryMerchandiserActivity.this, ProfileActivity.class);
                            intent.putExtra("coach", coach.getText().toString());
                            intent.putExtra("job", job);
                            intent.putExtra("coachee", coachee.getText().toString());
                            startActivity(intent);
                        } else {
                            Toast.makeText(CoachingSummaryMerchandiserActivity.this, "Failed to save the data!!!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

    };

    private void saveQA() {
        addingQA("","dsr_summary_1",false,summary_1.getText().toString(),false);
        addingQA("","dsr_summary_2",false,summary_2.getText().toString(),false);
        addingQA("","dsr_summary_3",false,summary_3.getText().toString(),false);
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
            setContentView(R.layout.activity_coaching_summary_merchandiser_bahasa);
        } else {
            setContentView(R.layout.activity_coaching_summary_merchandiser_english);
        }
        next = (Button) findViewById(R.id.next);
        coach = (EditText) findViewById(R.id.coach);
        coachee = (EditText) findViewById(R.id.coachee);
        store = (EditText) findViewById(R.id.store);
        summary_1 = (EditText) findViewById(R.id.edittext_summary_1);
        summary_2 = (EditText) findViewById(R.id.edittext_summary_2);
        summary_3 = (EditText) findViewById(R.id.edittext_summary_3);
        coach.setText(coach_email);
        coach.setEnabled(false);
        coachee.setText(coachee_email);
        coachee.setEnabled(false);
        store.setText(text_store);
        store.setEnabled(false);
        next.setOnClickListener(onClick);
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
            if(bundle.getString("store") != null) {
                text_store = bundle.getString("store");
            }
            if(bundle.getString("id") != null) {
                coachingSessionID = bundle.getString("id");
            }
        }
    }

}
