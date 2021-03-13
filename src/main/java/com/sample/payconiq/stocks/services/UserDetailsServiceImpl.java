package com.sample.payconiq.stocks.services;

import com.sample.payconiq.stocks.entity.Users;
import com.sample.payconiq.stocks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService{


    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<SimpleGrantedAuthority> roles ;
        Users user = userRepository.findByUserName(username);
        if (user != null) {
            if(!user.isEnabled())
            {
                throw new DisabledException("User account is disabled");
            }
            roles = Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
            return new User(user.getUserName(), user.getPassword(), roles);
        }
        throw new UsernameNotFoundException("User not found with the name " + username);
    }

}
