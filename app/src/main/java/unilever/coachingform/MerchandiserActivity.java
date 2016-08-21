package unilever.coachingform;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioButton;

import java.util.ArrayList;

public class MerchandiserActivity extends AppCompatActivity {
    private ArrayList<String> questions = new ArrayList<>();
    Button next, back;
    EditText coach, coachee, area, distributor, edit_product_11,edit_product_12,edit_size_11,edit_size_12;
    Button action_1,action_2,action_3,action_4,action_5,action_6,action_7,action_8,action_9,action_10,action_11,action_12;
    Boolean bahasa, english = false;
    String job, coach_email, coachee_email, text_area, text_distributor = "";
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.next) {
                Intent intent = new Intent(MerchandiserActivity.this, CoachingSummaryActivity.class);
                intent.putExtra("coach", coach.getText().toString());
                intent.putExtra("job", job);
                intent.putExtra("coachee", coachee.getText().toString());
                intent.putExtra("bahasa",bahasa);
                intent.putExtra("english", english);
                intent.putExtra("area", area.getText());
                intent.putExtra("distributor", distributor.getText());
                startActivity(intent);
            } else if (v.getId() == R.id.back) {
                Intent intent = new Intent(MerchandiserActivity.this, CoacheeHistoryActivity.class);
                intent.putExtra("coach", coach.getText().toString());
                intent.putExtra("job", job);
                intent.putExtra("coachee", coachee.getText().toString());
                intent.putExtra("bahasa",bahasa);
                intent.putExtra("english", english);
                intent.putExtra("area", area.getText());
                intent.putExtra("distributor", distributor.getText());
                startActivity(intent);
            } else if (v.getId() == R.id.product_1) {
                showQuestion(1);
            } else if (v.getId() == R.id.product_2) {
                showQuestion(2);
            } else if (v.getId() == R.id.product_3) {
                showQuestion(3);
            } else if (v.getId() == R.id.product_4) {
                showQuestion(4);
            } else if (v.getId() == R.id.product_5) {
                showQuestion(5);
            } else if (v.getId() == R.id.product_6) {
                showQuestion(6);
            } else if (v.getId() == R.id.product_7) {
                showQuestion(7);
            } else if (v.getId() == R.id.product_8) {
                showQuestion(8);
            } else if (v.getId() == R.id.product_9) {
                showQuestion(9);
            } else if (v.getId() == R.id.product_10) {
                showQuestion(10);
            } else if (v.getId() == R.id.product_11) {
                showQuestion(11);
            } else if (v.getId() == R.id.product_12) {
                showQuestion(12);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getExtra(getIntent().getExtras());
        if(english) {
            setContentView(R.layout.activity_merchandiser_bahasa);
        } else {
            setContentView(R.layout.activity_merchandiser_bahasa);
        }
        next = (Button) findViewById(R.id.next);
        back = (Button) findViewById(R.id.back);
        action_1 = (Button) findViewById(R.id.product_1);
        action_2 = (Button) findViewById(R.id.product_2);
        action_3 = (Button) findViewById(R.id.product_3);
        action_4 = (Button) findViewById(R.id.product_4);
        action_5 = (Button) findViewById(R.id.product_5);
        action_6 = (Button) findViewById(R.id.product_6);
        action_7 = (Button) findViewById(R.id.product_7);
        action_8 = (Button) findViewById(R.id.product_8);
        action_9 = (Button) findViewById(R.id.product_9);
        action_10 = (Button) findViewById(R.id.product_10);
        action_11 = (Button) findViewById(R.id.product_11);
        action_12 = (Button) findViewById(R.id.product_12);
        coach = (EditText) findViewById(R.id.coach);
        coachee = (EditText) findViewById(R.id.coachee);
        area = (EditText) findViewById(R.id.area);
        distributor = (EditText) findViewById(R.id.distributor);
        coach.setText(coach_email);
        coach.setEnabled(false);
        coachee.setText(coachee_email);
        coachee.setEnabled(false);
        area.setText(text_area);
        area.setEnabled(false);
        distributor.setText(text_distributor);
        distributor.setEnabled(false);
        next.setOnClickListener(onClick);
        back.setOnClickListener(onClick);
        action_1.setOnClickListener(onClick);
        action_2.setOnClickListener(onClick);
        action_3.setOnClickListener(onClick);
        action_4.setOnClickListener(onClick);
        action_5.setOnClickListener(onClick);
        action_6.setOnClickListener(onClick);
        action_7.setOnClickListener(onClick);
        action_8.setOnClickListener(onClick);
        action_9.setOnClickListener(onClick);
        action_10.setOnClickListener(onClick);
        action_11.setOnClickListener(onClick);
        action_12.setOnClickListener(onClick);
    }

    private void showQuestion(int indeks) {
        Log.d("masuk",indeks+"");
        RadioButton radio_1a, radio_1b, radio_2a, radio_2b, radio_3a, radio_4a, radio_5a, radio_7c;
        EditText edit_6a,edit_6b,edit_6c,edit_6d,edit_rpi,edit_7a,edit_7b;
        boolean satu_a,satu_b,dua_a,dua_b,tiga_a,tiga_b,empat_a,empat_b,lima_a,tujuh_c = false;
        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.merchandiser_question_1, null);
        final PopupWindow popupWindow = new PopupWindow(popupView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        popupWindow.setFocusable(true);
        popupWindow.update();
        Button btnDismiss = (Button) popupView.findViewById(R.id.next);
        radio_1a = (RadioButton) popupView.findViewById(R.id.fa_1a);
        radio_1b = (RadioButton) popupView.findViewById(R.id.fa_1b);
        radio_2a = (RadioButton) popupView.findViewById(R.id.fa_2a);
        radio_2b = (RadioButton) popupView.findViewById(R.id.fa_2b);
        radio_3a = (RadioButton) popupView.findViewById(R.id.fa_3a);
        radio_4a = (RadioButton) popupView.findViewById(R.id.fa_4a);
        radio_5a = (RadioButton) popupView.findViewById(R.id.fa_5a);
        radio_7c = (RadioButton) popupView.findViewById(R.id.fa_7c);
        edit_6a = (EditText) popupView.findViewById(R.id.fa_6a);
        edit_6b = (EditText) popupView.findViewById(R.id.fa_6b);
        edit_6c = (EditText) popupView.findViewById(R.id.fa_6c);
        edit_6d = (EditText) popupView.findViewById(R.id.fa_6d);
        edit_7a = (EditText) popupView.findViewById(R.id.fa_7a);
        edit_7b = (EditText) popupView.findViewById(R.id.fa_7b);
        edit_rpi = (EditText) popupView.findViewById(R.id.fa_rpi);
        btnDismiss.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }});
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
        }
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
