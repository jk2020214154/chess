package com.chess.backend.controller.user;

import com.chess.backend.common.BaseResponse;
import com.chess.backend.common.ResultUtils;
import com.chess.backend.service.user.account.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/user/account/token/")
    public BaseResponse<Map<String,String>> getToken(@RequestParam Map<String,String> map){
        String username=map.get("username");
        String password=map.get("password");
        //System.out.println(username+" " +password);
        return ResultUtils.success(loginService.getToken(username,password));
    }


}