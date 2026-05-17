package com.aman.crmsecurity.service;

import com.aman.crmsecurity.entity.User;
import com.aman.crmsecurity.repository.UserRepository;
import exception.CrmCustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

      Optional<User> userobj =  userRepository.findByEmail(email);
      User user = userobj.orElseThrow(()->new UsernameNotFoundException("the user does not exist please try with diffrent id"));


        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getName())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
