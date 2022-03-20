package com.hlg.fcf.model.basic;

import com.hlg.fcf.model.Model;
import lombok.Data;

@Data
public class BlackWhiteList extends Model implements IBlackWhiteList {
    private String id;
    private String type;
    private String start;
    private String end;
}
