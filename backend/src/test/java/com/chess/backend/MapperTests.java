package com.chess.backend;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chess.backend.pojo.Record;
import com.chess.backend.mapper.RecordMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@MapperScan("com.chess.backend.mapper")
@SpringBootTest
public class MapperTests {

    @Autowired
    private RecordMapper recordMapper;

    @Test
    void TestRecord() {
        List<Record> records = recordMapper.selectList(new QueryWrapper<>());
        for (Record record : records) {
            System.out.println(record);
        }
    }

}
