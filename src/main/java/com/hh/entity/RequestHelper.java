package com.hh.entity;

import com.alibaba.fastjson.JSON;
import com.hh.utils.CommonUtil;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class RequestHelper {

    //页数
    Integer page = 1;
    //每页数
    Integer pageSize = 20;

    String search;

    Map<String, String> keyMap;

    public  Map<String, String> apply() {
        keyMap = new HashMap<>();
        if (CommonUtil.notNull(search)) {
            keyMap = JSON.parseObject(search, Map.class);
        }
        return keyMap;
    }

}
