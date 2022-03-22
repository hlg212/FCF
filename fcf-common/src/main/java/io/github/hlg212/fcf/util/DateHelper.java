package  io.github.hlg212.fcf.util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * 时间帮助工具
 *
 * @author huangligui
 * @date 2020年5月30日
 */
public class DateHelper {

    private static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    private static final String FORMAT_DATE = "yyyy-MM-dd";

    public static Date parseDate(String date)
    {
        if( FORMAT_DATETIME.length() == date.length())
        {
            return parseDate(date,FORMAT_DATETIME);
        }
        if( FORMAT_DATE.length() == date.length())
        {
            return parseDate(date,FORMAT_DATE);
        }
        if( date.length() > FORMAT_DATE.length() )
        {
            parseDate(date.substring(0,FORMAT_DATE.length()),FORMAT_DATE);
        }
        return null;
    }

    public static Date parseDate(String date,String foramt)
    {
        try {
            return DateUtils.parseDate(date,foramt);
        } catch (ParseException e) {
            ExceptionHelper.throwServerException(e);
        }
        return null;
    }
}
