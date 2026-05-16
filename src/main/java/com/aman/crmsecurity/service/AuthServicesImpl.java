package com.aman.crmsecurity.service;

import com.aman.crmsecurity.dto.AuthResponseDTO;
import com.aman.crmsecurity.dto.LoginDTO;
import com.aman.crmsecurity.dto.RegisterDTO;
import com.aman.crmsecurity.entity.User;
import com.aman.crmsecurity.enums.Role;
import com.aman.crmsecurity.repository.UserRepository;
import com.aman.crmsecurity.security.JwtServices;
import exception.CrmCustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServicesImpl implements AuthServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    private JwtServices jwtServices;

    @Override
    public AuthResponseDTO login(LoginDTO loginDTO) throws CrmCustomException {

       Optional<User> userobj =  userRepository.findByEmailid(loginDTO.getEmail());

        User user = userobj.orElseThrow(() -> new CrmCustomException("user does not exist , please login with diffrent mail id."));

       authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

      String token =  jwtServices.genrateToken(user.getEmail() , user.getRole().name());

       AuthResponseDTO authResponseDTO =  new AuthResponseDTO();
       authResponseDTO.setToken(token);
       authResponseDTO.setRole(user.getRole().name());
       authResponseDTO.setMessage("the User Register the successfully");

        return authResponseDTO;
    }

    @Override
    public AuthResponseDTO register(RegisterDTO registerDTO) throws CrmCustomException {
        return null;
    }
}
