package in.buka.app.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import in.buka.app.R;
import in.buka.app.libs.configs.Constants;
import in.buka.app.libs.services.BLService;
import in.buka.app.libs.utils.HttpUtils;
import in.buka.app.ui.adapters.ActivityFeedAdapter;

/**
 * Created by Shade on 5/28/17.
 */

public class ProductCartActivity extends WebViewActivity {

    public static String KEY_ID = "id";
    public LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        root = (LinearLayout) findViewById(R.id.root);

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString(ActivityFeedAdapter.KEY_ID);
        url = Constants.VIEW_CART_URL;
        addToCart(id);
    }

    private void addToCart(String id) {
        Intent service = new Intent(this, BLService.class);
        Bundle send = new Bundle();
        send.putString(BLService.KEY_URL, String.format(Constants.ADD_TO_CART_URL, id));
        send.putString(BLService.KEY_DATA, "");
        send.putString(BLService.KEY_TYPE, BLService.TYPE_AUTH);
        send.putString(BLService.KEY_REQUEST, HttpUtils.POST_REQUEST);
        service.putExtras(send);
        startService(service);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                Bundle recv = intent.getExtras();

                JSONObject response = new JSONObject(recv.getString(BLService.KEY_RESPONSE));

                if(response.getString("status").equals("OK")){
                    initWebView();
                } else if(response.getString("status").equals("ERROR")){
                    Snackbar.make(root, response.getString("message"), Snackbar.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(Constants.REQUEST_COMPLETE_INTENT_FILTER));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}

