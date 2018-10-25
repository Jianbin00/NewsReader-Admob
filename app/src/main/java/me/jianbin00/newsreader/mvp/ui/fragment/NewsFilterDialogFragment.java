package me.jianbin00.newsreader.mvp.ui.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.pl.wheelview.WheelView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.art.utils.DataHelper;
import me.jianbin00.newsreader.R;
import me.jianbin00.newsreader.app.SharedPreferenceTags;
import me.jianbin00.newsreader.mvp.ui.activity.NewsActivity;
import timber.log.Timber;

/**
 * Jianbin Li
 * 2018/10/12
 */
public class NewsFilterDialogFragment extends DialogFragment
{
    @BindView(R.id.wheelview_mode)
    WheelView modeWheelview;
    @BindView(R.id.wheelview_detail)
    WheelView detailWheelview;

    Unbinder unbinder;
    static int mode = 0;
    static int value = 0;
    int modeArrayId;



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        View v = View.inflate(getContext(), R.layout.fragment_spinner, null);
        unbinder = ButterKnife.bind(this, v);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.news_filter_selector);
        mode = DataHelper.getIntergerSF(getContext(), SharedPreferenceTags.SP_TAG_MODE);
        value = DataHelper.getIntergerSF(getContext(), SharedPreferenceTags.SP_TAG_VALUE);
        modeWheelview.setOnSelectListener(new WheelView.OnSelectListener()
        {
            @Override
            public void endSelect(int id, String text)
            {
                Timber.w("id: " + id + "text:" + text);
                detailWheelview.refreshData(getArrayList(getModeArrayId(id)));
            }

            @Override
            public void selecting(int id, String text)
            {

            }
        });

        modeWheelview.setData(getArrayList(R.array.mode));
        detailWheelview.setData(getArrayList(getModeArrayId(mode)));

        modeWheelview.setDefault(mode);
        detailWheelview.setDefault(value);

        builder.setView(v);

        builder.setPositiveButton(R.string.apply, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                DataHelper.setIntergerSF(getContext(), SharedPreferenceTags.SP_TAG_MODE, modeWheelview.getSelected());
                DataHelper.setIntergerSF(getContext(), SharedPreferenceTags.SP_TAG_VALUE, detailWheelview.getSelected());

                ((NewsActivity) getActivity()).onRefresh();

            }
        })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dismiss();
                    }
                });
        return builder.create();
    }


    @Override
    public void onDestroyView()
    {

        super.onDestroyView();
        unbinder.unbind();
    }

    private int getModeArrayId(int modePos)
    {
        switch (modePos)
        {
            //Country/Area
            case 0:
                modeArrayId = R.array.area_name;
                break;
            //Category
            case 1:
                modeArrayId = R.array.category;
                break;
            //Language
            case 2:
                modeArrayId = R.array.language_name;
                break;
        }
        return modeArrayId;
    }

    private ArrayList<String> getArrayList(int arrayId)
    {
        return new ArrayList<>(Arrays.asList(getResources().getStringArray(arrayId)));
    }


}
