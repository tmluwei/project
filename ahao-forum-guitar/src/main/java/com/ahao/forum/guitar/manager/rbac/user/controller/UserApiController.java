package com.ahao.forum.guitar.manager.rbac.user.controller;

import com.ahao.core.entity.AjaxDTO;
import com.ahao.forum.guitar.manager.rbac.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class UserApiController {

    private UserService userService;

    @Autowired
    public UserApiController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/admin/user")
    public AjaxDTO getUser(@RequestParam("page") Integer page,
                           @RequestParam("search") String search){
        int pageSize = 10;
        return null;
    }
}
