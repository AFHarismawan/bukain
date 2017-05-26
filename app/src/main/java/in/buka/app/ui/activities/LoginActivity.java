package in.buka.app.ui.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import in.buka.app.libs.services.FirebaseService;
import org.json.JSONException;
import org.json.JSONObject;

import in.buka.app.R;
import in.buka.app.libs.configs.Constants;
import in.buka.app.libs.database.DatabaseHelper;
import in.buka.app.libs.services.BLService;
import in.buka.app.libs.utils.HttpUtils;
import in.buka.app.libs.utils.JsonUtils;
import in.buka.app.models.User;

/**
 * Created by A. Fauzi Harismawan on 22/05/2017.
 */

public class LoginActivity extends AppCompatActivity {

    private LinearLayout root;
    private EditText email, password;
    private Button login;
    private ProgressDialog progress;

    private FirebaseService firebaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseService = FirebaseService.getInstance();
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(R.string.login_buttons_log_in);
        }

        root = (LinearLayout) findViewById(R.id.container_login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!email.getText().toString().equals("") && !password.getText().toString().equals("")) {
                    login.setEnabled(true);
                } else {
                    login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        email.addTextChangedListener(watcher);
        password.addTextChangedListener(watcher);

        login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        Intent service = new Intent(this, BLService.class);
        Bundle send = new Bundle();
        send.putString(BLService.KEY_URL, Constants.LOGIN_URL);
        send.putString(BLService.KEY_DATA, "");
        send.putString(BLService.KEY_TYPE, BLService.TYPE_LOGIN);
        send.putString(BLService.KEY_REQUEST, HttpUtils.POST_REQUEST);
        send.putString(BLService.KEY_USERNAME, email.getText().toString());
        send.putString(BLService.KEY_PASSWORD, password.getText().toString());
        service.putExtras(send);
        startService(service);
        progress = ProgressDialog.show(this, "", "Loading...", true, false);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                Bundle recv = intent.getExtras();

                JSONObject response = new JSONObject(recv.getString(BLService.KEY_RESPONSE));
                if (response.getString("message").equals("null")) {
                    final User user = JsonUtils.parseUserCredentials(response);
                    DatabaseHelper helper = new DatabaseHelper(LoginActivity.this);
                    helper.setCredentials(user);

                    User.get(Integer.toString(user.id)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User temp = dataSnapshot.getValue(User.class);
                            if (temp != null) {
                                Log.d("BUKAIN", "NOT NULL");
                                firebaseService.signInFirebaseUser(temp.email, temp.token, LoginActivity.this);
                            } else {
                                Log.d("BUKAIN", "NULL");
                                firebaseService.registerFirebaseUser(user.email, user.token, LoginActivity.this);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    Snackbar.make(root, response.getString("message"), Snackbar.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            progress.dismiss();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter("in.buka.app.REQUEST_COMPLETE"));
        firebaseService.resumeAuth();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
        firebaseService.pauseAuth();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}
