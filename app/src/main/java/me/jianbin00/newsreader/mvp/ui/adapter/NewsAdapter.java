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

import android.animation.ObjectAnimator;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ImageButton;

import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

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

    private SparseBooleanArray expandState = new SparseBooleanArray();

    public NewsAdapter(List<NewsResponse.ArticlesBean> infos)
    {
        super(infos);
        for (int i = 0; i < infos.size(); i++)
        {
            expandState.append(i, false);
        }
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


    /*Add it for setting up ExpandableLinearLayout
     * Jianbin*/

    @Override
    public void onBindViewHolder(BaseHolder<NewsResponse.ArticlesBean> holder, int position)
    {
        super.onBindViewHolder(holder, position);
        holder.setIsRecyclable(false);
        ExpandableLinearLayout mExpandableLinearLayout = ((NewsItemHolder) holder).mExpandableLinearLayout;
        ImageButton mShowMoreButton = ((NewsItemHolder) holder).mShowMoreButton;
        mExpandableLinearLayout.setInRecyclerView(true);
        mExpandableLinearLayout.setExpanded(expandState.get(position));
        mExpandableLinearLayout.setListener(new ExpandableLayoutListenerAdapter()
        {
            @Override
            public void onPreOpen()
            {
                createRotateAnimator(mShowMoreButton, 0f, 180f).start();
                expandState.put(position, true);
            }

            @Override
            public void onPreClose()
            {
                createRotateAnimator(mShowMoreButton, 180f, 0f).start();
                expandState.put(position, false);
            }
        });
        mShowMoreButton.setRotation(expandState.get(position) ? 180f : 0f);
    }

    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to)
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }
}
