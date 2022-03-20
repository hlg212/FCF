package com.hlg.fcf.web.controller;

import com.hlg.fcf.ISerializable;
import com.hlg.fcf.Import;
import com.hlg.fcf.api.FileApi;
import com.hlg.fcf.model.ImpExpModel;
import com.hlg.fcf.model.Qco;
import com.hlg.fcf.model.basic.File;
import com.hlg.fcf.service.ImportService;
import com.hlg.fcf.util.SpringUtils;
import com.hlg.fcf.web.util.UploadHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

/**
 * 导入控制器
 *
 * @author huangligui
 * @date 2020年8月18日
 */
public interface ImportController<T extends ISerializable,Q extends Qco> extends Import<T,ImpExpModel>,ServiceGet {


    default <S extends ImportService<?>>S  getImportService()
    {
       // return (S)ServiceDaoHelper.getService(getClass(),ImportService.class);
        return (S)getService(ImportService.class);
    }


    @Override
    default public void importSave(File file) {
        getImportService().importSave(file);
    }

    @ApiOperation(value="解析导入文件，返回解析后的数据，传入文件中心文件路径")
    @ApiImplicitParam(name="path", value="文件路径,传入文件中心文件路径", required=true)
    @RequestMapping(value="/import/show", method = {RequestMethod.GET})
    default  public Collection<T> importShow(String path) {
        return importShow(getFile(path));
    }

    default  Collection<T> importShow(File file) {
        ImpExpModel impExpModel =  getImportService().importParse(file);
        return impExpModel.getDatas();
    }

    @ApiOperation(value="导入文件，直接进行保存，传入文件中心文件路径")
    @ApiImplicitParam(name="path", value="文件路径,传入文件中心文件路径", required=true)
    @RequestMapping(value="/import/save", method = {RequestMethod.GET})
    default public void importSave(String path) {
        getImportService().importSave(getFile(path));
    }

    static  File getFile(String path)
    {
        FileApi fileApi = SpringUtils.getBean(FileApi.class);
        return fileApi.download(path);
    }

    @ApiOperation(value="解析导入文件，返回解析后的数据，直接上传文件")
    @RequestMapping(value="/import/upload/show", method = {RequestMethod.POST})
    default public Collection<T> importUploadShow(MultipartFile multipartFile) {
        File f = UploadHelper.upload(multipartFile);
        return importShow(f);
    }

    @ApiOperation(value="导入文件，直接进行保存，直接上传文件")
    @RequestMapping(value="/import/upload/save", method = {RequestMethod.POST})
    default public void importUploadSave(MultipartFile multipartFile) {
        File f = UploadHelper.upload(multipartFile);
        importSave(f);
    }
}
