package  io.github.hlg212.fcf.service.impl;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import  io.github.hlg212.fcf.api.AppApi;
import  io.github.hlg212.fcf.api.DicApi;
import  io.github.hlg212.fcf.model.basic.IDic;
import  io.github.hlg212.fcf.service.DicService;
import  io.github.hlg212.fcf.util.AppContextHelper;
import  io.github.hlg212.fcf.util.TreeHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class DicServiceImpl implements DicService {

    @Autowired
    private DicApi dicApi;

    @Autowired
    private AppApi appApi;

    private Table<String, String, IDic> table = HashBasedTable.create();

    @Override
    public String getVal(String code, String key){
        if(StringUtils.isBlank(key)){
            return "";
        }
        if(StringUtils.isBlank(code)) {
            code = AppContextHelper.getAppCode();
        }
        IDic dic = getDic(code, key);
        if(dic != null){
            return dic.getValue();
        }
        return "";
    }

    @Override
    public String getName(String code, String key){
        if(StringUtils.isBlank(key)){
            return "";
        }
        if(StringUtils.isBlank(code)) {
            code = AppContextHelper.getAppCode();
        }
        IDic dic = getDic(code, key);
        if(dic != null){
            return dic.getMc();
        }
        return "";
    }

    @Override
    public Map<String, String> getMapVal(String code, final String key){
        if(StringUtils.isBlank(key)){
            return null;
        }
        if(StringUtils.isBlank(code)) {
            code = AppContextHelper.getAppCode();
        }
        List<IDic> lists =getChildren(code, key);
        Map<String, String> map = new HashMap<String, String>(lists.size());
        if( lists != null ){
            for(IDic dic : lists){
                map.put(dic.getKey(), dic.getValue());
            }
        }
        return map;
    }
    private List<IDic> getChildren(String code,String key)
    {
        IDic d =  getDic(code, key);
        if( d == null )
        {
            return Collections.EMPTY_LIST;
        }
        return d.getChildren();
    }
    @Override
    public Map<String, String> getMapName(String code, String key){
        if(StringUtils.isBlank(key)){
            return null;
        }
        if(StringUtils.isBlank(code)) {
            code = AppContextHelper.getAppCode();
        }
        List<IDic> lists = getChildren(code, key);
        Map<String, String> map = new HashMap<String, String>(lists.size());
        if( lists != null ){
            for(IDic dic : lists){
                map.put(dic.getKey(), dic.getMc());
            }
        }
        return map;
    }
    private IDic getDic(String appCode,String key)
    {
        if (!table.containsRow(appCode) )
        {
            load(appCode);
        }
        return table.get(appCode, key);
    }

    @Override
    public List<IDic> getAllDic(String appCode) {
        List<IDic> lists = table.row(appCode).values().stream().collect(Collectors.toList());
        if(lists == null || lists.isEmpty()) {
            lists = load(appCode);
        }
        return lists;
    }

    private List<IDic> load(String appCode)
    {
        List<IDic> lists = dicApi.getAllDics(appCode);
        table.rowMap().remove(appCode);
        Optional.ofNullable(lists).orElse(Collections.emptyList()).forEach(dict -> {
            table.put(appCode, dict.getKey(), dict);
        });
        if(lists != null && !lists.isEmpty()){
            TreeHelper.buildTree(lists);
        }
        return lists;
    }

    @Override
    public void refresh(String appCode) {
        load(appCode);
    }
}
