package com.he.chataiparent.mapper;

import com.he.chataiparent.model.entity.Chats;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author he
* @description 针对表【chats】的数据库操作Mapper
* @createDate 2023-11-20 10:32:26
* @Entity com.he.chataiparent.model.entity.Chats
*/
@Mapper
public interface ChatsMapper extends BaseMapper<Chats> {

}




