package com.he.chataiparent.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.he.chataiparent.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @TableName talk
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="talk")
@Data
public class Talk extends BaseEntity implements Serializable {

    /**
     * chat id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long chatId;

    /**
     * 对话类型 1：我，2：chat
     */
    private Integer type;

    /**
     * 对话时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime time;

    /**
     * 对话内容
     */
    private String content;

}