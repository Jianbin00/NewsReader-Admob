package me.jianbin00.newsreader.mvp.ui.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

/**
 * Jianbin Li
 * 2018/10/15
 */
public class OpenCloseAnimators
{


    private static ValueAnimator mOpenValueAnimator;
    private static ValueAnimator mCloseValueAnimator;

    public static void animOpen(final View view, int mHiddenViewMeasuredHeight)
    {
        view.setAlpha(0);
        view.setVisibility(View.VISIBLE);


        if (mOpenValueAnimator == null)
        {
            mOpenValueAnimator = createDropAnim(view, 0, mHiddenViewMeasuredHeight);
            mOpenValueAnimator.start();
        }

    }

    public static void animCloseAndShortenParent(final View view, final View parentView)
    {
        int origHeight = view.getHeight();
        view.setAlpha(1);
        int origParentHeight = view.getHeight();
        if (mCloseValueAnimator == null)
        {
            mCloseValueAnimator = createDropAnim(view, origHeight, 0);
            mCloseValueAnimator.addListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation)
                {
                    view.setVisibility(View.GONE);
                }
            });
            mCloseValueAnimator.start();
        }

        if (mCloseValueAnimator == null)
        {
            mCloseValueAnimator = createDropAnim(parentView, origParentHeight, origParentHeight - origHeight);
            mCloseValueAnimator.start();
        }

    }

    public static void animClose(final View view)
    {
        int origHeight = view.getHeight();
        view.setAlpha(1);
        if (mCloseValueAnimator == null)
        {
            mCloseValueAnimator = createDropAnim(view, origHeight, 0);
            mCloseValueAnimator.addListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation)
                {
                    view.setVisibility(View.GONE);
                }
            });

        }
        mCloseValueAnimator.start();

    }

    /**
     * 使用动画的方式来改变高度解决visible不一闪而过出现
     *
     * @param view
     * @param start 初始状态值
     * @param end   结束状态值
     * @return
     */
    private static ValueAnimator createDropAnim(final View view, int start, int end)
    {
        final ValueAnimator va = ValueAnimator.ofInt(start, end);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                int value = (int) animation.getAnimatedValue();//根据时间因子的变化系数进行设置高度
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                int mHiddenViewMeasuredHeight = Math.abs(start - end);
                float alpha = ((float) value) / mHiddenViewMeasuredHeight;
                view.setAlpha(alpha);

                view.setLayoutParams(layoutParams);//设置高度
            }
        });
        return va;
    }
}
