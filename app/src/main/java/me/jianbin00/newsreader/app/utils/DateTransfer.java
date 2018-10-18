package me.jianbin00.newsreader.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        dateString += " UTC";//注意是空格+UTC
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss z");//注意格式化的表达式
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss z", Locale.getDefault());
        String result = "";
        try
        {
            Date date = format.parse(dateString);
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            result = newFormat.format(date);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return result;

    }
}
