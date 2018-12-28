package com.sun.springwebsoket.controller;

import com.sun.springwebsoket.service.Mysoket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author ken
 * @date 2018/12/27  22:15
 * @description
 */
@Controller
public class Index {



    @RequestMapping("/")
    public String index()
    {

        return  "index.html";
    }

    @RequestMapping("push")
    @ResponseBody()
    public String push() throws IOException {

        return  "nuhao";
    }
}
