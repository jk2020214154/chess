package com.chess.backend.service.user.account;

import com.chess.backend.common.BaseResponse;

import java.util.Map;

public interface LoginService {
    public Map<String, String> getToken(String username, String password);
}