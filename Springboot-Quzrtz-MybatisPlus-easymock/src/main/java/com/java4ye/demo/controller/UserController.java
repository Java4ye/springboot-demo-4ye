package com.java4ye.demo.controller;


import com.java4ye.demo.entity.User;
import com.java4ye.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Java4ye
 * @since 2021-01-05
 */
@RestController
public class UserController {
    @Autowired
    IUserService iUserService;

    @GetMapping("/list")
    public List<User> test(String message){
        List<User> list = iUserService.list();
        return list;
    }
}
