package com.example.zipreciever.controller;

import com.example.zipreciever.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Controller
public class MainController {

    @Autowired
    LogService logService;

    @GetMapping
    public String home(Model model){
        model.addAttribute("log", logService.getLog());
        return "home";
    }

    @RequestMapping(value = "/zip", produces = "application/zip")
    public ResponseEntity<StreamingResponseBody> zip() {
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment; filename=\"test.zip\"")
                .body(out -> logService.getLogsAsZip(out));
    }
}
