package com.he.chataiparent.service;

import com.he.chataiparent.model.dto.TalkDTO;
import com.he.chataiparent.model.entity.Talk;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author he
* @description 针对表【talk】的数据库操作Service
* @createDate 2023-11-21 09:53:01
*/
public interface TalkService extends IService<Talk> {

    String answer(TalkDTO talkDTO);
}
