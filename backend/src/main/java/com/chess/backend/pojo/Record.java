package com.chess.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record implements Serializable
{
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     *
     */
    private Integer userId;

    /**
     *
     */
    private String current;

    /**
     *
     */
    private String target;

    /**
     *
     */
    private Integer type;

    /**
     *
     */
    private Integer chessId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}