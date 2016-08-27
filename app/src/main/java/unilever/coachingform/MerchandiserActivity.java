package unilever.coachingform;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import dao.CoachingQuestionAnswerDAO;
import dao.CoachingSessionDAO;
import entity.CoachingQuestionAnswerEntity;
import model.MerchandiserAnswer;
import utility.ConstantUtil;
import utility.RealmUtil;
import utility.SharedPreferenceUtil;

public class MerchandiserActivity extends AppCompatActivity {
    private ArrayList<String> questions = new ArrayList<>();
    Button next;
    TextView date;
    EditText coach, coachee, store, edit_product_11,edit_product_12,edit_size_11,edit_size_12;
    EditText edit_6a,edit_6b,edit_6c,edit_6d,edit_rpi,edit_7a,edit_7b;
    Button action_1,action_2,action_3,action_4,action_5,action_6,action_7,action_8,action_9,action_10,action_11,action_12;
    Boolean bahasa, english = false;
    String job, coach_email, coachee_email, text_store = "";
    String coachingSessionID = "";
    boolean satu_a,satu_b,dua_a,dua_b,tiga_a,empat_a,empat_b,lima_a,tujuh_c = false;
    RadioButton radio_1a, radio_1b, radio_2a, radio_2b, radio_3a, radio_4a, radio_5a, radio_7c;
    PopupWindow popupWindow;
    String kom_1 = "";
    String kom_2 = "";
    ArrayList<MerchandiserAnswer> answers = new ArrayList<MerchandiserAnswer>();
    final List<CoachingQuestionAnswerEntity> coachingQAs = new ArrayList<>();
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.next) {
                CoachingSessionDAO.updateDistributorStoreArea(coachingSessionID, "", "",
                        store.getText().toString(), new CoachingSessionDAO.UpdateCoachingListener() {
                            @Override
                            public void onGuidelineUpdated(boolean isSuccess) {
                                if (isSuccess) {
                                    saveQA();
                                } else {
                                    Toast.makeText(MerchandiserActivity.this, "Failed to save the data!!!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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
                if(edit_product_11.getText().toString().isEmpty()|| edit_size_11.getText().toString().isEmpty()) {
                    Toast.makeText(MerchandiserActivity.this, "Fill in the product and size",
                            Toast.LENGTH_SHORT).show();
                } else {
                    edit_product_11.setEnabled(false);
                    edit_size_11.setEnabled(false);
                    showQuestion(11);
                }
            } else if (v.getId() == R.id.product_12) {
                if(!edit_product_12.getText().toString().isEmpty() && !edit_size_12.getText().toString().isEmpty()) {
                    edit_product_12.setEnabled(false);
                    edit_size_12.setEnabled(false);
                    showQuestion(12);
                } else {
                    Toast.makeText(MerchandiserActivity.this, "Fill in the product and size",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private void saveQA() {
        for(MerchandiserAnswer answer : answers) {
            addingQA(answer.getProduct() +"\n"+answer.getSize(), answer.getIndeks(),answer.isSatu_a(),"", true);
            addingQA(answer.getProduct() +"\n"+answer.getSize(), answer.getIndeks(),answer.isSatu_b(),"", true);
            addingQA(answer.getProduct() +"\n"+answer.getSize(), answer.getIndeks(),answer.isDua_a(),"", true);
            addingQA(answer.getProduct()+"\n"+answer.getSize(), answer.getIndeks(),answer.isDua_b(),"", true);
            addingQA(answer.getProduct()+"\n"+answer.getSize(), answer.getIndeks(),answer.isTiga_a(),"", true);
            addingQA(answer.getProduct()+"\n"+answer.getSize(), answer.getIndeks(),answer.isEmpat_a(),"", true);
            addingQA(answer.getProduct()+"\n"+answer.getSize(), answer.getIndeks(),answer.isLima_a(),"", true);
            addingQA(answer.getProduct()+"\n"+answer.getSize(), answer.getIndeks(),false,answer.getEnam_a(), false);
            addingQA(answer.getProduct()+"\n"+answer.getSize(), answer.getIndeks(),false,answer.getEnam_b(), false);
            addingQA(answer.getProduct()+"\n"+answer.getSize(), answer.getIndeks(),false,answer.getEnam_c(), false);
            addingQA(answer.getProduct()+"\n"+answer.getSize(), answer.getIndeks(),false,answer.getEnam_d(), false);
            addingQA(answer.getProduct()+"\n"+answer.getSize(), answer.getIndeks(),false,answer.getRpi(), false);
            addingQA(answer.getProduct()+"\n"+answer.getSize(), answer.getIndeks(),false,answer.getTujuh_a(), false);
            addingQA(answer.getProduct()+"\n"+answer.getSize(), answer.getIndeks(),false,answer.getTujuh_b(), false);
            addingQA(answer.getProduct()+"\n"+answer.getSize(), answer.getIndeks(),answer.isTujuh_c(),"", true);
            addingQA(answer.getProduct()+"\n"+answer.getSize(), "kompetitor_"+answer.getIndeks(),false,answer.getKompetitor(), false);
        }
        CoachingQuestionAnswerDAO.insertCoachingQA(coachingQAs, new CoachingQuestionAnswerDAO.InsertCoachingQAListener() {
            @Override
            public void onInsertQuestionAnswerCompleted(boolean isSuccess) {
                if (isSuccess) {
                    Intent intent = new Intent(MerchandiserActivity.this, CoachingSummaryMerchandiserActivity.class);
                    intent.putExtra("coach", coach.getText().toString());
                    intent.putExtra("job", job);
                    intent.putExtra("coachee", coachee.getText().toString());
                    intent.putExtra("bahasa",bahasa);
                    intent.putExtra("english", english);
                    intent.putExtra("store", store.getText().toString());
                    intent.putExtra("id", coachingSessionID);
                    startActivity(intent);
                } else {
                    Toast.makeText(MerchandiserActivity.this, "Failed to save the data!!!",
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

    View.OnClickListener radioOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.fa_1a) {
                if(satu_a) {
                    radio_1a.setChecked(false);
                    satu_a = false;
                } else {
                    satu_a = true;
                    radio_1a.setChecked(true);
                }
            }
            if (v.getId() == R.id.fa_1b) {
                if(satu_b) {
                    radio_1b.setChecked(false);
                    satu_b = false;
                } else {
                    satu_b = true;
                    radio_1b.setChecked(true);
                }
            }
            if (v.getId() == R.id.fa_2a) {
                if(dua_a) {
                    radio_2a.setChecked(false);
                    satu_b = false;
                } else {
                    dua_a = true;
                    radio_2a.setChecked(true);
                }
            }
            if (v.getId() == R.id.fa_2b) {
                if(dua_b) {
                    radio_2b.setChecked(false);
                    dua_b = false;
                } else {
                    dua_b = true;
                    radio_2b.setChecked(true);
                }
            }
            if (v.getId() == R.id.fa_3a) {
                if(tiga_a) {
                    radio_3a.setChecked(false);
                    tiga_a = false;
                } else {
                    tiga_a = true;
                    radio_3a.setChecked(true);
                }
            }
            if (v.getId() == R.id.fa_4a) {
                if(empat_a) {
                    radio_4a.setChecked(false);
                    empat_a = false;
                } else {
                    empat_a = true;
                    radio_4a.setChecked(true);
                }
            }
            if (v.getId() == R.id.fa_5a) {
                if(lima_a) {
                    radio_5a.setChecked(false);
                    lima_a = false;
                } else {
                    lima_a = true;
                    radio_5a.setChecked(true);
                }
            }
            if (v.getId() == R.id.fa_7c) {
                if(tujuh_c) {
                    radio_7c.setChecked(false);
                    tujuh_c = false;
                } else {
                    tujuh_c = true;
                    radio_7c.setChecked(true);
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtra(getIntent().getExtras());
        if(english) {
            setContentView(R.layout.activity_merchandiser_bahasa);
        } else {
            setContentView(R.layout.activity_merchandiser_bahasa);
        }
        next = (Button) findViewById(R.id.next);
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
        store = (EditText) findViewById(R.id.store);
        edit_product_11 = (EditText) findViewById(R.id.edit_product_11);
        edit_product_12 = (EditText) findViewById(R.id.edit_product_12);
        edit_size_11 = (EditText) findViewById(R.id.edit_size_11);
        edit_size_12 = (EditText) findViewById(R.id.edit_size_12);
        date = (TextView) findViewById(R.id.date);
        date.setText(SharedPreferenceUtil.getString(ConstantUtil.SP_DATE));
        coach.setText(coach_email);
        coach.setEnabled(false);
        coachee.setText(coachee_email);
        coachee.setEnabled(false);
        next.setOnClickListener(onClick);
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
        final String in = "fa_"+indeks;
        final int n = indeks;
        resetBoolean();
        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.merchandiser_question_1, null);
        popupWindow = new PopupWindow(popupView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        popupWindow.setFocusable(true);
        popupWindow.update();
        final EditText kompetitor_1 = (EditText) popupView.findViewById(R.id.competitor_1);
        final EditText kompetitor_2 = (EditText) popupView.findViewById(R.id.competitor_2);
        kompetitor_1.setAllCaps(true);
        kompetitor_2.setAllCaps(true);
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
        radio_1a.setOnClickListener(radioOnClick);
        radio_1b.setOnClickListener(radioOnClick);
        radio_2a.setOnClickListener(radioOnClick);
        radio_2b.setOnClickListener(radioOnClick);
        radio_3a.setOnClickListener(radioOnClick);
        radio_4a.setOnClickListener(radioOnClick);
        radio_5a.setOnClickListener(radioOnClick);
        radio_7c.setOnClickListener(radioOnClick);
        if(n <=10) {
            kom_1 = getKompetitor(n);
            kom_2 = getKompetitor(n);
            kompetitor_1.setText(kom_1);
            kompetitor_2.setText(kom_2);
            kompetitor_1.setEnabled(false);
            kompetitor_2.setEnabled(false);
        } else {
            kompetitor_1.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    kompetitor_2.setText(kompetitor_1.getText().toString());
                    kompetitor_2.setEnabled(false);
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
        btnDismiss.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(n >10) {
                    kom_1 = kompetitor_1.getText().toString();
                    kom_2 = kompetitor_2.getText().toString();
                }
                int A = 1;
                if(!edit_6a.getText().toString().isEmpty()) {
                    A = Integer.parseInt(edit_6a.getText().toString());
                }
                int B = 1;
                if (!edit_6b.getText().toString().isEmpty()) {
                    B = Integer.parseInt(edit_6b.getText().toString());
                    if (B ==0) B = 1;
                }
                int C = 1;
                int D = 1;
                if (!edit_6c.getText().toString().isEmpty()) {
                    C = Integer.parseInt(edit_6c.getText().toString());
                    if (C ==0) C = 1;
                }
                if (!edit_6d.getText().toString().isEmpty()) {
                    D = Integer.parseInt(edit_6d.getText().toString());
                    if (D ==0) D = 1;
                }
                double rpi = (double) ((double)(A/B) / (double) (C/D));
                edit_rpi.setText(rpi + "");
                if(n == 11){
                    answers.add(new MerchandiserAnswer(in, edit_product_11.getText().toString(), edit_size_11.getText().toString(),
                            kom_1, satu_a, satu_b, dua_a, dua_b, tiga_a, empat_a, lima_a, edit_6a.getText().toString(), edit_6b.getText().toString(),
                            edit_6c.getText().toString(), edit_6d.getText().toString(), edit_7a.getText().toString(), edit_7b.getText().toString(),
                            edit_rpi.getText().toString(), tujuh_c));
                } else if (n == 12) {
                    answers.add(new MerchandiserAnswer(in, edit_product_12.getText().toString(), edit_size_12.getText().toString(),
                            kom_1, satu_a, satu_b, dua_a, dua_b, tiga_a, empat_a, lima_a, edit_6a.getText().toString(), edit_6b.getText().toString(),
                            edit_6c.getText().toString(), edit_6d.getText().toString(), edit_7a.getText().toString(), edit_7b.getText().toString(),
                            edit_rpi.getText().toString(), tujuh_c));
                } else {
                    answers.add(new MerchandiserAnswer(in, getResources().getString(R.string.product_1), getResources().getString(R.string.size_1),
                            kom_1, satu_a, satu_b, dua_a, dua_b, tiga_a, empat_a, lima_a, edit_6a.getText().toString(), edit_6b.getText().toString(),
                            edit_6c.getText().toString(), edit_6d.getText().toString(), edit_7a.getText().toString(), edit_7b.getText().toString(),
                            edit_rpi.getText().toString(), tujuh_c));
                }

                popupWindow.dismiss();
            }});
    }

    private String getKompetitor(int indeks) {
        String result = "";
        if(indeks == 1) {
            result = getResources().getString(R.string.kompetitor_1);
        } else if (indeks == 2) {
            result = getResources().getString(R.string.kompetitor_2);
        } else if (indeks == 3) {
            result = getResources().getString(R.string.kompetitor_3);
        } else if (indeks == 4) {
            result = getResources().getString(R.string.kompetitor_4);
        } else if (indeks == 5) {
            result = getResources().getString(R.string.kompetitor_5);
        } else if (indeks == 6) {
            result = getResources().getString(R.string.kompetitor_6);
        } else if (indeks == 7) {
            result = getResources().getString(R.string.kompetitor_7);
        } else if (indeks == 8) {
            result = getResources().getString(R.string.kompetitor_8);
        } else if (indeks == 9) {
            result = getResources().getString(R.string.kompetitor_9);
        } else if (indeks == 10) {
            result = getResources().getString(R.string.kompetitor_10);
        }
        return result;
    }

    private void resetBoolean() {
        satu_a = false;
        satu_b = false;
        dua_a = false;
        dua_b = false;
        tiga_a = false;
        empat_a = false;
        empat_b = false;
        lima_a = false;
        tujuh_c = false;
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
            if(bundle.getString("store") != null) {
                text_store = bundle.getString("store");
            }
            if(bundle.getString("id") != null) {
                coachingSessionID = bundle.getString("id");
            }
        }
    }
}
