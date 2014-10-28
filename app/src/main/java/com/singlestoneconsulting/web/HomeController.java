package com.singlestoneconsulting.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public final class HomeController {

    @RequestMapping(value = "/", method = GET)
    public String index() {
        return "home";
    }
}
