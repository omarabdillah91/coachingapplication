package unilever.coachingform;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.CoachAdapter;
import dao.CoachingSessionDAO;
import model.Coaching;
import service.SynchronizationService;
import utility.PDFUtil;

public class SynchronizationActivity extends AppCompatActivity {
    ListView listView;
    Button next;
    String email = "";
    int job = 0;
    private ProgressDialog progressBar;
    CoachAdapter adapter;
    ArrayList<String> coachingSession = new ArrayList<String>();
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.next) {
                Intent intent = new Intent(SynchronizationActivity.this, ProfileActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("job", job);
                startActivity(intent);
            }
        }
    };

    public void onSync(View v)
    {
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.setMessage("Generate PDF .....");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        final String id = (String) v.getTag();
        final LinearLayout vwParentRow = (LinearLayout)v.getParent();
        ListView listView = (ListView) vwParentRow.getParent();
        final int position = listView.getPositionForView(vwParentRow);
        final TextView status = (TextView)vwParentRow.getChildAt(2);
        Button btnChild = (Button)vwParentRow.getChildAt(3);
        if(status.getText().toString().equals("Sent")) {
            Toast.makeText(SynchronizationActivity.this, "The coaching activity has been sent",
                    Toast.LENGTH_SHORT).show();
        } else {
            progressBar.show();
            SynchronizationService.syncCoachingSession(id, new SynchronizationService.SyncCoachingListener() {
                @Override
                public void onSyncCoachingCompleted(boolean isSucceed) {
                    CoachingSessionDAO.updateSubmitted(id, true,
                            new CoachingSessionDAO.UpdateCoachingListener() {
                                @Override
                                public void onGuidelineUpdated(boolean isSuccess) {
                                    PDFUtil.createPDF(id, new PDFUtil.GeneratePDFListener() {
                                        @Override
                                        public void onPDFGenerated(boolean isSuccess) {
                                            progressBar.dismiss();
                                            SynchronizationService.sendEmail(id, SynchronizationActivity.this);
                                            adapter.remove(adapter.getItem(position));
                                        }
                                    });
                                }
                            });
                }
            });

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronization);
        listView = (ListView)findViewById(R.id.listView);
        next = (Button) findViewById(R.id.next);
        CoachingSessionDAO.getUnsubmittedCoaching(new CoachingSessionDAO.GetListCoachingListener() {
            @Override
            public void onUnsubmittedCoachingReceived(List<Coaching> coachingList) {
                onCoachingReceived(coachingList);
            }
        });
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            if(bundle.getString("email") != null) {
                email = bundle.getString("first_name");
            }
            if(bundle.getString("job") != null) {
                job = bundle.getInt("job");
            }
        }
        next.setOnClickListener(onClick);
    }

    public void onCoachingReceived(List<Coaching> coachingList) {
        adapter = new CoachAdapter(this,
                R.layout.synchronization_list, coachingList);
        listView.setAdapter(adapter);
    }
}
