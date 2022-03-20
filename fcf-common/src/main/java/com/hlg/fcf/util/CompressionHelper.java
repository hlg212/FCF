package com.hlg.fcf.util;

import org.springframework.util.Base64Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;



/**
 * @description: 压缩工具类
 * @author: huangligui
 * @create: 2020-03-30 16:01
 **/
public class CompressionHelper {

    private final static String DEF_CHARSET = "UTF-8";

    public static String compress(String str) {
        return compress(str, DEF_CHARSET);
    }

    public static String compress(String str, String charset) {
        byte[] bs = null;

        try {
            bs = str.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            ExceptionHelper.throwServerException(e);
        }
        try (
                ByteArrayOutputStream bsout = new ByteArrayOutputStream();
                GZIPOutputStream gout = new GZIPOutputStream(bsout);
        ) {
            byte[] outbs = null;
            gout.write(bs);
            gout.finish();
            gout.flush();
            outbs = bsout.toByteArray();

            return  Base64Utils.encodeToString(outbs);

        } catch (IOException e) {
            ExceptionHelper.throwServerException(e);
        }


        return null;
    }

    public static String decompress(String str) {
        return decompress(str, DEF_CHARSET);
    }

    public static String decompress(String str, String charset) {
        byte[] bs = null;
        bs = Base64Utils.decodeFromString(str);

        try (
                ByteArrayOutputStream bsout = new ByteArrayOutputStream();
                GZIPInputStream gin = new GZIPInputStream(new ByteArrayInputStream(bs));
        ) {
            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset = gin.read(buffer)) != -1) {
                bsout.write(buffer, 0, offset);
            }
            return new String(bsout.toByteArray(), charset);
        } catch (Exception e) {
            ExceptionHelper.throwServerException(e);
        }
        return null;
    }
}
