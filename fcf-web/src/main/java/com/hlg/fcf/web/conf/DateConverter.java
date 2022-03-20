package com.hlg.fcf.web.conf;

import com.hlg.fcf.Constants;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @description: 全局时间格式转换
 * @author: huangligui
 * @create: 2019-02-28 15:32
 **/

@Component(Constants.FRAME_BEAN_PREFIX+"DateConverter")
public class DateConverter implements Converter<String, Date> {

    private static final String YYYYMMDD = "yyyy-MM-dd";
    private static final String YYYYMMDDHH = "yyyy-MM-dd HH";
    private static final String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";
    private static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

    private static final String S_YYYYMMDD = "yyyy/MM/dd";
    private static final String S_YYYYMMDDHH = "yyyy/MM/dd HH";
    private static final String S_YYYYMMDDHHMM = "yyyy/MM/dd HH:mm";
    private static final String S_YYYYMMDDHHMMSS = "yyyy/MM/dd HH:mm:ss";

    @Override
    public Date convert(String dateStr) {
        if(StringUtils.isBlank(dateStr)){
            return null;
        }
        try {
            if (dateStr.indexOf("-") > 0) {
                if (dateStr.length() == 10) {
                    return DateUtils.parseDate(dateStr, YYYYMMDD);
                }
                if (dateStr.length() == 12) {
                    return DateUtils.parseDate(dateStr, YYYYMMDDHH);
                }
                if (dateStr.length() == 16) {
                    return DateUtils.parseDate(dateStr, YYYYMMDDHHMM);
                }
                if (dateStr.length() == 19) {
                    return DateUtils.parseDate(dateStr, YYYYMMDDHHMMSS);
                }
            }
            if (dateStr.indexOf("/") > 0) {
                if (dateStr.length() == 10) {
                    return DateUtils.parseDate(dateStr, S_YYYYMMDD);
                }
                if (dateStr.length() == 12) {
                    return DateUtils.parseDate(dateStr, S_YYYYMMDDHH);
                }
                if (dateStr.length() == 16) {
                    return DateUtils.parseDate(dateStr, S_YYYYMMDDHHMM);
                }
                if (dateStr.length() == 19) {
                    return DateUtils.parseDate(dateStr, S_YYYYMMDDHHMMSS);
                }
            }
        }catch(Exception e)
        {
            return null;
        }
        return null;
    }
}
