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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServicesImpl implements AuthServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtServices jwtServices;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDTO login(LoginDTO loginDTO) throws CrmCustomException {

       Optional<User> userobj =  userRepository.findByEmail(loginDTO.getEmail());

        User user = userobj.orElseThrow(() -> new CrmCustomException("user does not exist , please login with diffrent mail id."));

       authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

      String token =  jwtServices.genrateToken(user.getEmail() , user.getRole().name());

       AuthResponseDTO authResponseDTO =  new AuthResponseDTO();
       authResponseDTO.setToken(token);
       authResponseDTO.setRole(user.getRole().name());
       authResponseDTO.setMessage("the User Login  successfully");

        return authResponseDTO;
    }

    @Override
    public AuthResponseDTO register(RegisterDTO registerDTO) throws CrmCustomException {

      Optional<User> userobj = userRepository.findByEmail(registerDTO.getEmail());
//         User userfound = userobj.orElseThrow(()-> new CrmCustomException("this emailID is does not  exist please try with the diffrent email id"));
          if(userobj.isPresent()){
              throw  new CrmCustomException("this User is alrady present, please try with another email id");
          }
         User user = new User();



         user.setName(registerDTO.getName());
         user.setEmail(registerDTO.getEmail());
         user.setRole(Role.valueOf(registerDTO.getRole()));
         user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

         userRepository.save(user);

       String token =   jwtServices.genrateToken(user.getEmail(), user.getRole().name());

         AuthResponseDTO authResponseDTO = new AuthResponseDTO();

         authResponseDTO.setRole(user.getRole().name());
         authResponseDTO.setToken(token);
         authResponseDTO.setMessage("User Registerd Succefully");

        return authResponseDTO;
    }
}
