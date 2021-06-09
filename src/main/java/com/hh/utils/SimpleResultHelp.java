package com.hh.utils;

import lombok.Data;

/**
 *
 */
@Data
public class SimpleResultHelp<T> {
    String resCode = "0000";
    String resDesc;
    private T data;

    public SimpleResultHelp(T data) {
        this.data = data;
    }

    public SimpleResultHelp(String resCode, String resDesc) {
        this.resCode = resCode;
        this.resDesc = resDesc;
    }

    public SimpleResultHelp() {

    }

    public static SimpleResultHelp success(Object object) {
        SimpleResultHelp simpleResultHelp = new SimpleResultHelp();
        simpleResultHelp.setResCode("0000");
        simpleResultHelp.setData(object);
        return simpleResultHelp;
    }

    public static SimpleResultHelp result(String resCode, String resDesc) {
        SimpleResultHelp simpleResultHelp = new SimpleResultHelp();
        simpleResultHelp.setResCode(resCode);
        simpleResultHelp.setResDesc(resDesc);
        return simpleResultHelp;
    }

    public static SimpleResultHelp result(boolean flag) {
        SimpleResultHelp simpleResultHelp = new SimpleResultHelp();
        if (flag == true) {
            simpleResultHelp.setResCode("0000");
        } else {
            simpleResultHelp.setResCode("9999");
        }
        return simpleResultHelp;
    }
}
