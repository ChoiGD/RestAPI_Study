package org.test.restAPI.util;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
@Log4j2
public class JWTUtilTests {

    @Autowired
    private JWTUtil jwtUtil;

    @Test
    public void testGenerateToken(){

        Map<String, Object> claimMap = Map.of("mid", "ABCDE");

        String jwtStr = jwtUtil.generateToken(claimMap, 1);

        log.info(jwtStr);

    }

    @Test
    public void testValidate(){

        String token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NTQ2NjE0NTUsIm1pZCI6IkFCQ0RFIiwiaWF0IjoxNjU0NjYxMzk1fQ.c69OfKfR5fZEdyLx1bRhqVsLVVmpYiUTVpYv5V1SF4A";

        jwtUtil.validateToken(token);
    }

}
