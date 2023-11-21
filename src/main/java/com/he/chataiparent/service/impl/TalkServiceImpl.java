package com.he.chataiparent.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.aliyun.broadscope.bailian.sdk.AccessTokenClient;
import com.aliyun.broadscope.bailian.sdk.ApplicationClient;
import com.aliyun.broadscope.bailian.sdk.models.BaiLianConfig;
import com.aliyun.broadscope.bailian.sdk.models.CompletionsRequest;
import com.aliyun.broadscope.bailian.sdk.models.CompletionsResponse;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.he.chataiparent.model.dto.TalkDTO;
import com.he.chataiparent.model.entity.Talk;
import com.he.chataiparent.service.TalkService;
import com.he.chataiparent.mapper.TalkMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
* @author he
* @description 针对表【talk】的数据库操作Service实现
* @createDate 2023-11-21 09:53:01
*/
@Service
public class TalkServiceImpl extends ServiceImpl<TalkMapper, Talk> implements TalkService {

    @Value("${chat.accessKeyId}")
    private String accessKeyId;
    @Value("${chat.accessKeySecret}")
    private String accessKeySecret;
    @Value("${chat.agentKey}")
    private String agentKey;
    @Value("${chat.appId}")
    private String appId;

    /**
     * 回答
     * @param talkDTO
     */
    @Override
    public String answer(TalkDTO talkDTO) {
        String session = talkDTO.getChatSession();

        AccessTokenClient accessTokenClient = new AccessTokenClient(accessKeyId, accessKeySecret, agentKey);
        String token = accessTokenClient.getToken();

        BaiLianConfig config = new BaiLianConfig()
                .setApiKey(token);

        CompletionsRequest request = new CompletionsRequest()
                .setAppId(appId)
                .setPrompt(talkDTO.getContent())
                .setSessionId(session);

        ApplicationClient client = new ApplicationClient(config);
        CompletionsResponse response = client.completions(request);

        String ans = response.getData().getText();

        // 保存到数据库
        Talk talk = new Talk();
        talk.setChatId(talkDTO.getChatId());
        talk.setType(2);
        talk.setTime(LocalDateTime.now());
        talk.setContent(ans);
        this.save(talk);

        return ans;
    }
}




