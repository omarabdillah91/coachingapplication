package unilever.coachingform;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import adapter.DSRSaatQuestionAdapter;
import adapter.QuestionAdapter;

public class DSRSaatActivity extends AppCompatActivity {
    private ArrayList<String> questions = new ArrayList<>();
    private RecyclerView recyclerView;
    private DSRSaatQuestionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsr_saat_bahasa);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        prepareData();
        mAdapter = new DSRSaatQuestionAdapter(questions);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

    private void prepareData() {
        questions.add(getResources().getString(R.string.bahasa_dsr_saat_1));
        questions.add(getResources().getString(R.string.bahasa_dsr_saat_2));
        questions.add(getResources().getString(R.string.bahasa_dsr_saat_3a));
        questions.add(getResources().getString(R.string.bahasa_dsr_saat_3b));
        questions.add(getResources().getString(R.string.bahasa_dsr_saat_3c));
        questions.add(getResources().getString(R.string.bahasa_dsr_saat_3d));
        questions.add(getResources().getString(R.string.bahasa_dsr_saat_3e));
        questions.add(getResources().getString(R.string.bahasa_dsr_saat_4));
        questions.add(getResources().getString(R.string.bahasa_dsr_saat_5));
        questions.add(getResources().getString(R.string.bahasa_dsr_saat_6));
    }

}
