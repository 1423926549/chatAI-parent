package com.he.chataiparent.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.he.chataiparent.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @TableName user
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="user")
@Data
public class User extends BaseEntity implements Serializable {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String header;

}