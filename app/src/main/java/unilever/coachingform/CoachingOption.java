package unilever.coachingform;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CoachingOption extends AppCompatActivity {
    Button rsm, asm_pull, asm_push, fa, dsr, dts, sr, next, back, bahasa, english;
    boolean rsm_status, asm_pull_status, asm_push_status, fa_status, dsr_status, dts_status, sr_status, bahasa_status, english_status = false;
    String coach, coachee = "";
    String job = "";
    Bundle profile;
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.rsm_coaching) {
               rsm_status = reverseStatus(v, rsm_status);
            } else if (v.getId() == R.id.asm_pull_coaching) {
                asm_pull_status = reverseStatus(v,asm_pull_status);
            } else if (v.getId() == R.id.asm_push_coaching) {
                asm_push_status = reverseStatus(v,asm_push_status);
            } else if (v.getId() == R.id.fa_coaching) {
                fa_status = reverseStatus(v,fa_status);
            } else if (v.getId() == R.id.dsr_coaching) {
                dsr_status = reverseStatus(v,dsr_status);
            } else if (v.getId() == R.id.dts_coaching) {
                dts_status = reverseStatus(v,dts_status);
            } else if (v.getId() == R.id.sr_pull_coaching) {
                sr_status = reverseStatus(v,sr_status);
            } else if (v.getId() == R.id.next) {
                if(validSelection()) {
                    goCoaching();
                } else {
                    Toast.makeText(CoachingOption.this, "Please select only 1 Coaching Guideline and 1 language", Toast.LENGTH_SHORT).show();
                }
            } else if (v.getId() == R.id.back) {
                Intent intent = new Intent(CoachingOption.this, ProfileActivity.class);
                intent.putExtra("email", coach);
                intent.putExtra("job", job);
                intent.putExtra("profile",profile);
                startActivity(intent);
            } else if (v.getId() == R.id.indonesian) {
                bahasa_status = reverseStatus(v,bahasa_status);
            } else if (v.getId() == R.id.english) {
                english_status = reverseStatus(v,english_status);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coaching_option);
        rsm = (Button) findViewById(R.id.rsm_coaching);
        asm_pull = (Button) findViewById(R.id.asm_pull_coaching);
        asm_push = (Button) findViewById(R.id.asm_push_coaching);
        fa = (Button) findViewById(R.id.fa_coaching);
        dsr = (Button) findViewById(R.id.dsr_coaching);
        dts = (Button) findViewById(R.id.dts_coaching);
        sr = (Button) findViewById(R.id.sr_pull_coaching);
        next = (Button) findViewById(R.id.next);
        back = (Button) findViewById(R.id.back);
        bahasa = (Button) findViewById(R.id.indonesian);
        english = (Button) findViewById(R.id.english);
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            if(bundle.getString("coach") != null) {
                coach = bundle.getString("coach");
            }
            if(bundle.getString("coachee") != null) {
                coachee = bundle.getString("coachee");
            }
            if(bundle.getString("job") != null) {
                job = bundle.getString("job");
            }
            if(bundle.getBundle("profile") != null) {
                profile = bundle.getBundle("profile");
            }
        }
        rsm.setOnClickListener(onClick);
        asm_pull.setOnClickListener(onClick);
        asm_push.setOnClickListener(onClick);
        fa.setOnClickListener(onClick);
        dsr.setOnClickListener(onClick);
        dts.setOnClickListener(onClick);
        sr.setOnClickListener(onClick);
        next.setOnClickListener(onClick);
        back.setOnClickListener(onClick);
        bahasa.setOnClickListener(onClick);
        english.setOnClickListener(onClick);
    }

    private boolean validSelection() {
        int guides = 0;
        int language = 0;
        if(rsm_status) {
            guides++;
        }
        if (asm_pull_status) {
            guides++;
        }
        if (asm_push_status) {
            guides++;
        }
        if (dsr_status) {
            guides++;
        }
        if (dts_status) {
            guides++;
        }
        if (fa_status) {
            guides++;
        }
        if (sr_status) {
            guides++;
        }
        if (bahasa_status) {
            language++;
        }
        if(english_status) {
            language++;
        }
        if(guides > 1 || language > 1) {
            return false;
        }
        return true;
    }

    private void goCoaching() {
        if(rsm_status) {
            Intent intent = new Intent(CoachingOption.this, CoacheeHistoryActivity.class);
            intent.putExtra("coach", coach);
            intent.putExtra("job", job);
            intent.putExtra("coachee", coachee);
            intent.putExtra("bahasa",bahasa_status);
            intent.putExtra("english",english_status);
            intent.putExtra("coaching","rsm");
            startActivity(intent);
        } else if (asm_pull_status) {
            Intent intent = new Intent(CoachingOption.this, CoacheeHistoryActivity.class);
            intent.putExtra("coach", coach);
            intent.putExtra("job", job);
            intent.putExtra("coachee", coachee);
            intent.putExtra("bahasa",bahasa_status);
            intent.putExtra("english",english_status);
            intent.putExtra("coaching","asm_pull");
            startActivity(intent);
        } else if (asm_push_status) {
            Intent intent = new Intent(CoachingOption.this, CoacheeHistoryActivity.class);
            intent.putExtra("coach", coach);
            intent.putExtra("job", job);
            intent.putExtra("coachee", coachee);
            intent.putExtra("bahasa",bahasa_status);
            intent.putExtra("english",english_status);
            intent.putExtra("coaching","asm_push");
            startActivity(intent);
        } else if (dsr_status) {
            Intent intent = new Intent(CoachingOption.this, CoacheeHistoryActivity.class);
            intent.putExtra("coach", coach);
            intent.putExtra("job", job);
            intent.putExtra("coachee", coachee);
            intent.putExtra("bahasa",bahasa_status);
            intent.putExtra("english",english_status);
            intent.putExtra("coaching","dsr");
            startActivity(intent);
        } else if (dts_status) {
            Intent intent = new Intent(CoachingOption.this, CoacheeHistoryActivity.class);
            intent.putExtra("coach", coach);
            intent.putExtra("job", job);
            intent.putExtra("coachee", coachee);
            intent.putExtra("bahasa",bahasa_status);
            intent.putExtra("english",english_status);
            intent.putExtra("coaching","dts");
            startActivity(intent);
        } else if (fa_status) {
            Intent intent = new Intent(CoachingOption.this, CoacheeHistoryActivity.class);
            intent.putExtra("coach", coach);
            intent.putExtra("job", job);
            intent.putExtra("coachee", coachee);
            intent.putExtra("bahasa",bahasa_status);
            intent.putExtra("english",english_status);
            intent.putExtra("coaching","fa");
            startActivity(intent);
        } else if (sr_status) {
            Intent intent = new Intent(CoachingOption.this, CoacheeHistoryActivity.class);
            intent.putExtra("coach", coach);
            intent.putExtra("job", job);
            intent.putExtra("coachee", coachee);
            intent.putExtra("bahasa",bahasa_status);
            intent.putExtra("english",english_status);
            intent.putExtra("coaching","sr");
            startActivity(intent);
        }
    }

    private boolean reverseStatus(View v, boolean status) {
        boolean current_status = !status;
        if (v.getId() == R.id.rsm_coaching) {
            if(current_status) {
                v.setBackgroundColor(getResources().getColor(R.color.gray));
            } else {
                v.setBackgroundColor(getResources().getColor(R.color.orange));
            }
        } else if (v.getId() == R.id.asm_pull_coaching) {
            if(current_status) {
                v.setBackgroundColor(getResources().getColor(R.color.gray));
            } else {
                v.setBackgroundColor(getResources().getColor(R.color.orange));
            }
        } else if (v.getId() == R.id.asm_push_coaching) {
            if(current_status) {
                v.setBackgroundColor(getResources().getColor(R.color.gray));
            } else {
                v.setBackgroundColor(getResources().getColor(R.color.orange));
            }
        } else if (v.getId() == R.id.fa_coaching) {
            if(current_status) {
                v.setBackgroundColor(getResources().getColor(R.color.gray));
            } else {
                v.setBackgroundColor(getResources().getColor(R.color.orange));
            }
        } else if (v.getId() == R.id.dsr_coaching) {
            if(current_status) {
                v.setBackgroundColor(getResources().getColor(R.color.gray));
            } else {
                v.setBackgroundColor(getResources().getColor(R.color.orange));
            }
        } else if (v.getId() == R.id.dts_coaching) {
            if(current_status) {
                v.setBackgroundColor(getResources().getColor(R.color.gray));
            } else {
                v.setBackgroundColor(getResources().getColor(R.color.orange));
            }
        } else if (v.getId() == R.id.sr_pull_coaching) {
            if(current_status) {
                v.setBackgroundColor(getResources().getColor(R.color.gray));
            } else {
                v.setBackgroundColor(getResources().getColor(R.color.orange));
            }
        } else if (v.getId() == R.id.indonesian) {
            if(current_status) {
                v.setBackgroundColor(getResources().getColor(R.color.gray));
            } else {
                v.setBackgroundColor(getResources().getColor(R.color.orange));
            }
        } else if (v.getId() == R.id.english) {
            if(current_status) {
                v.setBackgroundColor(getResources().getColor(R.color.gray));
            } else {
                v.setBackgroundColor(getResources().getColor(R.color.orange));
            }
        }
        return current_status;
    }
}
