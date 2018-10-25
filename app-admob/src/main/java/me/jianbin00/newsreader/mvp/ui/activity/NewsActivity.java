/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.jianbin00.newsreader.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.paginate.Paginate;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;

import butterknife.BindView;
import me.jessyan.art.base.BaseActivity;
import me.jessyan.art.base.DefaultAdapter;
import me.jessyan.art.mvp.IView;
import me.jessyan.art.mvp.Message;
import me.jessyan.art.utils.ArtUtils;
import me.jessyan.art.utils.Preconditions;
import me.jianbin00.newsreader.R;
import me.jianbin00.newsreader.mvp.presenter.NewsPresenter;
import me.jianbin00.newsreader.mvp.ui.adapter.NewsAdapter;
import me.jianbin00.newsreader.mvp.ui.fragment.NewsFilterDialogFragment;
import timber.log.Timber;


/**
 * ================================================
 * 展示 View 的用法
 * <p>
 * Created by JessYan on 09/04/2016 10:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class NewsActivity extends BaseActivity<NewsPresenter> implements IView, SwipeRefreshLayout.OnRefreshListener
{

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private Paginate mPaginate;
    private boolean isLoadingMore;
    private RxPermissions mRxPermissions;
    private NewsAdapter mAdapter;


    @Override
    public int initView(@Nullable Bundle savedInstanceState)
    {
        return R.layout.activity_news;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState)
    {
        initRecyclerView();
        mRecyclerView.setAdapter(mAdapter);
        initPaginate();
        mPresenter.requestNews(Message.obtain(this, new Object[]{true}));//打开app时自动加载列表
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case (R.id.explore):
                NewsFilterDialogFragment newsFilterDialogFragment = new NewsFilterDialogFragment();
                newsFilterDialogFragment.show(getSupportFragmentManager(), "filter");
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

    @Override
    @Nullable
    public NewsPresenter obtainPresenter()
    {
        this.mRxPermissions = new RxPermissions(this);
        this.mAdapter = new NewsAdapter(new ArrayList<>());
        return new NewsPresenter(ArtUtils.obtainAppComponentFromContext(this), mAdapter, mRxPermissions);
    }


    @Override
    public void showLoading()
    {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading()
    {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(@NonNull String message)
    {
        Preconditions.checkNotNull(message);
        ArtUtils.snackbarText(message);
    }

    @Override
    public void handleMessage(@NonNull Message message)
    {
        Preconditions.checkNotNull(message);
        Timber.w("MESSAGE:" + message + " message.what:" + message.what);
        switch (message.what)
        {
            case 0:
                isLoadingMore = true;//开始加载更多
                break;
            case 1:
                isLoadingMore = false;//结束加载更多
                break;
        }
    }


    @Override
    public void onRefresh()
    {
        mPresenter.clear();
        mPresenter.requestNews(Message.obtain(this, new Object[]{true}));
        //mSwipeRefreshLayout.invalidate();
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView()
    {

        mSwipeRefreshLayout.setOnRefreshListener(this);
        ArtUtils.configRecyclerView(mRecyclerView, new GridLayoutManager(this, 1));
    }


    /**
     * 初始化Paginate,用于加载更多
     */
    private void initPaginate()
    {
        if (mPaginate == null)
        {
            Paginate.Callbacks callbacks = new Paginate.Callbacks()
            {
                @Override
                public void onLoadMore()
                {
                    mPresenter.requestNews(Message.obtain(NewsActivity.this, new Object[]{false}));
                }

                @Override
                public boolean isLoading()
                {
                    return isLoadingMore;
                }

                @Override
                public boolean hasLoadedAllItems()
                {
                    return false;
                }
            };

            mPaginate = Paginate.with(mRecyclerView, callbacks)
                    .setLoadingTriggerThreshold(0)
                    .build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }

    @Override
    protected void onDestroy()
    {
        DefaultAdapter.releaseAllHolder(mRecyclerView);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();
        this.mRxPermissions = null;
        this.mPaginate = null;
    }
}
