package com.he.chataiparent.controller;

import com.he.chataiparent.common.auth.AuthContextHolder;
import com.he.chataiparent.model.vo.EditUserVO;
import com.he.chataiparent.model.vo.UserVO;
import com.he.chataiparent.model.entity.User;
import com.he.chataiparent.common.result.Result;
import com.he.chataiparent.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param userVO
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserVO userVO, HttpServletRequest request) {
        return userService.login(userVO, request);
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
     * @param userVO
     * @return
     */
    @PostMapping("checkPassword")
    public Result checkPassword(@RequestBody UserVO userVO) {
        return userService.checkPassword(userVO);
    }

    @GetMapping("/isLogin")
    public Result isLogin() {
        return Result.ok(AuthContextHolder.getUserInfo());
    }

    @GetMapping("/logout")
    public Result logout() {
        userService.logout();
        return Result.ok("退出成功");
    }

}
