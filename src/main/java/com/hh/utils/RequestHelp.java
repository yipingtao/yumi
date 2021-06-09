package com.hh.utils;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class RequestHelp implements Serializable {

    String sortField = "";
    String sortBy = "";
    //查询条件
    String search = "{}";
    //导出Excel字段
    String exportData = "{}";
    //页数
    Integer page = 1;
    //每页跳数
    Integer pageSize = 20;



    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getExportData() {
        return exportData;
    }

    public void setExportData(String exportData) {
        this.exportData = exportData;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
