package com.chess.backend.controller.pk;

import com.chess.backend.common.BaseResponse;
import com.chess.backend.common.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pk")
public class BotInfoController {
    @GetMapping("/getbotinfo")
    public BaseResponse<Map<String,String>> getBotInfo(){
        Map<String,String> bot1=new HashMap<>();
        bot1.put("name","apple");
        bot1.put("rating","1500");
        return ResultUtils.success(bot1);
    }
}