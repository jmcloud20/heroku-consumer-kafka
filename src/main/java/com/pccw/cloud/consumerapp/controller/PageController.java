package com.pccw.cloud.consumerapp.controller;

import com.pccw.cloud.consumerapp.KafkaCloudListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletContext;

@Controller
public class PageController {

    private final ServletContext servletContext;

    public PageController(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @GetMapping("/")
    public String index(Model model) {
        this.setModel(model);
        return "index";
    }

    @GetMapping("/clear")
    public String clearContext(Model model) {
        servletContext.removeAttribute(KafkaCloudListener.CUSTOMER_OPT);
        servletContext.removeAttribute(KafkaCloudListener.UPDATE_EMAIL);
        servletContext.removeAttribute(KafkaCloudListener.PROD_OFFER);
        this.setModel(model);
        return "index";
    }

    private void setModel(Model model) {

        model.addAttribute(KafkaCloudListener.CUSTOMER_OPT, servletContext.getAttribute(KafkaCloudListener.CUSTOMER_OPT));
        model.addAttribute(KafkaCloudListener.UPDATE_EMAIL, servletContext.getAttribute(KafkaCloudListener.UPDATE_EMAIL));
        model.addAttribute(KafkaCloudListener.PROD_OFFER, servletContext.getAttribute(KafkaCloudListener.PROD_OFFER));
    }
}
