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
 * @since 2020-02-19
 */

@Data
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 招聘id
     */
    @TableField("storeId")
    private Integer storeId;

    /**
     * 用户id
     */
    @TableField("userId")
    private Integer userId;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private Long createTime;

    /**
     * 1有效 2无效
     */
    @TableField("isActive")
    private Integer isActive;

    /**
     * 回复内容
     */
    private String recontent;

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
    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
    public String getRecontent() {
        return recontent;
    }

    public void setRecontent(String recontent) {
        this.recontent = recontent;
    }


//自定义添加
@TableField(exist = false)
private String createTimeDesc;

    @Override
    public String toString() {
        return "Message{" +
        "id=" + id +
        ", storeId=" + storeId +
        ", userId=" + userId +
        ", content=" + content +
        ", createTime=" + createTime +
        ", isActive=" + isActive +
        ", recontent=" + recontent +
        "}";
    }
}
