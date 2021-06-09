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
 * @author hh带你飞
 * @since 2020-02-15
 */

@Data
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 菜品id
     */
    @TableField("storeId")
    private Integer storeId;

    /**
     * 用户id
     */
    @TableField("userId")
    private Integer userId;

    /**
     * 主订单
     */
    @TableField("orderId")
    private Integer orderId;

    /**
     * 内容
     */
    private String content;

    @TableField("createTime")
    private Long createTime;

    @TableField(exist = false)
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        return "Comment{" +
        "id=" + id +
        ", storeId=" + storeId +
        ", userId=" + userId +
        ", content=" + content +
        ", createTime=" + createTime +
        "}";
    }
}
