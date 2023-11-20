package com.he.chataiparent.mapper;

import com.he.chataiparent.model.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author he
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-11-17 15:41:04
* @Entity com.he.chataiparent.model.pojo.User
*/

@Mapper
public interface UserMapper extends BaseMapper<User> {

}




