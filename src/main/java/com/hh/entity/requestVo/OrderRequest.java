package com.hh.entity.requestVo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author hh带你飞
 * @since 2020-02-15
 */

@Data
public class OrderRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String code;
    private String money;
    private Integer storeId;
    private Integer userId;
    private String storeName;
    private String userName;
    private String address;
    private String content;
    private Integer isActive;
    private Integer state;
    List<GoodsRequest> goodsList;
}

