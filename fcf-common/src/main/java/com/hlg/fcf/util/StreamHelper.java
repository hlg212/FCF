package com.hlg.fcf.util;

import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;



/**
 * 流帮助工具
 *
 * @author huangligui
 * @date 2020年5月30日
 */
public class StreamHelper {

    public static byte[] read(InputStream in)
    {
        try {
            return StreamUtils.copyToByteArray(in);
        } catch (IOException e) {
            ExceptionHelper.throwServerException(e);
        }
        return null;
    }

    public static InputStream byteToInputStream(byte[] bs)
    {
        return new ByteArrayInputStream(bs);
    }

}
