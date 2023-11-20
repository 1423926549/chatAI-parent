package com.he.chataiparent.controller;

import com.he.chataiparent.model.vo.EditUserVO;
import com.he.chataiparent.model.vo.UserVO;
import com.he.chataiparent.model.entity.User;
import com.he.chataiparent.model.result.Result;
import com.he.chataiparent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param userVo
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserVO userVo) {
        return userService.login(userVo);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        return userService.register(user);
    }

    /**
     * 修改用户
     * @param editUserVO
     * @return
     */
    @PutMapping("update")
    public Result updateUser(@RequestBody EditUserVO editUserVO) {
        return userService.updateUser(editUserVO);
    }

    /**
     * 校验密码
     * TODO 后续改进不需要从前端获取用户名，直接根据ThreadLocal中获取
     * @param userVO
     * @return
     */
    @PostMapping("checkPassword")
    public Result checkPassword(@RequestBody UserVO userVO) {
        return userService.checkPassword(userVO);
    }

}
