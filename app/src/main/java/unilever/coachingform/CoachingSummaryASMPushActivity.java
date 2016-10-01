package unilever.coachingform;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dao.CoachingQuestionAnswerDAO;
import dao.CoachingSessionDAO;
import entity.CoachingQuestionAnswerEntity;
import service.SynchronizationService;
import utility.ConstantUtil;
import utility.PDFUtil;
import utility.RealmUtil;
import utility.SharedPreferenceUtil;

public class CoachingSummaryASMPushActivity extends AppCompatActivity {
    private static final String TAG = "SummaryASMPushActivity";
    Button next;
    EditText coach, coachee, area, distributor, summary_1, summary_2, summary_3;
    TextView date;
    boolean bahasa = false;
    boolean english = false;
    String job, coach_email, coachee_email, text_area, text_distributor = "";
    String coachingSessionID = "";
    final List<CoachingQuestionAnswerEntity> coachingQAs = new ArrayList<>();
    public ProgressDialog progressBar;
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.next) {
                progressBar = new ProgressDialog(v.getContext());
                progressBar.setCancelable(false);
                progressBar.setMessage("Loading .....");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.setProgress(0);
                progressBar.show();
                CoachingSessionDAO.updateAction(coachingSessionID, summary_3.getText().toString(),
                        new CoachingSessionDAO.UpdateCoachingListener() {
                            @Override
                            public void onGuidelineUpdated(boolean isSuccess) {
                                saveQA();
                            }
                        });

            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtra(getIntent().getExtras());
        if(english) {
            setContentView(R.layout.activity_coaching_summary_asmpush_bahasa);
        } else {
            setContentView(R.layout.activity_coaching_summary_asmpush_bahasa);
        }
        next = (Button) findViewById(R.id.next);
        coach = (EditText) findViewById(R.id.coach);
        coachee = (EditText) findViewById(R.id.coachee);
        area = (EditText) findViewById(R.id.area);
        distributor = (EditText) findViewById(R.id.distributor);
        summary_1 = (EditText) findViewById(R.id.edittext_summary_1);
        summary_2 = (EditText) findViewById(R.id.edittext_summary_2);
        summary_3 = (EditText) findViewById(R.id.edittext_summary_3);
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
    }

    private void saveQA() {
        addingQA("","asm_push_summary_1",false,summary_1.getText().toString(),false);
        addingQA("","asm_push_summary_2",false,summary_2.getText().toString(),false);
        addingQA("","asm_push_summary_3",false, summary_3.getText().toString(), false);
        CoachingSessionDAO.updateAction(coachingSessionID, summary_3.getText().toString(),
                new CoachingSessionDAO.UpdateCoachingListener() {
                    @Override
                    public void onGuidelineUpdated(boolean isSuccess) {
                        if (isSuccess) {
                            insert();
                        } else {
                            Toast.makeText(CoachingSummaryASMPushActivity.this, "Failed to save the data!!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void insert() {
        CoachingQuestionAnswerDAO.insertCoachingQA(coachingQAs, new CoachingQuestionAnswerDAO.InsertCoachingQAListener() {
            @Override
            public void onInsertQuestionAnswerCompleted(boolean isSuccess) {
                if (isSuccess) {
                    if (isNetworkAvailable()) {
                        SynchronizationService.syncCoachingSession(coachingSessionID, new SynchronizationService.SyncCoachingListener() {
                            @Override
                            public void onSyncCoachingCompleted(boolean isSucceed) {
                                CoachingSessionDAO.updateSubmitted(coachingSessionID, true,
                                        new CoachingSessionDAO.UpdateCoachingListener() {
                                            @Override
                                            public void onGuidelineUpdated(boolean isSuccess) {
                                                PDFUtil.createPDF(coachingSessionID, new PDFUtil.GeneratePDFListener() {
                                                    @Override
                                                    public void onPDFGenerated(boolean isSuccess) {
                                                        if (isSuccess) {
                                                            progressBar.dismiss();
                                                            SynchronizationService.sendEmail(coachingSessionID, CoachingSummaryASMPushActivity.this);
                                                        } else {
                                                            Toast.makeText(CoachingSummaryASMPushActivity.this, "Failed to generate PDF!!",
                                                                    Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }
                                        });
                            }
                        });
                    } else {
                        Toast.makeText(CoachingSummaryASMPushActivity.this, "Your coaching from will be saved locally",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CoachingSummaryASMPushActivity.this, ProfileActivity.class);
                        intent.putExtra("coach", coach.getText().toString());
                        intent.putExtra("job", job);
                        intent.putExtra("coachee", coachee.getText().toString());
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(CoachingSummaryASMPushActivity.this, "Failed to save the data!!!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ConstantUtil.REQ_SEND_EMAIL){
            Log.d(TAG, "Result Received");
            Intent intent = new Intent(CoachingSummaryASMPushActivity.this, ProfileActivity.class);
            intent.putExtra("coach", coach.getText().toString());
            intent.putExtra("job", job);
            intent.putExtra("coachee", coachee.getText().toString());
            startActivity(intent);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
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
