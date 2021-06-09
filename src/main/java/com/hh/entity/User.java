package com.hh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 */
@Data
public class User  implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String password;
    @TableField("loginName")
    private String loginName;
    private String sex;
    private String age;
    @TableField("isActive")
    private Integer isActive;
    private String photo;
    /**
     * 工号
     */
    private String code;
    /**
     * 创建时间
     */
    @TableField("createTime")
    private Long createTime;

}
