package  io.github.hlg212.fcf.web.util;

import  io.github.hlg212.fcf.model.basic.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
/**
 * 上传帮助工具
 *
 * @author huangligui
 * @date 2020年5月30日
 */
public class UploadHelper {


    public static File upload(MultipartFile multipartFile)
    {
        String fileName = multipartFile.getOriginalFilename();
        File file = new File();

        // 设置文件名
        file.setFileName(fileName.substring(0, fileName.lastIndexOf(".")));
        //file.setFileName(multipartFile.getOriginalFilename());
        // 设置文件格式
        file.setContentType(fileName.substring(fileName.lastIndexOf(".") + 1));
        //file.setContentType(multipartFile.getContentType());
        // 设置文件大小
        //file.setSize(Long.valueOf(fileByte.length));
        file.setSize(multipartFile.getSize());
        try {
            file.setContent(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


}
