package com.hh.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

public class CommonUtil {
    final static Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    public static boolean notNull(Object object) {
        boolean flag = true;
        if (null == object || object.equals("")) {
            flag = false;
        }
        return flag;
    }

    public static ResultHelp getResultHelp(IPage page) {
        ResultHelp resultHelp = new ResultHelp();
        resultHelp.setResultList(page.getRecords());
        resultHelp.setPageCount((int) page.getPages());
        resultHelp.setPage((int) page.getCurrent());
        resultHelp.setPageSize((int) page.getSize());
        resultHelp.setResultCount((int) page.getTotal());
        return resultHelp;
    }

    public static void exportCommon(HttpServletRequest request, HttpServletResponse response, String tableName, Workbook workbook) {
        InputStream inputStream = null;
        OutputStream out = null;
        try {
            String header = request.getHeader("User-Agent");
            String destFilePath = URLEncoder.encode(DateUtil.formatTime(System.currentTimeMillis(), "yyyyMMdd") + "_" + tableName + ".xlsx", "UTF-8");
            if (header.contains("Firefox")) {
                //说明是火狐浏览器,使用Base64Encoder类进行编码
                BASE64Encoder base = new BASE64Encoder();
                destFilePath = "=?utf-8?B?" + base.encode((DateUtil.formatTime(System.currentTimeMillis(), "yyyyMMdd") + "_" + tableName + ".xlsx").getBytes("utf-8")) + "?=";
            }
            response.reset();
            response.setContentType("application/msexcel");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=\"" + destFilePath + "\"");
            response.setCharacterEncoding("UTF-8");
            out = response.getOutputStream();
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("{}导出异常", tableName);
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }

    }

}
