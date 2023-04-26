package com.chess.backend.controller.pk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@ResponseBody
@RestController
@RequestMapping("/api/pk/")
public class IndexController {
    @RequestMapping("index/")
    public String index(){
        return "/pk/index.html";
    }
}
