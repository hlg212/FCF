package  io.github.hlg212.fcf.model;

import  io.github.hlg212.fcf.annotation.Field;
import lombok.Data;

@Data
public class PageQuery<Q extends Qco> {
    @Field(description="查询条件对象")
    private Q qco;
    @Field(description="当前页数")
    private int pageNum = Constants.PageQuery.PAGE_NUM;
    @Field(description="每页显示数量")
    private int pageSize = Constants.PageQuery.PAGE_SIZE;

    public PageQuery(Q qco, int pageNum, int pageSize) {
        this.qco = qco;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public PageQuery(Q qco) {
        this.qco = qco;
    }

    public PageQuery() {
    }
}
