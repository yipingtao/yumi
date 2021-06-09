package com.hh.entity;


import com.baomidou.mybatisplus.annotation.TableField;

public class CommonPage {

    //页数
    @TableField(exist = false)
    Integer page = 1;
    //每页跳数
    @TableField(exist = false)
    Integer pageSize = 20;

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
