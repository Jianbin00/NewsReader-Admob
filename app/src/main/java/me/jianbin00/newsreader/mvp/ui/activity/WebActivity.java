package me.jianbin00.newsreader.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jianbin00.newsreader.R;
import me.jianbin00.newsreader.app.EventBusTags;

public class WebActivity extends AppCompatActivity
{
    @BindView(R.id.webview)
    WebView mwebview;
/*    @BindView(R.id.toolbar)
    Toolbar toolbar;*/


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        //setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        Intent intent = getIntent();
        String urlString = intent.getStringExtra(EventBusTags.WEB_URL);
        mwebview.loadUrl(urlString);

    }

}
