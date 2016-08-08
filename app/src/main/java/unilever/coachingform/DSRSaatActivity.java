package unilever.coachingform;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import adapter.CustomerHeaderAdapter;

public class DSRSaatActivity extends AppCompatActivity {

    static final String[] numbers = new String[] {"1","2","3","4","5","6","7","8","9","10"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsr_saat);


    }

}
