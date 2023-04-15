package uz.internal_affairs.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @RequestMapping("/test")
    public String test(){
        return "Hi, Ravshanbek!";
    }

}
