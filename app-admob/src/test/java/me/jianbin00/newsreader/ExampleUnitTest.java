package me.jianbin00.newsreader;

import org.junit.Test;

import me.jianbin00.newsreader.app.utils.DateTransfer;

import static org.junit.Assert.assertNotNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest
{
    @Test
    public void DateTransferTest() throws Exception
    {
        String date = "2018-10-16T03:49:00.000Z";

        String d = DateTransfer.getDateFromTZFormatToLocale(date);
        //assertEquals("2018/10/16 03:49:00 PST", d);
        assertNotNull(d);
    }
}