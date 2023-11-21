package com.he.chataiparent.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.he.chataiparent.common.auth.AuthContextHolder;
import com.he.chataiparent.model.entity.Chats;
import com.he.chataiparent.model.entity.Talk;
import com.he.chataiparent.service.ChatsService;
import com.he.chataiparent.mapper.ChatsMapper;
import com.he.chataiparent.service.TalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author he
* @description 针对表【chats】的数据库操作Service实现
* @createDate 2023-11-20 10:32:26
*/
@Service
public class ChatsServiceImpl extends ServiceImpl<ChatsMapper, Chats> implements ChatsService{

    @Autowired
    private TalkService talkService;

    /**
     * 创建chat
     */
    @Override
    public void create() {
        String chatSession = IdUtil.simpleUUID();
        String chatName = "New Chat";
        Chats chat = new Chats();
        chat.setChatName(chatName);
        chat.setChatSession(chatSession);
        chat.setUserId(AuthContextHolder.getUserId());
        this.save(chat);
    }

    /**
     * 删除chat
     *
     * @param id
     */
    @Transactional
    @Override
    public void deleteChat(Long id) {
        LambdaQueryWrapper<Talk> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Talk::getChatId, id);
        talkService.remove(wrapper);
        this.removeById(id);
    }
}




