package com.aman.crmsecurity.service;

import com.aman.crmsecurity.dto.AuthResponseDTO;
import com.aman.crmsecurity.dto.LoginDTO;
import com.aman.crmsecurity.dto.RegisterDTO;
import exception.CrmCustomException;

public interface AuthServices {

     AuthResponseDTO register(RegisterDTO registerDTO) throws CrmCustomException;

    public AuthResponseDTO login(LoginDTO loginDTO) throws CrmCustomException;


}
