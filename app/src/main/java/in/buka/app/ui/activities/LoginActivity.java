package in.buka.app.ui.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import in.buka.app.R;
import in.buka.app.libs.configs.Constants;
import in.buka.app.libs.database.DatabaseHelper;
import in.buka.app.libs.services.BLService;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(R.string.login_buttons_log_in);
        }

        root = (LinearLayout) findViewById(R.id.container_login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        String data = null;
        try {
            data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(email.getText().toString(), "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password.getText().toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Intent service = new Intent(this, BLService.class);
        Bundle send = new Bundle();
        send.putString(BLService.KEY_URL, Constants.LOGIN_URL);
        send.putString(BLService.KEY_DATA, data);
        service.putExtras(send);
        startService(service);
        progress = ProgressDialog.show(this, "", "Loading...", true, false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                Bundle recv = intent.getExtras();

                JSONObject response = new JSONObject(recv.getString(BLService.KEY_RESPONSE));
                if (response.getString("message") == null) {
                    User user = JsonUtils.parseUserCredentials(response);
                    DatabaseHelper helper = new DatabaseHelper(LoginActivity.this);
                    helper.setCredentials(user);
                } else {
                    Snackbar.make(root, response.getString("message"), Snackbar.LENGTH_INDEFINITE).show();
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}
