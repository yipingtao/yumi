package com.hh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
/**
 * <p>
 * 
 * </p>
 *

 */

@Data
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 价位
     */
    private String money;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private Long createTime;

    /**
     * 照片
     */
    private String photo;

    /**
     * 1 有效 2 无效
     */
    @TableField("isActive")
    private Integer isActive;

    /**
     * 店铺id
     */
    @TableField("storeId")
    private Integer storeId;

    /**
     * 简介
     */
    private String remark;
    /**
     * 店铺名称
     */
    @TableField(exist = false)
    private String storeName;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


//自定义添加
@TableField(exist = false)
private String createTimeDesc;

    @Override
    public String toString() {
        return "Goods{" +
        "id=" + id +
        ", name=" + name +
        ", money=" + money +
        ", createTime=" + createTime +
        ", photo=" + photo +
        ", isActive=" + isActive +
        ", storeId=" + storeId +
        ", remark=" + remark +
        "}";
    }
}
