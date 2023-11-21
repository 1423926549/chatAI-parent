package com.he.chataiparent.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.he.chataiparent.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * 
 * @TableName chats
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="chats")
@ToString
@Data
public class Chats extends BaseEntity implements Serializable {

    /**
     * chat名
     */
    private String chatName;

    /**
     * 用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 对话session
     */
    private String chatSession;

}