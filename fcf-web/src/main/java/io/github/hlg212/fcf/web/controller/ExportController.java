package  io.github.hlg212.fcf.web.controller;

import  io.github.hlg212.fcf.Export;
import  io.github.hlg212.fcf.ISerializable;
import  io.github.hlg212.fcf.model.ImpExpModel;
import  io.github.hlg212.fcf.model.PageQuery;
import  io.github.hlg212.fcf.model.Qco;
import  io.github.hlg212.fcf.model.basic.File;
import  io.github.hlg212.fcf.service.ExportService;
import  io.github.hlg212.fcf.util.ExceptionHelper;
import  io.github.hlg212.fcf.util.LockHelper;
import  io.github.hlg212.fcf.web.util.HttpServletHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.UnsupportedEncodingException;


/**
 * 导出控制器
 *
 * @author huangligui
 * @date 2020年8月18日
 */
public interface ExportController<T extends ISerializable,Q extends Qco> extends Export<Q,ImpExpModel>,ServiceGet {


    default <S extends ExportService<?>>S  getExportService()
    {
        return (S)getService(ExportService.class);
    }

    @ApiOperation(value="下载导出的模板",notes="根据context进行判断,如果为空请根据路径下载文件；否则进行请将context进行blob转换文件")
    @Override
    @RequestMapping(value="/getExportTemplate", method = {RequestMethod.GET,RequestMethod.POST})
   default public File getExportTemplate() {
        return getExportService().getExportTemplate();
    }


    @RequestMapping(value="/down/exportTemplate", method = {RequestMethod.GET,RequestMethod.POST})
    default public byte[] downExportTemplate() {
        File f = getExportTemplate();
        if( f != null && f.getContent() != null ) {
            setHeaders(f.getFileName(),"");
            return f.getContent();
        }
        else
        {
            ExceptionHelper.throwBusinessException("文件不存在!");
        }
        return null;
    }
    @ApiOperation(value="导出数据",notes="根据context进行判断,如果为空请根据路径下载文件；否则进行请将context进行blob转换文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value="当前页数", required=true, dataType="int",example = "1",paramType="query"),
            @ApiImplicitParam(name="pageSize", value="每页显示数量", required=true, dataType="int",example = "10",paramType="query")
    })
    @RequestMapping(value="/exportPage", method = {RequestMethod.GET})
    default public File exportPage(Q qco, int pageNum, int pageSize) {
        return exportPage(new PageQuery<>(qco,pageNum,pageSize));
    }

    @ApiOperation(value="导出数据",notes="根据context进行判断,如果为空请根据路径下载文件；否则进行请将context进行blob转换文件")
    @RequestMapping(value="/exportPage", method = {RequestMethod.POST})
    @Override
    default public File exportPage(@RequestBody PageQuery<Q> pageQuery) {

        if( !LockHelper.tryLock("__EXPORT"))
        {
            ExceptionHelper.throwBusinessException("导出繁忙,请稍后再试!");
        }
        try{
            return getExportService().exportPage((PageQuery<Qco>) pageQuery);
        }finally {
            LockHelper.unlock("__EXPORT");
        }

    }

    @ApiOperation(value="导出数据,直接会弹出下载，open方式调用")
    @RequestMapping(value="/down/exportPage", method = {RequestMethod.POST})
    default  public byte[] downExportPage(@RequestBody PageQuery<Q> pageQuery) {
        File f = exportPage(pageQuery);
        if( f != null && f.getContent() != null ) {
            setHeaders(f.getFileName(),"");
            return f.getContent();
        }
        else
        {
            ExceptionHelper.throwBusinessException("文件不存在!");
        }
        return null;
    }

    @ApiOperation(value="导出数据,直接会弹出下载，open方式调用")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value="当前页数", required=true, dataType="int",example = "1",paramType="query"),
            @ApiImplicitParam(name="pageSize", value="每页显示数量", required=true, dataType="int",example = "10",paramType="query")
    })
    @RequestMapping(value="/down/exportPage", method = {RequestMethod.GET})
    default  public byte[] downExportPage(Q qco, int pageNum, int pageSize) {
        return downExportPage(new PageQuery<>(qco,pageNum,pageSize));
    }

    static void setHeaders(String fileName,String type)
    {
        HttpServletHelper.getResponse().setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
        try {
            HttpServletHelper.getResponse().setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            ExceptionHelper.throwServerException(e);
        }
    }
}
