package com.he.chataiparent.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.he.chataiparent.common.auth.AuthContextHolder;
import com.he.chataiparent.common.result.Result;
import com.he.chataiparent.model.entity.Chats;
import com.he.chataiparent.service.ChatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chats")
public class ChatsController {

    @Autowired
    private ChatsService chatsService;

    /**
     * 获取当前用户chat列表
     * @return
     */
    @GetMapping("/list")
    public Result list(){
        List<Chats> chatsList = chatsService.list(
                new LambdaQueryWrapper<Chats>()
                        .eq(Chats::getUserId, AuthContextHolder.getUserId())
                        .orderByDesc(Chats::getCreateTime)
        );
        return Result.ok(chatsList);
    }

    @PostMapping("/create")
    public Result create() {
        chatsService.create();
        return Result.ok("添加成功");
    }

    @PutMapping("/edit")
    public Result editChat(@RequestBody Chats chat){
        chatsService.updateById(chat);
        return Result.ok("修改成功");
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteChat(@PathVariable Long id) {
        chatsService.removeById(id);
        return Result.ok("删除成功");
    }

}
