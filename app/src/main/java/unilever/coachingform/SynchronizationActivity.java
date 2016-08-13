package unilever.coachingform;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import adapter.CoachAdapter;
import model.Coaching;

public class SynchronizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronization);
        ListView listView = (ListView)findViewById(R.id.listView);
        Coaching weather_data[] = new Coaching[]
                {
                        new Coaching("A","2016-09-08", "Sent"),
                        new Coaching("B","2016-09-08", "Submitted"),
                        new Coaching("C","2016-09-08", "Submitted"),
                        new Coaching("C","2016-09-08", "Submitted"),
                        new Coaching("A","2016-09-08", "Sent"),
                        new Coaching("B","2016-09-08", "Submitted"),
                        new Coaching("C","2016-09-08", "Submitted"),
                        new Coaching("C","2016-09-08", "Submitted"),
                        new Coaching("A","2016-09-08", "Sent"),
                        new Coaching("B","2016-09-08", "Submitted"),
                        new Coaching("C","2016-09-08", "Submitted"),
                        new Coaching("C","2016-09-08", "Submitted")
                };
        CoachAdapter adapter = new CoachAdapter(this,
                R.layout.synchronization_list, weather_data);
        listView.setAdapter(adapter);
    }

}
