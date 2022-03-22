package  io.github.hlg212.fcf.web.controller;

import  io.github.hlg212.fcf.Constants;
import  io.github.hlg212.fcf.ISerializable;
import  io.github.hlg212.fcf.QueryClientApi;
import  io.github.hlg212.fcf.annotation.RequestParamOrBody;
import  io.github.hlg212.fcf.model.PageInfo;
import  io.github.hlg212.fcf.model.PageQuery;
import  io.github.hlg212.fcf.model.Qco;
import  io.github.hlg212.fcf.service.QueryService;
import  io.github.hlg212.fcf.util.LogHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 查询控制器
 *
 * @author huangligui
 * @date 2020年8月18日
 */
public interface QueryController<T extends ISerializable,Q extends Qco> extends QueryClientApi<T,Q>,ServiceGet{

//    default  <S extends QueryService<T> & CurdService<T>>S  getService()
//    {
//        Class bc = ResolvableType.forClass(getClass()).getInterfaces()[0].getGeneric(0).resolve();
//        ResolvableType type = ResolvableType.forClassWithGenerics(QueryService.class,bc);
//        String beans[] = SpringHelper.getApplicationContext().getBeanNamesForType(type);
//        return (S) SpringHelper.getBean(beans[0]);
//    }
//
    default  <S extends QueryService<T>>S  getQueryService()
    {
        return (S)getService(QueryService.class);
    }

    @ResponseBody
    @ApiOperation("根据主键获取实体信息")
    @ApiImplicitParam(name="id", value="主键ID", required=true, paramType="query")
    @Override
    @RequestMapping(value="/getById",method={RequestMethod.POST,RequestMethod.GET})
    default  public <E extends T> E getById(@RequestParam("id") Object id) {
        LogHelper.getLog(getClass()).debug("根据主键获取实体信息.id:{}", id);
        return getQueryService().getById(id);
    }

    @ResponseBody
    @ApiOperation("查询实体数据列表")
    @Override
    @RequestMapping(value="/find",method={RequestMethod.POST,RequestMethod.GET})
    default public <E extends T> List<E> find(@RequestParamOrBody Q queryProperty) {
        LogHelper.getLog(getClass()).debug("查询实体数据列表.queryProperty:{}", queryProperty);
        return getQueryService().find(queryProperty);
    }

    @ResponseBody
    @ApiOperation("查询实体数据列表，提供分页功能")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value="当前页数", required=true, dataType="int",example = "1",paramType="query"),
            @ApiImplicitParam(name="pageSize", value="每页显示数量", required=true, dataType="int",example = "10",paramType="query")
    })
    @Override
    @RequestMapping(value="/findPage",method={RequestMethod.POST,RequestMethod.GET})
    default public  <E extends T> PageInfo<E> findPage(@RequestParamOrBody Q queryProperty, @RequestParam(name="pageNum",defaultValue = Constants.QueryClientApi.PAGE_NUM) int pageNum, @RequestParam(name="pageSize",defaultValue = Constants.QueryClientApi.PAGE_SIZE) int pageSize) {
        LogHelper.getLog(getClass()).debug("查询实体数据列表，提供分页功能.queryProperty:{},pageNum:{},pageSize:{}", queryProperty, pageNum, pageSize);
        return getQueryService().findPage(queryProperty, pageNum, pageSize);
    }

    @ResponseBody
    @ApiOperation("查询实体数据列表，提供分页功能,该接口支持使用json的方式，不会对前端数据请求造成参数不一致问题")
    @Override
    default public <E extends T> PageInfo<E> pageQuery(@RequestBody PageQuery<Q> pageQuery) {
        return this.findPage(pageQuery.getQco(),pageQuery.getPageNum(),pageQuery.getPageSize());
    }


    @ResponseBody
    @ApiOperation("查询实体信息")
    @Override
    @RequestMapping(value="/get",method={RequestMethod.POST,RequestMethod.GET})
    default public <E extends T> E get(@RequestParamOrBody Q queryProperty) {
        LogHelper.getLog(getClass()).debug("查询实体信息.queryProperty:{}", queryProperty);
        return getQueryService().get(queryProperty);
    }

    @ResponseBody
    @ApiOperation("查询实体信息其数量")
    @Override
    @RequestMapping(value="/count",method={RequestMethod.POST,RequestMethod.GET})
   default public Integer count(@RequestParamOrBody Q queryProperty) {
        LogHelper.getLog(getClass()).debug("查询实体信息其数量.queryProperty:{}", queryProperty);
        return getQueryService().count(queryProperty);
    }
}
