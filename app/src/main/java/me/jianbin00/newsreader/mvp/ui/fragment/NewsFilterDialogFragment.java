package me.jianbin00.newsreader.mvp.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import me.jessyan.art.mvp.Message;
import me.jianbin00.newsreader.R;
import me.jianbin00.newsreader.app.SharedPreferenceTags;
import me.jianbin00.newsreader.mvp.ui.activity.NewsActivity;

/**
 * Jianbin Li
 * 2018/10/12
 */
public class NewsFilterDialogFragment extends DialogFragment
{

    Spinner modeSpinner;
    Spinner detailSpinner;
    SharedPreferences preferences;
    static int mode = 0;
    static int value = 0;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        View v = View.inflate(getContext(), R.layout.fragment_spinner, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.news_filter_selector);
        builder.setView(v);
        preferences = getActivity().getSharedPreferences(SharedPreferenceTags.SP_SETTING_FILE_NAME, Context.MODE_PRIVATE);
        mode = preferences.getInt(SharedPreferenceTags.SP_TAG_MODE, 0);
        value = preferences.getInt(SharedPreferenceTags.SP_TAG_VALUE, 0);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                preferences = getActivity().getSharedPreferences("setting", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(SharedPreferenceTags.SP_TAG_MODE, mode);
                editor.putInt(SharedPreferenceTags.SP_TAG_VALUE, value);
                editor.commit();
                NewsActivity activity = (NewsActivity) getActivity();
                activity.obtainPresenter().requestNews(Message.obtain(activity, new Object[]{true}));
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        modeSpinner = view.findViewById(R.id.spinner_mode);
        detailSpinner = view.findViewById(R.id.spinner_detail);
        ArrayAdapter<CharSequence> modeAdapter = ArrayAdapter.createFromResource(getContext(), R.array.mode, android.R.layout.simple_spinner_item);
        modeSpinner.setAdapter(modeAdapter);
        modeSpinner.setSelection(mode);
        modeSpinner.setOnItemSelectedListener(new ModeSpinnerListener());
        setDetailSpinner();

    }

    private void setDetailSpinner()
    {
        ArrayAdapter<CharSequence> detailAdapter = ArrayAdapter.createFromResource(getContext(), mode, android.R.layout.simple_spinner_item);
        detailSpinner.setAdapter(detailAdapter);
        detailSpinner.setSelection(value);
        detailSpinner.setOnItemSelectedListener(new DetailSpinnerListener());
    }

    private class ModeSpinnerListener implements AdapterView.OnItemSelectedListener
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
        {
            switch (pos)
            {
                //Country/Area
                case 0:
                    mode = R.array.area_name;
                    break;
                //Category
                case 1:
                    mode = R.array.category;
                    break;
                //Language
                case 2:
                    mode = R.array.language_name;
                    break;
            }

            setDetailSpinner();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {

        }
    }

    private class DetailSpinnerListener implements AdapterView.OnItemSelectedListener
    {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
        {
            value = i;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView)
        {

        }
    }


}
