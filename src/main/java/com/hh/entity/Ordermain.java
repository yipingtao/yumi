package com.hh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
/**
 * <p>
 * 
 * </p>
 *
 * @author hh带你飞
 * @since 2020-02-15
 */

@Data
public class Ordermain implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单编号
     */
    private String code;

    /**
     * 价位
     */
    private String money;

    /**
     * 店铺Id
     */
    @TableField("storeId")
    private Integer storeId;

    /**
     * 用户ID
     */
    @TableField("userId")
    private Integer userId;

    /**
     * 店铺名称
     */
    @TableField("storeName")
    private String storeName;

    /**
     * 用户名称
     */
    @TableField("userName")
    private String userName;

    /**
     * 送货地址
     */
    private String address;

    /**
     * 备注
     */
    private String content;

    /**
     * 1 有效 2 无效
     */
    @TableField("isActive")
    private Integer isActive;

    /**
     * 1未支付 2已支付（待发货） 3已发货（待收货）4已收货（待评价）5已评价
     */
    @TableField("state")
    private Integer state;
    /**
     * 创建时间
     */
    @TableField("createTime")
    private Long createTime;

    @TableField(exist = false)
    private List<Ordersub> OrdersubList;

    @TableField(exist = false)
    private String comment;


    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }


//自定义添加
@TableField(exist = false)
private String createTimeDesc;

    @Override
    public String toString() {
        return "Ordermain{" +
        "id=" + id +
        ", code=" + code +
        ", money=" + money +
        ", storeId=" + storeId +
        ", userId=" + userId +
        ", storeName=" + storeName +
        ", userName=" + userName +
        ", address=" + address +
        ", content=" + content +
        ", isActive=" + isActive +
        ", createTime=" + createTime +
        "}";
    }
}
