package unilever.coachingform;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dao.CoachingQuestionAnswerDAO;
import dao.CoachingSessionDAO;
import entity.CoachingQuestionAnswerEntity;
import utility.ConstantUtil;
import utility.RealmUtil;
import utility.SharedPreferenceUtil;

public class ASMPullReportActivity extends AppCompatActivity {
    Context context;
    Button next;
    Button habbit;
    TextView date;
    EditText coach, coachee, area;
    boolean bahasa = false;
    boolean english = false;
    String job, coach_email, coachee_email, text_area = "";
    String coachingSessionID = "";
    final List<CoachingQuestionAnswerEntity> coachingQAs = new ArrayList<>();
    boolean status_1[][] = new boolean[9][5];
    RadioButton radio_1[][] = new RadioButton[9][5];
    int radio_id_1[][] = new int[9][5];
    EditText remarks_1[][] = new EditText[9][5];
    boolean status_2[][] = new boolean[20][3];
    RadioButton radio_2[][] = new RadioButton[20][3];
    EditText remarks_2[][] = new EditText[20][3];
    int radio_id_2[][] = new int[20][3];
    String index[] = new String[]{"","a","b","c","d"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtra(getIntent().getExtras());
        if(english) {
            setContentView(R.layout.activity_asmpull_report_english);
        } else {
            setContentView(R.layout.activity_asmpull_report_bahasa);
        }
        next = (Button) findViewById(R.id.next);
        habbit = (Button) findViewById(R.id.habbit_coaching);
        coach = (EditText) findViewById(R.id.coach);
        coachee = (EditText) findViewById(R.id.coachee);
        area = (EditText) findViewById(R.id.area);
        coach.setText(coach_email);
        coach.setEnabled(false);
        coachee.setText(coachee_email);
        coachee.setEnabled(false);
        date = (TextView) findViewById(R.id.date);
        date.setText(SharedPreferenceUtil.getString(ConstantUtil.SP_DATE));
        habbit.setOnClickListener(onClick);
        next.setOnClickListener(onClick);
        for(int i = 1; i < 9;i++) {
            for (int j = 1; j < 5; j++) {
                int id = getID("asm_pull_report_1_" + i+"_"+index[j]);
                radio_1[i][j] = (RadioButton) findViewById(id);
                radio_id_1[i][j] = id;
                radio_1[i][j].setOnClickListener(onRadioClickSatu);
                int remarks_id = getID("remarks_report_1_" + i+"_"+index[j]);
                remarks_1[i][j] = (EditText) findViewById(remarks_id);
            }
        }
        for(int i = 1; i < 20;i++) {
            for (int j = 1; j < 3; j++) {
                int id = getID("asm_pull_report_2_" + i+"_"+index[j]);
                radio_2[i][j] = (RadioButton) findViewById(id);
                radio_id_2[i][j] = id;
                radio_2[i][j].setOnClickListener(onRadioClickDua);
                int remarks_id = getID("remarks_report_2_" + i+"_"+index[j]);
                remarks_2[i][j] = (EditText) findViewById(remarks_id);
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

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.next) {
                CoachingSessionDAO.updateDistributorStoreArea(coachingSessionID, "", area.getText().toString(),
                        "", new CoachingSessionDAO.UpdateCoachingListener() {
                            @Override
                            public void onGuidelineUpdated(boolean isSuccess) {
                                if (isSuccess) {
                                    saveQA();
                                } else {
                                    Toast.makeText(ASMPullReportActivity.this, "Failed to save the data!!!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else if (v.getId() == R.id.habbit_coaching) {
                Intent intent = new Intent(ASMPullReportActivity.this, ASMPullHabbitActivity.class);
                intent.putExtra("coach", coach.getText().toString());
                intent.putExtra("job", job);
                intent.putExtra("coachee", coachee.getText().toString());
                intent.putExtra("bahasa",bahasa);
                intent.putExtra("english", english);
                intent.putExtra("area", area.getText().toString());
                intent.putExtra("id", coachingSessionID);
                startActivity(intent);
            }
        }
    };

    private void saveQA() {
        for(int i = 1; i < status_1.length;i++) {
            for (int j = 1;j < status_1[0].length;j++) {
                addingQA("","asm_pull_report_1_"+i+"_"+index[j], status_1[i][j], remarks_1[i][j].getText().toString(), true);
            }
        }
        for(int i = 1; i < status_2.length;i++) {
            for (int j = 1;j < status_2[0].length;j++) {
                addingQA("","asm_pull_report_2_"+i+"_"+index[j], status_2[i][j], remarks_2[i][j].getText().toString(), true);
            }
        }
        CoachingQuestionAnswerDAO.insertCoachingQA(coachingQAs, new CoachingQuestionAnswerDAO.InsertCoachingQAListener() {
            @Override
            public void onInsertQuestionAnswerCompleted(boolean isSuccess) {
                if (isSuccess) {
                    Intent intent = new Intent(ASMPullReportActivity.this, ASMPullHabbitActivity.class);
                    intent.putExtra("coach", coach.getText().toString());
                    intent.putExtra("job", job);
                    intent.putExtra("coachee", coachee.getText().toString());
                    intent.putExtra("bahasa", bahasa);
                    intent.putExtra("english", english);
                    intent.putExtra("area", area.getText().toString());
                    intent.putExtra("id", coachingSessionID);
                    startActivity(intent);
                } else {
                    Toast.makeText(ASMPullReportActivity.this, "Failed to save the data!!!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    View.OnClickListener onRadioClickSatu = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for(int i = 1; i < 9;i++) {
                for (int j = 1; j < 5; j++) {
                    if(v.getId() == radio_id_1[i][j]) {
                        if(status_1[i][j]) {
                            radio_1[i][j].setChecked(false);
                            status_1[i][j] = false;
                        } else {
                            status_1[i][j] = true;
                            radio_1[i][j].setChecked(true);
                        }
                        break;
                    }
                }
            }
        }
    };

    View.OnClickListener onRadioClickDua = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for(int i = 1; i < 20;i++) {
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

    private int getID(String s) {
        return getResources().getIdentifier(s, "id", this.getPackageName());
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
}
