package unilever.coachingform;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import adapter.QuestionAdapter;

public class MerchandiserActivity extends AppCompatActivity {
    private ArrayList<String> questions = new ArrayList<>();
    private RecyclerView recyclerView;
    private QuestionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchandiser_bahasa);
        //recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        prepareData();
//        mAdapter = new QuestionAdapter(questions);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(mAdapter);

    }

    public void prepareData() {
        questions.add(getResources().getString(R.string.bahasa_fa_title_1));
        questions.add(getResources().getString(R.string.bahasa_fa_1a));
        questions.add(getResources().getString(R.string.bahasa_fa_1b));
        questions.add(getResources().getString(R.string.bahasa_fa_title_2));
        questions.add(getResources().getString(R.string.bahasa_fa_2a));
        questions.add(getResources().getString(R.string.bahasa_fa_2b));
        questions.add(getResources().getString(R.string.bahasa_fa_title_3));
        questions.add(getResources().getString(R.string.bahasa_fa_3a));
        questions.add(getResources().getString(R.string.bahasa_fa_title_4));
        questions.add(getResources().getString(R.string.bahasa_fa_4a));
        questions.add(getResources().getString(R.string.bahasa_fa_title_5));
        questions.add(getResources().getString(R.string.bahasa_fa_5a));
        questions.add(getResources().getString(R.string.bahasa_fa_title_6));
        questions.add(getResources().getString(R.string.bahasa_fa_6a));
        questions.add(getResources().getString(R.string.bahasa_fa_6a));
        questions.add(getResources().getString(R.string.bahasa_fa_title_6_1));
        questions.add(getResources().getString(R.string.bahasa_fa_6c));
        questions.add(getResources().getString(R.string.bahasa_fa_6d));
        questions.add(getResources().getString(R.string.bahasa_fa_title_6_2));
        questions.add(getResources().getString(R.string.bahasa_rpi));
        questions.add(getResources().getString(R.string.bahasa_fa_title_7));
        questions.add(getResources().getString(R.string.bahasa_fa_7a));
        questions.add(getResources().getString(R.string.bahasa_fa_title_7_1));
        questions.add(getResources().getString(R.string.bahasa_fa_7b));
        questions.add(getResources().getString(R.string.bahasa_fa_7c));
    }

}
