package com.he.chataiparent.model.dto;

import com.he.chataiparent.model.entity.Talk;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author he
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TalkDTO extends Talk {
    private String chatSession;
}
