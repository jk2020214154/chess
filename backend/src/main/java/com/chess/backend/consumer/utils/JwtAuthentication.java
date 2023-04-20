package com.chess.backend.consumer.utils;

import com.chess.backend.utils.JwtUtil;
import io.jsonwebtoken.Claims;

public class JwtAuthentication {
    public static Integer getUserId(String token) {
        int userId = -1;
        try {
            //System.out.println(token);
            Claims claims = JwtUtil.parseJWT(token);
            userId = Integer.parseInt(claims.getSubject());
            //System.out.println(userId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return userId;
    }
}