package com.hh.exportEntity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class UsersEntity implements java.io.Serializable{

    private Integer id;

    @Excel(name = "姓名", height = 20, width = 30, isImportField = "true_st")
    private String name;

    @Excel(name = "登录名", isImportField = "true_st")
    private String loginName;

    @Excel(name = "密码", isImportField = "true_st")
    private String password;
}
