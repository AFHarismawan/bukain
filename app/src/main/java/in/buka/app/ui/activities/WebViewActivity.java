package in.buka.app.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import in.buka.app.R;

/**
 * Created by A. Fauzi Harismawan on 24/05/2017.
 */

public abstract class WebViewActivity extends AppCompatActivity {

    protected String url;
    protected WebView root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
    }

    protected void initWebView() {
        root = (WebView) findViewById(R.id.web_view);
        root.loadUrl(url);
    }
}
