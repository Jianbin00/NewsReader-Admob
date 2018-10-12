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
package me.jianbin00.newsreader.mvp.ui.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.art.base.BaseHolder;
import me.jessyan.art.base.DefaultAdapter;
import me.jessyan.art.di.component.AppComponent;
import me.jessyan.art.http.imageloader.ImageLoader;
import me.jessyan.art.http.imageloader.glide.ImageConfigImpl;
import me.jessyan.art.utils.ArtUtils;
import me.jianbin00.newsreader.R;
import me.jianbin00.newsreader.app.EventBusTags;
import me.jianbin00.newsreader.mvp.model.entity.NewsResponse;
import me.jianbin00.newsreader.mvp.ui.activity.WebActivity;

/**
 * ================================================
 * 展示 {@link BaseHolder} 的用法
 * <p>
 * Created by JessYan on 9/4/16 12:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class NewsItemHolder extends BaseHolder<NewsResponse.ArticlesBean>
{
    @BindView(R.id.iv_image)
    ImageView mImage;
    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.tv_source)
    TextView mSource;
    @BindView(R.id.tv_published_time)
    TextView mPublishedTime;
    @BindView(R.id.tv_desc)
    TextView mDesc;
    @BindView(R.id.tv_content)
    TextView mContent;


    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private Context mContext;

    private String newsUrl;
    private int contentHeight;

    public NewsItemHolder(View itemView)
    {
        super(itemView);
        mContext = itemView.getContext();
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ArtUtils.obtainAppComponentFromContext(mContext);
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(NewsResponse.ArticlesBean data, int position)
    {
        mTitle.setText(data.getTitle());
        mSource.setText(data.getSource().getName());
        mPublishedTime.setText(data.getPublishedAt());
        mDesc.setText(data.getContent());
        mContent.setText(data.getContent());
        //mContent.setVisibility(View.INVISIBLE);
        contentHeight = mContent.getHeight();
        mContent.setHeight(0);
        newsUrl = data.getUrl();

        //itemView 的 Context 就是 Activity, Glide 会自动处理并和该 Activity 的生命周期绑定
/*        mImageLoader.loadImage(itemView.getContext(),
                ImageConfigImpl
                        .builder()
                        .url(data.getUrlToImage())
                        .imageView(mImage)
                        .build());*/
        Glide.with(mContext)
                .load(data.getUrlToImage())
                .thumbnail(0.1f)
                .into(mImage);

    }

    /**
     * 在 Activity 的 onDestroy 中使用 {@link DefaultAdapter#releaseAllHolder(RecyclerView)} 方法 (super.onDestroy() 之前)
     * {@link BaseHolder#onRelease()} 才会被调用, 可以在此方法中释放一些资源
     */
    @Override
    protected void onRelease()
    {
        //只要传入的 Context 为 Activity, Glide 就会自己做好生命周期的管理, 其实在上面的代码中传入的 Context 就是 Activity
        //所以在 onRelease 方法中不做 clear 也是可以的, 但是在这里想展示一下 clear 的用法
        mImageLoader.clear(mAppComponent.application(), ImageConfigImpl.builder()
                .imageViews(mImage)
                .build());
        this.mImage = null;
        this.mTitle = null;
        this.mAppComponent = null;
        this.mImageLoader = null;
    }

    @OnClick(R.id.share_button)
    public void share()
    {
        Toast.makeText(mContext, "Share is not yet implement.", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.view_more)
    public void showOrHideContent()
    {
/*        if (mContent.getVisibility() == View.VISIBLE)
        {
            mContent.setVisibility(View.INVISIBLE);
        } else
        {
            mContent.setVisibility(View.VISIBLE);
        }*/
        if (contentHeight == 0)
        {
            mContent.setHeight(contentHeight);
        } else
        {
            mContent.setHeight(0);
        }

    }

    @OnClick(R.id.card_view)
    public void loadNewsPage()
    {
        Intent intent = new Intent(mContext, WebActivity.class);
        intent.putExtra(EventBusTags.WEB_URL, newsUrl);
        mContext.startActivity(intent);
    }

}
