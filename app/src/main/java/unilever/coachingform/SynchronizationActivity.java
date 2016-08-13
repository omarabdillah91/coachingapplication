package unilever.coachingform;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import adapter.CoachAdapter;
import dao.CoachingSessionDAO;
import model.Coaching;

public class SynchronizationActivity extends AppCompatActivity implements CoachingSessionDAO.CoachingSessionListener {
    ListView listView;
    Button sync;
    String email = "";
    int job_id = -1;
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.sync) {
                Intent intent = new Intent(SynchronizationActivity.this, ProfileActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("job", job_id);
                startActivity(intent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronization);
        listView = (ListView)findViewById(R.id.listView);
        CoachingSessionDAO.getUnsubmittedCoaching(this);
        sync = (Button) findViewById(R.id.sync);
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            if(bundle.getString("email") != null) {
                email = bundle.getString("first_name");
            }
            if(bundle.getString("job") != null) {
                job_id = bundle.getInt("job");
            }
        }
        sync.setOnClickListener(onClick);

    }

    @Override
    public void onCoachingReceived(List<Coaching> coachingList) {
        CoachAdapter adapter = new CoachAdapter(this,
                R.layout.synchronization_list, coachingList);
        listView.setAdapter(adapter);
    }

    @Override
    public void onCoachingInserted(boolean succees) {
    }
}
