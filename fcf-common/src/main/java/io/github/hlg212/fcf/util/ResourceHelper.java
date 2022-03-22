package  io.github.hlg212.fcf.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;


/**
 * 资源文件帮助工具
 *
 * @author huangligui
 * @date 2020年5月30日
 */
@Slf4j
public class ResourceHelper {


    public static InputStream getInputStream(String classPath) {

        try {
            return SpringHelper.getApplicationContext().getResource("classpath:"+classPath).getInputStream();
        } catch (IOException e) {
            ExceptionHelper.throwServerException(e);
        }
        return null;
    }
}
