package com.chess.backend.service.impl.Record;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chess.backend.pojo.Record;
import com.chess.backend.service.user.Record.RecordService;
import com.chess.backend.mapper.RecordMapper;
import org.springframework.stereotype.Service;

/**
* @author Cu1
* @description 针对表【record】的数据库操作Service实现
* @createDate 2023-03-30 12:20:13
*/
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record>
    implements RecordService{

}




