package com.chess.backend.controller.user;

import com.chess.backend.common.BaseResponse;
import com.chess.backend.common.ResultUtils;
import com.chess.backend.service.user.account.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class InfoController {
    @Autowired
    private InfoService infoService;
    @GetMapping("/user/account/info/")
    public BaseResponse<Map<String,String>> getinfo(){
        return ResultUtils.success(infoService.getinfo());
    }
}