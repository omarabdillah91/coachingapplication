package unilever.coachingform;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import dao.CoachingSessionDAO;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import model.Coaching;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, CoachingSessionDAO.CoachingSessionListener {
    private static final String TAG = "Login";
    String email = "";
    int job = 0;
    String password;
    EditText edit_email, edit_password;
    Button login;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener mAuthListener;
    ArrayAdapter<CharSequence> job_adapter;
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.login) {
                email = edit_email.getText().toString();
                password = edit_password.getText().toString();
                if (!email.equalsIgnoreCase("") && !edit_password.getText().toString().equalsIgnoreCase("")) {
                    signIn(email, password);
                } else {
                    Toast.makeText(LoginActivity.this, "First Name, Last Name, and ID Number must be filled", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edit_email = (EditText) findViewById(R.id.email);
        edit_password  = (EditText) findViewById(R.id.password);
        Spinner spinner = (Spinner) findViewById(R.id.job);
        job_adapter =ArrayAdapter.createFromResource(this,R.array.job_title, android.R.layout.simple_spinner_item);
        spinner.setAdapter(job_adapter);
        spinner.setOnItemSelectedListener(this);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(onClick);
        mAuth = FirebaseAuth.getInstance();
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this.getApplicationContext()).build();
        Realm.setDefaultConfiguration(realmConfig);
        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser temp = firebaseAuth.getCurrentUser();
                if (temp != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + temp.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                user = temp;
                updateUI(temp);
                // [END_EXCLUDE]
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        job = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private boolean validateForm() {
        boolean valid = true;

        String email = edit_email.getText().toString();
        if (TextUtils.isEmpty(email)) {
            edit_email.setError("Required.");
            valid = false;
        } else {
            edit_email.setError(null);
        }

        String password = edit_password.getText().toString();
        if (TextUtils.isEmpty(password)) {
            edit_password.setError("Required.");
            valid = false;
        } else {
            edit_password.setError(null);
        }

        return valid;
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    private void signIn(String email, String password) {
        if (!validateForm()) {
            return;
        }
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                if (!task.isSuccessful()) {
                    Log.w(TAG, "signInWithEmail:failed", task.getException());
                    Toast.makeText(LoginActivity.this, "Email and password do not match",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        // [END sign_in_with_email]
    }
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            CoachingSessionDAO.getUnsubmittedCoaching(this);
        }
    }

    @Override
    public void onCoachingReceived(List<Coaching> coachingList) {
        if(coachingList.size() > 0) {
            Intent intent = new Intent(LoginActivity.this, SynchronizationActivity.class);
            intent.putExtra("email", user.getEmail());
            intent.putExtra("job", job);
            startActivity(intent);
        } else {
            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
            intent.putExtra("email", user.getEmail());
            intent.putExtra("job", job);
            startActivity(intent);
        }
    }

    @Override
    public void onCoachingInserted(boolean succees) {

    }
}
