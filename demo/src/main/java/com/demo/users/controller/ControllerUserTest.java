package com.demo.users.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@RestController
public class ControllerUserTest {
    

    @GetMapping("/versionApi")
    @ResponseBody
    public String  loadVersionApi (){
        return  "1.0" ;
    }
    

}
