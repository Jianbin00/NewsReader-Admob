package me.jianbin00.newsreader.app.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Jianbin Li
 * 2018/10/15
 */
public class DateTransfer
{

    //"2018-10-16T03:49:00Z"
    public static String getDateFromTZFormatToLocale(String date)
    {
        date = date.replace("Z", " UTC");//注意是空格+UTC
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss z");//注意格式化的表达式
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss z", Locale.getDefault());

        return format.format(date);

    }
}
