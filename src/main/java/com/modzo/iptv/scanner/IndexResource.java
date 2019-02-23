package com.modzo.iptv.scanner;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexResource {
    @GetMapping(value = "/")
    public ModelAndView index(ModelMap model) {
        return new ModelAndView("redirect:/swagger-ui.html", model);
    }
}