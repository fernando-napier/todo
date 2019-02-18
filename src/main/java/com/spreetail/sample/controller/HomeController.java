package com.spreetail.sample.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HomeController {

    public HomeController() {
    }

    @RequestMapping("/home")
    public Map<String, Object> home() {
        System.out.println(new Date() + " ======= /home =======");
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "home");
        return model;
    }
}
