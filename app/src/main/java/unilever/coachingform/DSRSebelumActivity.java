package unilever.coachingform;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dao.CoachingQuestionAnswerDAO;
import dao.CoachingSessionDAO;
import entity.CoachingQuestionAnswerEntity;
import utility.RealmUtil;

public class DSRSebelumActivity extends AppCompatActivity {
    Button next;
    Button setelah, saat;
    EditText coach, coachee, area, distributor;
    boolean bahasa = false;
    boolean english = false;
    String job, coach_email, coachee_email = "";
    String coachingSessionID = "";
    RadioButton satu, dua, tiga, empat_a, empat_b, empat_c, empat_d, empat_e;
    boolean status_1,status_2,status_3,status_4a,status_4b,status_4c,status_4d,status_4e = false;
    EditText text_satu, text_dua, text_tiga, text_empat_a, text_empat_b, text_empat_c, text_empat_d, text_empat_e;
    final List<CoachingQuestionAnswerEntity> coachingQAs = new ArrayList<>();
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.next) {
                CoachingSessionDAO.updateDistributorStoreArea(coachingSessionID, distributor.getText().toString(), area.getText().toString(),
                        "", new CoachingSessionDAO.UpdateCoachingListener() {
                            @Override
                            public void onGuidelineUpdated(boolean isSuccess) {
                                if (isSuccess) {
                                    saveQA();
                                } else {
                                    Toast.makeText(DSRSebelumActivity.this, "Failed to save the data!!!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else if (v.getId() == R.id.on_coaching) {
                Intent intent = new Intent(DSRSebelumActivity.this, DSRSaatActivity.class);
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
                Intent intent = new Intent(DSRSebelumActivity.this, DSRSetelahActivity.class);
                intent.putExtra("coach", coach.getText().toString());
                intent.putExtra("job", job);
                intent.putExtra("coachee", coachee.getText().toString());
                intent.putExtra("bahasa",bahasa);
                intent.putExtra("english", english);
                intent.putExtra("area", area.getText().toString());
                intent.putExtra("distributor", distributor.getText().toString());
                intent.putExtra("id", coachingSessionID);
                startActivity(intent);
            } else if (v.getId() == R.id.dsr_sebelum_1) {
                if(status_1) {
                    satu.setChecked(false);
                    status_1 = false;
                } else {
                    status_1 = true;
                    satu.setChecked(true);
                }
            } else if (v.getId() == R.id.dsr_sebelum_2) {
                if(status_2) {
                    dua.setChecked(false);
                    status_2 = false;
                } else {
                    status_2 = true;
                    dua.setChecked(true);
                }
            } else if (v.getId() == R.id.dsr_sebelum_3) {
                if(status_3) {
                    tiga.setChecked(false);
                    status_3 = false;
                } else {
                    status_3 = true;
                    tiga.setChecked(true);
                }
            } else if (v.getId() == R.id.dsr_sebelum_4a) {
                if(status_4a) {
                    empat_a.setChecked(false);
                    status_4a = false;
                } else {
                    status_4a = true;
                    empat_b.setChecked(true);
                }
            } else if (v.getId() == R.id.dsr_sebelum_4b) {
                if(status_4b) {
                    empat_b.setChecked(false);
                    status_4b = false;
                } else {
                    status_4b = true;
                    empat_b.setChecked(true);
                }
            } else if (v.getId() == R.id.dsr_sebelum_4c) {
                if(status_4c) {
                    empat_c.setChecked(false);
                    status_4c = false;
                } else {
                    status_4c = true;
                    empat_c.setChecked(true);
                }
            } else if (v.getId() == R.id.dsr_sebelum_4d) {
                if(status_4d) {
                    empat_d.setChecked(false);
                    status_4d = false;
                } else {
                    status_4d = true;
                    empat_d.setChecked(true);
                }
            } else if (v.getId() == R.id.dsr_sebelum_4e) {
                if(status_4e) {
                    empat_e.setChecked(false);
                    status_4e = false;
                } else {
                    status_4e = true;
                    empat_e.setChecked(true);
                }
            }
        }
    };

    private void saveQA() {
        addingQA("","dsr_sebelum_1",status_1,text_satu.getText().toString(),true);
        addingQA("","dsr_sebelum_2",status_2,text_dua.getText().toString(),true);
        addingQA("","dsr_sebelum_3",status_3,text_tiga.getText().toString(),true);
        addingQA("","dsr_sebelum_4a",status_4a,text_empat_a.getText().toString(),true);
        addingQA("","dsr_sebelum_4b",status_4b,text_empat_b.getText().toString(),true);
        addingQA("","dsr_sebelum_4c",status_4c,text_empat_c.getText().toString(),true);
        addingQA("","dsr_sebelum_4d",status_4d,text_empat_d.getText().toString(),true);
        addingQA("","dsr_sebelum_4e",status_4e,text_empat_e.getText().toString(),true);
        CoachingQuestionAnswerDAO.insertCoachingQA(coachingQAs, new CoachingQuestionAnswerDAO.InsertCoachingQAListener() {
            @Override
            public void onInsertQuestionAnswerCompleted(boolean isSuccess) {
                if(isSuccess) {
                    Intent intent = new Intent(DSRSebelumActivity.this, DSRSaatActivity.class);
                    intent.putExtra("coach", coach.getText().toString());
                    intent.putExtra("job", job);
                    intent.putExtra("coachee", coachee.getText().toString());
                    intent.putExtra("bahasa",bahasa);
                    intent.putExtra("english", english);
                    intent.putExtra("area", area.getText().toString());
                    intent.putExtra("distributor", distributor.getText().toString());
                    intent.putExtra("id", coachingSessionID);
                    startActivity(intent);
                } else {
                    Toast.makeText(DSRSebelumActivity.this, "Failed to save the data!!!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
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
            setContentView(R.layout.activity_dsr_sebelum_english);
        } else {
            setContentView(R.layout.activity_dsr_sebelum_bahasa);
        }
        next = (Button) findViewById(R.id.next);
        setelah = (Button) findViewById(R.id.after_coaching);
        saat = (Button) findViewById(R.id.on_coaching);
        coach = (EditText) findViewById(R.id.coach);
        coachee = (EditText) findViewById(R.id.coachee);
        area = (EditText) findViewById(R.id.area);
        distributor = (EditText) findViewById(R.id.distributor);
        satu = (RadioButton) findViewById(R.id.dsr_sebelum_1);
        dua = (RadioButton) findViewById(R.id.dsr_sebelum_2);
        tiga = (RadioButton) findViewById(R.id.dsr_sebelum_3);
        empat_a  = (RadioButton) findViewById(R.id.dsr_sebelum_4a);
        empat_b = (RadioButton) findViewById(R.id.dsr_sebelum_4b);
        empat_c = (RadioButton) findViewById(R.id.dsr_sebelum_4c);
        empat_d = (RadioButton) findViewById(R.id.dsr_sebelum_4d);
        empat_e = (RadioButton) findViewById(R.id.dsr_sebelum_4e);
        text_satu = (EditText) findViewById(R.id.remarks_dsr_sebelum_1);
        text_dua = (EditText) findViewById(R.id.remarks_dsr_sebelum_2);
        text_tiga = (EditText) findViewById(R.id.remarks_dsr_sebelum_3);
        text_empat_a = (EditText) findViewById(R.id.remarks_dsr_sebelum_4a);
        text_empat_b = (EditText) findViewById(R.id.remarks_dsr_sebelum_4c);
        text_empat_c = (EditText) findViewById(R.id.remarks_dsr_sebelum_4d);
        text_empat_e = (EditText) findViewById(R.id.remarks_dsr_sebelum_4e);
        coach.setText(coach_email);
        coach.setEnabled(false);
        coachee.setText(coachee_email);
        coachee.setEnabled(false);
        next.setOnClickListener(onClick);
        setelah.setOnClickListener(onClick);
        saat.setOnClickListener(onClick);
        satu.setOnClickListener(onClick);
        dua.setOnClickListener(onClick);
        tiga.setOnClickListener(onClick);
        empat_a.setOnClickListener(onClick);
        empat_b.setOnClickListener(onClick);
        empat_c.setOnClickListener(onClick);
        empat_d.setOnClickListener(onClick);
        empat_e.setOnClickListener(onClick);
    }

    private void getExtra(Bundle bundle) {
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
        if(bundle.getString("id") != null) {
            coachingSessionID = bundle.getString("id");
        }
    }

}
