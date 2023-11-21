package com.he.chataiparent.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.he.chataiparent.common.exception.ChatException;
import com.he.chataiparent.common.result.Result;
import com.he.chataiparent.model.dto.TalkDTO;
import com.he.chataiparent.model.entity.Talk;
import com.he.chataiparent.service.TalkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/talk")
@Slf4j
public class TalkController {

    @Autowired
    private TalkService talkService;

    @GetMapping("/list/{chatId}")
    public Result list(@PathVariable Long chatId) {
        List<Talk> list = talkService.list(
                new LambdaQueryWrapper<Talk>()
                        .eq(Talk::getChatId, chatId)
                        .orderByAsc(Talk::getTime)
        );
        return Result.ok(list);
    }

    @PostMapping("/send")
    public Result add(@RequestBody TalkDTO talkDTO) {
        Talk talk = new Talk();
        BeanUtil.copyProperties(talkDTO, talk);
        talk.setType(1);
        talk.setTime(LocalDateTime.now());
        talkService.save(talk);
        return Result.ok("发送成功");
    }

    @PostMapping("/answers")
    public Result answers(@RequestBody TalkDTO talkDTO) {
        String ans = talkService.answer(talkDTO);
        return Result.ok(ans);
    }

}
