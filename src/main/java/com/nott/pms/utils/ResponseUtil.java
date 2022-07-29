package com.nott.pms.utils;

import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nott.pms.common.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Zouwenlong
 * Date on 2022/2/3 17:05
 */
public class ResponseUtil {
    public static void out(HttpServletResponse response, Result r) {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            mapper.writeValue(response.getWriter(), r);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
