package unilever.coachingform;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import adapter.CoacheeHistoryAdapter;
import model.CoacheeHistory;
import service.CoachingSessionService;

public class CoacheeHistoryActivity extends AppCompatActivity {
    String coach, job, coachee, coaching = "";
    String coachingSessionID = "";
    boolean bahasa, english = false;
    Button next;
    ListView history;
    Bundle profile;
    public ProgressDialog progressBar;
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.next) {
                goNext();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coachee_history);
        if (getIntent().getExtras() != null) {
            getExtra(getIntent().getExtras());
        }
        next = (Button) findViewById(R.id.next);
        history = (ListView) findViewById(R.id.listView);
        next.setOnClickListener(onClick);
        if(isNetworkAvailable()) {
            getData();
        } else {
            Toast.makeText(CoacheeHistoryActivity.this, "No internet access to load the the data!!!",
                    Toast.LENGTH_SHORT).show();
        }

    }

    private void getData() {
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.setMessage("Loading .....");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.show();
        Log.d("CoacheeHistory", coachee);
        CoachingSessionService.getCoacheeHistory(coachee, new CoachingSessionService.GetCoacheeHistoryListener() {
            @Override
            public void onCoacheeHistoryReceived(List<CoacheeHistory> coacheeHistories) {
                progressBar.hide();
                if(coacheeHistories.size() >0) {
                    onHistoryReceived(coacheeHistories);
                } else {
                    Toast.makeText(CoacheeHistoryActivity.this, "No coachee history found!!!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void getExtra(Bundle bundle) {
        if(bundle != null) {
            if(bundle.getString("coach") != null) {
                coach = bundle.getString("coach");
            }
            if(bundle.getString("coachee") != null) {
                coachee = bundle.getString("coachee");
            }
            if(bundle.getString("job") != null) {
                job = bundle.getString("job");
            }
            if(bundle.getString("coaching") != null) {
                coaching = bundle.getString("coaching");
            }
            if(bundle.getBoolean("english")) {
                english = bundle.getBoolean("english");
            }else {
                bahasa = bundle.getBoolean("bahasa");
            }
            if(bundle.getBundle("profile") != null) {
                profile = bundle.getBundle("profile");
            }
            if(bundle.getString("id") != null) {
                coachingSessionID = bundle.getString("id");
            }
        }
    }

    public void onHistoryReceived(List<CoacheeHistory> coacheeHistories) {
        CoacheeHistoryAdapter adapter = new CoacheeHistoryAdapter(this,
                R.layout.coachee_history, coacheeHistories);
        history.setAdapter(adapter);
    }
    private void goNext() {
        if(coaching.equalsIgnoreCase("rsm")) {
            Intent intent = new Intent(CoacheeHistoryActivity.this, RSMCoachingActivity.class);
            intent.putExtra("coach", coach);
            intent.putExtra("job", job);
            intent.putExtra("coachee", coachee);
            intent.putExtra("bahasa",bahasa);
            intent.putExtra("english",english);
            intent.putExtra("id", coachingSessionID);
            startActivity(intent);
        } else if (coaching.equalsIgnoreCase("asm_push")) {
            Intent intent = new Intent(CoacheeHistoryActivity.this, RSMCoachingActivity.class);
            intent.putExtra("coach", coach);
            intent.putExtra("job", job);
            intent.putExtra("coachee", coachee);
            intent.putExtra("bahasa",bahasa);
            intent.putExtra("english",english);
            intent.putExtra("id", coachingSessionID);
            startActivity(intent);
        } else if (coaching.equalsIgnoreCase("asm_pull")) {
            Intent intent = new Intent(CoacheeHistoryActivity.this, RSMCoachingActivity.class);
            intent.putExtra("coach", coach);
            intent.putExtra("job", job);
            intent.putExtra("coachee", coachee);
            intent.putExtra("bahasa",bahasa);
            intent.putExtra("english",english);
            intent.putExtra("id", coachingSessionID);
            startActivity(intent);
        } else if (coaching.equalsIgnoreCase("dsr")) {
            Intent intent = new Intent(CoacheeHistoryActivity.this, DSRSebelumActivity.class);
            intent.putExtra("coach", coach);
            intent.putExtra("job", job);
            intent.putExtra("coachee", coachee);
            intent.putExtra("bahasa",bahasa);
            intent.putExtra("english",english);
            intent.putExtra("id", coachingSessionID);
            startActivity(intent);
        } else if (coaching.equalsIgnoreCase("dts")) {
            Intent intent = new Intent(CoacheeHistoryActivity.this, RSMCoachingActivity.class);
            intent.putExtra("coach", coach);
            intent.putExtra("job", job);
            intent.putExtra("coachee", coachee);
            intent.putExtra("bahasa",bahasa);
            intent.putExtra("english",english);
            intent.putExtra("id", coachingSessionID);
            startActivity(intent);
        } else if (coaching.equalsIgnoreCase("fa")) {
            Intent intent = new Intent(CoacheeHistoryActivity.this, MerchandiserActivity.class);
            intent.putExtra("coach", coach);
            intent.putExtra("job", job);
            intent.putExtra("coachee", coachee);
            intent.putExtra("bahasa",bahasa);
            intent.putExtra("english",english);
            intent.putExtra("id", coachingSessionID);
            startActivity(intent);
        } else if (coaching.equalsIgnoreCase("sr")) {
            Intent intent = new Intent(CoacheeHistoryActivity.this, RSMCoachingActivity.class);
            intent.putExtra("coach", coach);
            intent.putExtra("job", job);
            intent.putExtra("coachee", coachee);
            intent.putExtra("bahasa",bahasa);
            intent.putExtra("english",english);
            intent.putExtra("id", coachingSessionID);
            startActivity(intent);
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
