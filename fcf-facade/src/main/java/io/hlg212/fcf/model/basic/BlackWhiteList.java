package io.hlg212.fcf.model.basic;

import io.hlg212.fcf.model.Model;
import lombok.Data;

@Data
public class BlackWhiteList extends Model implements IBlackWhiteList {
    private String id;
    private String type;
    private String start;
    private String end;
}
