package com.aman.crmsecurity.controllerAPI;

import com.aman.crmsecurity.dto.AuthResponseDTO;
import com.aman.crmsecurity.dto.LoginDTO;
import com.aman.crmsecurity.dto.RegisterDTO;
import com.aman.crmsecurity.service.AuthServices;
import exception.CrmCustomException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value =  "/auth")
public class AuthController {

    @Autowired
    private AuthServices authServices;

    @PostMapping(value = "/registration")
     public ResponseEntity<AuthResponseDTO>registration( @RequestBody @Valid RegisterDTO registerDTO)throws CrmCustomException{

         AuthResponseDTO authResponseDTO =  authServices.register(registerDTO);

         return new ResponseEntity<AuthResponseDTO>(authResponseDTO , HttpStatus.CREATED);
     }

     @PostMapping(value = "/login")
     public   ResponseEntity<AuthResponseDTO>Login(@Valid @RequestBody LoginDTO loginDTO) throws CrmCustomException{

        AuthResponseDTO authResponseDTO = authServices.login(loginDTO);

        return  new ResponseEntity<AuthResponseDTO>(authResponseDTO , HttpStatus.OK);
     }

     @GetMapping(value = "/admin/getprofile")
     public  ResponseEntity<String>forPrfileAPi(){
        String msg = "you successfully access the api";

        return  new ResponseEntity<String>(msg, HttpStatus.OK);
     }

    @GetMapping(value = "/manager/getprofile")
    public  ResponseEntity<String>userPrfileAPi(){
        String msg = "you successfully access the api";

        return  new ResponseEntity<String>(msg, HttpStatus.OK);
    }



}
