package me.jianbin00.newsreader.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import timber.log.Timber;

/**
 * Jianbin Li
 * 2018/10/15
 */
public class DateTransfer
{

    //"2018-10-16T03:49:00Z"
    public static String getDateFromTZFormatToLocale(String dateString)
    {
        dateString = dateString.substring(0, 16);
        Timber.w("date:" + dateString);
        dateString = dateString.replace("T", " ") + " UTC";//注意是空格+UTC
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm Z", Locale.getDefault());
        String result = "";
        try
        {
            result = format.format(format.parse(dateString));
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return result;

    }
}
