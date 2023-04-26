package com.chess.backend.controller.pk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@ResponseBody
@RestController
@RequestMapping("/api/pk/")
public class BotInfoController {
    @RequestMapping("getbotinfo/")
    public Map<String,String> getBotInfo(){
        Map<String,String> bot1=new HashMap<>();
        bot1.put("name","apple");
        bot1.put("rating","1500");
        return bot1;
    }
}