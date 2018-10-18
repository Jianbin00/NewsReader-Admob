package me.jianbin00.newsreader.mvp.ui.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.art.mvp.Message;
import me.jessyan.art.utils.DataHelper;
import me.jianbin00.newsreader.R;
import me.jianbin00.newsreader.app.SharedPreferenceTags;
import me.jianbin00.newsreader.mvp.ui.activity.NewsActivity;
import timber.log.Timber;

/**
 * Jianbin Li
 * 2018/10/12
 */
public class NewsFilterDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener
{
    @BindView(R.id.spinner_mode)
    AppCompatSpinner modeSpinner;
    @BindView(R.id.spinner_detail)
    AppCompatSpinner detailSpinner;

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
        modeSpinner.setOnItemSelectedListener(this);
        setDetailSpinner(modeArrayId);


        builder.setView(v);

/*        preferences = getActivity().getSharedPreferences(SharedPreferenceTags.SP_SETTING_FILE_NAME, Context.MODE_PRIVATE);
        mode = preferences.getInt(SharedPreferenceTags.SP_TAG_MODE, 0);
        value = preferences.getInt(SharedPreferenceTags.SP_TAG_VALUE, 0);*/
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                DataHelper.setIntergerSF(getContext(), SharedPreferenceTags.SP_TAG_MODE, modeSpinner.getSelectedItemPosition());
                DataHelper.setIntergerSF(getContext(), SharedPreferenceTags.SP_TAG_VALUE, detailSpinner.getSelectedItemPosition());
/*                preferences = getActivity().getSharedPreferences("setting", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(SharedPreferenceTags.SP_TAG_MODE, modeSpinner.getSelectedItemPosition());
                editor.putInt(SharedPreferenceTags.SP_TAG_VALUE, detailSpinner.getSelectedItemPosition());
                editor.commit();*/
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


    private void setDetailSpinner(int modeArrayId)
    {
        Timber.w("modeArrayId is?" + modeArrayId);
        ArrayAdapter<CharSequence> detailAdapter = ArrayAdapter.createFromResource(getActivity(), modeArrayId, android.R.layout.simple_spinner_item);
        //detailAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        detailSpinner.setAdapter(detailAdapter);
        //detailSpinner.setSelection(value);
        detailSpinner.setOnItemSelectedListener(this);
    }


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
        {
            if (id == R.id.spinner_mode)
            {
                switch (pos)
                {
                    //Country/Area
                    case 0:
                        modeArrayId = R.array.area_name;
                        parent.getItemAtPosition(pos);
                        break;
                    //Category
                    case 1:
                        modeArrayId = R.array.category;
                        parent.getItemAtPosition(pos);
                        break;
                    //Language
                    case 2:
                        modeArrayId = R.array.language_name;
                        parent.getItemAtPosition(pos);
                        break;
                }

                setDetailSpinner(modeArrayId);
            } else
            {
                parent.getItemAtPosition(pos);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {

        }

    @Override
    public void onDestroyView()
    {

        super.onDestroyView();
        unbinder.unbind();
    }
}
