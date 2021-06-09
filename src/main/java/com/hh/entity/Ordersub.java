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
public class Ordersub implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 商品id
     */
    @TableField("goodsId")
    private Integer goodsId;

    /**
     * 主订单id
     */
    @TableField("mainId")
    private Integer mainId;

    /**
     * 数量
     */
    private String amount;

    @TableField("createTime")
    private Long createTime;

    /**
     * 商品单价
     */
    private String money;

    /**
     * 商品名称
     */
    @TableField("goodName")
    private String goodName;
    @TableField(exist = false)
    private Goods goods;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }
    public Integer getMainId() {
        return mainId;
    }

    public void setMainId(Integer mainId) {
        this.mainId = mainId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }


//自定义添加
@TableField(exist = false)
private String createTimeDesc;

    @Override
    public String toString() {
        return "Ordersub{" +
        "id=" + id +
        ", goodsId=" + goodsId +
        ", mainId=" + mainId +
        ", amount=" + amount +
        ", createTime=" + createTime +
        ", money=" + money +
        ", goodName=" + goodName +
        "}";
    }
}
