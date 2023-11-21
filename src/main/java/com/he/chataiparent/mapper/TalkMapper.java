package com.he.chataiparent.mapper;

import com.he.chataiparent.model.entity.Talk;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author he
* @description 针对表【talk】的数据库操作Mapper
* @createDate 2023-11-21 09:53:01
* @Entity com.he.chataiparent.model.entity.Talk
*/
@Mapper
public interface TalkMapper extends BaseMapper<Talk> {

}




