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
package me.jianbin00.newsreader.mvp.ui.adapter;

import android.view.View;

import java.util.List;

import me.jessyan.art.base.BaseHolder;
import me.jessyan.art.base.DefaultAdapter;
import me.jianbin00.newsreader.R;
import me.jianbin00.newsreader.mvp.model.entity.NewsResponse;
import me.jianbin00.newsreader.mvp.ui.holder.NewsItemHolder;

/**
 * ================================================
 * 展示 {@link DefaultAdapter} 的用法
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class NewsAdapter extends DefaultAdapter<NewsResponse.ArticlesBean>
{


    public NewsAdapter(List<NewsResponse.ArticlesBean> infos)
    {
        super(infos);

    }

    @Override
    public BaseHolder<NewsResponse.ArticlesBean> getHolder(View v, int viewType)
    {
        return new NewsItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType)
    {
        return R.layout.recycle_list;
    }


}
