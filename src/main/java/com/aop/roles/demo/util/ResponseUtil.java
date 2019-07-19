package com.aop.roles.demo.util;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ResponseUtil {
    /**
     * 相应到前端的信息
     * @param response
     * @param msg
     */
    public static void repsonseSetInfo(HttpServletResponse response,JSONObject msg){
        try {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.write(msg.toJSONString());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
