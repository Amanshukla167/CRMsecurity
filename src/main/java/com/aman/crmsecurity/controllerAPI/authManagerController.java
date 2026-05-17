package com.aman.crmsecurity.controllerAPI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/manager")
public class authManagerController {

    @GetMapping(value = "/getprofile")
    public ResponseEntity<String> userMangerPrfileAPi(){
        String msg = "you successfully access the  manager api";

        return  new ResponseEntity<String>(msg, HttpStatus.OK);
    }

}
