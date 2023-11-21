package com.he.chataiparent.service;

import com.he.chataiparent.model.entity.Chats;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author he
* @description 针对表【chats】的数据库操作Service
* @createDate 2023-11-20 10:32:26
*/
public interface ChatsService extends IService<Chats> {

    /**
     * 创建chat
     */
    void create();
}
