package com.he.chataiparent.model.vo;

import lombok.Data;

@Data
public class EditUserVO {

    private String nickname;
    private String username;
    private String oldPassword;
    private String newPassword;
    private String header;

}
