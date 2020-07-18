/*
 * Copyright (c) 2020. 布比（北京）网络技术有限公司.
 * All rights reserved.
 */

package cn.prosayj.authentication.csrf.model.response;

import cn.prosayj.authentication.csrf.constant.BaseConstant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * ResponseWriter
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-14 下午 01:25
 * @since 1.0.0
 */
public class ResponseWriter {
    /**
     * 认证成功的返回
     *
     * @param response response
     * @param rest     rest
     * @throws IOException
     */
    public static void responseSuccessJsonWriter(HttpServletResponse response, Rest rest) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        String resBody = objectMapper.writeValueAsString(rest);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(resBody);
        printWriter.flush();
        printWriter.close();
    }

    /**
     * 认证成功的返回
     *
     * @param response response
     * @param rest     rest
     */
    public static void responseAccessDeniedJsonWriter(HttpServletResponse response, Rest rest) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding(BaseConstant.DEFAULT_CHARACTER_SET);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String resBody = objectMapper.writeValueAsString(rest);
            PrintWriter printWriter = response.getWriter();
            printWriter.print(resBody);
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
