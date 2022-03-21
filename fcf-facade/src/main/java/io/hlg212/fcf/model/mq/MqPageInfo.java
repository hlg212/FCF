package io.hlg212.fcf.model.mq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.hlg212.fcf.model.PageInfo;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MqPageInfo<T> extends PageInfo<T> {

    @JsonProperty("page_count")
    public void setPageCount(int pageCount) {
       setPages(pageCount);
    }

    @JsonProperty("filtered_count")
    public void setFilteredCount(int filteredCount) {
        setTotal(filteredCount);
    }

    public void setPage(int page) {
        setPageNum(page);
    }

    public void setItems(List<T> exchanges)
    {
        setList(exchanges);
    }

}
