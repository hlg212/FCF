package  io.github.hlg212.fcf.web.controller;

import  io.github.hlg212.fcf.ISerializable;
import  io.github.hlg212.fcf.Import;
import  io.github.hlg212.fcf.api.FileApi;
import  io.github.hlg212.fcf.model.ImpExpModel;
import  io.github.hlg212.fcf.model.Qco;
import  io.github.hlg212.fcf.model.basic.File;
import io.github.hlg212.fcf.model.basic.IFile;
import  io.github.hlg212.fcf.service.ImportService;
import  io.github.hlg212.fcf.util.SpringHelper;
import  io.github.hlg212.fcf.web.util.UploadHelper;
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
    default public void importSave(IFile file) {
        getImportService().importSave(file);
    }

    @ApiOperation(value="解析导入文件，返回解析后的数据，传入文件中心文件路径")
    @ApiImplicitParam(name="path", value="文件路径,传入文件中心文件路径", required=true)
        @RequestMapping(value="/import/show", method = {RequestMethod.GET})
    default  public Collection<T> importShow(String path) {
        return importShow(getFile(path));
    }

    default  Collection<T> importShow(IFile file) {
        ImpExpModel impExpModel =  getImportService().importParse(file);
        return impExpModel.getDatas();
    }

    @ApiOperation(value="导入文件，直接进行保存，传入文件中心文件路径")
    @ApiImplicitParam(name="path", value="文件路径,传入文件中心文件路径", required=true)
    @RequestMapping(value="/import/save", method = {RequestMethod.POST})
    default public void importSave(String path) {
        getImportService().importSave(getFile(path));
    }

    static IFile getFile(String path)
    {
        FileApi fileApi = SpringHelper.getBean(FileApi.class);

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
