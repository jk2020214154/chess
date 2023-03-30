package com.chess.backend.controller.Record;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chess.backend.common.BaseResponse;
import com.chess.backend.common.ErrorCode;
import com.chess.backend.common.ResultUtils;
import com.chess.backend.pojo.Record;
import com.chess.backend.pojo.dto.Record.RecordAddRequest;
import com.chess.backend.service.user.Record.RecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordController {

    @Resource
    private RecordService recordService;

    @GetMapping("/list/{chessId}")
    public BaseResponse<List<Record>> recordList(@PathVariable("chessId") int chessId) {
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chess_id", chessId);
        List<Record> records = recordService.list(queryWrapper);
        return ResultUtils.success(records);
    }

    @PostMapping("/add")
    public BaseResponse<Record> recordList(@RequestBody RecordAddRequest recordAddRequest, HttpServletRequest request) {
        if (recordAddRequest == null) {
            throw new RuntimeException(ErrorCode.PARAMS_ERROR.getMessage());
        }
        Record record = new Record();
        BeanUtils.copyProperties(recordAddRequest, record);
        recordService.save(record);
        return ResultUtils.success(record);
    }

    @PostMapping("/delete/chess")
    public BaseResponse<Boolean> recordDeleteByChessId(int chessId) {
        QueryWrapper<Record> recordDeleteWrapper = new QueryWrapper<>();
        recordDeleteWrapper.eq("chess_id", chessId);
        boolean res = recordService.remove(recordDeleteWrapper);
        return ResultUtils.success(res);
    }

    @PostMapping("/delete/entity")
    public BaseResponse<Boolean> recordDeleteById(int id) {
        boolean res = recordService.removeById(id);
        return ResultUtils.success(res);
    }

}
