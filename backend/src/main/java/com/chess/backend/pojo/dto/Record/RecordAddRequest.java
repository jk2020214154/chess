package com.chess.backend.pojo.dto.Record;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordAddRequest implements Serializable {

    private int userId;

    private String current;

    private String target;

    private int type;

    private int chessId;

}
