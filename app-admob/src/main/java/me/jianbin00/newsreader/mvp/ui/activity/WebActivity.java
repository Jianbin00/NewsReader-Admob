package me.jianbin00.newsreader.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jianbin00.newsreader.R;
import me.jianbin00.newsreader.app.EventBusTags;

public class WebActivity extends AppCompatActivity
{
    @BindView(R.id.webview)
    WebView mwebview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    String title;
    String urlString;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (toolbar != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }


        Intent intent = getIntent();
        title = intent.getStringExtra(EventBusTags.TITLE);
        urlString = intent.getStringExtra(EventBusTags.WEB_URL);
        mwebview.getSettings().setJavaScriptEnabled(true);
        mwebview.loadUrl(urlString);
        mwebview.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }


        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case (R.id.share):
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, title);
                intent.putExtra(Intent.EXTRA_TEXT, urlString);
                startActivity(Intent.createChooser(intent, getApplicationContext().getString(R.string.share_with)));
                return true;
            case (R.id.about):
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.about);
                builder.setMessage("Author: Jianbin Li \nEmail:lijianbin00@gmail.com \nv1.0.0");
                builder.create();
                builder.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
