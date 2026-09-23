package com.kaueadriano.novici.controller;

import com.kaueadriano.novici.dto.UserResponse;
import com.kaueadriano.novici.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserResponse> index(){
        return userService.listAll();
    }

    @GetMapping("/{id}")
    public UserResponse show(@PathVariable Integer id){
        return userService.listOne(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        userService.delete(id);
    }
}
